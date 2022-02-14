import java.io.IOException;
import java.util.*;
public class LeituraScanner {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        //Declara as variáveis e a string
        String texto = new String();
        char caractere;
        int inteiro;
        double real;

        //Realiza a leitura
        System.out.print("Digite uma palavra: ");
        texto = teclado.nextLine();
        System.out.print("Digite um caractere: ");
        caractere = teclado.nextLine().charAt(0);
        System.out.print("Digite um numero inteiro: ");
        inteiro = teclado.nextInt();
        System.out.print("Digite um numero real: ");
        real = teclado.nextDouble();

        System.out.println("String: " + texto);
        System.out.println("Caractere: " + caractere);
        System.out.println("Inteiro: " + inteiro);
        System.out.println("Real: " + real);
        teclado.close();
    }
}
