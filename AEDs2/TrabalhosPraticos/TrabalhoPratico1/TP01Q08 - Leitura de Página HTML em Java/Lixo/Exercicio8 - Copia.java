/*
Autor: Pedro Pampolini Mendicino
Data de criação 21/02/2022     Última atualização: 21/02/2022
Objetivo: Recebe o nome de um site e sua URL e retorna o número de cada vogal, sendo ela com e sem acento, o número de 
consoantes, de tags <br> e <table>. Então imprime os resultados e o nome da página na tela
*/

import java.io.*;
import java.net.*;
import java.nio.charset.*;

public class Exercicio8{
    public static boolean isFim(String s){
        return(s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }
    
    public static boolean isLetter(char c){
        return((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'));
    }

    public static boolean isVogal(char c){
        return(c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U');
    }

    public static boolean isConsonant(char c){
        return(isLetter(c) && !isVogal(c));
    }

    public static boolean checkTable(String code, int i){
        return(code.charAt(i) == '<' && code.charAt(i+1) == 't' && code.charAt(i+2) == 'a' && code.charAt(i+3) == 'b' && code.charAt(i+4) == 'l' && code.charAt(i+5) == 'e' && code.charAt(i+6) == '>');
    }

    public static boolean checkBr(String code, int i){
        return(code.charAt(i) == '<' && code.charAt(i+1) == 'b' && code.charAt(i+2) == 'r' && code.charAt(i+3) == '>');
    }

    public static int[] processCode(String code){
        int[] pattern = new int[25];    //Vetor com a marcação das contagens
        //Zera o vetor de contagens
        for (int i = 0; i < pattern.length; i++) {
            pattern[i] = 0;
        }
        boolean tagBrTable = false;     //Variável que verifica se a leitura está passando por uma tag <br> ou <table>
        String vogals = "aeiouáéíóúàèìòùãõâêîôû";   //String que marca as vogais, com suas posições respectivas do vetor
        
        //Inicia o processo
        for (int i = 0; i < code.length(); i++) {
            //Checa se o ponto analisado é o inicio de uma tag br ou table
            if (!tagBrTable && (checkTable(code,i) || checkBr(code, i))) {
                tagBrTable = true;  //Se for, seta o flag
                //Verifica qual dos dois é e incrementa no vetor na sua posição
                if (checkTable(code,i)) {
                    pattern[23]++;
                }
                else if (checkTable(code,i)) {
                    pattern[24]++;
                }
            }
            //Caso atinga o fechamento da tag, reseta o flag
            else if (tagBrTable && code.charAt(i) == '>') {
                tagBrTable = false;
                
            }

            //Realiza os procedimentos apenas se não estiver dentro de uma das duas tags, br ou table
            if (!tagBrTable) {
                //Verifica se a variável analisada é uma consoante
                if (isConsonant(code.charAt(i))) {
                    pattern[22]++;
                    //System.out.print(code.charAt(i));
                }
                else{
                    //Realiza o processo caso seja uma vogal, incrementando na sua respectiva posição
                    for (int j = 0; j < 22; j++) {
                        //Realiza a comparação do caractere do código com a vogal analisada
                        if ((int)code.charAt(i) == (int)vogals.charAt(j)) {
                            //System.out.println("" + vogals.length() + " " + j);
                            pattern[j]++;       //Incrementa na respectiva posição
                        }
                    }
                }
                if (code.charAt(i) == vogals.charAt(18)) {
                    pattern[18]++;
                }
            }
        }
        return pattern;
    }

    public static String getHtml(String endereco){
        URL url;
        InputStream is = null;
        BufferedReader br;
        String resp = "", line;
  
        try {
           url = new URL(endereco);
           is = url.openStream();  // throws an IOException
           br = new BufferedReader(new InputStreamReader(is,"ISO-8859-1"));
  
           while ((line = br.readLine()) != null) {
              resp += line + "\n";
           }
        } catch (MalformedURLException mue) {
           mue.printStackTrace();
        } catch (IOException ioe) {
           ioe.printStackTrace();
        } 
  
        try {
           is.close();
        } catch (IOException ioe) {
           // nothing to see here
  
        }
  
        return resp;
     }
    
    public static void printResults(int[] pattern, String name){
        String vogals = "aeiou�����������������";   //String que marca as vogais, com suas posições respectivas do vetor
        for (int i = 0; i < vogals.length(); i++) {
            MyIO.print("" + vogals.charAt(i) + "(" + pattern[i] + ") ");
        }
        MyIO.print("consoante(" + pattern[22] + ") ");
        MyIO.print("<br>(" + pattern[23] + ") ");
        MyIO.print("<table>(" + pattern[24] + ") ");
        MyIO.println(name);
    }
    
    public static int[] resetVector(int[] vet){
        for (int i = 0; i < vet.length; i++) {
            vet[i] = 0;
        }
        return vet;
    }

     public static void main(String[] args){
        String site = new String();
        String siteCode = new String();
        String name = new String();
        int[] patternCounter = new int[25]; 

        

        name = MyIO.readLine();
        while (!isFim(name)) {
            site = MyIO.readLine();
            siteCode = getHtml(site);
            patternCounter = processCode(siteCode);
            printResults(patternCounter, name);
            name = MyIO.readLine();
        }
    }
}



class MyIO {

   private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in, Charset.forName("ISO-8859-1")));
   private static String charset = "ISO-8859-1";

   public static void setCharset(String charset_){
      charset = charset_;
      in = new BufferedReader(new InputStreamReader(System.in, Charset.forName(charset)));
   }

   public static void print(){
   }

   public static void print(int x){
      try {
         PrintStream out = new PrintStream(System.out, true, charset);
         out.print(x);
      }catch(UnsupportedEncodingException e){ System.out.println("Erro: charset invalido"); }
   }
   
   public static void print(float x){
      try {
         PrintStream out = new PrintStream(System.out, true, charset);
         out.print(x);
      }catch(UnsupportedEncodingException e){ System.out.println("Erro: charset invalido"); }
   }
   
   public static void print(double x){
      try {
         PrintStream out = new PrintStream(System.out, true, charset);
         out.print(x);
      }catch(UnsupportedEncodingException e){ System.out.println("Erro: charset invalido"); }
   }

   public static void print(String x){
      try {
         PrintStream out = new PrintStream(System.out, true, charset);
         out.print(x);
      }catch(UnsupportedEncodingException e){ System.out.println("Erro: charset invalido"); }
   }

   public static void print(boolean x){
      try {
         PrintStream out = new PrintStream(System.out, true, charset);
         out.print(x);
      }catch(UnsupportedEncodingException e){ System.out.println("Erro: charset invalido"); }
   }

   public static void print(char x){
      try {
         PrintStream out = new PrintStream(System.out, true, charset);
         out.print(x);
      }catch(UnsupportedEncodingException e){ System.out.println("Erro: charset invalido"); }
   }

   public static void println(){
   }

   public static void println(int x){
      try {
         PrintStream out = new PrintStream(System.out, true, charset);
         out.println(x);
      }catch(UnsupportedEncodingException e){ System.out.println("Erro: charset invalido"); }
   }

   public static void println(float x){
      try {
         PrintStream out = new PrintStream(System.out, true, charset);
         out.println(x);
      }catch(UnsupportedEncodingException e){ System.out.println("Erro: charset invalido"); }
   }
   
   public static void println(double x){
      try {
         PrintStream out = new PrintStream(System.out, true, charset);
         out.println(x);
      }catch(UnsupportedEncodingException e){ System.out.println("Erro: charset invalido"); }
   }

   public static void println(String x){
      try {
         PrintStream out = new PrintStream(System.out, true, charset);
         out.println(x);
      }catch(UnsupportedEncodingException e){ System.out.println("Erro: charset invalido"); }
   }

   public static void println(boolean x){
      try {
         PrintStream out = new PrintStream(System.out, true, charset);
         out.println(x);
      }catch(UnsupportedEncodingException e){ System.out.println("Erro: charset invalido"); }
   }

   public static void println(char x){
      try {
         PrintStream out = new PrintStream(System.out, true, charset);
         out.println(x);
      }catch(UnsupportedEncodingException e){ System.out.println("Erro: charset invalido"); }
   }

   public static void printf(String formato, double x){
      try {
         PrintStream out = new PrintStream(System.out, true, charset);
         out.printf(formato, x);// "%.2f"
      }catch(UnsupportedEncodingException e){ System.out.println("Erro: charset invalido"); }
   }

   public static double readDouble(){
      double d = -1;
      try{
         d = Double.parseDouble(readString().trim().replace(",","."));
      }catch(Exception e){}
      return d;
   }

   public static double readDouble(String str){
      try {
         PrintStream out = new PrintStream(System.out, true, charset);
         out.print(str);
      }catch(UnsupportedEncodingException e){ System.out.println("Erro: charset invalido"); }
      return readDouble();
   }

   public static float readFloat(){
      return (float) readDouble();
   }

   public static float readFloat(String str){
      return (float) readDouble(str);
   }

   public static int readInt(){
      int i = -1;
      try{
         i = Integer.parseInt(readString().trim());
      }catch(Exception e){}
      return i;
   }

   public static int readInt(String str){
      try {
         PrintStream out = new PrintStream(System.out, true, charset);
         out.print(str);
      }catch(UnsupportedEncodingException e){ System.out.println("Erro: charset invalido"); }
      return readInt();
   }

   public static String readString(){
      String s = "";
      char tmp;
      try{
         do{
            tmp = (char)in.read();
            if(tmp != '\n' && tmp != ' ' && tmp != 13){
               s += tmp;
            }
         }while(tmp != '\n' && tmp != ' ');
      }catch(IOException ioe){
         System.out.println("lerPalavra: " + ioe.getMessage());
      }
      return s;
   }

   public static String readString(String str){
      try {
         PrintStream out = new PrintStream(System.out, true, charset);
         out.print(str);
      }catch(UnsupportedEncodingException e){ System.out.println("Erro: charset invalido"); }
      return readString();
   }

   public static String readLine(){
      String s = "";
      char tmp;
      try{
         do{
            tmp = (char)in.read();
            if(tmp != '\n' && tmp != 13){
               s += tmp;
            }
         }while(tmp != '\n');
      }catch(IOException ioe){
         System.out.println("lerPalavra: " + ioe.getMessage());
      }
      return s;
   }

   public static String readLine(String str){
      try {
         PrintStream out = new PrintStream(System.out, true, charset);
         out.print(str);
      }catch(UnsupportedEncodingException e){ System.out.println("Erro: charset invalido"); }
      return readLine();
   }

   public static char readChar(){
      char resp = ' ';
      try{
         resp  = (char)in.read();
      }catch(Exception e){}
      return resp;
   }

   public static char readChar(String str){
      try {
         PrintStream out = new PrintStream(System.out, true, charset);
         out.print(str);
      }catch(UnsupportedEncodingException e){ System.out.println("Erro: charset invalido"); }
      return readChar();
   }

   public static boolean readBoolean(){
      boolean resp = false;
      String str = "";

      try{
         str = readString();
      }catch(Exception e){}

      if(str.equals("true") || str.equals("TRUE") || str.equals("t") || str.equals("1") || 
            str.equals("verdadeiro") || str.equals("VERDADEIRO") || str.equals("V")){
         resp = true;
            }

      return resp;
   }

   public static boolean readBoolean(String str){
      try {
         PrintStream out = new PrintStream(System.out, true, charset);
         out.print(str);
      }catch(UnsupportedEncodingException e){ System.out.println("Erro: charset invalido"); }
      return readBoolean();
   }

   public static void pause(){
      try{
         in.read();
      }catch(Exception e){}
   }

   public static void pause(String str){
      try {
         PrintStream out = new PrintStream(System.out, true, charset);
         out.print(str);
      }catch(UnsupportedEncodingException e){ System.out.println("Erro: charset invalido"); }
      pause();
   }
}
