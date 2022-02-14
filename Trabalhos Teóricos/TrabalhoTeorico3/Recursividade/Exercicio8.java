public class Exercicio8 {
    public static int calcula(int time){
        int result;
        if(time == 0){
            result = 1;
        }
        else{
            result = calcula(time - 1) * calcula(time - 1);
        }
        return result;
    }
    public static void main(String[] args) {
        int time = 20;
        MyIO.println(calcula(time));
    }
}
