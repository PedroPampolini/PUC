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

public class TP01Q05P3 {
   //Retorna um vetor com um caracter de cada entrada, para substituir futuramente
   public static char[] getNumEntradas(String s){
      //Converte o primeiro caractere em inteiro, para definir quantas entradas terá
      int quantIn = (int)(s.charAt(0) - 48);

      //Cria o vetor de caracteres com a quantidade necessária de itens
      char[] entradas = new char[quantIn];
      //Converte cada item em um espaço separado no vetor de entradas
      for (int i = 0; i < entradas.length; i++) {
         entradas[i] = s.charAt(2*i+2);
      }
      return entradas;
    }
   //Substitui as entradas A B C e D pelo respectivo valor
   public static String replaceIn(String exp, char[] input){
        String newExp = new String();  //String que será retornada
        //Troca de caracteres
        for (int i = 0; i < exp.length(); i++) {
            if (exp.charAt(i) == 'A') {
                newExp += input[0];
            }
            else if (exp.charAt(i) == 'B') {
                newExp += input[1];
            }
            else if (exp.charAt(i) == 'C') {
                newExp += input[2];
            }
            else if (exp.charAt(i) == 'D') {
                newExp += input[3];
            }
            //Exclui os espaços e os numerais iniciais de definição de entrada
            else if (!(exp.charAt(i) == ' ' || (exp.charAt(i) >= '0' && exp.charAt(i) <= '9'))) {
                newExp += exp.charAt(i);
            }
        }
        
        return newExp;
    }
   //Retorna uma substring de s, entre min inclusive e max exclusive 
   public static String subString(String s, int min, int max){
        String newStr = new String();
        //Soma os caracteres da string para a nova string
        for (int i = min; i < max; i++) {
            newStr += s.charAt(i);
        }
        return newStr;
    }
   //Converte o input fechado, como (1,0,1) para (true,false,true)
   public static boolean[] convertInput(String input) {
      //Array booleano com a quantidade necessária de itens
      boolean[] output = new boolean[(input.length() - 1) / 2];
      int outIndex = 0;    //Index de contagem do array booleano
      //Percorre a string em busca de valores 0 ou 1
      for (int i = 0; i < input.length(); i++) {
         //Escreve true para valores de 1
         if (input.charAt(i) == '1') {
            output[outIndex] = true;
            outIndex++;
            //Escreve false para valores de 0
         } else if (input.charAt(i) == '0') {
            output[outIndex] = false;
            outIndex++;
         }
      }
      return output;
   }
   //Compara duas strings e retorna se são iguais ou não
   public static boolean stringEquals(String s1, String s2){
      boolean res = true;
      //percorre N vezes, sendo N o comprimento da menor string
      for (int i = 0; i < s1.length() && i < s2.length(); i++) {
         //Caso 1 caracter seja diferente, escreve falso e sai do laço for
         if (s1.charAt(i) != s2.charAt(i)) {
            res = false;
            i = s2.length();     //Saida do laço
         }
      }
      return res;
   }
   //Sobrescreve uma parte Procurada da string Principal, trocando por Nova
   public static String replace(String principal, String procurada, String nova){
      String newStr = new String();
      //Percorrendo todo o array principal
      for (int i = 0; i < principal.length(); i++) {
         //Caso o index + comprimento da procurada extrapolar a String principal, não irá substituir
         //e compara se o index chegou na parte procurada
         if(i + procurada.length() < principal.length() && stringEquals(procurada,subString(principal, i, i+procurada.length()))){
            //Troca cada caractere da string nova
            for (int j = 0; j < nova.length(); j++) {
               newStr += nova.charAt(j);
            }
            //Pula o resto da string procurada, para substituir por completo
            i += (procurada.length() -1);
         }
         else{
            //Caso o index não tenha chegado na procurada, ou ja tenha extrapolado o maximo da string+procurada,
            //apenas adiciona normalmente os caracteres
            newStr += principal.charAt(i);
         }
      }
      return newStr;
   }
   //Calcula cada parte separadamente
   public static boolean calcula(String expression){
      boolean res = true;  //Variável de retorno
      boolean first = true;   //Flag de primeira operação
      int abreIndex = 0;   //Index para o ( aberto, controle de operações fechadas
      String tempStr = new String();   //String que armazena as entradas
      String tempOp = new String();    //String que armazena a operação com as entradas

      for (int i = 0;i < expression.length();i++) {
         //System.out.println("Operação: " + tempOp + " res: " + res + " expression: " + expression + " abreIndex: " + abreIndex + " index: " + i);
         if (expression.charAt(i) == '(') {
            abreIndex = i;    //Irá armazenar o index do abre parentesês mais proximo do fecha parentesês seguinte
         }
         //Caso encontre o fecha parentesês, irá realizar a operação fechada (sem outras operações dentro, apenas números)
         if (expression.charAt(i) == ')') {            
            boolean[] specificInput;   //os valores da entrada
            //Uma string com os valores das entradas, como (1,0) por exemplo
            tempStr = subString(expression, abreIndex, i+1);

            //grava a string completa da operação, como and(1,0) por exemplo
            switch (expression.charAt(abreIndex -1)) {
               case 'd':   //Caso and
                  tempOp = subString(expression, abreIndex-3, i+1);
                  break;
               
               case 't':   //Caso not
                  tempOp = subString(expression, abreIndex-3, i+1);
                  break;

               case 'r':   //Caso or
                  tempOp = subString(expression, abreIndex-2, i+1);
                  break;
            }
            
            //Realiza a conta da operação fechada
            switch (expression.charAt(abreIndex -1)) {
               case 'd':       //Case and
                  if (first) {   //Caso seja a primeira operação, atribui o valor neutro da operação and
                        res = true;
                  }
                  //Converte a string de inputs em inputs booleanos para uso
                  specificInput = convertInput(tempStr);
                  for (int j = 0; j < specificInput.length; j++) {
                        res &= specificInput[j];   //realiza quantas operações forem necessárias
                  }
                  break;

               case 'r':       //Case or
                  if (first) {   //Caso seja a primeira operação, atribui o valor neutro da operação or
                        res = false;
                  }
                  //Converte a string de inputs em inputs booleanos para uso
                  specificInput = convertInput(tempStr);
                  for (int j = 0; j < specificInput.length; j++) {
                        res |= specificInput[j];   //realiza quantas operações forem necessárias
                  }
                  break;
               
               case 't':       //Case not
                  specificInput = convertInput(tempStr); //Converte o único input da not
                  res = !specificInput[0];   //Nega o único input dado
                  break;
               
               default:
                  System.out.println("ERRO");
                  break;
            }
            //Remove a expressão já processada pelo resultado dela
            expression = replace(expression, tempOp, (res)? "1" : "0");
            
            //Condicionais de saida
            if (expression.length() > 1) {
               i = -1;  //Retorna ao inicio para refazer as operações
            }
            //Caso a operação seja a menor possível, apenas uma, sai do loop e retorna o resultado
            if (stringEquals(expression, tempOp)) {
               i = expression.length();
            }
         }
      }

      return res;
    }
    public static void main(String[] args) {
        String expression = new String();
        expression = MyIO.readLine();  //Recebe a primeira entrada
        boolean result;

        while (expression.charAt(0) != '0') {
            char[] entradas = getNumEntradas(expression);   //Grava os valores em caracteres das entradas
            expression = replaceIn(expression, entradas);   //Retorna a expressão com os valores substituidos
            result = calcula(expression); //Calcula a expressão
            MyIO.println(result ? '1' : '0');   //Imprime 1 para verdadeiro e 0 para falso
            expression = MyIO.readLine(); //Requisita a proxima expressão
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
