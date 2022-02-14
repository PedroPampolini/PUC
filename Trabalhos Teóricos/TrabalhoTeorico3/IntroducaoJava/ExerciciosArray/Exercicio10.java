/**
 Autor: Pedro Pampolini Mendicino
 Criação: 11/02/2022    Última atualização: 11/02/2022
 Objetivo do Programa:  Lê um array e mostra a posição do menor elemento;
 */
public class Exercicio10 {
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
        //Declaração das variáveis e vetor de inteiros
        int maximo;
        int index;
        int[] elementos;

        maximo = MyIO.readInt("Qual a quantidade de items no vetor? ");
        elementos = new int[maximo];
        for (int i = 0; i < elementos.length; i++) {
            elementos[i] = MyIO.readInt(i + " elemento: ");
        }

        index = getMinorIndex(elementos);   //Chama método

        MyIO.println(index);
    }
}
