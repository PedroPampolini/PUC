import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class InitArq {
    public static void main(String[] args) throws Exception {
        RandomAccessFile raf = new RandomAccessFile("temp/aux2M1.tmp", "rw");
        while (raf.getFilePointer() != raf.length()) {
            int size = raf.readInt();
            byte[] ba = new byte[size];
            User u = new User();
            char marcador;
            raf.read(ba);
            marcador = raf.readChar();
            u.fromByteArray(ba);
            System.out.print(("" + u.getIdConta()) + marcador);
        }
        System.out.println();
    }
}
