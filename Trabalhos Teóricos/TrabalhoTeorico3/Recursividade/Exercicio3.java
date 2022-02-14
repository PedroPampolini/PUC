/**
 Autor: Pedro Pampolini Mendicino
 Criação: 12/02/2022    Última atualização: 12/02/2022
 Objetivo do Programa:  Método que recebe uma string e verifica recursivamente se é um palindromo ou não
 */
public class Exercicio3 {
    public static boolean isPalindromo(String s,int pos){
        boolean result;

        if (pos >= s.length()) {
            result = true;
        }
        else if(s.charAt(pos) != s.charAt(s.length() - 1 - pos)){
            result = false;
        }
        else{
            result = isPalindromo(s,pos+1);
        }
        return result;
    }
    
    public static boolean isPalindromo(String s){
        return isPalindromo(s,0);
    }
    public static void main(String[] args) {
        String str = "arara";

        MyIO.println(isPalindromo(str));
    }
}
