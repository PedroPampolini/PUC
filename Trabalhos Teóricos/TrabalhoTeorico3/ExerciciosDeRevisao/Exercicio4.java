package ExerciciosDeRevisao;
import java.util.*;
/**
 Autor: Pedro Pampolini Mendicino
 Criação: 09/02/2022    Última atualização: 09/02/2022
 Objetivo do Programa:  Um método que recebe um array e mostra na tela o maior e o menor número;
 */
public class Exercicio4 {
    public static void extremes(int[] vetor){
        //Declaração e inicialização das variáveis
        int max, min;
        min = 0;
        max = 0;

        //Percorre todo o vetor para procura do maior e menor valor
        for (int i = 0; i < vetor.length - 1; i++) {
            for (int j = (i+1); j < vetor.length; j++) {
                if (vetor[j] > vetor[max]) {
                    max = j;        //Salva o indice do maior valor
                }
                if (vetor[j] < vetor[min]) {
                    min = j;        //Salva o indice do menor valor
                }
            }
        }

        //Imprime na tela os resultados
        System.out.println("Maximo: " + vetor[max] + " Minimo: " + vetor[min]);
    }
    public static void main(String[] args) {
        int[] vetor = {4,5,6,7};
        extremes(vetor);
    }
}
