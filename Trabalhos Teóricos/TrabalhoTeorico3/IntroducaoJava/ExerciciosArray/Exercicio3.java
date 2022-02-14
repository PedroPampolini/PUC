/**
 Autor: Pedro Pampolini Mendicino
 Criação: 11/02/2022    Última atualização: 11/02/2022
 Objetivo do Programa:  Lê um array e mostra a posição do menor elemento;
 */
public class Exercicio3 {
    public static int getMinorIndex(int[] vet){
        int minIndex = 0;   //Index de referência
        //Percorre todo o vetor a procura do menor valor
        for (int i = 0; i < vet.length; i++) {
            //Compara se o menor valor atual é maior que o valor analisado
            if (vet[i] < vet[minIndex]) {
                minIndex = i;
            }
        }
        return minIndex;
    }

    public static void main(String[] args) {
        //Declaração do vetor de inteiros
        int[] elementos = {5,3,67,6,4,3,8,9,1,3,8,3};
        //Declaração do index
        int index;

        index = getMinorIndex(elementos);   //Chama método

        MyIO.println(index);
    }
}
