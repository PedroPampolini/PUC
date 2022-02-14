/**
 Autor: Pedro Pampolini Mendicino
 Criação: 11/02/2022    Última atualização: 11/02/2022
 Objetivo do Programa:  Um método que recebe duas matrizes e retorna sua soma
 */
public class Exercicio19 {
    public static void main(String[] args) {
        int xMax, yMax;
        int[][] m1,m2;

        xMax = MyIO.readInt("Quantas colunas? ");
        yMax = MyIO.readInt("Quantas linhas? ");
        m1 = new int[yMax][xMax];
        m2 = new int[yMax][xMax];

        MyIO.println("==========Matriz 1==========");
        for (int y = 0; y < m1.length; y++) {
            for (int x = 0; x < m1[y].length; x++) {
                m1[y][x] = MyIO.readInt("[" + y + "]" + "[" + x + "]: ");
            }
        }

        MyIO.println("==========Matriz 2==========");
        for (int y = 0; y < m2.length; y++) {
            for (int x = 0; x < m2[y].length; x++) {
                m2[y][x] = MyIO.readInt("[" + y + "]" + "[" + x + "]: ");
            }
        }

        for (int y = 0; y < m2.length; y++) {
            for (int x = 0; x < m2[y].length; x++) {
                m2[y][x] += m1[y][x];
            }
        }

        for (int y = 0; y < m2.length; y++) {
            for (int x = 0; x < m2[y].length; x++) {
                MyIO.print(m2[y][x] + " ");
            }
            System.out.println();
        }
    }
}
