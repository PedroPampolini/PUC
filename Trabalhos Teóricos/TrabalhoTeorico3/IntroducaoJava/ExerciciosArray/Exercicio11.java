/**
 Autor: Pedro Pampolini Mendicino
 Criação: 11/02/2022    Última atualização: 11/02/2022
 Objetivo do Programa:  Lê um array e mostra quantos elementos são divisiveis por 2 e quantos por 3;
 */
public class Exercicio11 {
    public static void mostra2E3(int[] vet){
        //Declara e inicializa as variáveis
        int div2 = 0;
        int div3 = 0;

        //Faz a busca
        for (int i = 0; i < vet.length; i++) {
            if (vet[i]%2 == 0) {
                div2++;
            }
            if (vet[i]%3 == 0) {
                div3++;
            }
        }
        //Imprime na tela os resultados
        MyIO.println("A quantidade de elementos divisíveis por 2 e " + div2);
        MyIO.println("A quantidade de elementos divisíveis por 3 e " + div3);
    }

    public static void main(String[] args) {
        //Declaração das variáveis e vetor de inteiros
        int maximo;
        int[] elementos;

        maximo = MyIO.readInt("Qual a quantidade de items no vetor? ");
        elementos = new int[maximo];
        for (int i = 0; i < elementos.length; i++) {
            elementos[i] = MyIO.readInt(i + " elemento: ");
        }

        mostra2E3(elementos);
    }
}
