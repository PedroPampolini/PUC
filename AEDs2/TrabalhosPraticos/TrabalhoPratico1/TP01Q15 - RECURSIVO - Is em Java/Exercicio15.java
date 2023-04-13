
/**
 Autor: Pedro Pampolini Mendicino
 Criação: 23/02/2022    Última atualização: 23/02/2022
 Objetivo do Programa:  um método que recebe uma string e verifica se pertence em um dos seguintes casos:
 formada apenas de vogais
 formada apenas de consoantes
 é um número inteiro
 é um número real
 */
import java.io.*;
import java.nio.charset.*;

public class Exercicio15 {
   //Conta quantos pontos e vírgulas uma string possui, para verificar se é um número real ou não
   public static int countReal(String s) {
      int counter = 0;
      for (int i = 0; i < s.length(); i++) {
         if (s.charAt(i) == ',' || s.charAt(i) == '.') {
            counter++;
         }
      }
      return counter;
   }
   //Verifica se uma string é igual à 'FIM' ou não
   public static boolean isFim(String s) {
      return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
   }
   //verifica se  um caractere é uma vogal ou não
   public static boolean isVogal(char c) {
      return (c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U' || c == 'a' || c == 'e' || c == 'i' || c == 'o'
            || c == 'u');
   }
   //Verifica se um caractere é uma letra ou não
   public static boolean isLetter(char c) {
      return ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'));
   }
   //Verifica se um caractere é uma consoante ou não
   public static boolean isConsoante(char c) {
      return (isLetter(c) && !isVogal(c));
   }
   //Verifica recursivamente se a string é composta apenas por consoantes
   public static boolean onlyConsoante(String s,int pos) {
      boolean resp = true;
      if (pos < s.length()) {
         //Caso o caracter lido não seja uma consoante, irá encerrar as chamadas consecutivas e retornará falso
         if (!isConsoante(s.charAt(pos))) {
            resp = false;
         }
         else{
            //Caso ainda seja uma consoante, lerá o próximo caractere
            resp = onlyConsoante(s,pos+1);
         }
      }
      return resp;
   }
   public static boolean onlyConsoante(String s){
      return onlyConsoante(s,0);
   }

   //Verifica recursivamente se a string é composta apenas por vogais
   public static boolean isVogal(String s,int pos) {
      boolean resp = true;
      if (pos < s.length()) {
         //Caso o caracter lido não seja uma vogal, irá encerrar as chamadas consecutivas e retornará falso
         if (!isVogal(s.charAt(pos))) {
            resp = false;
         }
         else{
            //Caso ainda seja uma vogal, lerá o próximo caractere
            resp = isVogal(s,pos+1);
         }
      }
      return resp;
   }
   public static boolean isVogal(String s){
      return isVogal(s,0);
   }

   public static boolean isInteger(String s,int pos) {
      boolean result = true;
      if (pos < s.length()) {
         //Caso o caracter lido não seja um número, irá encerrar as chamadas consecutivas e retornará falso
         if (s.charAt(pos) < '0' || s.charAt(pos) > '9') {
            result = false;
         }
         else{
            //Caso ainda seja um número, lerá o próximo caractere
            result = isInteger(s,pos+1);
         }
      }      
      return result;
   }
   public static boolean isInteger(String s){
      return isInteger(s,0);
   }

   public static boolean isReal(String s, int pos) {
      boolean result = true;
      if (pos < s.length()) {
         //Caso o caracter lido não seja um número, ponto ou vírgula, irá encerrar as chamadas consecutivas e retornará falso
         if (!((s.charAt(pos) >= '0' && s.charAt(pos) <= '9') || s.charAt(pos) == '.' || s.charAt(pos) == ',')) {
            result = false;
         } 
         if (countReal(s) > 1) {
            //Caso ainda seja um número, ponto ou vírgula, lerá o próximo caractere
            result = false;
         }
      }
      return result;
   }
   public static boolean isReal(String s) {
      return isReal(s, 0);
   }

   public static void main(String[] args) {
      String text = new String();
      text = MyIO.readLine();
      while (!isFim(text)) {
         //Verifica a entrada para cada uma das funções, retornado SIM ou NAO em cada caso
         MyIO.print(isVogal(text) ? "SIM " : "NAO ");
         MyIO.print(onlyConsoante(text) ? "SIM " : "NAO ");
         MyIO.print(isInteger(text) ? "SIM " : "NAO ");
         MyIO.println(isReal(text) ? "SIM" : "NAO");
         text = MyIO.readLine();    //Lê a próxima linha
      }
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
