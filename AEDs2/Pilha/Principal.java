//Main Simples para testar os métodos da Classe
public class Principal {
    public static void main(String[] args) throws Exception {
        Stack <Integer>pilha = new Stack<Integer>(10);
        System.out.println(pilha.toString());
        pilha.push(10);
        pilha.push(9);
        pilha.push(8);
        pilha.push(6);
        System.out.println(pilha.toString());
        pilha.clear();
        for (int i = 0; i < 10; i++) {
            pilha.push(i);
        }
        System.out.println(pilha.toString());
        System.out.println(pilha.last());
        //pilha.push(500);
        System.out.println(pilha.toString());

    }
}
