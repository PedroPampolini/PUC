/**
 Autor: Pedro Pampolini Mendicino
 Criação: 11/02/2022    Última atualização: 11/02/2022
 Objetivo do Programa:  Um programa que lê os gols de uma partida e devolve se houve empare ou quem venceu
 */


public class Exercicio5 {
    public static void main(String[] args) {
        //Declaração das variáveis
        int mandante, visitante;

        //Leitura dos valores
        mandante = MyIO.readInt("Digite quantos gols o time mandante fez: ");
        visitante = MyIO.readInt("Digite quantos gols o time visitante fez: ");

        //Realza as comparações de empare ou não
        if (mandante == visitante) {
            MyIO.println("Houve empate");
        }
        else{
            //Realiza a comparação de qual time venceu
            MyIO.println("O time " + (mandante > visitante ? "mandante" : "visitante") + " venceu");
        }
    }
}
