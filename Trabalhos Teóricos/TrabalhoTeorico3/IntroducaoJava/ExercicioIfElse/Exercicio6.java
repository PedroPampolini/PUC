/**
 Autor: Pedro Pampolini Mendicino
 Criação: 11/02/2022    Última atualização: 11/02/2022
 Objetivo do Programa:  Um programa que recebe o salário e uma prestação, caso o valor da prestação seja 
 maior que 40% do salario, deverá mostrar que ela não será concedida
 */

public class Exercicio6 {
    public static void main(String[] args) {
        //Declaração das variáveis
        double salario, prestacao;

        //Leitura de valores
        salario = MyIO.readDouble("Digite o valor do salario: ");
        prestacao = MyIO.readDouble("Digite o valor da prestacao: ");

        //Comparação e resultados
        if (prestacao > (salario * 0.4)) {
            MyIO.println("O emprestimo nao sera concedido");
        }
        else{
            MyIO.println("O emprestimo sera concedido");
        }
    }
}
