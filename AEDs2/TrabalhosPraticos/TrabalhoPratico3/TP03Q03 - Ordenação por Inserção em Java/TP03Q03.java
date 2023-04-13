
/*
Autor: Pedro Pampolini Mendicino
Data de Criação: 07/05/2022      Última atualização: 07/05/2022
Objetivo: Recebe uma lista de nome de arquivos, abre e realiza a leitura dos mesmos salvando os valores eo objetos da
classe Filme. Então ordena a partir do atributo de Data de lançamento utilizando o algorítmo de insertion sort
*/
import java.util.Date;
import java.lang.Math;
import java.text.SimpleDateFormat;
import java.io.*;
import java.nio.charset.*;

public class TP03Q03 {

    public static int count = 0;
    public static int movim = 0;

    public static Filme[] insertionSort(Filme[] catalogo){
      for (int i = 1; i < catalogo.length; i++) {
         Filme tmp = catalogo[i];   //Salva o filme em uma variável temporária para realizar o shift dos outros
         int j = i - 1; //Referente à maior posição da parte ordenada do vetor

         //Realiza o shift dos filmes até achar a posição do filme atual na parte ordenada
         while (j >= 0 && tmp.getLancamento().before(catalogo[j].getLancamento())) {
            catalogo[j+1] = catalogo[j];  //shift de um elemento com o seu anterior
            j--; 
            count++;
            movim++;
         }
         movim++;
         catalogo[j + 1] = tmp;  //Faz o swap para a posição certa
      }
      return catalogo;
    }

    private static boolean isFim(String s) {
        return (s.length() == 3 && s.charAt(1) == 'I' && s.charAt(2) == 'M');
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
        
        catalogo = insertionSort(catalogo);     //Chama a função de ordenação

       for (int i = 0; i < catalogo.length; i++) {
           MyIO.println(catalogo[i].toString());        //Imprime na tela o resultado ordenado
       }


       //Salva o número de comparações e movimentações do código
       RandomAccessFile matriculaSelecao = new RandomAccessFile("matricula_insercao.txt", "rw");
       matriculaSelecao.writeBytes("747472\t" + count + "\t" + movim);
       matriculaSelecao.close();
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
   Filme(String htmlUtil) throws Exception {
      this.htmlUtil = htmlUtil;
   }

   Filme() {
      this.htmlUtil = "";
   }

   public void getAll() throws IOException {
       try {
        this.html = new RandomAccessFile("/tmp/filmes/" + this.htmlUtil, "r");
       } catch (Exception e) {
        try {
            this.html = new RandomAccessFile("tmp/filmes/" + this.htmlUtil, "r");
           } catch (Exception erro) {
               System.out.println("Arquivo nao encontrado: " + erro.getMessage());
               return;
           }
       }
      
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
      this.setNome(reduceSpace(aux)); // Retira os espaços desnecessários da linha do título
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
      setLancamentoStr(reduceSpace(removeTag(aux))); // remove espaços e tags desnecessários
      setLancamento(parseDate(lancamentoStr)); // Converte a String em formato "Date"
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
      aux = removeTag(aux); // Remove a tag da linha
      aux = removeStrings(aux, "&nbsp;"); // Remove strings desncessárias
      setGenero(reduceSpace(aux));
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
      int tmpDuracao = 0;
      tmpDuracao = (aux.indexOf("h") != -1) ? parseInt("" + aux.charAt(0)) * 60 : 0; // Realiza o parse do char para
                                                                                     // inteiro
      if (aux.indexOf("m") != -1) {
         tmpDuracao += (aux.indexOf("h") != -1) ? parseInt(aux.substring(3, 5).replaceAll("m", ""))
               : parseInt(aux.substring(0, 2).replaceAll("m", ""));
      }
      setDuracao(tmpDuracao);
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
      aux = aux.substring(11, aux.length()); // Remove "Situação" da string
      setSituacao(aux);
      /*---------------------------------------------------------------------------*/

      /*
       * Realiza o escaneamento do Idioma Original
       */
      while (aux.indexOf("Idioma original") == -1) {
         aux = html.readLine();
      }
      aux = reduceSpace(removeTag(aux));
      aux = aux.substring(16, aux.length());
      setIdiomaOriginal(aux);
      /*---------------------------------------------------------------------------*/

      /*
       * Realiza o escaneamento do Orçamento
       */
      while (aux.indexOf("amento") == -1) {
         aux = html.readLine();
      }
      aux = reduceSpace(removeTag(aux));
      float tmpFloat = parseFloat(aux);
      setOrcamento(tmpFloat);
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
         String[] keyWordsList = new String[50];
         // Irá pegar o valor de todos os valores da lista <ul>
         while (aux.indexOf("</ul>") == -1) {
            if (aux.indexOf("<a") != -1) {
               keyWordsList[keyWordsIndex] = removeTag(aux).trim(); // Insere a palavra limpapedro
               keyWordsIndex++; // Incrementa o indice para adicionar na próxima posição
            }
            aux = html.readLine(); // Lê a próxima linha
         }

         setKeyWordsList(keyWordsList);
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
         aux = reduceSpace(removeTag(aux)).substring(17);
         setTituloOriginal(aux);
      } catch (Exception e) { // Caso a string vire null, apenas atribuirá a string nome ao título original
         setTituloOriginal(this.getNome());
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
      if (keyWords.length > 0 && keyWords[0] != null) {
         s += keyWords[0];
      }
      for (int i = 1; i < keyWords.length; i++) {
         if (keyWords[i] != null && keyWords[i].length() > 0) {
            s += ", " + keyWords[i];
         }
      }
      s += "]";
      return s;
   }

   public String[] getKeyWordsList() {
      String[] kwList = new String[keyWords.length];
      // Clona cada String da lista de palavras chave
      for (int i = 0; i < kwList.length; i++) {
         // Clona cada caracter da String na posição i
         kwList[i] = "";
         if (keyWords[i] != null) {
            kwList[i] = keyWords[i];
         }
      }
      return kwList;
   }

   public boolean getHasKeyWords() {
      return hasKeywords;
   }

   public String getHtmlUtil() {
      return htmlUtil;
   }

   // Métodos Set
   public void setNome(String nome) {
      this.nome = nome;
   }

   public void setTituloOriginal(String tituloOriginal) {
      this.tituloOriginal = tituloOriginal;
   }

   public void setLancamento(Date lancamento) {
      this.lancamento = lancamento;
   }

   public void setLancamentoStr(String lancamentoStr) {
      this.lancamentoStr = lancamentoStr;
   }

   public void setDuracao(int duracao) {
      this.duracao = duracao;
   }

   public void setGenero(String genero) {
      this.genero = genero;
   }

   public void setIdiomaOriginal(String idiomaOriginal) {
      this.idiomaOriginal = idiomaOriginal;
   }

   public void setSituacao(String situacao) {
      this.situacao = situacao;
   }

   public void setOrcamento(float orcamento) {
      this.orcamento = orcamento;
   }

   public void setKeyWordsList(String[] keyWords) {
      this.keyWords = keyWords;

   }

   public void setHtmlUtil(String htmlUtil) {
      this.htmlUtil = htmlUtil;
   }

   public void setHasKeywords(boolean hasKeywords) {
      this.hasKeywords = hasKeywords;
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

   public Filme clone() {
      Filme f;
      try {
         f = new Filme(this.htmlUtil);
      } catch (Exception e) {
         f = null;
      }

      f.setNome(this.getNome());
      f.setLancamento(this.getLancamento());
      f.setLancamentoStr(this.getLancamentoStr());
      f.setDuracao(this.getDuracao());
      f.setTituloOriginal(this.getTituloOriginal());
      f.setGenero(this.getGenero());
      f.setSituacao(this.getSituacao());
      f.setIdiomaOriginal(this.getIdiomaOriginal());
      f.setOrcamento(this.getOrcamento());
      f.setKeyWordsList(this.getKeyWordsList());
      f.setHtmlUtil(this.getHtmlUtil());

      return f;
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

