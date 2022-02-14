/**
 Autor: Pedro Pampolini Mendicino
 Criação: 11/02/2022    Última atualização: 11/02/2022
 Objetivo do Programa:  Um método que recebe uma string conta quantos caracteres possui e quantos são
 maiúsculos
 */

public class Exercicio3 {
    public static int retornA(String s){
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'A') {
                return i;
            }
        }
        return -1;
    }
    public static void main(String[] args) {
        String texto;
        texto = MyIO.readLine("Digite a frase: ");
        int index = retornA(texto);

        if (index > 0) {
            MyIO.println("O caractere 'A' esta na posicao " + index);
        }
        else{
            MyIO.println("O caractere 'A' nao esta na string");
        }
        
    }
}
