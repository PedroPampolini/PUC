/**
 * Teste
 */
public class Teste {

    public static boolean contains(int[] vet, int valor,int esq, int dir){
        int mid = (dir + esq) / 2;
        boolean resp = false;
        if (esq <= dir) {
            if (vet[mid] < valor) {
                esq = mid + 1;
                resp = contains(vet, valor, esq, dir);
            }
            else if (vet[mid] > valor){
                dir = mid - 1;
                resp = contains(vet, valor, esq, dir);
            }
            if (vet[mid] == valor) {
                resp = true;
            }
        }
        
        return resp;
    }

    public static boolean contains(int[] vet, int valor){
        return contains(vet, valor,0,vet.length - 1);
    }

    public static boolean containsIte(int[] vet, int valor){
        int esq = 0, dir = vet.length - 1, mid = (dir + esq) / 2;
        boolean resp = false;

        while (esq <= dir && valor != vet[mid]) {
            
            if (valor < vet[mid]) {
                dir = mid - 1;
                mid = (dir + esq) / 2;
            }
            else if (valor > vet[mid]) {
                esq = mid + 1;
                mid = (dir + esq) / 2;
            }
        }
        if(valor == vet[mid]){
            resp = true;
        }

        return resp;
    }

    public static boolean containsStr(String[] vet, String valor,int esq, int dir){
        int mid = (dir + esq) / 2;
        boolean resp = false;
        if (esq <= dir) {
            if (valor.compareTo(vet[mid]) > 0) {
                esq = mid + 1;
                resp = containsStr(vet, valor, esq, dir);
            }
            else if (valor.compareTo(vet[mid]) < 0){
                dir = mid - 1;
                resp = containsStr(vet, valor, esq, dir);
            }
            if (valor.compareTo(vet[mid]) == 0) {
                resp = true;
            }
        }
        
        return resp;
    }

    public static boolean containsStr(String[] vet, String valor){
        return containsStr(vet, valor,0,vet.length - 1);
    }

    public static void main(String[] args) {
        int[] v = {1,3,5,7,9,11,13,15};        
        int valor = 7;
        System.out.println((containsIte(v,valor) ? "SIM" : "NAO"));


        String[] vetStr = {"a","c","h","j","m","r","x","z"};
        String valorStr = "z";
        //System.out.println(valorStr.compareTo(vetStr[3]));
        //System.out.println(containsStr(vetStr,valorStr) ? "SIM" : "NAO");
        
    }
}