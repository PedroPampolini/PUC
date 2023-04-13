public class Node {
    int e;
    Node menor;
    Node maior;

    Node(){
        this(-1);
    }

    Node(int e){
        this.e = e;
        menor = null;
        maior = null;
    }

    Node(int e, Node menor, Node maior){
        this.e = e;
        this.menor = menor;
        this.maior = maior;
    }
}
