package Exercicio1TI2;
import java.util.Scanner;

public class SomarDoisNumeros {
	public static void main(String[] args) {
		//Objeto de leitura de entrada
		Scanner teclado = new Scanner(System.in);
		
		int a, b;	//Vari�veis utilizadas
		
		//Leitura do primeiro valor
		System.out.print("Primeiro N�mero: ");
		a = teclado.nextInt();
		
		//Leitura do segundo valor
		System.out.print("Segundo N�mero: ");
		b = teclado.nextInt();
		
		//Impress�o do resultado na tela
		System.out.println("O resultado �: " + (a+b));
		
		teclado.close();
	}
}
