package ExerciciosDeRevisao;
/**
 Autor: Pedro Pampolini Mendicino
 Criação: 09/02/2022    Última atualização: 09/02/2022
 Objetivo do Programa:  Um método que recebe um array e mostra na tela o maior e o menor número;
 */
public class Exercicio3 {
    public static void extremes(int[] vetor){
        //Declaração e inicialização de variáveis de mínimo e máximo
        int max, min;
        min = Integer.MAX_VALUE;
        max = Integer.MIN_VALUE;

        //Percorre todo o vetor para encontrar o maior e o menor valor
        for (int i = 0; i < vetor.length; i++) {
            if (vetor[i] > max) {
                max = vetor[i];
            }
            if (vetor[i] < min) {
                min = vetor[i];
            }
        }

        //Imprime na tela os valores
        System.out.println("Maximo: " + max + " Minimo: " + min);
    }
    public static void main(String[] args) {
        int[] vetor = {0,1,2,3,4,5,6,7};    //inicializa os vetores
        extremes(vetor);    //Chamada de função
    }
}
