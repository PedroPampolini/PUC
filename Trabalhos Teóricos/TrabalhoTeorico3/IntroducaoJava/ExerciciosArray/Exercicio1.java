/**
 Autor: Pedro Pampolini Mendicino
 Criação: 11/02/2022    Última atualização: 11/02/2022
 Objetivo do Programa:  Lê N valores e imprime a média e depois mostra quais ficaram acima da média;
 */

public class Exercicio1 {

    public static void main(String[] args) {
        //Declaração das variáveis
        int limite;
        double somaValores, media;
        double[] conjunto;

        //Inicialização das variáveis
        somaValores = 0;
        limite = MyIO.readInt("Digite quantos valores ira adicionar: ");
        conjunto = new double[limite];     //Vetor com todas as valores
        
        //Realiza a leitura de todas as valores e salva em um array e soma à variável da soma
        for (int i = 0; i < limite; i++) {
            conjunto[i] = MyIO.readDouble("Digite o " + (i+1) + " valor: ");
            somaValores += conjunto[i];
        }

        media = somaValores/limite;   //Calcula a média
        
        MyIO.println("A media do conjunto foi " + media + " pontos");      //Imprime a média da turma
        //Percorre todo o array e imprime os valores que ficaram acima da média
        for (int i = 0; i < limite; i++) {
            if (conjunto[i] > media) {
                MyIO.println("O valor "+ conjunto[i] +" do index " + i + " ficou acima da media");
            }
        }
        
    }
}