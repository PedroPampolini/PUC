/**
 Autor: Pedro Pampolini Mendicino
 Criação: 11/02/2022    Última atualização: 11/02/2022
 Objetivo do Programa:  Imprime o log na base de 10 de 1 a 10
 */
import java.lang.Math;

public class ExemploWhile02 {
    public static void main(String[] args) {
        //imprime na tela os 10 primeiro logaritimos na base 10
        for (int i = 1; i <= 10; i++) {
            System.out.println(Math.log(i)/Math.log(10));
        }
    }
}
