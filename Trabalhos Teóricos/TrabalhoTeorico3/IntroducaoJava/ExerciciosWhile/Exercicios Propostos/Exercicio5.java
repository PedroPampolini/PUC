/**
 Autor: Pedro Pampolini Mendicino
 Criação: 11/02/2022    Última atualização: 11/02/2022
 Objetivo do Programa:  Recebe um valor N e mostra na tela a sequência Fibonacci até o n-ésimo termo
 */

public class Exercicio5 {
    public static void main(String[] args) {
        //Declara e inicializa as variáveis
        int limite, num1, num2, num3, i;
        i = 0;
        num1 = 1;
        num2 = 1;
        num3 = num1 + num2;
        //Realiza a leitura do limite
        limite = MyIO.readInt("Digite a quantidade de termos: ");
        
        MyIO.println("1\n1");   //imprime os 2 primeiros números
        i +=2;      //Incrmenta o index para os 2 primeiros termos

        //Realiza as operções de fibonacci até o n-ésimo termo
        while(i < limite){
            MyIO.println(num3);
            num1 = num2;
            num2 = num3;
            num3 = num1 + num2;
            i++;
        }
    }
}
