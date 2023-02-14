import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.io.IOException;

public class ReverseIndex {
    RandomAccessFile lista;
    RandomAccessFile itens;
    ArrayList<String> camposExistente;  // campos existentes no arquivo de lista


    ReverseIndex(String campo){
        try{
            lista = new RandomAccessFile("reverseIndex/" + campo + "Lista.rix", "rw");
            itens = new RandomAccessFile("reverseIndex/" + campo + "Itens.rix", "rw");
            camposExistente = new ArrayList<String>();

            while(lista.getFilePointer() < lista.length()){
                String tmp = lista.readUTF();
                lista.readInt();
                lista.readLong();
                camposExistente.add(tmp);
            }
        } catch(IOException e){
            System.out.println("Erro ao abrir os arquivos");
        }
    }

    public void add(String elemento, long pointer){
        try {
            if(camposExistente.contains(elemento)){
                lista.seek(0);
                String tmp = lista.readUTF();
                int qtd = lista.readInt();
                long pos = lista.readLong();
                //Percorre a lista em busca  do elemento pesquisado
                while(lista.getFilePointer() < lista.length() && !tmp.equals(elemento)){
                    tmp = lista.readUTF();
                    qtd = lista.readInt();
                    pos = lista.readLong();
                }
                lista.seek(lista.getFilePointer() - (Long.BYTES + Integer.BYTES));
                lista.writeInt(qtd + 1);
                for(int i = 0; i < qtd; i++){
                    itens.seek(pos);
                    itens.readLong();       //Salta o ponteiro para o arquivo de dados
                    pos = itens.readLong(); //Pega o ponteiro para o próximo elemento
                }
                //Retorna 1 long para sobrescrever com o ponteiro para o proximo
                itens.seek(itens.getFilePointer() - Long.BYTES);
                itens.writeLong(itens.length());
            }
            else{
                camposExistente.add(elemento);
                lista.seek(lista.length());
                lista.writeUTF(elemento);   //Escreve o novo elemento
                lista.writeInt(1);  //Inicializa como 1 para já adicionar
                lista.writeLong(itens.length());    //Irá inserir no final do arquivo de itens
                System.out.println("Adicinou " + elemento +  " na lista");

            }
            //Inserção efetiva no arquivo de itens
            itens.seek(itens.length());     //Salta para o final do arquivo
            itens.writeLong(pointer);   //Escreve o ponteiro do arquivo de dados
            itens.writeLong(-1);        //Escreve -1 como próximo pois não existirá a priori

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ArrayList<Long> getAll(String elemento){
        ArrayList<Long> rsp = new ArrayList<Long>();
        if(camposExistente.contains(elemento)){
            try {
                lista.seek(0);
                String tmp = lista.readUTF();
                int qtd = lista.readInt();
                long pos = lista.readLong();
                //Percorre a lista em busca  do elemento pesquisado
                while(lista.getFilePointer() < lista.length() && !tmp.equals(elemento)){
                    tmp = lista.readUTF();
                    qtd = lista.readInt();
                    pos = lista.readLong();
                }
                //Percorre a lista de itens
                for(int i = 0; i < qtd; i++){
                    itens.seek(pos);
                    rsp.add(itens.readLong());      //Adiciona o ponteiro para o arquivo de dados
                    pos = itens.readLong(); //Pega o ponteiro para o próximo elemento
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            System.out.println("Elemento não encontrado");
        }
        return rsp;
    }

    public long removeRecursivo(long pointerItem, long pointerIndex){
        long rsp = -1;
        try {
            if(itens.getFilePointer() < itens.length() && pointerItem != -1){
                itens.seek(pointerItem);
                long chave = itens.readLong();
                rsp = itens.readLong();
                System.out.println("Chave: " + chave);
                if(chave == pointerIndex){  //Encontrou
                    itens.seek(itens.getFilePointer() - Long.BYTES);
                    itens.writeLong(-1);
                }
                else{
                    long preRecurPointer = itens.getFilePointer() - Long.BYTES;
                    long proxPointer = removeRecursivo(rsp, pointerIndex);
                    itens.seek(preRecurPointer);
                    itens.writeLong(proxPointer);
                    rsp = itens.getFilePointer() - 2 * Long.BYTES;
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return rsp;
    }

    public void remove(String elemento, long pointer){
        if(camposExistente.contains(elemento)){
            try {
                lista.seek(0);
                String tmp = lista.readUTF();
                int qtd = lista.readInt();
                long pos = lista.readLong();
                //Percorre a lista em busca  do elemento pesquisado
                while(lista.getFilePointer() < lista.length() && !tmp.equals(elemento)){
                    tmp = lista.readUTF();
                    qtd = lista.readInt();
                    pos = lista.readLong();
                }
                //Chama a recursão para remover o elemento
                itens.seek(0);
                long newPosition = removeRecursivo(pos, pointer);
                lista.seek(lista.getFilePointer() - (Long.BYTES + Integer.BYTES));
                lista.writeInt(qtd - 1);
                lista.writeLong(newPosition);
            } catch (Exception e) {
                System.err.println("Deu ruim antes da recursão");
            }
        }
    }

    public void update(String elemento, long item, long newPointer){
        if(camposExistente.contains(elemento)){
            try {
                lista.seek(0);
                String tmp = lista.readUTF();
                int qtd = lista.readInt();
                long pos = lista.readLong();
                //Percorre a lista em busca  do elemento pesquisado
                while(lista.getFilePointer() < lista.length() && !tmp.equals(elemento)){
                    tmp = lista.readUTF();
                    qtd = lista.readInt();
                    pos = lista.readLong();
                }
                //Percorre a lista de itens
                for(int i = 0; i < qtd; i++){
                    itens.seek(pos);
                    long chave = itens.readLong();
                    long prox = itens.readLong();
                    if(chave == item){
                        itens.seek(itens.getFilePointer() - (2 * Long.BYTES));
                        itens.writeLong(newPointer);
                        i = qtd;   //Para o loop
                    }
                    pos = prox;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void close(){
        try {
            lista.close();
            itens.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public void deleteAll(){
        try {
            lista.setLength(0);
            itens.setLength(0);
            camposExistente.clear();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
