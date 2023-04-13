/*
Autor: Pedro Pampolini Mendicino
Data de Criação: 05/04/2022      Última atualização: 05/04/2022
Objetivo: Recebe uma lista de nome de arquivos, abre e realiza a leitura dos mesmos salvando os valores eo objetos da
classe Filme. Então recebe uma outra série de entradas para realizar uma busca sequencial para verificar se o filme
procurado se encontra ou não no vetor
*/
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;
import java.lang.Math;
import java.text.SimpleDateFormat;
import java.io.*;
import java.nio.charset.*;

public class Exercicio3 {
   private static int count = 0;


   private static boolean isFim(String s) {
      return (s.length() == 3 && s.charAt(1) == 'I' && s.charAt(2) == 'M');
   }

   //Método que realiza uma busca sequencial no arquivo
   public static boolean hasMovie(String filme, Filme[] catalogo) {
      boolean resp = false;   //Variável com a resposta se foi encontrado ou não
      for (int i = 0; i < catalogo.length; i++) {
         count++;    //Incrementa o contador de contabilização de condições
         //Verifica se o filme analisado é igual ao procurado
         if (catalogo[i].getNome().equals(filme) == true) {
            resp = true;   // caso seja, seta a variável de resposta
            i = catalogo.length; //seta a condicional de parada do loop
         }
      }
      return resp;
   }

   public static void main(String[] args) throws Exception {
      String[] listaFilmes = new String[50]; // Vetor que recebe o nome dos arquivos para abertura
      int max = 0; // Variável que armazena o máximo de filmes que serão analisados

      listaFilmes[0] = MyIO.readLine(); // Realiza a leitura do primeiro
      // Realiza a leitura dos nomes dos arquivos até que encontre a string "FIM"
      while (!isFim(listaFilmes[max]) && max < listaFilmes.length) {
         max++; // Incrementa o máximo de filmes
         listaFilmes[max] = MyIO.readLine(); // Reliza a leitura do próximo
      }
      //Cria um vetor dos filmes com o tamanhp igual ao número de nomes de arquivos lidos
      Filme[] catalogo = new Filme[max];
      //Realiza a leitura de cada arquivo
      for (int i = 0; i < max; i++) {
         catalogo[i] = new Filme(listaFilmes[i]);     //Cria o construtor para o arquivo
         catalogo[i].getAll();      //Chama o método que realiza a leitura de todos os atributos do filme
      }

      //Cria o arquivo para análise futura de rendimento do algorítmo
      RandomAccessFile log = new RandomAccessFile("matricula_sequencial.txt", "rw");
      Long d1 = new Date().getTime();  //Salva o tempo antes da pesquisa
      // Busca dos valores
      String busca = MyIO.readLine();  //Realiza a leitura do primeiro título
      //Enquanto a string de entrada for Diferente de "FIM", continua a leitura da entrada
      while (!isFim(busca)) {
         //Chama o método de busca binária
         MyIO.println((hasMovie(busca,catalogo)) ? "SIM" : "NAO");
         busca = MyIO.readLine();   //Realiza a leitura da próxima linha
      }
      long d2 = new Date().getTime() - d1; // Compara o tempo após as buscas com o tempo antes
      log.writeBytes("747472\t" + d2 + "\t" + count); //Salva no arquivo o valor de cada
      log.close();      //Fecha o arquivo para salvar
   }
}

class Filme {
   // Atrbutos base
   private String nome = new String();
   private String tituloOriginal = new String();
   private Date lancamento = new Date();
   private String lancamentoStr = new String();
   private int duracao;
   private String genero = new String();
   private String idiomaOriginal = new String();
   private String situacao = new String();
   private float orcamento; // Variável que armazena
   private String[] keyWords = new String[50]; // Array de strings que armazenará todas as palavras chaves
   private RandomAccessFile html; // Arquivo que armazenará o código da página, para ser analisado
   private String htmlUtil = new String(); // String que armazena o html com as informações
   private boolean hasKeywords = true; // Flag para verificar se possui palavras chaves ou não

   // Construtor com os parâmetros
   Filme(String html) throws Exception {
      this.html = new RandomAccessFile("/tmp/filmes/" + html, "r");
      // htmlUtil = getHtmlUtil();
   }

   public void getAll() throws Exception {
      String aux = new String(); // String que armazenará a linha
      aux = html.readLine();
      /*
       * Realiza o escaneamento do Nome
       */
      // Pesquisa pela linha que dará início à sessão de linhas úteis
      while (aux.indexOf("header poster") == -1) {
         aux = html.readLine(); // Passa para a próxima linha
      }
      // Realiza a leitura até encontrar a linha com o titulo
      while (aux.indexOf("<a href=") == -1) {
         aux = html.readLine();
      }
      aux = this.removeTag(aux); // remove as tags da linha
      nome = reduceSpace(aux); // Retira os espaços desnecessários da linha do título
      /*---------------------------------------------------------------------------*/

      /*
       * Realiza o escaneamento da Data de Lançamento
       */
      // Realiza as leituras de linhas até achar <div class="facts">
      while (aux.indexOf("facts") == -1) {
         aux = html.readLine(); // Pula para a próxima linha
      }

      // Realiza as leituras de linhas até achar <div class="release">
      while (aux.indexOf("release") == -1) {
         aux = html.readLine(); // Pula para a próxima linha
      }
      aux = html.readLine(); // pula mais uma linha, pois a data se encontra na linha abaixo à abertura da
                             // tag
      lancamentoStr = reduceSpace(removeTag(aux)); // remove espaços e tags desnecessários
      lancamento = parseDate(lancamentoStr); // Converte a String em formato "Date"
      /*---------------------------------------------------------------------------*/

      /*
       * Realiza o escaneamento dos Gêneros
       */
      // Realiza as leituras de linhas até achar <div class="genres">
      while (aux.indexOf("genres") == -1) {
         aux = html.readLine(); // Pula para a próxima linha
      }
      // Pula as linhas até encontrar a que possui os gêneros
      while (aux.indexOf("<a href") == -1) {
         aux = html.readLine();
      }
      genero = removeTag(aux); // Remove a tag da linha
      genero = removeStrings(genero, "&nbsp;"); // Remove strings desncessárias
      genero = reduceSpace(genero);
      /*---------------------------------------------------------------------------*/

      /*
       * Realiza o escaneamento da Duração
       */
      // Pula as linhas até encontrar <div class="runtime">
      while (aux.indexOf("runtime") == -1) {
         aux = html.readLine();
      }
      aux = html.readLine(); // Pula uma quebra de linha desnecessária
      aux = html.readLine(); // Realiza a leitura do tempo
      // System.out.println(aux);
      aux = reduceSpace(aux);
      duracao = (aux.indexOf("h") != -1) ? parseInt("" + aux.charAt(0)) * 60 : 0; // Realiza o parse do char para
                                                                                  // inteiro
      duracao += (aux.indexOf("h") != -1) ? parseInt(aux.substring(3, 5).replaceAll("m", ""))
            : parseInt(aux.substring(0, 2).replaceAll("m", ""));
      /*---------------------------------------------------------------------------*/

      /*
       * Realiza o escaneamento da Para a section com os valores restantes
       * 
       * Realiza a leitura das linhas até chegar na linha <section
       * class="facts left_column">
       */
      while (aux.indexOf("facts left_column") == -1) {
         aux = html.readLine();
      }
      // Realizar a leitura do Título original após ler tudo, não há um padrão certo
      /*
       * Realiza o escaneamento da Situação
       */
      while (aux.indexOf("Situa") == -1) {
         aux = html.readLine(); // Realiza a leitura da próxima linha
      }
      aux = reduceSpace(removeTag(aux)); // Limpa as tags e os espaços em excesso
      situacao = aux.substring(11, aux.length()); // Remove "Situação" da string
      /*---------------------------------------------------------------------------*/

      /*
       * Realiza o escaneamento do Idioma Original
       */
      while (aux.indexOf("Idioma original") == -1) {
         aux = html.readLine();
      }
      aux = reduceSpace(removeTag(aux));
      idiomaOriginal = aux.substring(16, aux.length());
      /*---------------------------------------------------------------------------*/

      /*
       * Realiza o escaneamento do Orçamento
       */
      while (aux.indexOf("amento") == -1) {
         aux = html.readLine();
      }
      aux = reduceSpace(removeTag(aux));
      orcamento = parseFloat(aux);
      /*---------------------------------------------------------------------------*/

      /*
       * Realiza o escaneamento das Palavras chaves
       */
      // Posiciona na linha que se encontra <section class="keywords right_column">
      while (aux.indexOf("keywords right_column") == -1) {
         aux = html.readLine();
      }
      // Posiciona o vetor no início da lista <ul> ou irá ler que não há palavras
      // chave
      while (aux.indexOf("<li") == -1 && aux.indexOf("Nenhuma") == -1) {
         aux = html.readLine();
      }
      // Verifica se a linha lida é a que mostra se não há palavras chave
      if (aux.indexOf("Nenhuma") != -1) {
         hasKeywords = false;
      }
      if (hasKeywords) { // Apenas lerá se houver palavras chaves
         int keyWordsIndex = 0; // Index da posição das palavras-chave
         // Irá pegar o valor de todos os valores da lista <ul>
         while (aux.indexOf("</ul>") == -1) {
            if (aux.indexOf("<a") != -1) {
               keyWords[keyWordsIndex] = removeTag(aux).trim(); // Insere a palavra limpapedro
               keyWordsIndex++; // Incrementa o indice para adicionar na próxima posição
            }
            aux = html.readLine(); // Lê a próxima linha
         }
      }

      /*
       * Reescaneia o arquivo, procurando o título original
       */
      html.seek(0); // Reposiciona o ponteiro para o inicio
      try {
         while (aux.indexOf("tulo original") == -1) {
            aux = html.readLine(); // Lê a proxima linha
         }
         // Atribui a string encontrada ao título original
         tituloOriginal = reduceSpace(removeTag(aux)).substring(17);
      } catch (Exception e) { // Caso a string vire null, apenas atribuirá a string nome ao título original
         tituloOriginal = nome;
      }

      html.close();

   }

   // Métodos Get
   public String getNome() {
      return nome;
   }

   public String getLancamentoStr() {
      String dataStr = new String();
      try {
         SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/YYYY");
         dataStr = fmt.format(lancamento);
      } catch (Exception e) {
         System.err.println(e.getMessage());
      }
      return dataStr;
   }

   public Date getLancamento() {
      return lancamento;
   }

   public String getGenero() {
      return genero;
   }

   public int getDuracao() {
      return duracao;
   }

   public String getTituloOriginal() {
      return tituloOriginal;
   }

   public String getSituacao() {
      return situacao;
   }

   public String getIdiomaOriginal() {
      return idiomaOriginal;
   }

   public float getOrcamento() {
      return orcamento;
   }

   public String getKeyWords() {
      String s = "[";
      if (keyWords[0] != null) {
         s += keyWords[0];
      }
      for (int i = 1; i < keyWords.length; i++) {
         if (keyWords[i] != null) {
            s += ", " + keyWords[i];
         }
      }
      s += "]";
      return s;
   }

   /*
    * Métodos que serão utilizados como facilitadores para a leitura dos itens
    */
   public String removeTag(String s) {
      String newS = new String(); // String sem as tags
      int inTag = 0; // Variável que verifica se está lendo uma tag ou um texto

      // Percorre toda a string para recuperar apenas texto
      for (int i = 0; i < s.length(); i++) {
         if (s.charAt(i) == '<') { // Verifica se está entrando em uma tag
            inTag++; // Incrementa para não permitir a leitura
         } else if (s.charAt(i) == '>') { // Verifica se saiu da tag
            inTag--; // Decrementa para se aproximar da permissão da leitura
         } else if (inTag == 0) { // Se inTag == 0, significa que está lendo um texto
            newS += "" + s.charAt(i);
         }
      }

      return newS;
   }

   // Método que remove os espaços em excesso das strings, inclusive o espaço no
   // primeiro caractere
   private String reduceSpace(String s) {
      String newS = new String();
      for (int i = 0; i < s.length(); i++) {
         if (s.charAt(i) != ' ') {
            newS += s.charAt(i);
         } else if (s.charAt(i + 1) != ' ' && newS.length() > 0) {
            newS += ' ';
         }
      }
      return newS;
   }

   // Método que converte uma string no formato dd/mm/aaaa (local)
   private Date parseDate(String s) {
      Date data = null;
      if (s != null) {
         try {
            SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
            data = fmt.parse(s);
         } catch (Exception e) {
            System.err.println(e.getMessage());
         }
      }
      return data;
   }

   // Método que converte uma string em inteiro
   private int parseInt(String s) {
      int result = 0;
      for (int i = 0; i < s.length(); i++) {
         result += ((int) s.charAt(i) - 48) * Math.pow(10, s.length() - 1 - i);
      }
      return result;
   }

   // Método que converte uma String em um Float
   public float parseFloat(String s) {
      float num = 0.0f;
      int indexPot = 0;
      for (int i = s.length() - 4; i >= 0; i--) { // realiza a leitura do número desconsiderando a parte decimal
         if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
            num += (s.charAt(i) - 48) * Math.pow(10, indexPot++);
         }
      }
      return num;
   }

   // Método que retira strings de uma string
   public String removeStrings(String s, String pattern) {
      String newS = new String();
      if (s.length() > pattern.length()) {

         for (int i = 0; i < s.length(); i++) {
            if (i >= s.length() - pattern.length()) {
               newS += s.charAt(i);
            } else {
               if (!s.substring(i, i + pattern.length()).equals(pattern)) {
                  newS += s.charAt(i);
               } else {
                  i += pattern.length() - 1;
               }
            }
         }
      }

      return newS;
   }

   // Método que transforma tudo em string
   public String toString() {
      return this.getNome() + " " + this.getTituloOriginal() + " " + this.getLancamentoStr() + " " + this.getDuracao()
            + " " + this.getGenero() + " " + this.getIdiomaOriginal() + " " + this.getSituacao() + " "
            + this.getOrcamento() + " " + this.getKeyWords();
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
