/**
 Autor: Pedro Pampolini Mendicino
 Criação: 11/02/2022    Última atualização: 11/02/2022
 Objetivo do Programa:  Recebe nota de 5 alunos, e escreve na tela a soma, o menor valor e a média
 */
public class Exercicio7 {
    public static void main(String[] args) {
        //Declara e incializa as variáveis e o vetor
        double soma = 0;
        double min = Double.MAX_VALUE;
        double[] notas = new double[5];

        //Realiza as 5 leituras e a comparação
        for (int i = 0; i < 5; i++) {
            notas[i] = MyIO.readDouble("Digite a " + i + " nota: ");    //Leitura da nota
            soma += notas[i];   //Soma a nota atual à variável
            //Alterna o menor valor se for necessário
            if (min > notas[i]) {
                min = notas[i];
            }
        }

        //Mostra os resultados
        MyIO.println("Menor nota: " + min);
        MyIO.println("Soma das notas: " + soma);
        MyIO.println("Media das notas: " + soma/5);
    }
}
