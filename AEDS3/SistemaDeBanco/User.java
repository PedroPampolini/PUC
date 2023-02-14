import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


/**
 * User
 */
public class User {
    private int idConta;
    private String nomePessoa;
    private String nomeUsuario;
    private String[] emails;
    private String senha;
    private char[] cpf;
    private String cidade;
    private int transferenciasRealizadas;
    private float saldoConta;

    //Construtor vazio
    User(){
        this(0,"default","default",new String[0],"default","00000000000".toCharArray(),"default",0,0.0F);
    }
    //Construtor com todos os parametros menos o ID e o cpf como vetor de char
    User(String nomePessoa, String nomeUsuario, String[] emails, String senha, char[] cpf, String cidade,
    int transferenciasRealizadas, float saldoConta){
        this(0, nomePessoa, nomeUsuario, emails, senha, cpf, cidade, transferenciasRealizadas, saldoConta);
    }
    //Construtor com todos os parametros menos o ID e o cpf como string
    User(String nomePessoa, String nomeUsuario, String[] emails, String senha, String cpf, String cidade,
    int transferenciasRealizadas, float saldoConta){
        this(0, nomePessoa, nomeUsuario, emails, senha, cpf, cidade, transferenciasRealizadas, saldoConta);
    }
    //Construtor com todos os parametros e o cpf como vetor de char
    User(int idConta, String nomePessoa, String nomeUsuario, String[] emails, String senha, char[] cpf, String cidade,
    int transferenciasRealizadas, float saldoConta){
        if(cpf.length != 11){
            System.err.println("O tamanho de cpf deve ser de 11 caracteres");
            return;
        }
        this.idConta = idConta;
        this.nomePessoa = nomePessoa;
        this.nomeUsuario = nomeUsuario;
        this.emails = emails;
        this.senha = senha;
        this.cpf = cpf;
        this.cidade = cidade;
        this.transferenciasRealizadas = transferenciasRealizadas;
        this.saldoConta = saldoConta;
    }
    //Construtor com todos os parametros e o cpf como String
    User(int idConta, String nomePessoa, String nomeUsuario, String[] emails, String senha, String cpf, String cidade,
    int transferenciasRealizadas, float saldoConta){
        if(cpf.length() != 11){
            System.err.println("O tamanho de cpf deve ser de 11 caracteres");
            return;
        }
        this.idConta = idConta;
        this.nomePessoa = nomePessoa;
        this.nomeUsuario = nomeUsuario;
        this.emails = emails;
        this.senha = senha;
        this.cpf = cpf.toCharArray();
        this.cidade = cidade;
        this.transferenciasRealizadas = transferenciasRealizadas;
        this.saldoConta = saldoConta;
    }

    public void decryptPass() throws Exception{
        RSA rsa = new RSA();
        this.senha = rsa.DecryptText(this.senha);
    }
    public void encryptPass() throws Exception{
        RSA rsa = new RSA();
        this.senha = rsa.EncrypText(this.senha);
    }

    //Converte o objeto para um vetor de arrays, para futuramente ser escrito
    public byte[] toByteArray(){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();   //Classe que armazenará os bytes
        DataOutputStream dos = new DataOutputStream(baos);  //Classe de controle de fluxo de bytes
        try{
            dos.writeInt(idConta);  //Escreve o Id no vetor
            dos.writeUTF(nomePessoa);   //Escreve o nome da pessoa no vetor
            dos.writeUTF(nomeUsuario);  //Escreve o username no vetor
            dos.writeInt(emails.length);    //Armazena a quantidade de emails que foram armazenados
            //Armazena todos os emails como string
            for (int i = 0; i < emails.length; i++) {
                dos.writeUTF(emails[i]);
            }
            dos.writeUTF(senha);    //Armazena a senha no vetor
            //Armazena cada caractere do cpf separadamente
            for (int i = 0; i < cpf.length; i++) {
                dos.writeChar(cpf[i]);
            }
            dos.writeUTF(cidade);   //Armazena o nome da cidade como string
            dos.writeInt(transferenciasRealizadas); //armazena o número de transferências realizadas 
            dos.writeFloat(saldoConta); //Armazena o saldo total da conta
        }
        catch(IOException ioe){
            //System.err.println("IOException ao escrever os dados para DataOutputStream: " + ioe.getMessage());
        }
        
        return baos.toByteArray();
    }

    public void fromByteArray(byte[] array){
        ByteArrayInputStream bias = new ByteArrayInputStream(array);    //Classe que irá realizar a leitura do array
        DataInputStream dis = new DataInputStream(bias);    //Classe de controle de fluxo
        try{
            int quantEmails;
            this.idConta = dis.readInt(); //Lê um inteiro para o idConta
            this.nomePessoa = dis.readUTF(); //Lê uma string para o nomePessoa
            this.nomeUsuario = dis.readUTF(); //Lê um inteiro para o nomeUsuário
            quantEmails = dis.readInt(); //Lê um inteiro para saber quantos emaisl deverá ler
            
            emails = new String[quantEmails];   //cria um vetor com o número necessário de emails
            for (int i = 0; i < quantEmails; i++) {
                this.emails[i] = dis.readUTF();     //Leitura de cada email separadamente
            }
            this.senha = dis.readUTF();     //Leitura de uma string e armazenando na senha
            //Lê os 11 caracteres que representam o cpf
            for (int i = 0; i < cpf.length; i++) {
                this.cpf[i] = dis.readChar();
            }
            this.cidade = dis.readUTF();    //Leitura da string da cidade
            this.transferenciasRealizadas = dis.readInt();  //Leitura do int que representa as transações
            this.saldoConta = dis.readFloat();  //Leitura do valor do saldo
        }
        catch(IOException ioe){
            //System.err.println("IOException ao ler os dados de DataInputStream: " + ioe.getMessage());
        }
       
    }
    
    //Transforma o objeto em string para imprimir na tela
    public String toString(){
        String s = "idConta: " + idConta  + " nomePessoa: " + nomePessoa  + " nomeUsuario: " + nomeUsuario  +
        " emails: emailsReplace senha: " + senha  + " cpf: cpfReplace cidade: " + cidade  +
        " transfRealizadas: " + transferenciasRealizadas  + " saldoConta:  " + saldoConta;
        String cpfStr = new String();
        for (int i = 0; i < cpf.length; i++) {
            cpfStr += cpf[i];
        }
        s = s.replaceAll("cpfReplace", cpfStr);
        String emailsStr = "[";
        if(emails.length > 0){
            emailsStr += emails[0];
        }
        for (int i = 1; i < emails.length; i++) {
            emailsStr += ", " + emails[i];
        }
        emailsStr += "]";

        s = s.replaceAll("emailsReplace", emailsStr);

        return s;
    }
    
    public void setIdConta(int idConta){
        this.idConta = idConta;
    }
    public int getIdConta(){
        return idConta;
    }

    public void setNomePessoa(String nomePessoa){
        this.nomePessoa = nomePessoa;
    }
    public String getNomePessoa(){
        return nomePessoa;
    }

    public void setNomeUsuario(String nomeUsuario){
        this.nomeUsuario = nomeUsuario;
    }
    public String getNomeUsuario(){
        return nomeUsuario;
    }

    public void setEmails(String[] emails){
        this.emails = emails;
    }
    public String[] getEmails(){
        return emails;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }
    public String getSenha(){
        return senha;
    }

    public void setCpf(char[] cpf){
        this.cpf = cpf;
    }
    public char[] getCpf(){
        return cpf;
    }

    public void setCidade(String cidade){
        this.cidade = cidade;
    }
    public String getCidade(){
        return cidade;
    }

    public void setTransferenciasRealizadas(int transferenciasRealizadas){
        this.transferenciasRealizadas = transferenciasRealizadas;
    }
    public int getTransferenciasRealizadas(){
        return transferenciasRealizadas;
    }

    public void setSaldoConta(float saldoConta){
        this.saldoConta = saldoConta;
    }
    public float getSaldoConta(){
        return saldoConta;
    }

}