/**
 Autor: Pedro Pampolini Mendicino
 Criação: 11/02/2022    Última atualização: 11/02/2022
 Objetivo do Programa:  Lê um número N e imprime os N primeiros númeos primos
 */
public class Exercicio2 {
    public static void main(String[] args) {
        //Declaração e leitura da variável de limite e index
        int limite, i;
        i = 0;
        limite = MyIO.readInt("Digite quantos numeros deseja imprimir: ");

        //Imprime na tela os N números impares
        while(i < limite) {
            MyIO.println(i*2 + 1);
            i++;
        }
    }
}
