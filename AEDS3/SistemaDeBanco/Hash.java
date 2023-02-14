import java.io.IOError;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Hash{
    private RandomAccessFile pointers;
    private RandomAccessFile bucketFile;
    private int bucketSize;
    private int p;
    private final int bucketSizeBytes;

    Hash() throws IOException {
        this(4);
    }
    Hash(int bucketSize) throws IOException{
        this.bucketSize = bucketSize;
        this.p = 2;
        pointers = new RandomAccessFile("hash/hash.hsh", "rw");
        bucketFile = new RandomAccessFile("hash/bucket.hsh", "rw");

        bucketSizeBytes = bucketSize * (Integer.BYTES + Long.BYTES) + (2 * Integer.BYTES);
        if(this.isInitialized() == false){
            this.initFiles();
        }
        else{
            pointers.seek(0);
            this.p = pointers.readInt();
        }
    }

    //Adiciona um novo elemento no hash
    public void add(int index, long pointer){
        try {
            long arqPos = (index % (int)Math.pow(2, p)) * 8 + 4; //Pega a posição no arquivo de ponteiros, +4 pelo cabeçalho
            System.out.println("Hash result: "  + arqPos);
            pointers.seek(arqPos);
            long bucketPos = pointers.readLong(); //Pega a posição do bucket no arquivo de buckets
            bucketFile.seek(bucketPos);
            System.out.println("BucketPos: "  + bucketPos);
            int pLocal = bucketFile.readInt(); //Pega o ponteiro local
            int qtd = bucketFile.readInt(); //Pega a quantidade de elementos no bucket
            if(qtd < bucketSize){ //Se houver espaço no bucket, adiciona o elemento
                bucketFile.seek(bucketFile.getFilePointer() - Integer.BYTES);   //volta o ponteiro para incrementar qtd
                bucketFile.writeInt(qtd + 1);
                //Caminha pelo bucket procurando uma posição vazia
                for (int i = 0; i < bucketSize; i++) {
                    int chave = bucketFile.readInt();   //Lê a chave
                    long ponteiro = bucketFile.readLong();  //Lê o ponteiro
                    if(chave == -1){
                        //Se a chave for -1, significa que é uma posição vazia, então volta para
                        //escrever a chave e o ponteiro
                        bucketFile.seek(bucketFile.getFilePointer() - (Integer.BYTES + Long.BYTES));
                        System.out.println("Inserindo " + index + " de ponteiro " + pointer + " na posição " + bucketFile.getFilePointer());
                        bucketFile.writeInt(index); //Escreve a chave
                        bucketFile.writeLong(pointer);  //Escreve o ponteiro
                        i = bucketSize; //sai do loop
                    }
                }
                        
            }
            else{
                //Se nao houver espaço, deve criar um novo bucket, atualizar o ponteiro, o P global e o local
                if(pLocal == p){
                    System.out.println("Bucket cheio e p igual");
                    //Caso o bucket esteja lotado e o P for igual ao global, estourar o hash
                    p++;
                    pointers.setLength((int)Math.pow(2, p) * Long.BYTES);  //Aumenta o tamanho do arquivo de ponteiros
                    pointers.seek(0);
                    pointers.writeInt(p);   //Atualiza o P global
                    int repeat = (int)Math.pow(2, p);
                    for (int i = 0; i < repeat/2; i++) {
                        pointers.writeLong(i * bucketSizeBytes); //Atualiza os ponteiros
                    }
                    //Reaponta as duas metades do arquivo de ponteiros
                    for (int i = 0; i < repeat/2; i++) {
                        pointers.writeLong(i * bucketSizeBytes); //Atualiza os ponteiros
                    }

                    System.out.println("reapontou");
                    //Cria um novo bucket
                    bucketFile.setLength(bucketFile.length() + bucketSizeBytes); //Aumenta o tamanho do arquivo em um bucket
                    arqPos = (index % (int)Math.pow(2, p-1)) * 8 + 4 + ((int)Math.pow(2, p-1) * Long.BYTES);
                    pointers.seek(arqPos);  //Vai para a posição do ponteiro que sera reapontado
                    pointers.writeLong(bucketFile.length() - bucketSizeBytes); //Atualiza o ponteiro
                    //Remove os elementos do bucket antigo
                    int[] chavesTmp = new int[bucketSize];
                    long[] ponteirosTmp = new long[bucketSize];
                    bucketFile.seek(bucketPos);
                    bucketFile.writeInt(pLocal + 1);    //Atualiza o P local
                    bucketFile.writeInt(0); //Zera a quantidade de elementos
                    for (int i = 0; i < bucketSize; i++) {
                        chavesTmp[i] = bucketFile.readInt();
                        ponteirosTmp[i] = bucketFile.readLong();
                        bucketFile.seek(bucketFile.getFilePointer() - (Integer.BYTES + Long.BYTES));
                        bucketFile.writeInt(-1);
                        bucketFile.writeLong(-1);
                    }
                    //Configura o novo bucket
                    bucketFile.seek(bucketFile.length() - bucketSizeBytes); //Posiciona no inicio do novo bucket
                    bucketFile.writeInt(pLocal + 1);    //Atualiza o P local
                    bucketFile.writeInt(0); //Zera a quantidade de elementos
                    for (int i = 0; i < bucketSize; i++) {
                        bucketFile.writeInt(-1);
                        bucketFile.writeLong(-1);
                    }
                    //Reinsere os elementos no novo bucket
                    for (int i = 0; i < bucketSize; i++) {
                        if(chavesTmp[i] != -1){
                            add(chavesTmp[i], ponteirosTmp[i]);
                        }
                    }
                    //Reinsere o elemento que não coube no bucket
                    add(index, pointer);
                }
                else{
                    //Se o P for diferente do global, criar um novo bucket e atualizar o ponteiro
                    System.out.println("Bucket cheio e p diferente");
                    //Cria um novo bucket
                    bucketFile.setLength(bucketFile.length() + bucketSizeBytes); //Aumenta o tamanho do arquivo em um bucket
                    arqPos = (index % (int)Math.pow(2, p-1)) * 8 + 4 + ((int)Math.pow(2, p-1) * Long.BYTES);
                    pointers.seek(arqPos);  //Vai para a posição do ponteiro que sera reapontado
                    pointers.writeLong(bucketFile.length() - bucketSizeBytes); //Atualiza o ponteiro
                    //Remove os elementos do bucket antigo
                    int[] chavesTmp = new int[bucketSize];
                    long[] ponteirosTmp = new long[bucketSize];
                    bucketFile.seek(bucketPos);
                    bucketFile.writeInt(pLocal + 1);    //Atualiza o P local
                    bucketFile.writeInt(0); //Zera a quantidade de elementos
                    for (int i = 0; i < bucketSize; i++) {
                        chavesTmp[i] = bucketFile.readInt();
                        ponteirosTmp[i] = bucketFile.readLong();
                        bucketFile.seek(bucketFile.getFilePointer() - (Integer.BYTES + Long.BYTES));
                        bucketFile.writeInt(-1);
                        bucketFile.writeLong(-1);
                    }
                    //Configura o novo bucket
                    bucketFile.seek(bucketFile.length() - bucketSizeBytes); //Posiciona no inicio do novo bucket
                    bucketFile.writeInt(pLocal + 1);    //Atualiza o P local
                    bucketFile.writeInt(0); //Zera a quantidade de elementos
                    for (int i = 0; i < bucketSize; i++) {
                        bucketFile.writeInt(-1);
                        bucketFile.writeLong(-1);
                    }
                    //Reinsere os elementos no novo bucket
                    for (int i = 0; i < bucketSize; i++) {
                        if(chavesTmp[i] != -1){
                            add(chavesTmp[i], ponteirosTmp[i]);
                        }
                    }
                    //Reinsere o elemento que não coube no bucket
                    add(index, pointer);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public long remove(int index){
        long pointer = -1;
        try {
            //Procura a posição no arquivo de ponteiros
            int arqPos = (index % (int)Math.pow(2, p)) * 8 + 4;
            pointers.seek(arqPos);
            long bucketPos = pointers.readLong();
            //Vai para o bucket apontado
            bucketFile.seek(bucketPos);
            bucketFile.readInt();   //Pula o P local
            int qtd = bucketFile.readInt(); //Pega a quantidade de elementos existentes
            int lidos = 0;
            bucketFile.seek(bucketFile.getFilePointer() - Integer.BYTES); //Volta para a posição da quantidade de elementos
            bucketFile.writeInt(qtd - 1);   //Atualiza a quantidade de elementos
            for(int i = 0; i < qtd; i++){
                int chave = bucketFile.readInt();   //Pega a chave
                long ponteiro = bucketFile.readLong();  //Pega o ponteiro
                lidos++;
                //Caso encontre o index, deverá remover e fazer um shift
                if(chave == index){
                    pointer = ponteiro;     //Guarda o ponteiro para retornar
                    //Faz o shift
                    for (int j = 0; j < bucketSize - lidos; j++) {
                        //Lê o próximo conjunto
                        int tmpChave = bucketFile.readInt();
                        long tmpPonteiro = bucketFile.readLong();
                        //Volta para o conjunto anterior
                        bucketFile.seek(bucketFile.getFilePointer() - 2 * (Integer.BYTES + Long.BYTES));
                        //Sobrescreve o conjunto anterios
                        bucketFile.writeInt(tmpChave);
                        bucketFile.writeLong(tmpPonteiro);
                        //Pula para o próximo conjunto
                        bucketFile.skipBytes(Integer.BYTES + Long.BYTES);
                    }
                    i = qtd;    //Para o loop

                }
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return pointer;
    }

    public void update(int index, long newPointer){
        try {
            int arqPos = (index % (int)Math.pow(2, p)) * 8 + 4; //Procura a posição no arquivo de ponteiros
            pointers.seek(arqPos);
            long bucketPos = pointers.readLong();   //Pega o ponteiro para o bucket
            bucketFile.seek(bucketPos); //Vai para o bucket
            bucketFile.readInt();   //Pula o P local
            int qtd = bucketFile.readInt(); //Pega a quantidade de elementos existentes
            for(int i = 0; i < qtd; i++){
                int chave = bucketFile.readInt();   //Pega a chave
                long ponteiro = bucketFile.readLong();  //Pega o ponteiro
                if(chave == index){
                    bucketFile.seek(bucketFile.getFilePointer() - Long.BYTES); //Volta para o ponteiro
                    bucketFile.writeLong(newPointer);   //Atualiza o ponteiro
                    i = qtd;    //Para o loop
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getPointer(int index){
        long pointer = -1;
        try {
            int arqPos = (index % (int)Math.pow(2, p)) * 8 + 4; //Posição que será lida no arquivo de ponteiros
            pointers.seek(arqPos);  //Posiciona no local calculado
            long bucketPos = pointers.readLong();   //Pega o ponteiro para o bucket
            bucketFile.seek(bucketPos); //Posiciona no bucket
            bucketFile.readInt();   //Pula o P local
            int qtd = bucketFile.readInt(); //Pega a quantidade de elementos existentes
            for(int i = 0; i < qtd; i++){
                int chave = bucketFile.readInt();   //Pega a chave
                long ponteiro = bucketFile.readLong();  //Pega o ponteiro
                if(chave == index){
                    pointer = ponteiro;     //Guarda o ponteiro para retornar
                    i = qtd;    //Para o loop
                }
            }
        } catch (Exception e) {
            // TODO: handle exception]
            e.printStackTrace();
        }

        return pointer;
    }

    public void print(){
        try {
            //Reposiciona o ponteiro para o inicio dos arquivos
            pointers.seek(0);
            bucketFile.seek(0);
            System.out.println("PGlobal: " + pointers.readInt());   //Imprime o valor de PGlobal
            int repeat = (int)Math.pow(2, p);   //Calcula o numero de linhas
            for (int i = 0; i < repeat; i++) {
                System.out.println(i + " | " + pointers.readLong() + " |");     //Imprime cada linha dos ponteiros
            }
            while(bucketFile.getFilePointer() < bucketFile.length()){
                System.out.print(bucketFile.getFilePointer() + "\t: PLocal: " + bucketFile.readInt() + " | Qtd: " + bucketFile.readInt() + " | ");
                for (int i = 0; i < bucketSize; i++) {
                    System.out.print("C: " + bucketFile.readInt() + ", P: " + bucketFile.readLong() + " | ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void initFiles() throws IOException {
        p = 2;
        pointers.setLength((int)Math.pow(2, p) * Long.BYTES);  

        //Seta o tamanho mínimo do arquivo {PLocal|qtdDeElementos|elementos - chave...|}
        bucketFile.setLength(bucketSizeBytes * (int)Math.pow(2, p));
        
        //Escreve os valores inicias para ambos os arquivos
        bucketFile.seek(0);
        pointers.seek(0);
        pointers.writeInt(p);
        int repeat = (int)Math.pow(2, p);   //Calcula quantas linhas irá escrever
        for (int i = 0; i < repeat; i++) {
            pointers.writeLong(bucketFile.getFilePointer());    //Escreve o ponteiro para o arquivo de buckets
            bucketFile.writeInt(p); //Escreve o valor de PLocal
            bucketFile.writeInt(0); //Escreve a quantidade de elementos
            //Escreve os elementos e ponteiros iniciais, = -1
            for (int j = 0; j < bucketSize; j++) {
                bucketFile.writeInt(-1);
                bucketFile.writeLong(-1);
            }
        }
    }

    //Retorna se os arquivos já existia ou se foi recem criado
    public boolean isInitialized() throws IOException {
        return pointers.length() > 0 && bucketFile.length() > 0;
    }
}