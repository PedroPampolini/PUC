/**
 Autor: Pedro Pampolini Mendicino
 Criação: 11/02/2022    Última atualização: 11/02/2022
 Objetivo do Programa:  Mostra os elementos contidos em um array;
 */
public class Exercicio6 {
    public static String toString(int[] vet){
        String text = new String();     //String que terá os valores do array
        text += "[";    //Abertura de colchetes
        //Escreve o primeiro item o array na String
        if (text.length() > 0) {
            text += "" + vet[0];
        }

        //Escreve o restante dos elementos do array
        for (int i = 1; i < vet.length; i++) {
            text += ", " + vet[i];
        }
        text += "]";    //Fecha a string

        return text;
    }
    public static void main(String[] args) {
        int[] vet = {10, 5, 8, 2, 8};       //Inicializa o vetor
        MyIO.println(toString(vet));        //Imprime na tela o vetor convertido para string
    }
}
