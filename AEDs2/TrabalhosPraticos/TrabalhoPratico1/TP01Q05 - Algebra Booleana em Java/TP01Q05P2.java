/**
 * TP01Q05 - Algebra Booleana
 */

 public class TP01Q05P2 extends MyIO{

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

    public static int countParams(char[] expression){
        int numberParams = 0;
        int index = 1;
        int inOut = 0;
        while (expression.length > index) {
            if (expression[index] == ',' && inOut ==0) {
                numberParams++;
            }
            if (expression[index] == '(') {
                inOut++;
            }
            else if(expression[index] == ')'){
                inOut--;
            }
        }
        return numberParams;
    }

    public static boolean calculaBooleano(char[] expression, int pos){
        boolean result = false;
        if (pos <= expression.length) {
            println(expression);
            switch (expression[pos]) {
                case 'a':
                    result = true;
                    for(int i = 0; i < 4; i++){
                        expression[pos+i] = 'e';
                    }
                    int limit = countParams(getSubExpression(expression));
                    for (int i = 0; i < limit; i++) {
                        pos++;
                        result &= calculaBooleano(expression, pos);
                    }
                    result = calculaBooleano(expression,pos+4) & calculaBooleano(expression, pos+5);
                    break;

                case 'o':
                result = false;
                    for(int i = 0; i < 3; i++){
                        expression[pos+i] = 'e';
                    }
                    result = calculaBooleano(expression,pos+3) | calculaBooleano(expression, pos+4);
                    break;

                case 'n':
                    for(int i = 0; i < 4; i++){
                        expression[pos+i] = 'e';
                    }
                    result = !calculaBooleano(expression, pos+4);
                    break;
                case '1':
                    result = true;
                    expression[pos] = 'e';
                    break;

                case '0':
                    result = false;
                    expression[pos] = 'e';
                    break;

                default:
                    if(expression[pos] != '('){
                        expression[pos] = 'e';
                    }
                    result = calculaBooleano(expression,pos+1);
                    break;
            }
            if (pos <= expression.length) {
                //result = calculaBooleano(expression,pos+1);
            }
            
        }
        
        return result;
    }

    public static boolean calculaBooleano(char[] expression){
        return calculaBooleano(expression,0);
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

    public static char[] stringToChar(String str){
        char[] vetChar = new char[str.length()];
        for (int i = 0; i < vetChar.length; i++) {
            vetChar[i] = str.charAt(i);
        }
        return vetChar;
    }
    public static void main(String[] args) {
        boolean sair = false;

        while (!sair) {
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
            
        }

        
    }
    
}