/*
Autor: Pedro Pampolini Mendicino
Data de Criação: 21/03/2022     Última Atualização: 21/03/2022
Objetivo: Uma classe de Fila ou Queue simples, com os métodos de inserção, remoção, mostrar o primeiro item, 
toString, e para limpar a fila
*/

class Queue <E>{
    private E[] vet;    //Array que irá armazenar os itens do vetor
    private int max;    //Variável de controle do último item

    //Construtor com o tamanho padrão do array em 100
    Queue(){
        vet = (E[]) new Object[100];
        max = 0;
    }

    //Construtor com o tamanho do array escolhido pelo usuário
    Queue(int i){
        vet = (E[]) new Object[i];
        max = 0;
    }

    //Método que insere o elemento na fila
    public void queue(E e) throws Exception{
        if (max < vet.length) { //Verifica se o máximo de itens foi atingido
            vet[max] = e;   //Inclui o novo item
            max++;  //Aumenta a quantidade máxima de itens
        }
        else{
            throw new Exception("Full Queue!!");    //Lança excessão de fila cheia
        }
    }

    //Método que retorna o primeiro item
    public E dequeue() throws Exception{
        E e;    //item que será retornado
        if (max > 0) {  //Verifica se a fila está vazia
            e = vet[0]; //atribui o primeiro item ao item retornado
            //Realiza um shift com os itens do vetor para que não hajam rupturas
            for (int i = 0; i < max -1; i++) {
                vet[i] = vet[i+1];
            }
            max--;  //Decrementa o número máximo de itens
        }
        else{
            throw new Exception("Empty Queue!!");   //Lança excessão de fila vazia
        }
        return e;       //Retorna o item obtido
    }

    //Método que converte o array da fila em uma string
    public String toString(){
        String s = "[";     //Abre a string
        if (max > 0) {  //Caso o array não esteja vazio, escreve o primeiro elemento
            s += vet[0].toString();
        }
        //Escreve os demais itens na string
        for (int i = 1; i < max; i++) {
            s += ", " + vet[i].toString();
        }
        s += "]";   //Fecha a string
        return s;
    }

    //retorna o primeiro item, sem removê-lo
    public E first(){
        return vet[0];
    }

    //Limpa a fila
    public void clear(){
        //Percorre toda a fila, apontando para null seus valores
        for (int i = 0; i < vet.length; i++) {
            vet[i] = null;
        }
        max = 0;    //Zera a quantidade de itens na lista
    }
}