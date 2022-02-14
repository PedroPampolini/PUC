import java.util.*;

public class SomarDoisNumeros {
    public static Scanner teclado = new Scanner(System.in);
    public static void main(String[] args) {
        //Declara variáveis
        int num1, num2, soma;

        System.out.print("Digite um numero: ");
        num1 = teclado.nextInt();

        System.out.print("Digite outro numero: ");
        num2 = teclado.nextInt();

        soma = num1 + num2;

        System.out.println("Soma: " + soma);
    }    
}
