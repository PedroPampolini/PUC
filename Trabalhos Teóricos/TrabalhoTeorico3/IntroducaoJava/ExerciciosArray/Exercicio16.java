/**
 Autor: Pedro Pampolini Mendicino
 Criação: 11/02/2022    Última atualização: 11/02/2022
 Objetivo do Programa:  Um método que recebe um vetor e o ordena
 */
public class Exercicio16 {
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
        //Declaração das variáveis e vetor de inteiros
        int maximo;
        int[] vet;

        //Realiza a leitura dos valores do vetor
        maximo = MyIO.readInt("Qual a quantidade de items no vetor? ");
        vet = new int[maximo];
        for (int i = 0; i < vet.length; i++) {
            vet[i] = MyIO.readInt(i + " valor: ");
        }

        vet = bubble(vet);  //Chama o método

        //Imprime o vetor na tela
        MyIO.print("[ ");
        for (int i = 0; i < vet.length; i++) {
            MyIO.print(vet[i] + " ");
        }
        MyIO.print("]");
        
    }
}
