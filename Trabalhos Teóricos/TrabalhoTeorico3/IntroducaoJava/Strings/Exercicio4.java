/**
 Autor: Pedro Pampolini Mendicino
 Criação: 11/02/2022    Última atualização: 11/02/2022
 Objetivo do Programa:  Um método que recebe uma string conta quantos caracteres, letras, não letras, 
 vogais e consoantes possui
 maiúsculos
 */
public class Exercicio4 {
    public static boolean isLetra(char c){
        return((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'));
    }

    public static boolean isVogal(char c){
        return(c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U' || c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u');
    }

    public static boolean isConsolante(char c){
        return(isLetra(c) && !isVogal(c));
    }

    public static int countLetra(String s){
        int cont = 0;
        for (int i = 0; i < s.length(); i++) {
            if (isLetra(s.charAt(i))) {
                cont++;
            }
        }
        return cont;
    }

    public static int countConsoante(String s){
        int cont = 0;
        for (int i = 0; i < s.length(); i++) {
            if (isConsolante(s.charAt(i))) {
                cont++;
            }
        }
        return cont;
    }

    public static int countVogal(String s){
    int cont = 0;
        for (int i = 0; i < s.length(); i++) {
            if (isVogal(s.charAt(i))) {
                cont++;
            }
        }
        return cont;
    }
    public static void main(String[] args) {
        int vogais,consoantes,letras;
        String texto = MyIO.readLine("Digite a string: ");

        letras = countLetra(texto);
        vogais = countVogal(texto);
        consoantes = countConsoante(texto);

        MyIO.println("Caracteres: " + texto.length());
        MyIO.println("Letras: " + letras);
        MyIO.println("Nao Letras: " + (texto.length() - letras));
        MyIO.println("Vogais: " + vogais);
        MyIO.println("Consoantes: " + consoantes);
    }
}
