package LinkedList;

public class principal {
    public static void main(String[] args) {
        SLinkedList<String> lista = new SLinkedList<String>();

        lista.addFirst(new Node("Cassia"));
        lista.addLast(new Node("Clara"));
        lista.insertNode(new Node("Pedro"), lista.length());        
        lista.addFirst(new Node("Jose"));
        System.out.println("Lista prévia:  " + lista.toString() + "  Size: " + lista.length());
        lista.insertNode(new Node("Teco"), 2);

        System.out.println("Lista com o teco:  " + lista.toString() + "  Size: " + lista.length());

        lista.deleteElement(3);
        System.out.println("4º elemento deletado:  " + lista.toString() + "  Size: " + lista.length());

        lista.deleteElement(0);
        System.out.println("1º elemento deletado:  " + lista.toString() + "  Size: " + lista.length());

        lista.deleteElement(lista.length() - 1);
        System.out.println("Último elemento deletado:  " + lista.toString() + "  Size: " + lista.length());

        lista.clear();
        System.out.println("Lista limpa:  " + lista.toString() + "  Size: " + lista.length());
        

        lista.insertNode(new Node("Segundo"), 0);
        System.out.println("Lista reiniciada:  " + lista.toString() +   "  Size: " + lista.length());

        lista.addFirst(new Node("Primeiro"));
        System.out.println("Lista reiniciada:  " + lista.toString() +   "  Size: " + lista.length());

        lista.addLast(new Node("Quarto"));
        System.out.println("Lista reiniciada:  " + lista.toString() +   "  Size: " + lista.length());

        lista.insertNode(new Node("Terceiro"), lista.length() - 1);
        System.out.println("Lista reiniciada:  " + lista.toString() +   "  Size: " + lista.length());

        lista.insertNode(new Node("Quinto"), lista.length());
        System.out.println("Lista reiniciada:  " + lista.toString() +   "  Size: " + lista.length());
    }
}
