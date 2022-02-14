package ExerciciosDeRevisao;
import java.util.*;
/**
 Autor: Pedro Pampolini Mendicino
 Criação: 09/02/2022    Última atualização: 09/02/2022
 Objetivo do Programa:  um método que recebe um array de inteiros e retorna se o valor solicitado está ou não presente nele
 */
public class Exercicio1 {

    public static boolean isThere(int number, int[] vetor){
        //Para cada item do vetor, compara se ele é igual ao número entregue 
        for (int i = 0; i < vetor.length; i++) {
            if (vetor[i] == number) {
                return true;
            }
        }
        return false;
    }
    public static void main(String[] args) {
        int[] vetor = {54,3,7,3,6,8,2,3,4,87,9,2};  //Declaração do vetor de exemplo
        int number;     //Declaração do número que será lido
        
        //Leitura da variável
        Scanner keyboard = new Scanner(System.in);
        number = keyboard.nextInt();

        //chamada da função e impressão do resultado na tela
        System.out.println(isThere(number, vetor));
        keyboard.close();
    }
}