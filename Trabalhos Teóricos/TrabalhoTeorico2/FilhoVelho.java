import java.io.*;
import java.nio.charset.*;

public class FilhoVelho {

   public static int retornaVelho(int[] idades){
      idades[3] = idades[0] - idades[1] - idades[2];
      int velho = 0;
      for (int i = 1; i < idades.length; i++) {
         if(velho < idades[i]){
            velho = idades[i];
         }
      }
      return velho;
   }

    public static void main (String[] args){
        int[] idades = new int[4];
        idades[0] = MyIO.readInt();
        while (idades[0] > 0) {
           idades[1] = MyIO.readInt();
           idades[2] = MyIO.readInt();
           MyIO.println(retornaVelho(idades));
           idades[0] = MyIO.readInt();
        }
    }
}

/*
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
 */