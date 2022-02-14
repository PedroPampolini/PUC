/**
 Autor: Pedro Pampolini Mendicino
 Criação: 11/02/2022    Última atualização: 11/02/2022
 Objetivo do Programa:  Lê 5 notas de alunos e imprime a média das notas acima de 80
 */
public class ExemploForIf {
    public static void main(String[] args) {
        //Declaraçõ e inicialização da variável 
        double somaNotas, notaAtual;
        int quantAcima;
        somaNotas = 0;
        quantAcima = 0;
        //Laço for que lê a nota
        for (int i = 0; i < 5; i++) {
            notaAtual = MyIO.readDouble("Digite a " + (i+1) + " nota: ");
            //Irá somar à variável da soma se a nota for maior ou igual à 80
            if (notaAtual >= 80) {
                somaNotas += notaAtual;
                quantAcima++;
            }
        }
        //Imprime na tela os resultados
        MyIO.println("A media das notas acima de 80 foi de " + (somaNotas/quantAcima) + " pontos");
    }
}