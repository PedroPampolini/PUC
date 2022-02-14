/**
 Autor: Pedro Pampolini Mendicino
 Criação: 11/02/2022    Última atualização: 11/02/2022
 Objetivo do Programa:  Um método que recebe uma matriz e calcula a média de cada linha
 */
public class Exercicio22 {
    public static void main(String[] args) {
        //Declaração das variáveis
        int xMax;
        int yMax;
        double media;
        int[][] matriz;

        //Inicialização das variáveis e da matriz
        xMax = MyIO.readInt("Quantas colunas? ");
        yMax = MyIO.readInt("Quantas linhas? ");
        matriz = new int[yMax][xMax];

        for (int y = 0; y < matriz.length; y++) {
            for (int x = 0; x < matriz[y].length; x++) {
                matriz[y][x] = MyIO.readInt("[" + y + "]" + "[" + x + "]: ");
            }
        }

        for (int y = 0; y < matriz.length; y++) {
            media = 0;
            for (int x = 0; x < matriz[y].length; x++) {
                media += matriz[y][x];
            }
            media /= xMax;
            MyIO.println("Media linha " + y + ": "+ media);
        }
    }
}
