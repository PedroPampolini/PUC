import java.beans.Expression;

public class Exercicio5 {

    public static boolean and(char[] expression){
        boolean result = true;
        int pos = 0;
        
        for (int i = 0; i < expression.length; i++) {
            //println(expression);
            //MyIO.println(result);
            switch (expression[pos]) {
                case '1':
                    expression[pos] = 'e';
                    result &= true;
                    pos++;
                    break;

                case '0':
                    expression[pos] = 'e';
                    result &= false;
                    pos++;
                    break;
                case 'n':
                    for (int j = 0; j < 3; j++) {
                        expression[pos+j] = 'e';
                    }
                    result = not(getSubExpression(expression));
                    break;
                
                case 'o':
                    for (int j = 0; j < 2; j++) {
                        expression[pos+j] = 'e';
                    }
                    result = or(getSubExpression(expression));
                    break;
                
                case 'a':
                    for (int j = 0; j < 3; j++) {
                        expression[pos+j] = 'e';
                    }
                    result = and(getSubExpression(expression));
                    break;
                default:
                expression[pos] = 'e';
                    pos++;
                    break;
            }
            println(expression);
            MyIO.println(result);
        }
        return result;
    }

    public static boolean or(char[] expression){
        boolean result = false;
        int pos = 0;
        for (int i = 0; i < expression.length; i++) {
            switch (expression[pos]) {
                case '1':
                    expression[pos] = 'e';
                    result |= true;
                    pos++;
                    break;

                case '0':
                    expression[pos] = 'e';
                    result |= false;
                    pos++;
                    break;
                case 'n':
                    for (int j = 0; j < 3; j++) {
                        expression[pos+j] = 'e';
                    }
                    result = not(getSubExpression(expression));
                    break;
                
                case 'o':
                    for (int j = 0; j < 2; j++) {
                        expression[pos+j] = 'e';
                    }
                    result = or(getSubExpression(expression));
                    break;
                
                case 'a':
                    for (int j = 0; j < 3; j++) {
                        expression[pos+j] = 'e';
                    }
                    result = and(getSubExpression(expression));
                    break;
                default:
                    pos++;
                    break;
            }
        }
        return result;
    }

    public static boolean not(char[] expression){
        boolean result = true;
        int pos = 0;
        for (int i = 0; i < expression.length; i++) {
            switch (expression[pos]) {
                case '1':
                    expression[pos] = 'e';
                    result = false;
                    pos++;
                    break;

                case '0':
                    expression[pos] = 'e';
                    result = true;
                    pos++;
                    break;
                case 'n':
                    for (int j = 0; j < 3; j++) {
                        expression[pos+j] = 'e';
                    }
                    result = not(getSubExpression(expression));
                    break;
                
                case 'o':
                    for (int j = 0; j < 2; j++) {
                        expression[pos+j] = 'e';
                    }
                    result = or(getSubExpression(expression));
                    break;
                
                case 'a':
                    for (int j = 0; j < 3; j++) {
                        expression[pos+j] = 'e';
                    }
                    result = and(getSubExpression(expression));
                    break;
                default:
                    pos++;
                    break;
            }
        }
        return result;
    }

    public static char[] getSubExpression(char[] expression){
        int indexInitial = 1;
        int out = 1;
        while (expression[indexInitial] != '(' && indexInitial < expression.length) {
            indexInitial++;
        }
        
        char[] subExpression = new char[expression.length - indexInitial];
        int i = indexInitial;
        do{
            if (expression[i] == '(') {
                out++;
            }
            else if(expression[i] == ')'){
                out--;
            }
            if (out > 0) {
                
                subExpression[i - indexInitial] = expression[i];
                i++;
            }
        }while (out > 0 && i < expression.length);
        return subExpression;
    }

    public static boolean calculaBooleano(final char[] expression){
        boolean result = false;
        int pos = 0;
        
        
        switch (expression[pos]) {
            case '1':
                expression[pos] = 'e';
                result = true;
                pos++;
                break;

            case '0':
                expression[pos] = 'e';
                result = false;
                pos++;
                break;
            case 'n':
                for (int j = 0; j < 3; j++) {
                    expression[pos+j] = 'e';
                }
                result = not(getSubExpression(expression));
                break;
            
            case 'o':
                for (int j = 0; j < 2; j++) {
                    expression[pos+j] = 'e';
                }
                result = or(getSubExpression(expression));
                break;
            
            case 'a':
                for (int j = 0; j < 3; j++) {
                    expression[pos+j] = 'e';
                }
                result = and(getSubExpression(expression));
                break;
            default:
                pos++;
                break;
        }
        
        return result;
    }

    public static void println(char[] c){
        for (int i = 0; i < c.length; i++) {
            MyIO.print(c[i]);
        }
        MyIO.println("");
    }

    public static char[] getNumEntradas(char[] vetChar){
        int quantIn = (int)(vetChar[0] - 48);
        char[] entradas = new char[quantIn];
        for (int i = 0; i < entradas.length; i++) {
            entradas[i] = vetChar[2*i+2];
        }
        return entradas;
    }

    public static char[] stringToChar(String str){
        char[] vetChar = new char[str.length()];
        for (int i = 0; i < vetChar.length; i++) {
            vetChar[i] = str.charAt(i);
        }
        return vetChar;
    }

    public static char[] replaceIn(char[] vetChar, char[] entradas){
        char[] provChar = new char[vetChar.length];
        for (int i = 0; i < vetChar.length; i++) {
            if (vetChar[i] == 'A') {
                vetChar[i] = entradas[0];
            }
            else if (vetChar[i] == 'B') {
                vetChar[i] = entradas[1];
            }
            else if (vetChar[i] == 'C') {
                vetChar[i] = entradas[2];
            }
        }
        for (int i = (entradas.length+1)*2; i < provChar.length; i++) {
            provChar[i - (entradas.length+1)*2] = vetChar[i];
        }
        return provChar;
    }
    public static void main(String[] args) {
        boolean sair = false;
        char[] expression = stringToChar(MyIO.readLine());
        System.out.println(calculaBooleano(expression));
        /*while (!sair) {
            boolean resultado = false;
            String text = MyIO.readLine();      //String que recebe o texto inicialmente
            char[] expression = stringToChar(text); //vetor de caracter que é manipulável
            char[] entradas = getNumEntradas(expression);
            
            if (expression[0] == '0') {
                sair = true;
            }
            else{
                //println(expression);
                expression = replaceIn(expression, entradas);
                resultado = calculaBooleano(expression);
                MyIO.println(resultado? 1 : 0);
            }
            
        }*/
    }
}
