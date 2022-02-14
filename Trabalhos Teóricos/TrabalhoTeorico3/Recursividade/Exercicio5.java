/**
 Autor: Pedro Pampolini Mendicino
 Criação: 12/02/2022    Última atualização: 12/02/2022
 Objetivo do Programa:  Método que recebe uma string e retorna a quantidade de caracteres não vogais nem
 consoantes maiusculas
 */
public class Exercicio5 {
    public static boolean isLetra(char c){
        return((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'));
    }

    public static boolean isVogal(char c){
        return(c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U' || c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u');
    }

    public static boolean isConsolante(char c){
        return(isLetra(c) && !isVogal(c));
    }

    public static boolean isMaiuscula(char c){
        return(c >= 'A' && c <'Z');
    }

    public static int contaChar(String str, int pos){
        int result = 0;
        if(pos >= str.length()){
            return result;
        }
        else if (!isVogal(str.charAt(pos)) && !(isConsolante(str.charAt(pos)) && isMaiuscula(str.charAt(pos)))) {
            result = 1 + contaChar(str, pos+1);
        }
        else{
            result = contaChar(str, pos+1);
        }

        return result;
    }

    public static int contaChar(String str){
        return contaChar(str,0);
    }
    public static void main(String[] args) {
        String str = "Teste123";
        MyIO.println(contaChar(str));
    }
}
