//Main Simples para testar os métodos da Classe
public class Principal {
    public static void main(String[] args) throws Exception {
        ArrayLista <Integer> lista = new ArrayLista<Integer>();
        int x1,x2,x3,x4;
        lista.insertFirst(1);
        System.out.println(lista.toString());
        lista.insertLast(5);
        System.out.println(lista.toString());
        lista.insert(2, 1);
        System.out.println(lista.toString());
        lista.insertFirst(0);
        System.out.println(lista.toString());
        x1 = lista.removeFirst();
        System.out.println(lista.toString());
        x2 = lista.remove(1);
        System.out.println(lista.toString());
        x3 = lista.removeLast();
        System.out.println(lista.toString());
        x4 = lista.removeLast();
        System.out.println(lista.toString());
        for (int i = 0; i < 10; i++) {
            lista.insert(i+1,lista.length());
        }
        System.out.println(lista.toString());
        System.out.println(lista.changeItem(249,5));
        System.out.println(lista.toString());
        System.out.println(lista.subArray(3, 5).toString());
    }
}
