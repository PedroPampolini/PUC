/**
 Autor: Pedro Pampolini Mendicino
 Criação: 10/02/2022    Última atualização: 10/02/2022
 Objetivo do Programa:  Um programa que recebe 3 números e ordena mostrando a ordem do menor para o maior
 */

public class Exercicio2 {
    public static void main(String[] args) {
        int num1, num2, num3, maior;    //Variáveis dos 3 números digitados e do maior número analisado 
        int[] ordenado = new int[3];    //Vetor que armazena a ordem dos números

        //Leitura das variáveis
        num1 = MyIO.readInt("Digite o valor do primeiro numero: ");
        num2 = MyIO.readInt("Digite o valor do segundo numero: ");
        num3 = MyIO.readInt("Digite o valor do terceiro numero: ");

        //Laço de ordenação
        for (int i = 0; i < ordenado.length; i++) {
            if (num1 > num2 && num1 > num3) {
                maior = num1;
                num1 = Integer.MIN_VALUE;
            }
            else if (num2 > num3) {
                maior = num2;
                num2 = Integer.MIN_VALUE;
            }
            else{
                maior = num3;
                num3 = Integer.MIN_VALUE;
            }
            ordenado[i] = maior;
        }
        
        //Imprime na tela o resultado
        System.out.println("Maior: " + ordenado[0] + " Meio: " + ordenado[1] + " Menor: " + ordenado[2]);

    }
}
