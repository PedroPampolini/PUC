
/**
Autor: Pedro Pampolini Mendicino
Criação: 14/02/2022    Última atualização: 14/02/2022
Objetivo do Programa:  Recebe uma expressão booleana no formato:
(X entradas) (valor de cada entrada em 1 ou 0) operação -> como no exemplo abaixo:
3 0 1 0 or(and(A , B , C) , and(or(A , B) , C))
e retorna 1 para verdadeiro e 0 para falso
 */
import java.io.*;
import java.nio.charset.*;

public class Exercicio14 {
   public static String substring(String s, int min, int max) {
      String newStr = new String();
      for (int i = min; i < s.length() && i < max; i++) {
         newStr += s.charAt(i);
      }
      return newStr;
   }

   public static boolean equals(String s1, String s2) {
      boolean result = true;
      if (s1.length() != s2.length()) {
         result = false;
      } else
         for (int i = 0; i < s1.length() && i < s2.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
               result = false;
               i = s1.length();
            }
         }
      return result;
   }

   public static String replace(String principal, String procurada, String substituta) {
      String result = new String();
      String aux = new String();

      for (int i = 0; i < principal.length(); i++) {
         aux = substring(principal, i, procurada.length()+i);
         if (equals(aux, procurada)) {
            for (int j = 0; j < substituta.length(); j++) {
               result += substituta.charAt(j);
            }
            i += procurada.length() - 1;
         } else {
            result += principal.charAt(i);
         }
         //MyIO.println("Main: " + principal + " procurada: " + procurada + " substituta:" + substituta + " aux: " + aux + " result: " + result);
      }
      return result;
   }

   public static char[] recuperaInput(String expression, int quanti) {
      char[] entradas = new char[quanti];
      for (int i = 0; i < quanti; i++) {
         entradas[i] = expression.charAt((i + 1) * 2);
      }
      return entradas;
   }

   public static void printChar(char[] in) {
      MyIO.print("[ ");
      for (int i = 0; i < in.length; i++) {
         MyIO.print(in[i] + " ");
      }
      MyIO.println("]");
   }

   public static String replaceInput(String expression, char[] entradas) {
      String newExpress = new String();
      //For iniciado a partir da expressão, ignorando as entradas
      for (int i = (entradas.length + 1)*2; i < expression.length(); i++) {
         switch (expression.charAt(i)) {
            case 'A':
               newExpress += entradas[0];
               break;

            case 'B':
               newExpress += entradas[1];
               break;

            case 'C':
               newExpress += entradas[2];
               break;

            case 'D':
               newExpress += entradas[3];
               break;
            case ' ':
               break;
            default:
               newExpress += expression.charAt(i);
               break;
         }
      }
      return newExpress;
   }

   public static boolean[] getLogicInputs(String input){
      //cria o vetor com o tamanho certo de variáveis
      boolean[] logicInput = new boolean[(input.length() -2)/2];
      int index = 0; //index do input lógico
      for (int i = 0; i < input.length(); i++) {
         if (input.charAt(i) == '1') {
            logicInput[index] = true;
            index++;
         }
         else if (input.charAt(i) == '0') {
            logicInput[index] = false;
            index++;
         }
      }
      return logicInput;
   }

   public static String calcExpression(String exp){
      return calcExpression(exp,0);
   }

   public static String calcExpression(String exp, int pos){
      String resp = new String();
      if (exp.length() > 1) {
         int abreIndex = 0;
         String contaAtual = new String();
         for (int i = 0; i < exp.length(); i++) {
            if (exp.charAt(i) == '(') {
               abreIndex = i;
            }
            else if (exp.charAt(i) == ')') {
               contaAtual = substring(exp, abreIndex-1, i+1);
               boolean logicResp = false;
               boolean[] logicInput = getLogicInputs(contaAtual);
               switch (contaAtual.charAt(0)) {
                  case 'r':
                     //MyIO.println("entrou na or");
                     logicResp = false;
                     for (int j = 0; j < logicInput.length; j++) {
                        logicResp |= logicInput[j];
                     }
                     contaAtual = substring(exp, abreIndex-2, i+1);
                     resp = replace(exp, contaAtual, (logicResp) ? "1" : "0");
                     //MyIO.println("Expressão: " + exp + " resp: " + resp + " contaAtual: " + contaAtual);
                     break;
                     
                  case 'd':
                     //MyIO.println("entrou na and");
                     logicResp = true;
                     for (int j = 0; j < logicInput.length; j++) {
                        logicResp &= logicInput[j];
                     }
                     contaAtual = substring(exp, abreIndex-3, i+1);
                     resp = replace(exp, contaAtual, (logicResp) ? "1" : "0");
                     //MyIO.println("Expressao: " + exp + " resp: " + resp + " contaAtual: " + contaAtual);
                     break;

                  case 't':
                     //MyIO.println("entrou na Not");
                     logicResp = !logicInput[0];
                     contaAtual = substring(exp, abreIndex-3, i+1);
                     resp = replace(exp, contaAtual, (logicResp) ? "1" : "0");
                     //MyIO.println("Expressão: " + exp + " resp: " + resp + " contaAtual: " + contaAtual);
                     break;
               }
               i = exp.length();
            }
         }
         if (resp.length() > 1) {
            resp = calcExpression(resp,pos+1);
         }         
      }
      //System.out.println(resp);
      return resp;
   }


   public static void main(String[] args){
      String expression = new String();
      int quantiIn; // Quantidade de entradas
      char[] entradas; // Valores de entradas

      expression = MyIO.readLine();
      //System.out.println("Tentativa 1");
      while (expression.charAt(0) != '0') {
         quantiIn = (int) (expression.charAt(0)) - 48; // Lẽ o inteiro das quantidades de entradas
         entradas = recuperaInput(expression, quantiIn); // Recupera as entradas em caracteres
         expression = replaceInput(expression, entradas);
         expression = calcExpression(expression);
         MyIO.println(expression);
         expression = MyIO.readLine();
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