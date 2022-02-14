/**
 Autor: Pedro Pampolini Mendicino
 Criação: 11/02/2022    Última atualização: 11/02/2022
 Objetivo do Programa:  Lê um array e calcula a soma do 1 com o ultimo, o 2 com o penúlimo...
 */

public class Exercicio12 {
    public static void main(String[] args) {
        //Declaração das variáveis e vetor de inteiros
        int maximo;
        int[] vet;

        //Realiza a leitura dos valores do vetor
        maximo = MyIO.readInt("Qual a quantidade de items no vetor? ");
        vet = new int[maximo];
        for (int i = 0; i < vet.length; i++) {
            vet[i] = MyIO.readInt(i + " valor: ");
        }

        //Realiza a operação dependendo do comprimento do vetor
        if (vet.length%2 ==0) {
            for (int i = 0; i < vet.length/2; i++) {
                MyIO.println("" + i + " + " + (vet.length-1-i) + " = " + (vet[i] + vet[vet.length-1-i]));
            } 
        }
        else{
            for (int i = 0; i < 1 + vet.length/2; i++) {
                MyIO.println("" + i + " + " + (vet.length-1-i) + " = " + (vet[i] + vet[vet.length-1-i]));
            }
        }
        
    }
}
