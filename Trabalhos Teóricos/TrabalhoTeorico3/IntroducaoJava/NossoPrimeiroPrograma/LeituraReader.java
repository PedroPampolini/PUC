import java.io.*;
import java.nio.charset.*;

class LeituraReader {
    private static BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in, Charset.forName("ISO-8859-1")));

    public static String readString(){
        String s = "";
        char temp;
        try {
            
            do {
                temp = (char)teclado.read();
                if (!(temp == '\n' || temp == ' ' || temp == 13)) {
                    s += temp;
                }
            } while (temp != '\n' && temp != ' ');
        } catch (Exception e) {}
        return s;
    }
    public static void main(String[] args) throws Exception{

        System.out.print("Digite uma palavra: ");
        String texto = readString();

        System.out.print("Digite um numero inteiro: ");
        int inteiro = Integer.parseInt(readString().trim());

        System.out.print("Digite um numero real: ");
        double real = Double.parseDouble(readString().trim().replace(",","."));

        System.out.print("Digite um caractere: ");
        char caractere = (char)teclado.read();

        System.out.println("String: " + texto);
        System.out.println("Caractere: " + caractere);
        System.out.println("Inteiro: " + inteiro);
        System.out.println("Real: " + real);
        
    }
}
