/**
 Autor: Pedro Pampolini Mendicino
 Criação: 11/02/2022    Última atualização: 11/02/2022
 Objetivo do Programa:  Recebe uma nota N e então lê o valor da nota de 20 alunos, imprimindo na 
 resultados
 */
public class Exercicio4 {
    public static void main(String[] args) {
        //Declara variáveis
        double somaNota, notaAtual;
        int notaMaxima, abaixoMedia, acima90, i;
        //Inicializa variáveis
        i = 0;
        somaNota = 0;
        abaixoMedia = 0;
        acima90 = 0;
        notaMaxima = MyIO.readInt("Digite a nota maxima: ");
        
        while (i < 20) {
            //Leitura da nota do aluno
            notaAtual = MyIO.readDouble("Digite a nota do " + (i+1) + " aluno: ");
            somaNota+= notaAtual;   //Soma da nota atual com o total

            //Avalia se a nota é menor que 60% da máxima
            if (notaAtual < notaMaxima*0.6) {
                abaixoMedia++;
            }
            //Avalia se a nota é maior que 90% da máxima
            else if (notaAtual >= notaMaxima*0.9) {
                acima90++;
            }
            i++;
        }

        //Imprime na tela os resultados
        MyIO.println("A media da turma foi " + (somaNota/20) + " pontos.");
        MyIO.println("A quantidade de alunos abaixo da media foi " + abaixoMedia);
        MyIO.println("A quantidade de alunos acima da 90% foi de " + acima90);
    }
}
