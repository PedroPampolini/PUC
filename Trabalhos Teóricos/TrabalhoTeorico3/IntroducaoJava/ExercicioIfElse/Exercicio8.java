/**
 Autor: Pedro Pampolini Mendicino
 Criação: 11/02/2022    Última atualização: 11/02/2022
 Objetivo do Programa:  Um programa que recebe 10 números e imprime o maior e o menor deles na tela
 */

public class Exercicio8 {
    public static void main(String[] args) {
        int maior, menor,atual;     //Declaração das variáveis

        //Inicialização das variáveis
        maior = Integer.MIN_VALUE;
        menor = Integer.MAX_VALUE;

        //Recebe os 10 valores
        for(int i = 0; i < 10; i++){
            atual = MyIO.readInt("Digite o " + i + " numero: ");
            //Compara se o número atual é maior ou menor que o analisado
            if(atual > maior){
                maior = atual;
            }
            if(atual < menor){
                menor = atual;
            }
        }

        MyIO.println("O menor valor foi " + menor + " e o maior valor foi " + maior);
    }
}
