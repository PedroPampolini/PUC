package ExerciciosDeRevisao;
import java.util.*;
/**
 Autor: Pedro Pampolini Mendicino
 Criação: 09/02/2022    Última atualização: 09/02/2022
 Objetivo do Programa:  um método que recebe um array de inteiros ordenado em order crescente e retorna se o valor solicitado está ou não
 presente nele
 */
public class Exercicio2 {
    public static boolean isThere(int number, int[] vetor){

        //Declaração e inicialização das variáveis
        int right, left, middle;
        right = vetor.length - 1;
        left = 0;
        middle = right/2;
        
        //Enquanto o ponteiro da direita e da esquerda não se encontrarem irá procurar o número
        while (right >= left) {
            //Caso o item procurado seja igual o item do meio analisado, retorna verdadeiro
            if (vetor[middle] == number) {
                return true;
            }
            //Caso o item procurado seja maior que o item do meio analisado, reposiciona o vetor da esquerda
            else if (vetor[middle] < number) {
                left = middle + 1;
            }
            //Caso o item procurado seja menor que o item do meio analisado, reposiciona o vetor da direita
            else{
                right = middle - 1;
            }
            middle = (right + left)/2;
        }
        return false;
    }
    public static void main(String[] args) {
        int[] vetor = {0,1,2,3,4,5,6,7};    //Declaração do vetor ordenado de exemplo
        int number;     //Declaração da variável que será procurada

        //Leitura da variável
        Scanner keyboard = new Scanner(System.in);
        number = keyboard.nextInt();

        //Chamada da função e exibição do resultado na tela
        System.out.println(isThere(number, vetor));
        keyboard.close();
    }
}
