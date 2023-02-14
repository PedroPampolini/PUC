/**
 * KMP
 */
public class KMP {
    private String padrao;
    private int[] vetFalha;
    private String texto;

    public KMP() {
        this("");
    }

    KMP(String padrao){
        this.padrao = padrao;
        this.vetFalha = new int[padrao.length()];
        this.texto = "";
        this.constroiVetorFalha();
    }

    private void constroiVetorFalha(){
        vetFalha[0] = 0;    //O primeiro elemento do vetor sempre será 0
        int i = 0;      //indice do vetor de falha
        int j = 1;      //indice do padrao
        //Itera sobre o padrao para preencher o vetor de falha com os retornos de estados
        while(j < padrao.length()){
            //Caso de match, avança o índice de ambos os vetores para continuar o casamento
            if(padrao.charAt(i) == padrao.charAt(j)){
                vetFalha[j] =  i + 1;   //Salva no vetor o seu estado de retorno
                i++;
                j++;
            }
            //Caso falhe, verifica sejá houve algum caminhamento, se sim, volta para o estado de retorno
            else if(i > 0){
                i = vetFalha[i - 1];    //verifica a última posição de sucesso e retorna à ela
            }
            else{
                vetFalha[j] = 0;    //Caso não haja caminhamento, o estado de retorno é 0
                j++;
            }
        }
    }

    public boolean contains(String texto){
        return this.indexOf(this.padrao, texto) >= 0;
    }

    public boolean contains(String padrao,String texto){
        return this.indexOf(padrao, texto) >= 0;
    }

    public int indexOf(String texto){
        return this.indexOf(this.padrao, texto);
    }

    public int indexOf(String padrao,String texto){
        this.padrao = padrao;
        this.vetFalha = new int[padrao.length()];
        this.texto = texto;
        int padraoIndex = 0;    //indice do padrao
        int textoIndex = 0;     //indice do texto
        int resp = -1;      //index do padrao encontrado
        if(padrao.length() > 0 && texto.length() > 0){
            //enquando houverem caracteres para se comparar, procura o padrão
            while(textoIndex < texto.length() && padraoIndex < padrao.length()){
                //Caso haja match, avança os indices
                if(texto.charAt(textoIndex) == padrao.charAt(padraoIndex)){
                    textoIndex++;
                    padraoIndex++;
                }
                else{
                    //Caso a falha nao seja na primeira letra, vai para a ultima posição que deu match
                    if(padraoIndex > 0){
                        padraoIndex = vetFalha[padraoIndex - 1];
                    }
                    //Caso a falha seja na primeira letra, avança o texto
                    else{
                        textoIndex++;
                    }
                }
            }
            //Caso o indice padrão tenha chegado no final, significa que o padrão foi encontrado
            if(padraoIndex == padrao.length()){
                //Salva o index do padrão encontrado
                resp = textoIndex - padrao.length();
            }
        }
        return resp;
    }

    public void imprimeVetorFalha(){
        for(int i = 0; i < vetFalha.length; i++){
            System.out.print(vetFalha[i] + " ");
        }
        System.out.println();
    }
}