package LinkedList;

import javax.swing.text.Position;

public class SLinkedList<E> {
    protected Node<E> tail = new Node<E>();     //Último nodo da lista
    protected Node<E> head = new Node<E>();    //primeiro nodo da lista
    
    protected long size;
    public SLinkedList(){
        size = 0;
    }
    
    //Returns the list size  Complexity: 1
    public int length(){
        return (int)size;
    }

    //Returns a string with all values  Complexity: N
    public String toString(){
        String str = "[";
        Node<E> temp = head;
        if (size == 0) {
            str += "]";
            return str;
        }
        for (int i = 0; i < size - 1; i++) {
            str += (String)temp.getElement() + ", ";
            temp = temp.getNext();
        }
        str += temp.getElement();
        str += "]";
        return str;
    }

    //Add new head  Complexity: 1
    public void addFirst(Node<E> newHead){
        if(size == 0){
            head = newHead;
            size++;
            tail = head;
            return;
        }
        newHead.setNext(head);
        head = newHead;
        size++;
    }

    //Add new Tail  Complexity: 1
    public void addLast(Node<E> newTail){
        if (size == 0) {
            tail = newTail;
            head = tail;
        }
        newTail.setNext(null);
        tail.setNext(newTail);
        tail = newTail;
        size++;
    }

    //Gets head  Complexity: 1
    public Node<E> getHead()
    {
        return head;
    }

    //Gets tail  Complexity: 1
    public Node<E> getTail(){
        return tail;
    }

    //Gets head's element  Complexity: 1
    public E getHeadElement(){
        return head.getElement();
    }

    //Sets head's element  Complexity: 1
    public void setHeadElement(E newElement){
        head.setElement(newElement);
    }


    //Sets tail's element  Complexity: 1
    public E getTailElement(){
        return tail.getElement();
    }

    //Sets tail's element  Complexity: 1
    public void setTailElement(E newElement){
        tail.setElement(newElement);
    }

    //Removes first element  Complexity: 1
    public Node<E> removeFirst(){
        if (head == null) {
            System.out.println("Error: Empty list");
            return null;
        }

        Node<E> temp = head;
        head = head.getNext();
        temp.setNext(null);
        size--;
        return temp;
    }

    //Gets Specific element  Complexity: N
    public Node<E> getNode(int position){
        //Verifies Range
        if (position >= size) {
            System.out.println("Out of Range");
            return null;
        }

        Node<E> temp = head;   //Get a new temp node pointer
        for (int i = 0; i < position; i++) {
            temp = temp.getNext();
        }
        return temp;
    }

    public void setNodeElement(E newElement, int position){
        //If position isn't out of range, will search for
        if(position < size){
            Node<E> temp = head;
            //Go through the list
            for (int i = 0; i < position; i++){
                temp = temp.getNext();
            }
            temp.setElement(newElement);
        }
        else{
            System.out.println("Out of Range");
        }
    }

    public void insertNode(Node<E> newNode, int position){
        //Declare prev and next node pointers
        Node<E> prev = head;
        Node<E> next = head;

        if (position == 0) {
            addFirst(newNode);
            return;
        }

        if(position == size){
            prev = tail;
            tail.setNext(newNode);
            tail = newNode;
            size++;
            return;
        }

        //Aprimorar
        if (getNode(position).getElement() == null) {
            System.out.println("Vazio");
            return;
        }
        if (position > size) {
            System.out.println("Mt grande");
            return;
        }

        

        //Go through the list to set next node from new one
        for (int i = 0; i < position; i++) {
            next = next.getNext();
        }
        newNode.setNext(next);

        //Go throungh the list to set previous node from new one
        for (int i = 0; i < position - 1; i++) {
            prev = prev.getNext();
        }
        prev.setNext(newNode);

        size++;

    }

    public Node<E> deleteElement(int position){
        //Declares node pointers
        Node<E> temp;
        Node<E> prev = head;

        temp = getNode(position);       //Set node pointer
        if (position == 0) {
            temp = removeFirst();
            return temp;
        }

        //Go through the list to get previous node from list
        for (int i = 0; i < position-1; i++) {
            prev = prev.getNext();
        }
        prev.setNext(temp.getNext());

        //Set to null the deleted node next's reference
        temp.setNext(null);
        
        size--; //Decrease the size

        return temp;
    }

    public void clear(){
        int max = (int)size;
        for (int i = 0; i < max; i++) {
            removeFirst();
        }
    }
}
