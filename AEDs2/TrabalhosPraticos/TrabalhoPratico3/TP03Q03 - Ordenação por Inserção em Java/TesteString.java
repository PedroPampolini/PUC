import java.sql.Date;

public class TesteString {

    public static void printa(int[] num){
        for (int i = 0; i < num.length; i++) {
            System.out.print(num[i] + " ");
        }
        System.out.println();
    }

    public static int[] selectionSort(int[] numeros){

        for (int i = 0; i < numeros.length; i++){
            //Filme menor = catalogo[i];
            for (int j = i+1; j < numeros.length; j++) {
                if (numeros[j] < numeros[i]) {
                    int tmp = numeros[i];
                    numeros[i] = numeros[j];
                    numeros[j] = tmp;
                }
            }
            System.out.print("Passada " + i + ": ");
            printa(numeros);
        }
        return numeros;
    }

    public static void main(String[] args) {
        /*String s1 = "A";
        String s2 = "B";
        String str = (s1.compareTo(s2) < 0) ? "True" : "False";
        
        System.out.println(str);*/
        /*int[] num = {1,3,2,7,6,4,5,9,8,0};

        printa(num);

        num = selectionSort(num);

        printa(num);*/

        Date d1 = new Date(2000,11,30);
        Date d2 = new Date(2022,5,7);

        System.out.println(d1.before(d2));
    }
}
