/**
 Autor: Pedro Pampolini Mendicino
 Criação: 12/02/2022    Última atualização: 12/02/2022
 Objetivo do Programa:  Método que recebe 2 inteiros e retorna recursivamente a multiplicação deles
 */
public class Exercicio1 {
    public static int multiplica(int num1, int num2){
        if (num2 == 1) {
            return num1;
        }
        else{
            num1 += multiplica(num1, num2-1); 
        }
        return num1;
    }
    public static void main(String[] args) {
        int num1,num2;
        num1 = 5;
        num2 = 13;
        MyIO.println(multiplica(num1,num2));
    }
}