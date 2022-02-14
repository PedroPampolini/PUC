/**
 Autor: Pedro Pampolini Mendicino
 Criação: 11/02/2022    Última atualização: 11/02/2022
 Objetivo do Programa:  Um método que recebe um vetor e o ordena
 */
public class Exercicio4 {
    static public int[] bubble(int[] list){
        if (list.length > 1) {
            //Realiza a operação N vezes para a garantia de ordenação
            for (int i = 0; i < list.length - 1; i++) {
                //Percorre o vetor para troca do i pelo i+1 caso o primeiro seja maior que o segundo
                for (int j = 0; j < list.length - 1; j++) { 
                    //Inverte a ordem dos valores caso seja necessário
                    if (list[j] > list[j+1]) {
                        int temp = list[j];
                        list[j] = list[j+1];
                        list[j+1] = temp;
                    }
                }
            }
        }
        return list;
    }
    public static void main(String[] args) {
        //Declara vetor de exemplo
        int[] vetor = {6,41,98,1,5,4,5,1,54,2,3,5,6,61,5,6,65,14,6,1,54};
        vetor = bubble(vetor);  //Chama o método

        //Imprime o vetor na tela
        MyIO.print("[ ");
        for (int i = 0; i < vetor.length; i++) {
            MyIO.print(vetor[i] + " ");
        }
        MyIO.print("]");
        
    }
}
