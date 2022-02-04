package LinkedList;

public class Node<E> {
    private Node<E> next;
    private E element;

    public Node(E newElement, Node<E> newNext){
        next = newNext;
        element = newElement;
    }

    public Node(){
        this(null,null);
    }

    public Node(E newElement){
        element = newElement;
        next = null;
    }

    public E getElement(){
        return element;
    }

    public Node<E> getNext(){
        return next;
    }

    public void setElement(E newElement){
        element = newElement;
    }

    public void setNext(Node<E> newNext){
        next = newNext;
    }

    public void setNode(E newElement, Node<E> newNext){
        element = newElement;
        next = newNext;
    }
}
