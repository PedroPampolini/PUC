/**
 Autor: Pedro Pampolini Mendicino
 Criação: 12/02/2022    Última atualização: 12/02/2022
 Objetivo do Programa:  Método que recebe um vetor de caracteres e retorna quantas vogais existem
 */
public class Exercicio4 {
    public static boolean isVogal(char c){
        return(c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U' || c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u');
    }


    public static int countVogal(char[] str, int pos){
        int result = 0;
        if (pos >= str.length) {
            return result;
        }
        else if(isVogal(str[pos])){
            result = 1 + countVogal(str, pos+1);
        }
        else{
            result = countVogal(str, pos+1);
        }
        return result;
    }


    public static int countVogal(char[] str){
        return countVogal(str,0);
    }

    public static void main(String[] args) {
        char[] str = new char[]{'A', 'b', 'e', 'c', 'e', 'd', 'a', 'r', 'i', 'o'};
        MyIO.println(countVogal(str));
    }
}
