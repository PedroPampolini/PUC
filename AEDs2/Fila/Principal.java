//Main Simples para testar os métodos da Classe
public class Principal {
    public static void main(String[] args) throws Exception {
        Queue <Integer> fila = new <Integer>Queue(10);
        System.out.println(fila.toString());
        for (int i = 0; i < 10; i++) {
            fila.queue(i+1);
        }
        System.out.println(fila.toString());
        System.out.println(fila.dequeue());
        System.out.println(fila.toString());
        fila.clear();
        System.out.println(fila.toString());
        for (int i = 0; i < 10; i++) {
            fila.queue(i+1);
        }
        System.out.println(fila.toString());
        //fila.queue(5);
    }
}
