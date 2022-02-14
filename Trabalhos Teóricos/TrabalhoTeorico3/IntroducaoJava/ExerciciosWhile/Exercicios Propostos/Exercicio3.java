/**
 Autor: Pedro Pampolini Mendicino
 Criação: 11/02/2022    Última atualização: 11/02/2022
 Objetivo do Programa:  Mostra os N primeiros números da sequência: 1, 5, 12, 16, 23, 27, 34.
 */

public class Exercicio3 {
    public static void main(String[] args) {
        //Declaração de variáveis
        int limite, numero,i;
        numero = 1; //Inicializa a variável com o primeiro valor
        i = 0; //Inicializa a variável index
        //Leitura da variável
        limite = MyIO.readInt("Digite a quantidade de numeros: ");

        //Realiza a operação
        while(i < limite) {
            MyIO.println(numero);   //Mostra o número atual
            //Alterna entre a soma que irá fazer
            if (i%2 == 0) {
                numero += 4;
            } else {
                numero += 7;
            }
            i++;
        }
    }
}
