import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

/**
 * Ordenacao
 * OBS: ESTOU USANDO OS MARCADORES , PARA SEPARAR REGISTROS E | PARA SEPARAR MEMORIA, ATENÇÃO E MUDAR ISSO
 */
public class Ordenacao {

    private int caminhos;
    private int segmentoSize;
    private int qtdUser;
    RandomAccessFile[] aux;

    Ordenacao(int caminhos, int segmentoSize){
        this.caminhos = caminhos;
        this.segmentoSize = segmentoSize;
        this.aux = new RandomAccessFile[caminhos];
    }


    
    public CRUD intercalacaoBalanceada(CRUD crud) throws IOException{
        crud.close();
        
        RandomAccessFile arqOrig = new RandomAccessFile(crud.getPath(), "rw");
        distribuicao(arqOrig);
        intercalacao(arqOrig);
        
        //Fecha todos os arquivos auxiliares
        for (int i = 0; i < aux.length; i++) {
            aux[i].close();
            File f1 = new File("tmp/aux" + i + ".tmp");
            File f2 = new File("tmp/aux2M" + i + ".tmp");
            f1.delete();
            f2.delete();
        }
        return new CRUD(crud.getPath());
    }

    private void distribuicao(RandomAccessFile arqOrig) throws IOException{
        User[] us;
        int indexAux = 0;
        try {
            for (int i = 0; i < aux.length; i++) {
                aux[i] = new RandomAccessFile("tmp/aux" + i + ".tmp", "rw");
                aux[i].writeInt(0);     //Quantidade de itens no arquivo
            }
        } catch (Exception e) {
            System.err.println("Erro ao criar arquivos auxiliares");
        }
        
        //Inicia a varredura do arquivo
        int qtdReg, lastId, qtdDel;
        arqOrig.seek(0);
        qtdUser = arqOrig.readInt();
        qtdReg = arqOrig.readInt();
        lastId = arqOrig.readInt();
        qtdDel = arqOrig.readInt();

        //Varre o arquivo para a quantidade de usuários que existem
        for (int i = 0; i < qtdUser;) {
            //Cria um vetor que terá o tamanho minimo entre o tamanho da memória ou a quantidade de arquivos restantes
            us = new User[Math.min(qtdUser-i, segmentoSize)];
            //Lê os usuarios e salva no vetor
            for (int j = 0; j < us.length;) {
                //Definição das variáveis
                char lapide;
                int size;
                User u = null;
                try {
                    u = new User();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                lapide = arqOrig.readChar();    //Leitura do valor da lapide
                size = arqOrig.readInt();   //Leitura do tamanho do registro em bytes
                //Apenas adiciona caso não tenha sido deletado
                if(lapide != '*'){
                    byte[] ba = new byte[size];
                    arqOrig.read(ba);
                    u.fromByteArray(ba);
                    //Le o usuario ja que nao esta deletado
                    us[j] = u;  //Salva no vetor
                    j++;    //Incrementa a posição do vetor
                    i++;    //Incrementa em 1 o número de usuários acicionados
                }
                else{
                    //Caso tenha sido deletado, apenas sobrescreve o número de bytes
                    arqOrig.skipBytes(size);
                }
            }
            //Ordena o vetor (otimizar algorítmo de sorting)
            sort(us);
            
            //Escreve no vetor o arquivo distribuido
            for (int j = 0; j < us.length; j++) {
                byte[] ba = us[j].toByteArray();
                //indexAux % aux.length pois irá incrementar o valor de indexAux e deverá circular no vetor de arq.temp
                aux[indexAux % aux.length].writeInt(ba.length); //Escreve o tamanho do registro
                aux[indexAux % aux.length].write(ba);   //Escreve o registro
            }
            //incrementa a quantidade de itens no arquivo
            aux[indexAux % aux.length].seek(0);
            int qtd = aux[indexAux % aux.length].readInt();
            aux[indexAux % aux.length].seek(0); //volta para o inicio do arquivo para escrever
            aux[indexAux % aux.length].writeInt(qtd + us.length);    //incrementa a quantidade de itens no arquivo
            //volta o ponteiro para o final do arquivo
            aux[indexAux % aux.length].seek(aux[indexAux % aux.length].length());
            indexAux++;
        }

    }

    public void intercalacao(RandomAccessFile raf) throws IOException{
        int indexAux = 0;   //Index do arquivo
        int segmentoSizeAtual = segmentoSize;  //Até qual item pode ser lido no momento
        int[] controleSegmentos = new int[caminhos];    //Vetor para controle de barraira dos segmentos
        //Todos os 2M arquivos auxiliares
        RandomAccessFile[] aux = new RandomAccessFile[caminhos];
        RandomAccessFile[] aux2M = new RandomAccessFile[caminhos];
        //Cria os arquivos auxiliares
        for (int i = 0; i < aux.length; i++) {
            try {
                aux[i] = new RandomAccessFile("tmp/aux" + i + ".tmp", "rw");
                aux2M[i] = new RandomAccessFile("tmp/aux2M" + i + ".tmp", "rw");
                aux2M[i].writeInt(0); //Salva quantos itens possui
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Recupera quantos itens há no total
        int qtdTotalReg = 0;
        for (int i = 0; i < aux.length; i++) {
            aux[i].seek(0);
            qtdTotalReg += aux[i].readInt();    //Soma no total de registros
        }
        
        boolean sair = false;
        //System.out.println("Segmento size Atual: " + segmentoSizeAtual);
        while(sair == false){
            for(int i = 0; i < qtdTotalReg; i++){
                //Recupera o menor usuario dentre os primeiros de cada arquivo
                User menorUser = pegaMenor(aux,controleSegmentos, segmentoSizeAtual);
                //System.out.println("passada: " + i + " ");
                //Escreve o user recuperado no arquivo
                gravaUser(aux2M[indexAux % aux2M.length], menorUser, (indexAux % aux2M.length));

                //Caso todos os arquivos tenham colidido, aumenta o tamanho do segmento para abrangir os proximos
                //E troca de arquivo
                if(todosColidiram(controleSegmentos, segmentoSizeAtual)){
                    segmentoSizeAtual += segmentoSize;
                    indexAux++;
                    //System.out.println("Aumentou Atual: " + segmentoSizeAtual +  " real: " + segmentoSize);
                }
            }
            
            segmentoSize *= caminhos;  //Dobra o tamanho do segmento para próxima passada
            segmentoSizeAtual = segmentoSize;
            //System.out.println("aumentou segmento maximo: " + segmentoSize);
            indexAux = 0;
            //Zera o controle de segmentos para proxima passada
            for(int k = 0; k < controleSegmentos.length; k++){
                controleSegmentos[k] = 0;   
            }

            //Faz o swap dos arquivos auxiliares
            RandomAccessFile[] auxSwap = aux;
            aux = aux2M;
            aux2M = auxSwap;

            //Reseta as posições dos arquivos auxiliares e reseta os arquivos 2M
            for(int k = 0; k < aux.length; k++){
                aux[k].seek(4);     //Posiciona após a quantidade de users
                aux2M[k].setLength(0);  //Apaga todo o arquivo
                aux2M[k].seek(0);
                aux2M[k].writeInt(0);
            }

            //Verifica se há apenas 1 arquivo com itens, mostrando que acabou
            int qtdArquivosComItens = 0;
            for(int k = 0; k < aux.length; k++){
                aux[k].seek(0); //Posiciona o ponteiro no inicio do arquivo para a leitura
                if(aux[k].readInt() > 0){   //Contabiliza quantos usuarios existem no arquivo
                    qtdArquivosComItens++;
                }
            }

            //Caso haja apenas 1 arquivo com usuarios, sair do loop 
            if(qtdArquivosComItens == 1){
                sair = true;
                ////System.out.println("Acabou");
            }
        }

        //Atualiza o cabeçalho do arquivo original
        raf.seek(0);    //Posiciona no inicio
        raf.writeInt(qtdTotalReg);  //Escreve quantidade de usuarios
        raf.writeInt(qtdTotalReg);  //Escreve quantidade de usuarios (qtdReg == qtdUser pq nao foram incluidos os deletados)
        raf.skipBytes(4);   //Pula o último id
        raf.writeInt(0);    //Escreve 0 deletados
        raf.setLength(raf.getFilePointer());
        //Escreve o conteúdo do único arquivo com users no arquivo original
        aux[indexAux % aux.length].seek(4);     //Posiciona no inicio do arquivo
        //Salva byte a byte do arquivo auxiliar
        while(aux[indexAux % aux.length].getFilePointer() < aux[indexAux % aux.length].length()){
            byte[] ba;
            int size = aux[indexAux % aux.length].readInt();
            ba = new byte[size];
            aux[indexAux % aux.length].read(ba);
            raf.writeChar('%');
            raf.writeInt(ba.length);
            raf.write(ba);
        }

        //Fecha os arquivos auxiliares
        for (int k = 0; k < aux.length; k++) {
            try {
                aux[k].close();
                aux2M[k].close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void gravaUser(RandomAccessFile raf, User u, int i) throws IOException{
        if(u != null && u.getIdConta() != Integer.MAX_VALUE){
            //System.out.println("Escrevendo: " + u.getIdConta() + " no arquivo: " + i + " posicao: " + raf.getFilePointer());
            //Salva o user em bytes
            byte[] bytes = u.toByteArray();
            raf.writeInt(bytes.length);
            raf.write(bytes);

            long pos = raf.getFilePointer();    //Salva a posição atual
            raf.seek(0);    //vai para o inicio do arquivo
            int qtd = raf.readInt();    //le a quantidade atual de registros
            raf.seek(0);    //volta ao inicio para salvar a nova quantidade
            raf.writeInt(qtd + 1);  //Salva a quantidade incrementada de usuários
            raf.seek(pos);  //retorna a posição anterior
        }
    }
  
    public User pegaMenor(RandomAccessFile[] aux, int[] controleSegmentos, int segmentoSizeAtual){
        User menorUser = null;  //usuarios q será retornado
        int indexMenor = -1;
        long[] posArq = new long[aux.length];   //Posição de cada arquivo antes da leitura
        User[] users = new User[aux.length];    //Lista dos usuários
        //Salva a posição dos arquivos antes das leituras
        for (int i = 0; i < aux.length; i++) {
            try {
                posArq[i] = aux[i].getFilePointer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Lê os usuários dos arquivos
        for (int i = 0; i < aux.length; i++) {
            try {
                //Adiciona apenas se o segmento não estiver cheio
                if(aux[i].getFilePointer() < aux[i].length() && controleSegmentos[i] < segmentoSizeAtual){
                    int size = aux[i].readInt();
                    byte[] ba = new byte[size];
                    User tmp = new User();
                    aux[i].read(ba);
                    tmp.fromByteArray(ba);
                    users[i] = tmp;
                    tmp = null;
                }
                else{
                    User tmp = new User();
                    tmp.setIdConta(Integer.MAX_VALUE);
                    users[i] = tmp;
                    tmp = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Pega o menor user
        indexMenor = 0;
        for (int i = 0; i < users.length; i++) {
            if(controleSegmentos[i] < segmentoSizeAtual){
                if(users[indexMenor].getIdConta() > users[i].getIdConta()){
                    indexMenor = i;
                }
            }
        }

        //volta as posições anteriores dos arquivos
        for (int i = 0; i < aux.length; i++) {
            try {
                if(i != indexMenor){
                    aux[i].seek(posArq[i]);
                }
                else{
                    controleSegmentos[i]++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        menorUser = users[indexMenor];
        ////System.out.println("Selecionou: " + menorUser.toString());
        return menorUser;

    }

    //metodo que verifica se todos ja colidiram
    public boolean todosColidiram(int[] controleSegmentos, int segmentoSize){
        boolean rsp = true;
        for (int i = 0; i < controleSegmentos.length; i++) {
            if(controleSegmentos[i] < segmentoSize){
                rsp = false;
            }
        }
        return rsp;
    }

    public void sort(User[] us){
        for (int i = 0; i < us.length-1; i++) {
            int menor = i;
            for (int j = i+1; j < us.length; j++) {
                if(compUsers(us,menor,j) > 0){
                    menor = j;
                }
            }
            swap(us,menor,i);
        }
        
    }

    //Método que compara dois indices de uma lista de usuarios, retornando 0 se iguais, -1 se i < j e 1 de i > J
    private int compUsers(User[] us, int i, int j){
        int rsp = 0;
        if(us[i].getIdConta() < us[j].getIdConta()){
            rsp = -1;
        }
        else if(us[i].getIdConta() > us[j].getIdConta()){
            rsp = 1;
        }
        return rsp;
    }

    private void swap(User[] us, int i, int j){
        User tmp = us[i];
        us[i] = us[j];
        us[j] = tmp;
    }

}