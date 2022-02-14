/**
 Autor: Pedro Pampolini Mendicino
 Criação: 12/02/2022    Última atualização: 12/02/2022
 Objetivo do Programa:  Método que recebe um array de inteiros e seu tamanho, e retorna o maior número nele
 */
public class Exercicio2 {
    public static int retMaior(int[] vet, int len, int pos){
        int result;
        if (pos == (len-1)) {
            result = vet[pos];
        }
        else{
            result = retMaior(vet, len, pos+1);
            if (vet[pos] > result) {
                result = vet[pos];
            }
        }
        return result;
    }

    public static int retMaior(int[] vet, int len){
        return retMaior(vet, len,0);
    }
    public static void main(String[] args) {
        int[] vet = {0,1,2,3,4,5};
        
        MyIO.println(retMaior(vet,6));
    }
}
