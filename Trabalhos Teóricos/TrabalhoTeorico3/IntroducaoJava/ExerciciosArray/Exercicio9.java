/**
 Autor: Pedro Pampolini Mendicino
 Criação: 11/02/2022    Última atualização: 11/02/2022
 Objetivo do Programa:  Lê N valores e caso o tamanho seja par, realizar a soma do 1º com o 2º, 3º com 4º...
 */

public class Exercicio9 {
    public static void main(String[] args) {
        //Declaração de variavel e do vetor
        int maximo;
        int[] vet;

        //Incialização
        maximo = MyIO.readInt("Digite quantos numeros deseja: ");
        vet = new int[maximo];

        //Leitura dos valores do vetor
        for (int i = 0; i < vet.length; i++) {
            vet[i] = MyIO.readInt(i + " valor: ");
        }

        //Realiza as somas
        if (maximo%2 == 0) {
            for (int i = 0; i < vet.length; i+=2) {
                MyIO.println(vet[i] + vet[i+1]);
            }
        }
    }
}
