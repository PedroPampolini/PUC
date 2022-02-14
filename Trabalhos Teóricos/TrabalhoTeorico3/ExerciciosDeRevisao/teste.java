package ExerciciosDeRevisao;

public class teste {
    public static boolean isConsoante(String s, int n){
        boolean resp = true;
        if(n != s.length()){
            if(s.charAt(n) < '0' || s.charAt(n) > '9'){
                if(s.charAt(n) == 'A' || s.charAt(n) == 'E' || s.charAt(n) == 'I' || s.charAt(n) == 'O' || s.charAt(n) == 'U' || s.charAt(n) == 'a' || s.charAt(n) == 'e' || s.charAt(n) == 'i' || s.charAt(n) == 'o' || s.charAt(n) == 'u'){
                    resp = false;
                }
                else{
                    n++;
                    resp = isConsoante(s,n);
                }
            }
            else{
                resp = false;
            }
        }
        return resp;
    }

    public static void main(String[] args) {
        String nome = "Pdr";

        System.out.println(isConsoante(nome,1));
    }
}
