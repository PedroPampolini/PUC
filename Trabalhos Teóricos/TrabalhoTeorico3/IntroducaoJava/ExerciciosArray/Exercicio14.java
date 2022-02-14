/**
 Autor: Pedro Pampolini Mendicino
 Criação: 11/02/2022    Última atualização: 11/02/2022
 Objetivo do Programa:  Recebe 2 arrays e mostra na tela de forma intercalada
 */
public class Exercicio14 {
    public static void main(String[] args) {
        //Declaração das variáveis e vetor de inteiros
        int max;
        int[] vet1, vet2;

        //Realiza a leitura dos valores dos vetores
        max = MyIO.readInt("Qual a quantidade de items nos vetores? ");
        vet1 = new int[max];
        vet2 = new int[max];
        for (int i = 0; i < vet1.length; i++) {
            vet1[i] = MyIO.readInt(i + " valor: ");
        }
        MyIO.println("=====Vetor 2=====");
        for (int i = 0; i < vet2.length; i++) {
            vet2[i] = MyIO.readInt(i + " valor: ");
        }       

        for (int i = 0; i < max; i++) {
                MyIO.println(vet1[i]);
                MyIO.println(vet2[i]);
        }
    }
}
