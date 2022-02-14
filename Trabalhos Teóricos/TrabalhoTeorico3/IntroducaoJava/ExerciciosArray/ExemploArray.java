/**
 Autor: Pedro Pampolini Mendicino
 Criação: 11/02/2022    Última atualização: 11/02/2022
 Objetivo do Programa:  Lê a nota de 5 alunos e imprime a média e depois mostra quais ficaram acima da média;
 */

public class ExemploArray {

    public static void main(String[] args) {
        //Declara e inicializa a variável e o vetor
        double media = 0;
        double[] turma = new double[5];

        //Realiza a leitura das notas
        for (int i = 0; i < 5; i++) {
            turma[i] = MyIO.readDouble("Digite a nota do aluno " + i + ": ");
            media += turma[i];
        }

        media /= 5;     //Calcula a média

        //Percorre todo o array e imprime na tela os alunos que ficaram acima da média
        for (int i = 0; i < 5; i++) {
            if (turma[i] > media) {
                MyIO.println("O aluno " + i + " ficou acima da media com " + turma[i] + " pontos");
            }
        }
    }
}
