/**
 Autor: Pedro Pampolini Mendicino
 Criação: 11/02/2022    Última atualização: 11/02/2022
 Objetivo do Programa:  Recebe 2 arrays e mostra na tela de forma intercalada
 */
public class Exercicio15 {
    public static void main(String[] args) {
        //Declaração das variáveis e vetor de inteiros
        int max1,max2;
        int[] vet1, vet2;

        //Realiza a leitura dos valores dos vetores
        max1 = MyIO.readInt("Qual a quantidade de items no vetor1? ");
        vet1 = new int[max1];
        
        for (int i = 0; i < vet1.length; i++) {
            vet1[i] = MyIO.readInt(i + " valor: ");
        }
        MyIO.println("=====Vetor 2=====");
        max2 = MyIO.readInt("Qual a quantidade de items no vetor1? ");
        vet2 = new int[max2];
        for (int i = 0; i < vet2.length; i++) {
            vet2[i] = MyIO.readInt(i + " valor: ");
        }       

        for (int i = 0; i < max1 || i < max2; i++) {
            if (i < max1) {
                MyIO.println(vet1[i]);
            }
            if (i < max2) {
                MyIO.println(vet2[i]);
            }                
        }
    }
}
