/*
Autor: Pedro Pampolini Mendicino
Data de Criação: 21/03/2022     Última Atualização: 21/03/2022
Objetivo: Uma classe de Pilha ou Stack simples, com os métodos de push, pop, mostrar o último item, 
toString, e para limpar a pilha
*/
public class Stack <E>{
    private E[] list;   //Array com os valores da pilha
    private int last;   //Variável de controle do índice da pilha

    //Construtor que aloca um tamanho padrão de 100 itens
    Stack(){
        list = (E[])new Object[100];
        last = 0;
    }

    //Construtor que aloca um tamanho desejado pelo usuário
    Stack(int i){
        list = (E[]) new Object[i];
        last = 0;
    }
    
    //Método para empilhar
    public void push(E e) throws Exception{
        if (last < list.length) {   //Verifica se o limite já foi atingido
            list[last] = e; //Atribui o valor passado como parâmetro para o último item
            last++;     //Incrementa a quantidade de itens na pilha
        }
        else{
            throw new Exception("Full Stack!!");    //Lança uma excessão caso o limite tenha sido atingido
        }
    }

    //Método de desempilhar
    public E pop() throws Exception{
        E e;    //Variável que receberá o item que será removido
        if (last > 0) { //Verifica se há itens na pilha
            last--; //Decrementa a quantidade de itens na pilha
            e = list[last]; //Salva o último item
        }
        else{
            throw new Exception("Empty Stack!!");   //Lança uma excessão caso a pilha esteja vazia
        }
        return e;   //Retorna o último item
    }

    //Retorna o tamanho da pilha
    public int lengh(){
        return last;
    }

    //Retorna uma String com todos os itens da pilha
    public String toString(){
        String s = "[";
        //Caso a pilha não esteja vazia, grava o primeiro item como string
        if (last > 0) {
            s += "" + list[0].toString();
        }
        //Para os demais itens, grava a vírgula seguida do item
        for (int i = 1; i < last; i++) {
            s += ", " + list[i].toString();
        }
        s += "]";
        return s;
    }

    //Método que limpa o conteúdo da pilha
    public void clear(){
        //Percorre-a toda, salvando seus itens como null
        for (int i = 0; i < list.length; i++) {
            list[i] = null;
        }
        last = 0;
    }

    //Retorna o último item, sem removê-lo
    public E last(){
        return list[last-1];
    }
}
