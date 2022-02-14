/**
 Autor: Pedro Pampolini Mendicino
 Criação: 12/02/2022    Última atualização: 12/02/2022
 Objetivo do Programa:  Método que recebe um array de inteiros e os ordena recursivamente
 */
public class Exercicio6 {

    public static int[] bubble(int[] vet, int pos,int time){
        if (time < vet.length) {
            if (pos >= vet.length - 1) {
                return bubble(vet, 0, time +1);
            }
            else{
                if (vet[pos] > vet[pos+1]) {
                    int temp = vet[pos];
                    vet[pos] = vet[pos+1];
                    vet[pos+1] = temp;
                }
            }
            return bubble(vet, pos+1,time);
        }
        else{
            return vet;
        }
        
    }

    public static int[] bubble(int[] vet){
        
        return bubble(vet,0,0);
    }
    public static void main(String[] args) {
        int[] vet = {9,8,7,6,5,4,3,2,1,0};

        for (int i = 0; i < vet.length; i++) {
            MyIO.println(bubble(vet)[i]);
        }
        
    }
}
