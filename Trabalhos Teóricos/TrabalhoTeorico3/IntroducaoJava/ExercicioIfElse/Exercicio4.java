/**
 Autor: Pedro Pampolini Mendicino
 Criação: 10/02/2022    Última atualização: 10/02/2022
 Objetivo do Programa:  Um programa que recebe 2 números dependendo do valor deles, realiza uma operação
 diferente com eles
 */

public class Exercicio4 {
    public static void main(String[] args) {
        //Declaração de variáveis
        int num1, num2, resultado = 0;

        //Leitura de valores
        num1 = MyIO.readInt("Digite o valor do primeiro numero: ");
        num2 = MyIO.readInt("Digite o valor do segundo numero: ");

        //Comparações de valores
        if(num1 > 45 || num2 > 45){
            resultado = num1 + num2;
        }
        else if(num1 > 20 && num2 > 20){
            if(num1 > num2){
                resultado = num1 - num2;
            }
            else{
                resultado = num2 - num1;
            }
        }else if((num1 < 10 && num2 != 0) || (num2 < 10 && num1 != 0)){
            if(num1 < 10 && num2 != 0){
                resultado = num1 / num2;
            }
            else{
                resultado = num2 / num1;
            }
        }
        else{
            MyIO.println("Pedro");
        }

        //Exibição do resultado
        MyIO.println("Resultado: " + resultado);

    }
}
