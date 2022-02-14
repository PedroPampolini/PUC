/**
 Autor: Pedro Pampolini Mendicino
 Criação: 11/02/2022    Última atualização: 11/02/2022
 Objetivo do Programa:  Um método que recebe uma string e um caractere para verificar se ele esta na string
 */

 public class Exercicio1 {

    public static boolean include(char letra, String texto){
        for (int i = 0; i < texto.length(); i++) {
            if (texto.charAt(i) == letra) {
                return true;
            }
        }
        return false;
    }
 
     public static void main(String[] args) {
        String texto;
        char letra;
        texto = MyIO.readLine("Digite a frase: ");
        letra = MyIO.readChar("Digite o caractere: ");

        MyIO.println("A letra '" + letra + "'"+ (include(letra,texto)? "" : " nao") + " esta inclusa no texto" );
     }
 }