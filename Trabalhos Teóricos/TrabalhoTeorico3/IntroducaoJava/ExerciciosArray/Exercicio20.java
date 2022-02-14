/**
 Autor: Pedro Pampolini Mendicino
 Criação: 11/02/2022    Última atualização: 11/02/2022
 Objetivo do Programa:  Um método que recebe uma matriz quadrada e mostra a diagonal primaria e secundária
 */
public class Exercicio20 {
    public static void main(String[] args) {
        int dim;
        int[][] m1;

        dim = MyIO.readInt("Qual a dimensao da matriz? ");
        m1 = new int[dim][dim];

        for (int y = 0; y < dim; y++) {
            for (int x = 0; x < dim; x++) {
                m1[y][x] = MyIO.readInt("[" + y + "]" + "[" + x + "]: ");
            }
        }

        MyIO.println("=====Diagonal Principal=====");
        for (int i = 0; i < dim; i++) {
            MyIO.print(m1[i][i] + " ");
        }
        
        System.out.println();

        MyIO.println("=====Diagonal Secundaria  =====");
        for (int i = 0; i < dim; i++) {
            MyIO.print(m1[i][(dim-1) - i] + " ");
        }
    }
}
