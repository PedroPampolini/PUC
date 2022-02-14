/**
 Autor: Pedro Pampolini Mendicino
 Criação: 11/02/2022    Última atualização: 11/02/2022
 Objetivo do Programa:  Um método que recebe uma matriz e mostra a sua transposta como grid
 */
public class Exercicio18 {
    public static void main(String[] args) {
        //Declaração das variáveis
        int xMax;
        int yMax;
        int[][] matriz;
        int[][] transposta;

        //Inicialização das variáveis e da matriz
        xMax = MyIO.readInt("Quantas colunas? ");
        yMax = MyIO.readInt("Quantas linhas? ");
        matriz = new int[yMax][xMax];
        transposta = new int[xMax][yMax];

        for (int y = 0; y < matriz.length; y++) {
            for (int x = 0; x < matriz[y].length; x++) {
                matriz[y][x] = MyIO.readInt("[" + y + "]" + "[" + x + "]: ");
            }
        }

        for (int y = 0; y < matriz.length; y++) {
            for (int x = 0; x < matriz[y].length; x++) {
                transposta[x][y] = matriz[y][x];
            }
        }

        for (int y = 0; y < matriz.length; y++) {
            for (int x = 0; x < matriz[y].length; x++) {
                MyIO.print(matriz[y][x] + " ");
            }
            System.out.println();
        }

        for (int y = 0; y < transposta.length; y++) {
            for (int x = 0; x < transposta[y].length; x++) {
                MyIO.print(transposta[y][x] + " ");
            }
            System.out.println();
        }

        

    }
}
