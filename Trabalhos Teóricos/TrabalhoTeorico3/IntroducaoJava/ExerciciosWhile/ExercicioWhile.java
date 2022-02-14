/**
 Autor: Pedro Pampolini Mendicino
 Criação: 11/02/2022    Última atualização: 11/02/2022
 Objetivo do Programa:  Lê 5 notas de alunos e mostra a média na tela
 */

public class ExercicioWhile {
    public static void main(String[] args) {
        //Declara e inicializa as notas
        double somaNotas = 0;
        
        //Recebe as notas e soma à variavel
        for (int i = 1; i <= 5; i++) {
            somaNotas += MyIO.readDouble("Digite a " + i + " nota: ");
        }
        //Imprime na tela o a média das notas
        System.out.println("A médias da nota é " + somaNotas/5);
    }
}
