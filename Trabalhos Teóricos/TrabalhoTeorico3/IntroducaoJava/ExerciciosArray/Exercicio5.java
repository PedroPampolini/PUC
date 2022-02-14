/**
 Autor: Pedro Pampolini Mendicino
 Criação: 11/02/2022    Última atualização: 11/02/2022
 Objetivo do Programa:  Um método que recebe 2 vetores e mostra a intercessão e a união entre eles
 */
public class Exercicio5 {
    public static boolean include(int number, int[] vet){
        //Returna se um número existe ou não em um array
        for (int i = 0; i < vet.length; i++) {
            if(vet[i] == number){
                return true;
            }
        }
        return false;
    }


    public static void unAndInter(int[] vet1, int[] vet2){
        //Variável que armazena o maior comprimento dos vetores
        int maximo = vet1.length + vet2.length;
        int indexUni = 0;
        int indexInter = 0;
        //Arrays de união e intercessão
        int[] uniao = new int[maximo];
        int[] inter = new int[maximo];

        //Iguala os vetores vet2 e inter para calcular a uniao
        for (int i = 0; i < vet2.length; i++) {
            uniao[i] = vet2[i];
        }
        indexUni = vet2.length; //Seta o index do vetor de união


        for (int i = 0; i < vet1.length; i++) {
            //Adiciona o item na intercessão caso exista em ambos
            if (include(vet1[i], vet2)) {
                inter[indexInter] = vet1[i];
                indexInter++;
            }
            //Adiciona o item na união
            else{
                uniao[indexUni] = vet1[i];
                indexUni++;
            }
        }

        //Imprime na tela o vetor de intercessões
        MyIO.println("Intercessao: ");
        MyIO.print("[ ");
        for (int i = 0; i < inter.length; i++) {
            MyIO.print(inter[i] + " ");
        }
        MyIO.println("]");

        //Imprime na tela o vetor de união
        MyIO.println("Uniao: ");
        MyIO.print("[ ");
        for (int i = 0; i < uniao.length; i++) {
            MyIO.print(uniao[i] + " ");
        }
        MyIO.println("]");
    }
    public static void main(String[] args) {
        int[] vet1 = {3,4,5,6,7,8,9};
        int[] vet2 = {0,1,2,3,4,5};
        unAndInter(vet1,vet2);
    }
}
