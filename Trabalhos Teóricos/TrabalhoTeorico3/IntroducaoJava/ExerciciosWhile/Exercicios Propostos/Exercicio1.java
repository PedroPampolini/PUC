/**
 Autor: Pedro Pampolini Mendicino
 Criação: 11/02/2022    Última atualização: 11/02/2022
 Objetivo do Programa:  Mostra os 10 primeiros números inteiros e positivos
 */
public class Exercicio1 {
    public static void main(String[] args) {
        int i = 0;  //Declara e inicializa a variável de index
        //Imprime os 10 primeros númeor
        while (i < 10) {
            MyIO.println(i);
            i++;
        }
    }
}