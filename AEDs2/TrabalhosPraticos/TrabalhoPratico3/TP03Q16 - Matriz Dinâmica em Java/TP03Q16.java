
/*
Autor: Pedro Pampolini Mendicino
Data de Criação: 17/05/2022      Última atualização: 17/05/2022
Objetivo: Recebe x pares de matrizes de inteiros, grava os valores nas devidas posições e então faz as seguintes
operações: 
1) Mostra a diagonal principal da matriz 1
2) Mostra a diagonal secundaria da matriz 1
3) Calcula a soma de m1 com m2
4) Calauma a multipicação de m1 com m2
*/

import java.io.*;
import java.nio.charset.*;


public class TP03Q16{

    public static void main(String[] args) throws Exception {
        
        int repeticao = MyIO.readInt();
        for (int i = 0; i < repeticao; i++) {
            
            int cm1, lm1,cm2,lm2;
            Matriz m1,m2;
            cm1 = MyIO.readInt();   //Lê a quantidade de colunas de m1
            lm1 = MyIO.readInt();   //Lê a quantidade de linhas de m1

            m1 = new Matriz(lm1, cm1);  //Cria a matriz flexível 1 com os valores escolhidos
            //Lê todos os valores da matriz
            for (int y = 0; y < cm1; y++) {
                for (int x = 0; x < lm1; x++) {
                    int valor = MyIO.readInt();
                    m1.alter(x, y, valor);
                }
            }

            cm2 = MyIO.readInt();   //Lê a quantidade de colunas de m2
            lm2 = MyIO.readInt();   //Lê a quantidade de linhas de m2
            m2 = new Matriz(lm2, cm2);  //Cria a matriz flexível 2 com os valores escolhidos
            //Lê todos os valores da matriz
            for (int y = 0; y < cm2; y++) {
                for (int x = 0; x < lm2; x++) {
                    int valor = MyIO.readInt();
                    m2.alter(x, y, valor);
                }
            }

            //System.out.println("M1 diag princ:");
            m1.printDiagPrincipal();
            //System.out.println("M1 diag secun:");
            m1.printDiagSec();
            //System.out.println("M1 soma M2:");
            m1.soma(m2).show();
            //System.out.println("M1 mult M2:");
            m1.multiplica(m2).show();
        }
    }
}

class Matriz{
    private Node topo;
    int lin;
    int col;
    Matriz(){
        this(3,3);
    }

    Matriz(int linha, int coluna){
        lin = linha;
        col = coluna;
        topo = new Node();
        Node tmp = topo;
        //Constroi a primeira linha
        for(int i = 1; i < coluna;i++){
            tmp.right = new Node();
            tmp.right.left = tmp;
            tmp = tmp.right;
        }

        //Constroi as demais linhas conectando com a superior
        Node linhaCima = topo; 
        for (int i = 1; i < linha; i++) {
            Node primeLinha = linhaCima;
            linhaCima.down = new Node();
            tmp = linhaCima.down;
            tmp.up = linhaCima;
            for (int j = 1; j < coluna; j++) {
                tmp.right = new Node(); //Cria a célula à direita
                tmp.right.left = tmp;   //Conecta a célula à direita com a da esquerda
                tmp = tmp.right;        //Pula para célula à direita
                linhaCima = linhaCima.right;    //Faz a célula da linha de cima andar para à direita
                tmp.up = linhaCima;
                linhaCima.down = tmp;
            }
            linhaCima = primeLinha.down;
        }
    }

    public void alter(int x, int y, int elemento) throws Exception{
        if (x >= col || y >= lin || x < 0 || y < 0) {
            throw new Exception("Item nao existente");
        }

        Node tmp = topo;
        for (int i = 0; i < x; i++) {
            tmp = tmp.right;
        }
        for (int i = 0; i < y; i++) {
            tmp = tmp.down;
        }
        tmp.e = elemento;
    }
    
    public void show(){
        Node tmp = topo;    //Aponta o vetor temporario para o topo
        for (int i = 0; i < lin; i++) {
            Node primeLin = tmp;    //Guarda a posição da linha que, futuramente, ficará acima
            //Navega por toda a linha atual, imprimindo na tela o valor to elemento nessa posição
            for (int j = 0; j < col; j++) {
                System.out.print(tmp.e + " ");
                tmp = tmp.right;    //Repociciona para a próxima posição
            }
            //Salta para a linha de baixo, no primeiro elemento
            tmp = primeLin.down;
            System.out.print("\n");
        }
    }

    public int getNumber(int x, int y) throws Exception{
        if (x >= col || y >= lin || x < 0 || y < 0) {
            throw new Exception("Item nao existente");
        }
        Node tmp = topo;    //Se posiciona na primeira posição

        //Caminha até a coluna certa
        for (int i = 0; i < x; i++) {
            tmp = tmp.right;
        }
        //Caminha até a linha certa, no local desejado
        for (int i = 0; i < y; i++) {
            tmp = tmp.down;
        }
        return tmp.e;   //entrega o valor decidido
    }

    //Retorna o número de colunas
    public int getColumn(){
        return col;
    }

    //Retorna o número de linhas
    public int getLines(){
        return lin;
    }

    public Matriz soma(Matriz m2) throws Exception{
        if (lin != m2.getLines() || col != m2.getLines()) {
            throw new Exception("Matrizes diferentes");
        }
        int valor;
        Matriz result = new Matriz(lin, col);   //Cria uma matriz com o resultado
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < lin; j++) {
                //Realiza a busca e a soma dos valores, atribuíndo-os na tabela
                //(passível de otimização, fazendo a busca/navegação na função)
                valor = this.getNumber(i, j) + m2.getNumber(i, j);
                result.alter(i, j, valor);
            }
        }

        return result;
        
    }

    public int[] getColunaSeq(int coluna){
        int[] vet = new int[col];
        Node tmp = topo;    //Posiciona o ponteiro na posição (0,0)
        //Caminha até se posiciona na coluna certa
        for (int i = 0; i < coluna; tmp = tmp.right, i++);
        //Caminha por toda a coluna, atribuindo os valores dela a um vetor
        for (int i = 0; i < lin; vet[i] = tmp.e,tmp = tmp.down, i++);
        return vet;
    }

    public int[] getLinhaSeq(int linha){
        int[] vet = new int[lin];
        Node tmp = topo;    //Posiciona o ponteiro na posição (0,0)
        //Caminha até se posiciona na linha certa
        for (int i = 0; i < linha; tmp = tmp.down, i++);
        //Caminha por toda a linha, atribuindo os valores dela a um vetor
        for (int i = 0; i < lin; vet[i] = tmp.e,tmp = tmp.right, i++);
        return vet;
    }

    public Matriz multiplica(Matriz m2) throws Exception{
        if (col != m2.getLines()) {
            throw new Exception("Impossivel Multiplicar");
        }
        //Cria uma matriz de resultado, sendo ela (m1.lin, m2.col)
        Matriz result = new Matriz(lin,m2.getColumn());
        //Percorre por todas as linhas da matriz 1
        for (int i = 0; i < lin; i++) {
            //recebe um vetor da linha 'i' da matriz 1
            int[] vetLinM1 = this.getLinhaSeq(i);
            for (int j = 0; j < m2.getColumn(); j++) {
                int valor = 0;
                //recebe um vetor da coluna 'j' da matriz 2
                int[] vetColM2 = m2.getColunaSeq(j);
                //Enquanto houverem itens nas matrizes, irá somar o produto de ambos no resultado
                for (int k = 0; k < vetColM2.length; k++) {
                    valor += (vetLinM1[k] * vetColM2[k]);
                }
                //Armazena o resultado da soma dos produtos na matriz resultante na posição desejada
                result.alter(j, i, valor);
            }
        }
        return result;
    }

    public void printDiagPrincipal() throws Exception{
        if (col != lin) {
            throw new Exception("Matriz nao quadrada");
        }

        Node tmp = topo;//Posiciona o ponteiro na posição (0,0)
        System.out.print(tmp.e + " ");
        //Como ja imprimiu o 1º elemento, inicia do 1 e vai até o fim das colunas
        for(int i = 1; i < col;i++){
            tmp = tmp.down.right;   //Pula para o item na sua diagonal
            System.out.print(tmp.e + " ");  //imprime na tela o valor do item
            
        }
        System.out.println();
    }

    public void printDiagSec() throws Exception{
        if (col != lin) {
            throw new Exception("Matriz nao quadrada");
        }
        
        Node tmp = topo;//Posiciona o ponteiro na posição (0,0)
        //Caminha até o extremo direito da linha 1 da matriz
        for(;tmp.right != null;tmp = tmp.right);
        System.out.print(tmp.e + " ");  //imprime o primeiro elemento
        //Como ja imprimiu o 1º elemento, inicia do 1 e vai até o fim das colunas
        for(int i = 1; i < col;i++){
            tmp = tmp.down.left;    //Pula para o item na sua diagonal
            System.out.print(tmp.e + " ");  //imprime na tela o valor do item
            
        }
        System.out.println();
    }
}

class Node{
    public Node up;
    public Node down;
    public Node right;
    public Node left;
    int e;

    public Node(){
        up = null;
        down = null;
        right = null;
        left = null;
        e = 0;
    }

    public Node(int e){
        up = null;
        down = null;
        right = null;
        left = null;
        this.e = e;
    }
    
}

class MyIO {

    private static BufferedReader in = new BufferedReader(
          new InputStreamReader(System.in, Charset.forName("ISO-8859-1")));
    private static String charset = "ISO-8859-1";
 
    public static void setCharset(String charset_) {
       charset = charset_;
       in = new BufferedReader(new InputStreamReader(System.in, Charset.forName(charset)));
    }
 
    public static void print() {
    }
 
    public static void print(int x) {
       try {
          PrintStream out = new PrintStream(System.out, true, charset);
          out.print(x);
       } catch (UnsupportedEncodingException e) {
          System.out.println("Erro: charset invalido");
       }
    }
 
    public static void print(float x) {
       try {
          PrintStream out = new PrintStream(System.out, true, charset);
          out.print(x);
       } catch (UnsupportedEncodingException e) {
          System.out.println("Erro: charset invalido");
       }
    }
 
    public static void print(double x) {
       try {
          PrintStream out = new PrintStream(System.out, true, charset);
          out.print(x);
       } catch (UnsupportedEncodingException e) {
          System.out.println("Erro: charset invalido");
       }
    }
 
    public static void print(String x) {
       try {
          PrintStream out = new PrintStream(System.out, true, charset);
          out.print(x);
       } catch (UnsupportedEncodingException e) {
          System.out.println("Erro: charset invalido");
       }
    }
 
    public static void print(boolean x) {
       try {
          PrintStream out = new PrintStream(System.out, true, charset);
          out.print(x);
       } catch (UnsupportedEncodingException e) {
          System.out.println("Erro: charset invalido");
       }
    }
 
    public static void print(char x) {
       try {
          PrintStream out = new PrintStream(System.out, true, charset);
          out.print(x);
       } catch (UnsupportedEncodingException e) {
          System.out.println("Erro: charset invalido");
       }
    }
 
    public static void println() {
    }
 
    public static void println(int x) {
       try {
          PrintStream out = new PrintStream(System.out, true, charset);
          out.println(x);
       } catch (UnsupportedEncodingException e) {
          System.out.println("Erro: charset invalido");
       }
    }
 
    public static void println(float x) {
       try {
          PrintStream out = new PrintStream(System.out, true, charset);
          out.println(x);
       } catch (UnsupportedEncodingException e) {
          System.out.println("Erro: charset invalido");
       }
    }
 
    public static void println(double x) {
       try {
          PrintStream out = new PrintStream(System.out, true, charset);
          out.println(x);
       } catch (UnsupportedEncodingException e) {
          System.out.println("Erro: charset invalido");
       }
    }
 
    public static void println(String x) {
       try {
          PrintStream out = new PrintStream(System.out, true, charset);
          out.println(x);
       } catch (UnsupportedEncodingException e) {
          System.out.println("Erro: charset invalido");
       }
    }
 
    public static void println(boolean x) {
       try {
          PrintStream out = new PrintStream(System.out, true, charset);
          out.println(x);
       } catch (UnsupportedEncodingException e) {
          System.out.println("Erro: charset invalido");
       }
    }
 
    public static void println(char x) {
       try {
          PrintStream out = new PrintStream(System.out, true, charset);
          out.println(x);
       } catch (UnsupportedEncodingException e) {
          System.out.println("Erro: charset invalido");
       }
    }
 
    public static void printf(String formato, double x) {
       try {
          PrintStream out = new PrintStream(System.out, true, charset);
          out.printf(formato, x);// "%.2f"
       } catch (UnsupportedEncodingException e) {
          System.out.println("Erro: charset invalido");
       }
    }
 
    public static double readDouble() {
       double d = -1;
       try {
          d = Double.parseDouble(readString().trim().replace(",", "."));
       } catch (Exception e) {
       }
       return d;
    }
 
    public static double readDouble(String str) {
       try {
          PrintStream out = new PrintStream(System.out, true, charset);
          out.print(str);
       } catch (UnsupportedEncodingException e) {
          System.out.println("Erro: charset invalido");
       }
       return readDouble();
    }
 
    public static float readFloat() {
       return (float) readDouble();
    }
 
    public static float readFloat(String str) {
       return (float) readDouble(str);
    }
 
    public static int readInt() {
       int i = -1;
       try {
          i = Integer.parseInt(readString().trim());
       } catch (Exception e) {
       }
       return i;
    }
 
    public static int readInt(String str) {
       try {
          PrintStream out = new PrintStream(System.out, true, charset);
          out.print(str);
       } catch (UnsupportedEncodingException e) {
          System.out.println("Erro: charset invalido");
       }
       return readInt();
    }
 
    public static String readString() {
       String s = "";
       char tmp;
       try {
          do {
             tmp = (char) in.read();
             if (tmp != '\n' && tmp != ' ' && tmp != 13) {
                s += tmp;
             }
          } while (tmp != '\n' && tmp != ' ');
       } catch (IOException ioe) {
          System.out.println("lerPalavra: " + ioe.getMessage());
       }
       return s;
    }
 
    public static String readString(String str) {
       try {
          PrintStream out = new PrintStream(System.out, true, charset);
          out.print(str);
       } catch (UnsupportedEncodingException e) {
          System.out.println("Erro: charset invalido");
       }
       return readString();
    }
 
    public static String readLine() {
       String s = "";
       char tmp;
       try {
          do {
             tmp = (char) in.read();
             if (tmp != '\n' && tmp != 13) {
                s += tmp;
             }
          } while (tmp != '\n');
       } catch (IOException ioe) {
          System.out.println("lerPalavra: " + ioe.getMessage());
       }
       return s;
    }
 
    public static String readLine(String str) {
       try {
          PrintStream out = new PrintStream(System.out, true, charset);
          out.print(str);
       } catch (UnsupportedEncodingException e) {
          System.out.println("Erro: charset invalido");
       }
       return readLine();
    }
 
    public static char readChar() {
       char resp = ' ';
       try {
          resp = (char) in.read();
       } catch (Exception e) {
       }
       return resp;
    }
 
    public static char readChar(String str) {
       try {
          PrintStream out = new PrintStream(System.out, true, charset);
          out.print(str);
       } catch (UnsupportedEncodingException e) {
          System.out.println("Erro: charset invalido");
       }
       return readChar();
    }
 
    public static boolean readBoolean() {
       boolean resp = false;
       String str = "";
 
       try {
          str = readString();
       } catch (Exception e) {
       }
 
       if (str.equals("true") || str.equals("TRUE") || str.equals("t") || str.equals("1") ||
             str.equals("verdadeiro") || str.equals("VERDADEIRO") || str.equals("V")) {
          resp = true;
       }
 
       return resp;
    }
 
    public static boolean readBoolean(String str) {
       try {
          PrintStream out = new PrintStream(System.out, true, charset);
          out.print(str);
       } catch (UnsupportedEncodingException e) {
          System.out.println("Erro: charset invalido");
       }
       return readBoolean();
    }
 
    public static void pause() {
       try {
          in.read();
       } catch (Exception e) {
       }
    }
 
    public static void pause(String str) {
       try {
          PrintStream out = new PrintStream(System.out, true, charset);
          out.print(str);
       } catch (UnsupportedEncodingException e) {
          System.out.println("Erro: charset invalido");
       }
       pause();
    }
 }