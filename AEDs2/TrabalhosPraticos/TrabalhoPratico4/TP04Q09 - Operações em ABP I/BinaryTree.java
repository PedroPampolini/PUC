public class BinaryTree {
    Node raiz;

    BinaryTree(){
        raiz = null;
    }

    public void insert(char e) throws Exception{
        raiz = insert(e, raiz);
    }
    private Node insert(char e, Node i) throws Exception{
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

    public void remove(char e) throws Exception{
        raiz = remove(e,raiz);
    }
    private Node remove(char e, Node i) throws Exception{
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

    public boolean search(char c){
        return search(c,raiz);
    }
    private boolean search(char c,Node i){
        boolean rsp = false;
        if (i != null) {
            if (c < i.e) {
                rsp = search(c,i.menor);
            }
            else if (c > i.e) {
                rsp = search(c,i.maior);
            }
            else{
                rsp = true;
            }
        }
        return rsp;
    }
}
