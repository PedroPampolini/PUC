package Exercicio1TI2;
import java.util.Scanner;

public class SomarDoisNumeros {
	public static void main(String[] args) {
		//Objeto de leitura de entrada
		Scanner teclado = new Scanner(System.in);
		
		int a, b;	//Variáveis utilizadas
		
		//Leitura do primeiro valor
		System.out.print("Primeiro Número: ");
		a = teclado.nextInt();
		
		//Leitura do segundo valor
		System.out.print("Segundo Número: ");
		b = teclado.nextInt();
		
		//Impressão do resultado na tela
		System.out.println("O resultado é: " + (a+b));
		
		teclado.close();
	}
}
