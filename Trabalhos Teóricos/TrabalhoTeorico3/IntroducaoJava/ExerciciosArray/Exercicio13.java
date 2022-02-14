/**
 Autor: Pedro Pampolini Mendicino
 Criação: 11/02/2022    Última atualização: 11/02/2022
 Objetivo do Programa:  Lê um array e calcula a dos múltiplos de 3 do array
 */
public class Exercicio13 {
    public static void main(String[] args) {
        //Declaração das variáveis e vetor de inteiros
        int maximo, soma;
        int[] vet;

        //Realiza a leitura dos valores do vetor
        soma = 0;
        maximo = MyIO.readInt("Qual a quantidade de items no vetor? ");
        vet = new int[maximo];
        for (int i = 0; i < vet.length; i++) {
            vet[i] = MyIO.readInt(i + " valor: ");
        }

        //Percorre todo o vetor, somando os múltiplos de 3
        for (int i = 0; i < vet.length; i++) {
            if(vet[i]%3 == 0){
                soma += vet[i];
            }
        }

        MyIO.println("Soma: " + soma);
    }
}
