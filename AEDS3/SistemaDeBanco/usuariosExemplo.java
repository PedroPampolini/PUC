import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class usuariosExemplo {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int quantUsers = sc.nextInt();
        FileWriter arq = new FileWriter("EntradaUsers.example");
        String s = "userX\nusernameX\n1\nuserX@email.com\nadmin123\nY"
        + "\nCidade dos Xs\nX00\n";
        String toWrite;
        for (int i = 0; i < quantUsers; i++) {
            toWrite = s.replaceAll("X", "" + (i+1));
            toWrite = toWrite.replace("Y", "YYYYYYYYYYY".replaceAll("Y", "" + (i+1)).substring(0,10));
            arq.write("1\n");
            arq.write(toWrite);
        }
        arq.close();
        sc.close();
    }
}
