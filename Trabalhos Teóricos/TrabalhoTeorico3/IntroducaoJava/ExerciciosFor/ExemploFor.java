/**
 Autor: Pedro Pampolini Mendicino
 Criação: 11/02/2022    Última atualização: 11/02/2022
 Objetivo do Programa:  Lê 5 notas de alunos e imprime a média
 */
public class ExemploFor {
    public static void main(String[] args) {
        //Declaraçõ e inicialização da variável 
        double somaNotas;
        somaNotas = 0;

        //Laço for que lê a nota e soma à variável
        for (int i = 0; i < 5; i++) {
            somaNotas += MyIO.readDouble("Digite a " + (i+1) + " nota: ");
        }
        //Imprime na tela os resultados
        MyIO.println("A media das notas foi de " + (somaNotas/5) + " pontos");
    }
}