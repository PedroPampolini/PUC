
/**
 * Principal
 */

import java.io.*;
import java.nio.charset.*;
import java.util.Scanner;

public class TP04Q08 {
   public static void main(String[] args) throws Exception {
      Scanner sc = new Scanner(System.in);
      int repeticao = sc.nextInt();

      for (int i = 0; i < repeticao; i++) {
         BinaryTree arvore = new BinaryTree();
         int quantEntradas = sc.nextInt();
         for (int j = 0; j < quantEntradas; j++) {
            int input = sc.nextInt();
            arvore.insert(input);
         }
         System.out.println("Case " + (i + 1) + ": ");
         System.out.print("Pre.: ");
         arvore.caminharPre();
         System.out.println();
         System.out.print("In..: ");
         arvore.caminharCentral();
         System.out.println();
         System.out.print("Post: ");
         arvore.caminharPos();
         System.out.println("\n");
      }
      sc.close();
   }
}

class BinaryTree {
   Node raiz;

   BinaryTree() {
      raiz = null;
   }

   public void insert(int e) throws Exception {
      raiz = insert(e, raiz);
   }

   private Node insert(int e, Node i) throws Exception {
      if (i == null) {
         i = new Node(e);
      } else if (e < i.e) {
         i.menor = insert(e, i.menor);
      } else if (e > i.e) {
         i.maior = insert(e, i.maior);
      } else {
         throw new Exception("Elemento ja existente!!");
      }
      return i;
   }

   public void remove(int e) throws Exception {
      raiz = remove(e, raiz);
   }

   private Node remove(int e, Node i) throws Exception {
      if (i == null) {
         throw new Exception("Elemento nao existente");
      }
      if (e < i.e) {
         i.menor = remove(e, i.menor);
      } else if (e > i.e) {
         i.maior = remove(e, i.maior);
      } else {
         if (i.maior == null) {
            i = i.menor;
         } else if (i.menor == null) {
            i = i.maior;
         } else {
            i.menor = maiorEsq(i, i.menor);
         }
      }
      return i;
   }

   private Node maiorEsq(Node i, Node j) {
      if (j.maior == null) {
         i.e = j.e;
         j = j.menor;
      } else {
         j.maior = maiorEsq(i, j.maior);
      }
      return j;
   }

   public void caminharCentral() {
      caminharCentral(raiz);
   }

   private void caminharCentral(Node i) {
      if (i != null) {
         caminharCentral(i.menor);
         System.out.print(i.e + " ");
         caminharCentral(i.maior);
      }
   }

   public void caminharPos() {
      caminharPos(raiz);
   }

   private void caminharPos(Node i) {
      if (i != null) {
         caminharPos(i.menor);
         caminharPos(i.maior);
         System.out.print(i.e + " ");
      }
   }

   public void caminharPre() {
      caminharPre(raiz);
   }

   private void caminharPre(Node i) {
      if (i != null) {
         System.out.print(i.e + " ");
         caminharPre(i.menor);
         caminharPre(i.maior);
      }
   }
}

class Node {
   int e;
   Node menor;
   Node maior;

   Node() {
      this(-1);
   }

   Node(int e) {
      this.e = e;
      menor = null;
      maior = null;
   }

   Node(int e, Node menor, Node maior) {
      this.e = e;
      this.menor = menor;
      this.maior = maior;
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
