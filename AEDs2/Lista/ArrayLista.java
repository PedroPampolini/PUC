/*
Autor: Pedro Pampolini Mendicino
Data de Criação: 25/02/2022     Última Atualização: 25/02/2022
Objetivo: Uma classe de Lista simples, com os métodos básicos
*/
public class ArrayLista <E> {
    private E lista[];
    private int len;
    private int max;

    //Construtores
    ArrayLista(){
        lista = (E[])new Object[10];
        len = 0;
        max = 10;
    }
    ArrayLista(int size){
        lista = (E[]) new Object[size];
        len = 0;
        max = size;
    }
    

    //Métodos de inserção
    public void insertFirst(E element) throws Exception{
        //Exceção caso o maior tamanho ja tenha sido atingido
        if (len >= max) {
            throw new Exception("Maximum range reached");
        }
        if (len > 0) {
            //Desloca todos os itens para uma posição a mais para inserir na primeira posição
            for (int i = len-1; i >= 0; i--) {
                lista[i+1] = lista[i];
            }
        }
        lista[0] = element;
        len++;
    }
    public void insertLast(E element) throws Exception{
        if(len >= max){
            throw new Exception("Maximum range reached");
        }
        lista[len] = element;
        len++;
    }
    public void insert(E element, int pos) throws Exception{
        if (len >= max) {
            throw new Exception("Maximum range reached");
        }
        //Desloca todosos itens à direita da posição desejada para inserir o item
        for (int i = len-1; i >= pos; i--) {
            lista[i+1] = lista[i];
        }
        lista[pos] = element;
        len++;
    }
    
    //Métodos de remoção
    public E removeFirst() throws Exception{
        if (len == 0) {
            throw new Exception("NotEnoughItems");
        }
        E first = lista[0];
        if (len > 1) {
            //Reposiciona os itens para a posição à sua esquerda para não haver fragmentação
            for (int i = 0; i < len; i++) {
                lista[i] = lista[i+1];
            }
            lista[len] = null;
        }
        len--;
        return first;
    }
    public E removeLast() throws Exception{
        if (len == 0) {
            throw new Exception("NotEnoughItems");
        }
        E last = lista[len-1];
        lista[len-1] = null;
        len--;
        return last;
    }
    public E remove(int pos) throws Exception{
        if (pos >= len) {
            throw new Exception("NotEnoughItems");
        }
        E removed = lista[pos];
        //reposiciona todos os itens da posição até o máximo para uma posição à sua direita para não haver fragmentação
        for (int i = pos; i < len; i++) {
            lista[i] = lista[i+1];
        }
        lista[len] = null;
        len--;
        return removed;
    }
    
    //Método que retorna a string da lista
    public String toString(){
        String s = new String();
        s += "[";
        if (len > 0) {
            s += "" + lista[0];
        }
        for (int i = 1; i < len; i++) {
            s += ", " + lista[i];
        }
        s+= "]";
        return s;
    }
    
    //Método que retorna o tamanho atual da lista
    public int length(){
        return len;
    }

    //Método que retorna o item de uma posição específica
    public E getItem(int pos) throws Exception{
        if (pos >= len) {
            throw new Exception("Index out of range");
        }
        return lista[pos];
    }

    //Método que insere em uma posição determinada e retornando o item que estava nela
    public E changeItem(E element, int pos) throws Exception{
        if(pos >= len){
            throw new Exception("Index out of range");
        }
        E item = lista[pos];
        lista[pos] = element;
        return item;
    }

    //retorna um subarray
    public ArrayLista<E> subArray(int minimun, int maximum) throws Exception{
        if (minimun < 0) {
            throw new Exception("Minimum needs to be at least 0");
        }
        if(maximum >= len){
            throw new Exception("Maximum needs to be " + len + " or less");
        }
        ArrayLista<E> subArray = new ArrayLista<E>((maximum-minimun)+1);
        for (int i = minimun; i <= maximum; i++) {
            subArray.insertLast(this.getItem(i));
        }
        return subArray;
    }
}