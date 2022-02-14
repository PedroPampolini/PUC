/**
 Autor: Pedro Pampolini Mendicino
 Criação: 10/02/2022    Última atualização: 10/02/2022
 Objetivo do Programa:  Um programa que recebe 10 números e mostra na tela o menor
 */

public class Exercicio3 {
    public static void main(String[] args) {
        //Declaração das variáveis de menor valor e a atual analisada
        int menor = Integer.MAX_VALUE;
        int atual;

        //Laço de leitura dos 10 valores
        for (int i = 0; i < 10; i++) {
            atual = MyIO.readInt("Digite o " + (i + 1) + " numero: ");
            if (atual < menor) {
                menor = atual;
            }
        }

        //Imprime o resultado
        MyIO.println("O menor numero foi " + menor);
    }
}
