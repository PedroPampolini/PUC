import java.io.*;
import java.net.*;
import java.nio.charset.*;

public class Exercicio8P2 {
    public static boolean isFim(String s) {
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static boolean isLetter(char c) {
        return ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'));
    }

    public static int vogalIndex(char c) {
        int index = -1;
        String vogais = "aeiou";
        for (int i = 0; i < vogais.length(); i++) {
            if (vogais.charAt(i) == c) {
                index = i;
                i = vogais.length();
            }
        }
        return index;
    }

    public static boolean isVogal(char c) {
        return (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'A' || c == 'E' || c == 'I' || c == 'O'
                || c == 'U');
    }

    public static boolean isConsonant(char c) {
        return (isLetter(c) && !isVogal(c));
    }

    public static String getHtml(String endereco) {
        URL url;
        InputStream is = null;
        BufferedReader br;
        String resp = "", line;

        try {
            url = new URL(endereco);
            is = url.openStream(); // throws an IOException
            br = new BufferedReader(new InputStreamReader(is, "ISO-8859-1"));

            while ((line = br.readLine()) != null) {
                resp += line + "\n";
            }
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        try {
            is.close();
        } catch (IOException ioe) {
            // nothing to see here

        }

        return resp;
    }

    public static boolean table(String s, int i) {
        boolean resp = true;
        String table = "<table>";
        for (int j = 0; j < 7; j++) {
            if (s.charAt(i + j) != table.charAt(j)) {
                resp = false;
                j = 7;
            }
        }
        return resp;
    }

    public static boolean br(String s, int i) {
        boolean resp = true;
        String br = "<br>";
        for (int j = 0; j < 4; j++) {
            if (s.charAt(i + j) != br.charAt(j)) {
                resp = false;
                j = 7;
            }
        }
        return resp;
    }

    public static void imprimeResultados(int[] vetor, String nome) {
        MyIO.print("a(" + vetor[0] + ") ");
        MyIO.print("e(" + vetor[1] + ") ");
        MyIO.print("i(" + vetor[2] + ") ");
        MyIO.print("o(" + vetor[3] + ") ");
        MyIO.print("u(" + vetor[4] + ") ");
        MyIO.print("�(" + vetor[5] + ") ");
        MyIO.print("�(" + vetor[6] + ") ");
        MyIO.print("�(" + vetor[7] + ") ");
        MyIO.print("�(" + vetor[8] + ") ");
        MyIO.print("�(" + vetor[9] + ") ");
        MyIO.print("�(" + vetor[10] + ") ");
        MyIO.print("�(" + vetor[11] + ") ");
        MyIO.print("�(" + vetor[12] + ") ");
        MyIO.print("�(" + vetor[13] + ") ");
        MyIO.print("�(" + vetor[14] + ") ");
        MyIO.print("�(" + vetor[15] + ") ");
        MyIO.print("�(" + vetor[16] + ") ");
        MyIO.print("�(" + vetor[17] + ") ");
        MyIO.print("�(" + vetor[18] + ") ");
        MyIO.print("�(" + vetor[19] + ") ");
        MyIO.print("�(" + vetor[20] + ") ");
        MyIO.print("�(" + vetor[21] + ") ");
        MyIO.print("consoante(" + vetor[22] + ") ");
        MyIO.print("<br>(" + vetor[23] + ") ");
        MyIO.print("<table>(" + vetor[24] + ") ");
        MyIO.println(nome);

    }

    public static int[] process(String html) {
        int[] tudo = new int[25];
        boolean tagBrTable = false;
        for (int i = 0; i < html.length(); i++) {
            if (table(html, i)) {
                tudo[24]++;
                tagBrTable = true;
            } else if (br(html, i)) {
                tudo[23]++;
                tagBrTable = true;

            }

            if (tagBrTable && html.charAt(i) == '>') {
                tagBrTable = false;
            }
            if (!tagBrTable) {
                if (isConsonant(html.charAt(i))) {
                    // MyIO.println(html.charAt(i));
                    tudo[22]++;
                } else if (vogalIndex(html.charAt(i)) > -1) {
                    // MyIO.println(html.charAt(i));
                    int index = vogalIndex(html.charAt(i));
                    tudo[index]++;
                } else if ((int) html.charAt(i) == 195) {
                    int prox = (int) html.charAt(i + 1);
                    int incIndex = -1;
                    switch (prox) {
                        case 161:
                            incIndex = 5;
                            tudo[incIndex]++;
                            break;
                        case 169:
                            incIndex = 6;
                            tudo[incIndex]++;
                            break;
                        case 173:
                            incIndex = 7;
                            tudo[incIndex]++;
                            break;
                        case 179:
                            incIndex = 8;
                            tudo[incIndex]++;
                            break;
                        case 186:
                            incIndex = 9;
                            tudo[incIndex]++;
                            break;
                        case 160:
                            incIndex = 10;
                            tudo[incIndex]++;
                            break;
                        case 168:
                            incIndex = 11;
                            tudo[incIndex]++;
                            break;
                        case 172:
                            incIndex = 12;
                            tudo[incIndex]++;
                            break;
                        case 178:
                            incIndex = 13;
                            tudo[incIndex]++;
                            break;
                        case 185:
                            incIndex = 14;
                            tudo[incIndex]++;
                            break;
                        case 163:
                            incIndex = 15;
                            tudo[incIndex]++;
                            break;
                        case 181:
                            incIndex = 16;
                            tudo[incIndex]++;
                            break;
                        case 162:
                            incIndex = 17;
                            tudo[incIndex]++;
                            break;
                        case 170:
                            incIndex = 18;
                            tudo[incIndex]++;
                            break;
                        case 174:
                            incIndex = 19;
                            tudo[incIndex]++;
                            break;
                        case 180:
                            incIndex = 20;
                            tudo[incIndex]++;
                            break;
                        case 187:
                            incIndex = 21;
                            tudo[incIndex]++;
                            break;

                        default:
                            break;
                    }

                }
            }

        }
        // imprimeResultados(tudo);
        return tudo;
    }

    public static void main(String[] args) {
        // char teste = MyIO.readChar();
        String siteURL = new String();
        String nome = new String();
        int[] contagem = new int[26];
        // siteURL = "http://maratona.crc.pucminas.br/series/Black_Mirror.html";
        nome = MyIO.readLine();
        while (!isFim(nome)) {
            siteURL = MyIO.readLine();
            String html = getHtml(siteURL);
            contagem = process(html);
            imprimeResultados(contagem, nome);
            nome = MyIO.readLine();
        }

    }
}

class MyIO {

    private static BufferedReader in = new BufferedReader(
            new InputStreamReader(System.in, Charset.forName("ISO-8859-1")));
    private static String charset = "ISO-8859-1";

    public static void setCharset(String charset_) {
        charset = charset_;
        in = new BufferedReader(new InputStreamReader(System.in, Charset.forName(charset)));
    }

    public static void print() {
    }

    public static void print(int x) {
        try {
            PrintStream out = new PrintStream(System.out, true, charset);
            out.print(x);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro: charset invalido");
        }
    }

    public static void print(float x) {
        try {
            PrintStream out = new PrintStream(System.out, true, charset);
            out.print(x);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro: charset invalido");
        }
    }

    public static void print(double x) {
        try {
            PrintStream out = new PrintStream(System.out, true, charset);
            out.print(x);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro: charset invalido");
        }
    }

    public static void print(String x) {
        try {
            PrintStream out = new PrintStream(System.out, true, charset);
            out.print(x);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro: charset invalido");
        }
    }

    public static void print(boolean x) {
        try {
            PrintStream out = new PrintStream(System.out, true, charset);
            out.print(x);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro: charset invalido");
        }
    }

    public static void print(char x) {
        try {
            PrintStream out = new PrintStream(System.out, true, charset);
            out.print(x);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro: charset invalido");
        }
    }

    public static void println() {
    }

    public static void println(int x) {
        try {
            PrintStream out = new PrintStream(System.out, true, charset);
            out.println(x);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro: charset invalido");
        }
    }

    public static void println(float x) {
        try {
            PrintStream out = new PrintStream(System.out, true, charset);
            out.println(x);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro: charset invalido");
        }
    }

    public static void println(double x) {
        try {
            PrintStream out = new PrintStream(System.out, true, charset);
            out.println(x);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro: charset invalido");
        }
    }

    public static void println(String x) {
        try {
            PrintStream out = new PrintStream(System.out, true, charset);
            out.println(x);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro: charset invalido");
        }
    }

    public static void println(boolean x) {
        try {
            PrintStream out = new PrintStream(System.out, true, charset);
            out.println(x);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro: charset invalido");
        }
    }

    public static void println(char x) {
        try {
            PrintStream out = new PrintStream(System.out, true, charset);
            out.println(x);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro: charset invalido");
        }
    }

    public static void printf(String formato, double x) {
        try {
            PrintStream out = new PrintStream(System.out, true, charset);
            out.printf(formato, x);// "%.2f"
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro: charset invalido");
        }
    }

    public static double readDouble() {
        double d = -1;
        try {
            d = Double.parseDouble(readString().trim().replace(",", "."));
        } catch (Exception e) {
        }
        return d;
    }

    public static double readDouble(String str) {
        try {
            PrintStream out = new PrintStream(System.out, true, charset);
            out.print(str);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro: charset invalido");
        }
        return readDouble();
    }

    public static float readFloat() {
        return (float) readDouble();
    }

    public static float readFloat(String str) {
        return (float) readDouble(str);
    }

    public static int readInt() {
        int i = -1;
        try {
            i = Integer.parseInt(readString().trim());
        } catch (Exception e) {
        }
        return i;
    }

    public static int readInt(String str) {
        try {
            PrintStream out = new PrintStream(System.out, true, charset);
            out.print(str);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro: charset invalido");
        }
        return readInt();
    }

    public static String readString() {
        String s = "";
        char tmp;
        try {
            do {
                tmp = (char) in.read();
                if (tmp != '\n' && tmp != ' ' && tmp != 13) {
                    s += tmp;
                }
            } while (tmp != '\n' && tmp != ' ');
        } catch (IOException ioe) {
            System.out.println("lerPalavra: " + ioe.getMessage());
        }
        return s;
    }

    public static String readString(String str) {
        try {
            PrintStream out = new PrintStream(System.out, true, charset);
            out.print(str);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro: charset invalido");
        }
        return readString();
    }

    public static String readLine() {
        String s = "";
        char tmp;
        try {
            do {
                tmp = (char) in.read();
                if (tmp != '\n' && tmp != 13) {
                    s += tmp;
                }
            } while (tmp != '\n');
        } catch (IOException ioe) {
            System.out.println("lerPalavra: " + ioe.getMessage());
        }
        return s;
    }

    public static String readLine(String str) {
        try {
            PrintStream out = new PrintStream(System.out, true, charset);
            out.print(str);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro: charset invalido");
        }
        return readLine();
    }

    public static char readChar() {
        char resp = ' ';
        try {
            resp = (char) in.read();
        } catch (Exception e) {
        }
        return resp;
    }

    public static char readChar(String str) {
        try {
            PrintStream out = new PrintStream(System.out, true, charset);
            out.print(str);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro: charset invalido");
        }
        return readChar();
    }

    public static boolean readBoolean() {
        boolean resp = false;
        String str = "";

        try {
            str = readString();
        } catch (Exception e) {
        }

        if (str.equals("true") || str.equals("TRUE") || str.equals("t") || str.equals("1") ||
                str.equals("verdadeiro") || str.equals("VERDADEIRO") || str.equals("V")) {
            resp = true;
        }

        return resp;
    }

    public static boolean readBoolean(String str) {
        try {
            PrintStream out = new PrintStream(System.out, true, charset);
            out.print(str);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro: charset invalido");
        }
        return readBoolean();
    }

    public static void pause() {
        try {
            in.read();
        } catch (Exception e) {
        }
    }

    public static void pause(String str) {
        try {
            PrintStream out = new PrintStream(System.out, true, charset);
            out.print(str);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro: charset invalido");
        }
        pause();
    }
}