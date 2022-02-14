public class Exercicio7 {
    public static int calcula(int time){
        int result;
        if(time == 0){
            result = 0;
        }
        else if (time == 1) {
            result = 2;
        }
        else{
            result = calcula(time - 1) * calcula(time - 2) - calcula(time - 1);
        }
        return result;
    }
    public static void main(String[] args) {
        int time = 10;
        MyIO.println(calcula(time));
    }
}
