import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class CRUD {
    private RandomAccessFile arq;
    private int quantUser;
    private int quantRegister;
    private int lastId;
    private int quantDel;
    private long initPointer;   //Armazena qual o endereço inicial para a leitura dos registros/fim do cabeçalho
    private String path;
    private BPlusTree tree;

    CRUD(String path) throws FileNotFoundException, IOException{
        this.path = path;
        tree = null;
        arq = new RandomAccessFile(path, "rw");
        if(arq.length() == 0){
            quantUser = 0;
            quantRegister = 0;
            lastId = 0;
            quantDel = 0;
            arq.seek(0);
            arq.writeInt(quantUser);
            arq.writeInt(quantRegister);
            arq.writeInt(lastId);
            arq.writeInt(quantDel);
            initPointer = arq.getFilePointer();
        }
        else{
            quantUser = arq.readInt();  //Lê quantos usuários estão presentes
            quantRegister = arq.readInt();  //Lê a quantidade de registros que tem, com ou sem lápide
            lastId = arq.readInt(); //Lê qual o último ID que foi inserido
            quantDel = arq.readInt(); //Lê qual o último ID que foi inserido
            initPointer = arq.getFilePointer(); //Salva o endereço atual, ou seja, do inicio dos registros
        }
        //Cria e preenche a árvore B+ com os registros
        this.CreateAndFillTree();
        
    }

    public String getPath(){
        return path;
    }

    public void close() throws IOException{
        arq.close();
    }

    public void atualizaCabecalho(){
        try {
            arq.seek(0);
            arq.writeInt(quantUser);
            arq.writeInt(quantRegister);
            arq.writeInt(lastId);
            arq.writeInt(quantDel);
        } catch (Exception e) {
            System.err.println("Nao foi possivel atualizar o cabeçalho");
        }
        
    }

    //Insere um registro no arquivo
    public long insert(User u) throws Exception{
        long rsp = -1;
        try{
            int erro = checaUser(u);
            if(erro == 1){  //Lança 1 caso seja o username
                throw new IllegalArgumentException("Nome de usuario ja existente");
            }
            else if(erro == 2){
                throw new IllegalArgumentException("CPF ja existente");
            }
            lastId++;
            u.setIdConta(lastId);   //Escreve o ID que será incremental
            byte[] ba = u.toByteArray();    //Recupera o objeto em formato de array de bytes
            rsp = arq.length(); //Salva o endereço do registro
            arq.seek(arq.length()); //Posiciona o ponteiro no final do arquivo
            arq.writeChar('%'); //Escreve a lápide como não deletado
            arq.writeInt(ba.length);    //Escreve o tamanho do arquivo em bytes
            arq.write(ba);  //escreve os bytes do registro
            quantUser++;    //Incrementa a quantidade de usuários
            quantRegister++;
            atualizaCabecalho();
            CreateAndFillTree();
        }
        catch(IOException ioe){
            rsp = -1;
            System.err.println("Erro ao escrever o user : " + u.getNomePessoa() + " erro: " + ioe.getMessage());
        }
        return rsp;
    }

    public boolean delete(long pointer){
        boolean sucess = false;
        try{
            arq.seek(pointer);
            arq.writeChar('*'); //Escreve a lápide como deletado
            quantUser--;
            quantDel++;
            atualizaCabecalho();
            CreateAndFillTree();
            sucess = true;
        }
        catch(IOException ioe){
            System.err.println("Erro ao deletar o user : " + ioe.getMessage());
        }
        return sucess;
    }

    public boolean delete(int id){
        User u;
        boolean rsp = false;
        try {
            long lapidePos;
            char lapideMark;
            int regLen;
            u = new User();
            arq.seek(initPointer);
            lapidePos = arq.getFilePointer();
            lapideMark = arq.readChar();
            regLen = arq.readInt();
            u = readUser(arq.getFilePointer(),regLen);
            while (u != null && u.getIdConta() != id || lapideMark == '*') {
                System.out.println(u.toString());
                lapidePos = arq.getFilePointer();
                lapideMark = arq.readChar();
                regLen = arq.readInt();
                u = readUser(arq.getFilePointer(),regLen);
            }
            if (u != null) {
                arq.seek(lapidePos);
                arq.writeChar('*');
                quantDel++;
                quantUser--;
                atualizaCabecalho();
                rsp = true;
            }
            CreateAndFillTree();
        } catch (Exception e) {
            System.err.println("Erro ao deletar usuario: " + id);
        }
        return rsp;
    }

    public long update(User u, long pointer){
        long retPointer = pointer;
        byte[] ba = u.toByteArray();
        try {
            arq.seek(pointer);
            char lapide = arq.readChar();   //Lê a lápide
            int regLen = arq.readInt();     //Lê o tamanho do registro
            //Se o tamanho for diferente do anterior, reposiciona no final do arquivo e escreve a lapide e o tamanho
            if(ba.length != regLen){
                arq.seek(arq.getFilePointer() - (Character.BYTES + Integer.BYTES)); //Volta o ponteiro para o inicio do registro
                arq.writeChar('*'); //Escreve a lápide como deletado
                arq.seek(arq.length());     //Se o tamanho do registro for diferente, insere no final do arquivo
                retPointer = arq.length();  //Salva o ponteiro para retornar e atualizar o hash
                arq.writeChar('%'); //Escreve a lápide como não deletado
                arq.writeInt(ba.length);    //Escreve o tamanho do arquivo em bytes
                quantDel++;
                quantRegister++;
            }
            arq.write(ba);  //escreve os bytes do registro
            CreateAndFillTree();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retPointer;
    }

    public boolean update(User u){
        User aux = null;
        long pos = initPointer;
        char lapide;
        int regLen;
        long initRegPos = initPointer + 5;
        boolean rsp = false;
        try {
            aux = new User();
            while (aux != null && aux.getIdConta() != u.getIdConta()) {
                arq.seek(pos);
                lapide = arq.readChar();
                regLen = arq.readInt();
                initRegPos = arq.getFilePointer();
                aux = readUser(initRegPos, regLen);
                pos = arq.getFilePointer();
            }
            if(aux != null){
                //Teremos 4 casos de update
                byte[] userByte = u.toByteArray();
                byte[] auxByte = aux.toByteArray();
                if(userByte.length == aux.toByteArray().length){
                    arq.seek(initRegPos);
                    arq.write(u.toByteArray());
                }
                else{
                    arq.seek(initRegPos - 6);
                    arq.writeChar('*');
                    arq.seek(arq.length());
                    arq.writeChar('%');
                    arq.writeInt(userByte.length);
                    arq.write(userByte);
                    quantRegister++;
                    quantDel++;
                    atualizaCabecalho();
                }
            }
            CreateAndFillTree();
            rsp = true;
        } catch (Exception e) {
            System.err.println("Erro ao atualizar usuario " + u.toString() + " : " + e.getMessage());
        }

        return rsp;

    }
    
    public User readUser(long pos, int size) throws Exception{
        User u = null;  //Retornará usuário null caso não encontre nenhum
        if(pos < initPointer){
            throw new Exception("Posicao fora da area de registros!!");
        }
        
        try {
            //Leitura do usuário e construção do objeto do mesmo
            byte[] ba = new byte[size];    //Criação do array de bytes;
            u = new User();
            arq.seek(pos);  //Posiciona na posição desejada
            
            arq.read(ba);   //Armazena os valores lidos no array de bytes
            u.fromByteArray(ba);    //Recupera o user a partir dos bytes recebidos
        } catch (Exception e) {
            System.err.println("Erro ao ler o registro na posicao: " + pos + " erro: " + e.getMessage());
        }
        //System.out.println("User lido: " + u.toString());
        return u;
    }

    public long getPointer(int id) throws Exception{
        User u = new User();
        long pointer = -1;
        long pos = initPointer;
        char lapide;
        int size;
        arq.seek(pos);
        while(pos < arq.length()){
            pointer = pos;
            lapide = arq.readChar();
            size = arq.readInt();
            pos = arq.getFilePointer();
            u = readUser(pos,size); 
            pos = arq.getFilePointer();
            if(lapide != '*' && u.getIdConta() == id){
                pos = arq.length();
            }
            else{
                u = null;
                pointer = -1;
            }
        }
        return pointer;
    }

    public User search(int id) throws Exception{
        User u = new User();
        long pos = initPointer;
        char lapide;
        int size;
        arq.seek(pos);
        while(pos < arq.length()){
            lapide = arq.readChar();
            size = arq.readInt();
            pos = arq.getFilePointer();
            u = readUser(pos,size); 
            pos = arq.getFilePointer();
            if(lapide != '*' && u.getIdConta() == id){
                pos = arq.length();
            }
            else{
                u = null;
            }
        }
        return u;
    }

    public User getUserByTree(int id) throws IOException {
        long fileLocation = tree.search(id);
        byte[] ba;
        User u = new User();
        if (fileLocation != -1) {
            arq.seek(fileLocation);
            ba = new byte[arq.readInt()];
            arq.read(ba);
            u.fromByteArray(ba);
        } else {
            u =  null;
        }
        return u;
    }

    public ArrayList<Long> getIntervalo(int idMin, int idMax){
        return tree.search(idMin, idMax);
    }

    public ArrayList<User> getAll() throws Exception{
        ArrayList<User> lista = new ArrayList<User>();
        User aux = new User();
        arq.seek(initPointer);
        while(arq.getFilePointer() < arq.length()){
            char lapide = arq.readChar();
            int size = arq.readInt();
            byte[] ba = new byte[size];
            arq.read(ba);
            aux.fromByteArray(ba);
            if(lapide != '*'){
                lista.add(aux);
            }
            aux = new User();
        }
        return lista;
    }

    public int getQuantUser(){
        return quantUser;
    }
    public int getLasId(){
        return lastId;
    }
    public int getQuantRegister(){
        return quantRegister;
    }
    public int getQuantDel(){
        return quantDel;
    }
    private int checaUser(User u){
        int rsp = 0;
        try {
            arq.seek(initPointer);
            User tmp;
            char lapide;
            int size;
            for (int i = 0; i < quantUser; i++) {
                lapide = arq.readChar();
                size = arq.readInt();
                if(lapide == '*'){
                    arq.skipBytes(size);
                }
                else {
                    tmp = readUser(arq.getFilePointer(), size);
                    if(tmp.getNomeUsuario().equals(u.getNomeUsuario())){
                        rsp = 1;
                        i = quantUser;
                    }
                    else if(tmp.getCpf().equals(u.getCpf())){
                        rsp = 2;
                        i = quantUser;
                    }
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch(Exception e){
            System.err.println("Erro desconhecido: " + e.getMessage());
        }
        return rsp;
    }

    public User getPosition(long pos) throws Exception{
        User u = null;
        char lapide;
        int size;
        arq.seek(pos);
        lapide = arq.readChar();
        size = arq.readInt();
        if(lapide != '*'){
            u = readUser(arq.getFilePointer(), size);
        }
        return u;
    }

    public long getFilePointer() throws IOException{
        return arq.getFilePointer();
    }

    public void CreateAndFillTree() throws IOException {
        ByteArrayInputStream bias;
        DataInputStream dis;
        byte[] ba;
        char lapide;
        long registerLocation;

        tree = new BPlusTree(3);
        arq.seek(initPointer);
        for (int i = 0; i < quantRegister; i++) {
            lapide = arq.readChar();
            registerLocation = arq.getFilePointer();
            ba = new byte[arq.readInt()];
                arq.read(ba);
                if (lapide != '*') {  
                    bias = new ByteArrayInputStream(ba);
                    dis = new DataInputStream(bias);   
                    tree.insert(dis.readInt(), registerLocation);
                    dis.close();
                    bias.close();
                }
        }
        arq.seek(0);
    }



}
