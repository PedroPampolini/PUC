import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
/*
 * Autor: Pedro Pampolini Mendicino     Criação:18/09/2022  Última Atualização: 23/09/2022
 * Objetivo: Realizar um crud e a ordenação de registros de usuário em um banco
 * Link para o video do TP2: https://youtu.be/Hbob3Bx1wN8
*/

public class Principal {
    public static CRUD crud;
    public static BufferedReader in;
    public static Hash hash;
    public static ReverseIndex indexCity;
    public static ReverseIndex indexName;
    public static void main(String[] args) throws Exception {
        crud = new CRUD("User.db");
        hash = new Hash(4);
        indexCity = new ReverseIndex("Cidade");
        indexName = new ReverseIndex("Nome");
        boolean sair = false;
        String menu = "Digite o número da opção:\n0) Sair\n1) Criar conta\n2) Remover\n3) Atualizar\n"
                        + "4) Ler todos\n5) Transferir\n6) Pesquisar Conta\n7) Ordenar\n"
                        + "8) Imprimir Hash\n9) Pesquisar por cidade\n10) Pesquisar por nome\n"
                        + "11) Pesquisar por intervalo\n12) Comprimir arquivo\n13) Descomprimir Arquivo\n"
                        + "14) Realizar Busca de texto\n100) Deletar tudo";
        String op = new String();
        in = new BufferedReader(new InputStreamReader(System.in,"UTF-8"));
        
        System.out.println(menu);
        while(sair == false){
            op = in.readLine();
            if(op.equals("0")){
                System.out.println("Saindo...");
                sair = true;
            } else if(op.equals("1")){  //Insere um usuário
                System.out.println("Criar Conta: ");
                try {
                    User u = preencheUsuario();
                    u.encryptPass();    //Encripta a senha
                    //Insere o usuario no arquivo e no hash apenas se retornar true
                    long pointer = crud.insert(u);
                    if(pointer != -1) {
                        hash.add(crud.getLasId(), pointer);
                        indexCity.add(u.getCidade(), pointer);
                        indexName.add(u.getNomePessoa(), pointer);
                        
                    }
                } catch (IllegalArgumentException e) {
                    System.err.println("Nome de Usuario ja existente");
                }
            } else if(op.equals("2")){  //Remove o usuário do banco
                System.out.println("Remover");
                deleta();
            } else if(op.equals("3")){  //Atualiza o usuário
                System.out.println("Atualizar");
                atualiza();
            } else if(op.equals("4")){  //Recupera todos os registros válidos e imprime na tela
                System.out.println("Read all");
                ArrayList<User> lista = crud.getAll();
                showAll(lista);
            } else if(op.equals("5")){  //Transferir
                System.out.println("Transferir");
                int id1, id2;
                float quantia;
                System.out.println("Conta que ira transferir: ");
                id1 = Integer.parseInt(in.readLine());
                System.out.println("Conta que ira receber: ");
                id2 = Integer.parseInt(in.readLine());
                System.out.println("Quantia que sera transferida: ");
                quantia = Float.parseFloat(in.readLine());
                transfere(id1, id2, quantia);
            } else if(op.equals("6")){      //Faz a pesquisa no banco
                System.out.println("Pesquisar Conta");
                User u = pesquisa();
                u.decryptPass();
                System.out.println((u != null) ? u.toString() : "Nao existente");
            } else if(op.equals("7")){    //Ordena o banco
                Ordenacao ord = new Ordenacao(4, 5);
                crud = ord.intercalacaoBalanceada(crud);

                //Reindexa o hash
                hash.initFiles();
                int lastId = crud.getLasId();
                for(int i = 0; i <= lastId; i++){
                    long userPointer = crud.getPointer(i);
                    if(userPointer != -1){
                        hash.add(i, userPointer);
                    }
                }

                //Reindexa o as listas invertidas
                indexCity.deleteAll();
                indexName.deleteAll();
                for (int i = 0; i <= lastId; i++) {
                    long userPointer = hash.getPointer(i);
                    if(userPointer != -1){
                        User u = crud.getPosition(userPointer);
                        indexCity.add(u.getCidade(), userPointer);
                        indexName.add(u.getNomePessoa(), userPointer);
                    }
                }

                //Reindexa a arvore B+
                crud.CreateAndFillTree();
            } else if(op.equals("8")){  //Imprime o hash
                hash.print();
            } else if(op.equals("9")) { //Pesquisa por cidade
                System.out.print("Digite a cidade: ");
                String cidade = in.readLine();
                ArrayList<Long> lista = indexCity.getAll(cidade);
                ArrayList<User> listaUser = new ArrayList<User>();
                for(Long l : lista) {
                    listaUser.add(crud.getPosition(l));
                }
                System.out.println("Lista de usuarios da cidade " + cidade);
                showAll(listaUser);
            } else if(op.equals("10")) {    //Pesquisa por nome
                System.out.print("Digite o nome: ");
                String name = in.readLine();
                ArrayList<Long> lista = indexName.getAll(name);
                ArrayList<User> listaUser = new ArrayList<User>();
                for(Long l : lista) {
                    listaUser.add(crud.getPosition(l));
                }
                System.out.println("Lista de usuarios com o nome " + name);
                showAll(listaUser);
            } else if(op.equals("11")){    //Imprime um intervalo de registros
                System.out.println("Id inicial: ");
                int id1 = Integer.parseInt(in.readLine());
                System.out.println("Id final: ");
                int id2 = Integer.parseInt(in.readLine());
                ArrayList<Long> lista = crud.getIntervalo(id1, id2);
                for(Long l : lista) {
                    l = l - (Character.BYTES);
                    User u = crud.getPosition(l);
                    u.decryptPass();
                    System.out.println(u);
                }
            } else if(op.equals("12")){   //Comprime o arquivo em huffman ou lzw
                crud.close();
                String inFile, outFile;
                System.out.println("Digite o nome do arquivo de para comprimir: ");
                inFile = in.readLine();
                System.out.println("Digite o nome do arquivo comprimido: ");
                outFile = in.readLine();
                System.out.println("Digite o metodo de Compressao: (1)huffman ou (2)lzw");
                String metodo = in.readLine();
                if(metodo.equals("1")){
                    Huffman.CompressFile(inFile, outFile);
                } else if(metodo.equals("2")){
                    LZW lzw = new LZW();
                    lzw.comprimir(inFile, outFile);
                }
                crud = new CRUD("User.db");
            } else if(op.equals("13")){     //Descomprime o arquivo em huffman ou lzw
                crud.close();
                String inFile, outFile;
                System.out.println("Digite o nome do arquivo de para descomprimir: ");
                inFile = in.readLine();
                System.out.println("Digite o nome do arquivo de saida: ");
                outFile = in.readLine();
                System.out.println("Digite o metodo de descompressao: (1)huffman ou (2)lzw");
                String metodo = in.readLine();
                if(metodo.equals("1")){
                    Huffman.DecompressFile(inFile, outFile);

                } else if(metodo.equals("2")){
                    LZW lzw = new LZW();
                    lzw.descomprimir(inFile, outFile);
                }
                crud = new CRUD("User.db");
            } else if(op.equals("14")){    //Busca por texto
                System.out.println("Digite o texto que deseja buscar no banco: ");
                String procurado = in.readLine();
                ArrayList<User> lista = crud.getAll();
                KMP kmp = new KMP(procurado);
                for (User user : lista) {
                    user.decryptPass();
                    if(kmp.contains(user.toString())){
                        System.out.println(user);
                        break;
                    }
                }

            } else if(op.equals("15")){ 
                
            } else if(op.equals("100")){      //Deleta o database por completo
                System.out.println("Deletar tudo");
                System.out.println("Digite DELETE para confirmar");
                String confirmacao = in.readLine();
                if(confirmacao.equals("DELETE")){
                    try{
                        crud.close();
                        File myFile = new File("User.db");
                        myFile.delete();
                        RandomAccessFile raf = new RandomAccessFile("User.db", "rw");
                        raf.writeInt(0);    //Escreve 0 para a quantidade de usuarios presentes
                        raf.writeInt(0);    //Escreve 0 para a quantidade de registros presentes
                        raf.writeInt(0);    //Escreve 0 para o último id escrito
                        raf.writeInt(0);    //Escreve 0 para a quantidade de usuarios deletados
                        raf.close();
                        crud = new CRUD("User.db");

                        hash.initFiles();

                        indexCity.deleteAll();
                        indexName.deleteAll();
                    }
                    catch(Exception e){
                        System.out.println("Nao foi possivel deletar o arquivo: " + e.getMessage());
                    }
                }
                else{
                    System.out.println("Nao deletando");
                }
            } else  {
                System.out.println("Operação desconhecida");
            }
            System.out.println("\n" + menu);
        }
        
    }

    public static User preencheUsuario(){
        String aux = new String();
        String[] emails;
        char[] cpf = new char[11];
        int quantEmails;
        float saldoTeste = 0;
        User user = null;
        try{
            user = new User();
            System.out.print("Nome: ");
            aux = in.readLine();
            user.setNomePessoa(aux);
            System.out.print("Username: ");
            aux = in.readLine();
            user.setNomeUsuario(aux);
            System.out.print("Quantidade de emails: ");
            quantEmails = Integer.parseInt(in.readLine());
            emails = new String[quantEmails];
            for (int i = 0; i < emails.length; i++) {
                System.out.print("Digite o " + (i+1) + " email: ");
                emails[i] = in.readLine();
            }
            user.setEmails(emails);
            System.out.print("Senha: ");
            aux = in.readLine();
            user.setSenha(aux);
            System.out.print("Cpf: ");
            cpf = in.readLine().toCharArray();  //Fazer verificação de cpf
            user.setCpf(cpf);
            System.out.print("Cidade: ");
            aux = in.readLine();
            user.setCidade(aux);
            user.setTransferenciasRealizadas(0);    //Inicialmente n tem nenhuma transferencia
            System.out.print("Saldo: ");
            saldoTeste = Float.parseFloat(in.readLine());
            user.setSaldoConta(saldoTeste);   //Inicialmente não ha saldo
        }
        catch(IOException ioe){
            System.err.println("Erro ao criar usuario");
        }
        catch(NumberFormatException nfe){
            System.err.println("Erro ao ler numero: " + nfe.getMessage());
        }
        catch(Exception e){
            System.err.println("Erro desconhecido: " + e.getMessage());
        }
        return user;
    }
    
    public static void showAll(ArrayList<User> lista) throws Exception{
        for (int i = 0; i < lista.size(); i++) {
            User u = lista.get(i);
            u.decryptPass();
            System.out.println("> " + u);
        }
    }

    public static void deleta() {
        int id;
        try {
            System.out.println("Digite o ID a ser apagado");
            id = Integer.parseInt(in.readLine());
            User u = crud.getPosition(hash.getPointer(id));
            long pointer = hash.remove(id);
            if(pointer != -1 && crud.delete(pointer)){
                indexCity.remove(u.getCidade(),pointer);
                indexCity.remove(u.getNomePessoa(),pointer);
                System.out.println("Usuario deletado com sucesso");
            }
            else{
                System.out.println("Erro ao deletar");
            }

        } catch (NumberFormatException e) {
            // TODO: handle exception
            System.err.println("Digite um numero como ID e tente novamente");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch(Exception e){
            System.err.println("Erro desconhecido: " + e.getMessage());
            e.printStackTrace();
        }
        
    }

    public static void atualiza() throws Exception{
        int id;
        String aux = new String();
        int auxInt = 0;
        float auxFloat = 0.0F;
        String[] emails;
        System.out.println("Id para atualizar: ");
        id = Integer.parseInt(in.readLine());
        User u = crud.getPosition(hash.getPointer(id));
        String cidadeAnt = u.getCidade();
        String nomeAnt = u.getNomePessoa();
        System.out.println("Digite o novo nome: Antigo = " + u.getNomePessoa());
        aux = in.readLine();
        u.setNomePessoa(aux);
        System.out.println("Digite o novo username: Antigo = " + u.getNomeUsuario());
        aux = in.readLine();
        u.setNomeUsuario(aux);
        
        System.out.println("Quantos emails? Antigo = " + u.getEmails().length);
        auxInt = Integer.parseInt(in.readLine());
        emails = new String[auxInt];
        for (int i = 0; i < emails.length; i++) {
            System.out.println((i+1) + " email: Antigo = " + ( (u.getEmails().length > i) ? u.getEmails()[i] : "Nao existente") );
            emails[i] = in.readLine();
        }
        u.setEmails(emails);
        u.decryptPass();
        System.out.println("Digite a nova senha: Antiga = " + u.getSenha());
        aux = in.readLine();
        u.setSenha(aux);
        System.out.println("Digite o novo CPF: Antigo = " + String.valueOf(u.getCpf()));
        aux = in.readLine();
        u.setCpf(aux.toCharArray());
        System.out.println("Digite a nova cidade: Antiga = " + u.getCidade());
        aux = in.readLine();
        u.setCidade(aux);
        System.out.println("Digite a nova transferencia: Antiga = " + u.getTransferenciasRealizadas());
        auxInt = Integer.parseInt(in.readLine());
        u.setTransferenciasRealizadas(auxInt);
        System.out.println("Digite o novo saldo: Antigo = " + u.getSaldoConta());
        auxFloat = Float.parseFloat(in.readLine());
        u.setSaldoConta(auxFloat);
        u.encryptPass();
        long posAntigo = hash.getPointer(id);
        long newPos = crud.update(u, hash.getPointer(id));
        hash.update(id, newPos);
        //Remove e insere o index de nome caso tenha sido alterado
        if(!nomeAnt.equals(u.getNomePessoa())){
            indexName.remove(nomeAnt, posAntigo);
            indexName.add(u.getNomePessoa(), newPos);
        }
        //Se ele se mantém, apenas atualiza
        else{
            indexName.update(u.getNomePessoa(), posAntigo, newPos);
        }

        //Remove e insere o index de nome caso tenha sido alterado
        if(!cidadeAnt.equals(u.getCidade())){
            indexCity.remove(cidadeAnt, posAntigo);
            indexCity.add(u.getCidade(), newPos);
        }
        //Se ele se mantém, apenas atualiza
        else{
            indexCity.update(u.getCidade(), posAntigo, newPos);
        }
    }

    public static void transfere(int id1, int id2, float quantia){
        User u1 = null;
        User u2 = null;
        try {
            long pos1 = hash.getPointer(id1);
            long pos2 = hash.getPointer(id2);
            u1 = crud.getPosition(pos1);
            u2 = crud.getPosition(pos2);
            if(u1.getSaldoConta() < quantia){
                System.out.println("Saldo nao e suficiente para a transferencia");
            }
            else{
                u2.setSaldoConta(u2.getSaldoConta() + quantia);
                u1.setSaldoConta(u1.getSaldoConta() - quantia);
                u2.setTransferenciasRealizadas(u2.getTransferenciasRealizadas() + 1);
                u1.setTransferenciasRealizadas(u1.getTransferenciasRealizadas() + 1);
                hash.update(u1.getIdConta(), crud.update(u1, pos1));
                hash.update(u2.getIdConta(), crud.update(u2, pos2));
            }
        } catch (Exception e) {
            System.out.println("Nao foi possivel tranferir: " + e.getMessage());
        }
    }

    //Aprimorar, fazendo um método em user que transforma em string e comparar cada campo pela string passada como
    //parametro
    public static User pesquisa() throws Exception{
        User u = new User();
        int id;
        System.out.println("Digite o id da pessoa: ");
        id = Integer.parseInt(in.readLine());
        System.out.println("Pesquisar por arvore ou hash? (1/2)");
        String option = in.readLine();
        if(option.equals("1")){
            u = crud.getUserByTree(id);
        }
        else if(option.equals("2")){
            u = crud.getPosition(hash.getPointer(id));
        }
        else{
            System.out.println("Opcao invalida");
        }
        
        return u;
    }
    
}
