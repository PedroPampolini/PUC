/**
 Autor: Pedro Pampolini Mendicino
 Criação: 11/02/2022    Última atualização: 11/02/2022
 Objetivo do Programa:  Um método que recebe uma matriz quadrada e mostra a media dos elementos
 */
public class Exercicio21 {
    public static void main(String[] args) {
        //Declaração das variáveis
        int xMax;
        int yMax;
        double media;
        int[][] matriz;

        //Inicialização das variáveis e da matriz
        media = 0;
        xMax = MyIO.readInt("Quantas colunas? ");
        yMax = MyIO.readInt("Quantas linhas? ");
        matriz = new int[yMax][xMax];

        for (int y = 0; y < matriz.length; y++) {
            for (int x = 0; x < matriz[y].length; x++) {
                matriz[y][x] = MyIO.readInt("[" + y + "]" + "[" + x + "]: ");
            }
        }

        for (int y = 0; y < matriz.length; y++) {

            for (int x = 0; x < matriz[y].length; x++) {
                media += matriz[y][x];
            }   
        }

        media /= (xMax * yMax);
        MyIO.println("Media: " + media);
    }
}
