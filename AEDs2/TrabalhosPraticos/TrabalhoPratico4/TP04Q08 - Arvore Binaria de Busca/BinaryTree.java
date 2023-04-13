public class BinaryTree {
    Node raiz;

    BinaryTree(){
        raiz = null;
    }

    public void insert(int e) throws Exception{
        raiz = insert(e, raiz);
    }
    private Node insert(int e, Node i) throws Exception{
        if (i == null) {
            i = new Node(e);
        }
        else if(e < i.e){
            i.menor = insert(e, i.menor);
        }
        else if(e > i.e){
            i.maior = insert(e, i.maior);
        }
        else{
            throw new Exception("Elemento ja existente!!");
        }
        return i;
    }

    public void remove(int e) throws Exception{
        raiz = remove(e,raiz);
    }
    private Node remove(int e, Node i) throws Exception{
        if(i == null){
            throw new Exception("Elemento nao existente");
        }
        if(e < i.e){
            i.menor = remove(e,i.menor);
        }
        else if(e > i.e){
            i.maior = remove(e,i.maior);
        }
        else{
            if(i.maior == null){
                i = i.menor;
            }
            else if(i.menor == null){
                i = i.maior;
            }
            else{
                i.menor = maiorEsq(i, i.menor);
            }
        }
        return i;
    }

    private Node maiorEsq(Node i, Node j){
        if(j.maior == null){
            i.e = j.e;
            j = j.menor;
        }
        else{
            j.maior = maiorEsq(i, j.maior);
        }
        return j;
    }

    public void caminharCentral(){
        caminharCentral(raiz);
    }
    private void caminharCentral(Node i){
        if(i != null){
            caminharCentral(i.menor);
            System.out.print(i.e + " ");
            caminharCentral(i.maior);
        }
    }

    public void caminharPos(){
        caminharPos(raiz);
    }
    private void caminharPos(Node i){
        if(i != null){
            caminharPos(i.menor);
            caminharPos(i.maior);
            System.out.print(i.e + " ");
        }
    }

    public void caminharPre(){
        caminharPre(raiz);
    }
    private void caminharPre(Node i){
        if(i != null){
            System.out.print(i.e + " ");
            caminharPre(i.menor);
            caminharPre(i.maior);
        }
    }
}
