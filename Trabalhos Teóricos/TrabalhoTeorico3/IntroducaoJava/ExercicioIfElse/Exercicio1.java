/**
 Autor: Pedro Pampolini Mendicino
 Criação: 10/02/2022    Última atualização: 10/02/2022
 Objetivo do Programa:  um programa que recebe 3 valores de triângulo e retorna qual dos 3 casos pertence 
 */
public class Exercicio1 {

    public static void main(String[] args) {
        //Declaração e leitura das variáveis
        int lado1, lado2, lado3;
        lado1 = MyIO.readInt("Digite o valor do lado 1: ");
        lado2 = MyIO.readInt("Digite o valor do lado 2: ");
        lado3 = MyIO.readInt("Digite o valor do lado 3: ");

        //Comparação e retorno de resultado
        if (lado1 == lado2 && lado1 == lado3) {
            MyIO.println("Triangulo Equilatero");
        }
        else if (lado1 != lado2 && lado1 != lado3 && lado2 != lado3) {
            MyIO.println("Triangulo Escaleno");
        } else {
            MyIO.println("Triangulo Isosceles");
        }
    }
}