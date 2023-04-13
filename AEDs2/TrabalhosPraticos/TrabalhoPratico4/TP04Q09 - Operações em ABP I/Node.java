public class Node {
    char e;
    Node menor;
    Node maior;

    Node(){
        this('a');
    }

    Node(char e){
        this.e = e;
        menor = null;
        maior = null;
    }

    Node(char e, Node menor, Node maior){
        this.e = e;
        this.menor = menor;
        this.maior = maior;
    }
}
