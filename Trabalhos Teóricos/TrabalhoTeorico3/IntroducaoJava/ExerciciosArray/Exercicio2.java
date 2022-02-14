/**
 Autor: Pedro Pampolini Mendicino
 Criação: 11/02/2022    Última atualização: 11/02/2022
 Objetivo do Programa:  Lê N valores e imprime a soma do i-ésimo termo com o (2*i + 1)-ésimo termo
 enquanto (2*i + 1) < n
 */
public class Exercicio2 {
    public static void main(String[] args) {
        //Declaração das variáveis
        double[] valores;
        int maximo;
        
        //Inicialização das variáveis
        maximo = MyIO.readInt("Digite quantos numeros ira escrever: ");
        valores = new double[maximo];
        
        //Realiza a leitura dos valores
        for (int i = 0; i < maximo; i++) {
            valores[i] = MyIO.readDouble("Digite o " + i + " valor: ");
        }

        //Realiza a soma e imprime na tela os resultados
        for (int i = 0; (2*i + 1) < maximo; i++) {
            MyIO.println("Soma= " + (valores[i] + valores[2*i + 1]));
        }
    }
}
