/**
 * Levenshtein
 */
@SuppressWarnings("unused")
public class Levenshtein {



    private int[][] matriz;
    private String word1;
    private String word2;

    Levenshtein(String word1, String word2){
        this.word1 = word1;
        this.word2 = word2;
        this.matriz = new int[word1.length()+1][word2.length()+1];  //Matriz com uma linha e coluna a mais, para o caractere vazio
    }

    //Inicializa a matriz com a primeira linha e coluna com valores iniciais
    public void initMatriz(){
        for(int i = 0; i < matriz.length; i++){
            matriz[i][0] = i;
        }
        for (int i = 0; i < matriz[0].length; i++) {
            matriz[0][i] = i;
        }
    }

    //Calcula a distancia entre as palavras por linha e retorna o valor da ultima celula
    public int getDistancia(){
        for (int i = 1; i < matriz.length; i++) {
            for (int j = 1; j < matriz[i].length; j++) {
                //Se os caracteres forem iguais, o valor da celula é o valor da celula diagonal
                if(word2.charAt(j-1) == word1.charAt(i-1)){
                    matriz[i][j] = matriz[i-1][j-1];
                }
                //Se os caracteres forem diferentes, o valor da celula é o valor da celula diagonal + 1
                else{
                    matriz[i][j] = 1+ Minimo(i,j);
                }
            }
        }

        return matriz[matriz.length-1][matriz[0].length-1];
    }

    //Retorna o menor valor entre a celula diagonal, a celula acima e a celula a esquerda
    private int Minimo(int i, int j){
        int minimo = matriz[i-1][j];    //Valor da celula acima
        //Se o valor da celula a esquerda for menor que o valor da celula acima
        if(matriz[i][j-1] < minimo){    
            minimo = matriz[i-1][j-1];
        }
        //Se o valor da celula diagonal for menor que o valor da celula a esquerda
        if(matriz[i-1][j-1] < minimo){
            minimo = matriz[i-1][j-1];
        }
        return minimo;
    }

    //Imprime a matriz
    public void imprimeMatriz(){
        System.out.print("   ");
        //Imprime a palavra
        for (int i = 0; i < word2.length(); i++) {
            System.out.print(" " + word2.charAt(i));
        }
        System.out.print("\n  ");
        //Imprime a primeira linha da matriz
        for (int i = 0; i < matriz[0].length; i++) {
            System.out.print(matriz[0][i] + " ");
        }
        System.out.println();
        //Imprime o resto da matriz
        for (int i = 1; i < matriz.length; i++) {
            System.out.print(word1.charAt(i - 1) + " ");    //Imprime uma letra da palavra
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }
    
}