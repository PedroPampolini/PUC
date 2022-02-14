/**
 Autor: Pedro Pampolini Mendicino
 Criação: 11/02/2022    Última atualização: 11/02/2022
 Objetivo do Programa:  Um programa que recebe 2 valores, calcula a raíz cúbica do menor e o logarítmo do menor usando o maior como base
 */

import java.lang.Math.*;
public class Exercicio7 {
    public static void main(String[] args) {
        double num1, num2;  //Declaração de variáveis

        //Leitura das variáveis
        num1 = MyIO.readDouble("Digite o valor do primeiro numero: ");
        num2 = MyIO.readDouble("Digite o valor do segundo numero: ");

        //Estrutura de decisão e exibição dos resultados na tela
        if(num1 > num2){
            MyIO.println("Raiz cubica do menor: " + Math.cbrt(num2) + " Logaritimo do maior: " + (Math.log(num2)/Math.log(num1)));
        }
        else{
            MyIO.println("Raiz cubica do menor: " + Math.cbrt(num1) + " Logaritimo do maior: " + (Math.log(num1)/Math.log(num2)));
        }
    }
}
