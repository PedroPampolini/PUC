/**
 Autor: Pedro Pampolini Mendicino
 Criação: 11/02/2022    Última atualização: 11/02/2022
 Objetivo do Programa:  Um método que recebe uma string conta quantos caracteres possui e quantos são
 maiúsculos
 */

public class Exercicio2 {
    public static void main(String[] args) {
        int upper = 0;
        String texto;
        texto = MyIO.readLine("Digite a frase: ");

        for (int i = 0; i < texto.length(); i++) {
            if (texto.charAt(i) >= 'A' && texto.charAt(i) <= 'Z') {
                upper++;
            }
        }

        MyIO.println("A string tem " + texto.length() + " sendo " + upper + " deles maiusculos");
    }
}
