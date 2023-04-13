
/*
Autor: Pedro Pampolini Mendicino
Data de Criação: 19/06/2022      Última atualização: 19/06/2022
Objetivo: Cria uma árvore binária da classe utilizando o atributo Titulo Original como chave.
Foram implementados os métodos de remoção, inserção, dois caminhamentos e pesquisa.
*/

import java.io.*;
import java.nio.charset.*;
import java.util.Date;
import java.util.Scanner;
import java.text.SimpleDateFormat;


public class TP04Q02 {


   public static boolean isFim(String s) {
      return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
   }

   public static void main(String[] args) throws Exception {
      Scanner sc = new Scanner(System.in);
      //Primeira parte, que insere os itens iniciais
      String[] nomesFilmes = new String[50]; //Um vetor que armazenará os nomes dos arquivos html, para abertura futura
      int maxFilmes = 0;   //variável que armazena quantos items há
      //Insere o primeiro nome de arquivo html
      nomesFilmes[maxFilmes] = sc.nextLine();
      maxFilmes++;

      //Lê do terminal e grava
      while (isFim(nomesFilmes[maxFilmes - 1]) == false) {
         nomesFilmes[maxFilmes] = sc.nextLine();
         maxFilmes++;
      }
      maxFilmes--;   //decrementa o "FIM" lido

      ArvoreDupla arvore = new ArvoreDupla();
      arvore.init();
      //Insere os filmes lidos anteriormente na lista
      for (int i = 0; i < maxFilmes; i++) {
         Filme f = new Filme(nomesFilmes[i]);
         f.getAll();
         arvore.insert(f.clone());
      }

      //Segunda Parte, que insere ou remove os itens
      int quantCmd = sc.nextInt();
      sc.nextLine();    //Limpa o \n da leitura de inteiro
      String cmd = new String();
      //Recebe as linhas de comando inserção e caminhamento
      for (int i = 0; i < quantCmd; i++) {
         cmd = sc.nextLine(); //Lê a linha de comando
         //Caso vá inserir
         if (cmd.charAt(0) == 'I') {
            Filme f = new Filme(cmd.substring(2));
            f.getAll();
            arvore.insert(f.clone());
         }
         //Erro de comando desconhecido
         else{
            System.out.println("Erro no comando: " + cmd);
         }
      }

      //Terceira parte, que pesquisa os itens na árvore
      String itemPesquisa = sc.nextLine();
      BufferedWriter file = new BufferedWriter(new FileWriter("BinaryTree.txt"));
      while (sc.hasNext()) {
         System.out.println("=> " + itemPesquisa);
         System.out.println((arvore.search(itemPesquisa) ? " SIM" : " NAO"));
         itemPesquisa = sc.nextLine();
      }

      sc.close();
   }
}

class ArvoreDupla{
   NodeChar raiz;

   ArvoreDupla(){
      raiz = null;
   }

   public void init() throws Exception{
      char[] alfabeto = {'D', 'R', 'Z', 'X', 'V', 'B', 'F', 'P', 'U', 'I', 'G', 'E', 'J', 'L', 'H', 'T', 'A', 'W', 'S', 'O', 'M', 'N', 'K', 'C', 'Y', 'Q'};
      for (int i = 0; i < alfabeto.length; i++) {
         insert(alfabeto[i]);
      }
   }

   public void insert(char c) throws Exception{
      raiz = insert(c, raiz);
   }
   private NodeChar insert(char c, NodeChar i) throws Exception{
      if(i == null){
         i = new NodeChar(c);
      }
      else{
         if(c < i.c){
            i.menor = insert(c, i.menor);
         }
         else if(c > i.c){
            i.maior = insert(c, i.maior);
         }
         else{
            throw new Exception("Caractere " + c + " ja existente");
         }
      }
      return i;
   }

   public void insert(Filme f) throws Exception{
      raiz = insert(f, raiz);
   }
   private NodeChar insert(Filme f, NodeChar i) throws Exception{
      if(i == null){
         throw new Exception("Caractere " + f.getTituloOriginal().charAt(0) + " nao encontrado ");
      }
      //Caso o caractere do filme for menor que o caractere 0 do filme, insere na subárvore da esquerda
      else if(f.getTituloOriginal().charAt(0) < i.c){
         i.menor = insert(f, i.menor);
      }
      //Caso o caractere do filme for maior que o caractere 0 do filme, insere na subárvore da direita
      else if(f.getTituloOriginal().charAt(0) > i.c){
         i.maior = insert(f, i.maior);
      }
      //Caso o caracter seja o mesmo, insere nessa subárvore
      else{
         i.arvore.insert(f);
      }
      return i;
   }

   public boolean search(String titulo){
      System.out.print("raiz ");
      return search(titulo, raiz);
   }
   private boolean search(String titulo, NodeChar i){
      boolean rsp = false;
      if(i != null){
         rsp = rsp || i.arvore.search(titulo);

         System.out.print(rsp ? "" : " ESQ ");
         rsp = rsp || search(titulo, i.menor);
         
         System.out.print(rsp ? "" : " DIR ");
         rsp = rsp || search(titulo, i.maior);
      }
      return rsp;
   }

   public void caminharPre(){
      caminharPre(raiz);
   }
   private void caminharPre(NodeChar i){
      if(i != null){
         System.out.print(i.c + " ");
         caminharPre(i.menor);
         caminharPre(i.maior);
      }
   }
}

class NodeChar{
   char c;
   BinaryTree arvore;
   NodeChar menor;
   NodeChar maior;

   NodeChar(){
      this('a', new BinaryTree(), null, null);
   }
   NodeChar(char c){
      this(c ,new BinaryTree(), null, null);
   }
   NodeChar(char c, BinaryTree arvore){
      this(c , arvore, null, null);
   }
   NodeChar(char c, BinaryTree arvore, NodeChar menor, NodeChar maior){
      this.c = c;
      this.arvore = arvore;
      this.menor = menor;
      this.maior = maior;
   }
}

class BinaryTree{
   Node raiz;

   BinaryTree(){
      raiz = null;
   }

   public void insert(Filme f) throws Exception{
      raiz = insert(f,raiz);
   }
   private Node insert(Filme f, Node i) throws Exception{
      //Caso o nodo seja null, insere um novo nodo
      if (i == null) {
         i = new Node(f);
      }
      //Compara se o item é menor que o item no nodo atual, se for, chama recursivamente ao nodo à esquerda
      else if (f.getTituloOriginal().compareTo(i.f.getTituloOriginal()) < 0) {
         i.menor = insert(f,i.menor);
      }
      //Compara se o item é maior que o item no nodo atual, se for, chama recursivamente ao nodo à direita
      else if(f.getTituloOriginal().compareTo(i.f.getTituloOriginal()) > 0){
         i.maior = insert(f, i.maior);
      }
      //Caso o item já exista, retornar um erro
      else{
         throw new Exception("Elemento já existente: " + f.getTituloOriginal());
      }
      return i;
   }
   
   
   public void remove(String f) throws Exception{
      raiz = remove(f, raiz);
   }
   private Node remove(String f, Node i) throws Exception{
      //Se o nodo for null, significa que o filme pesquisado não existe
      if (i == null) {
         throw new Exception("Filme não existente");
      }
      //Compara se o item é menor que o item no nodo atual, se for, chama recursivamente ao nodo à esquerda
      if(f.compareTo(i.f.getTituloOriginal()) < 0){
         i.menor = remove(f,i.menor);
      }
      //Compara se o item é maior que o item no nodo atual, se for, chama recursivamente ao nodo à direita
      else if(f.compareTo(i.f.getTituloOriginal()) > 0){
         i.maior = remove(f,i.maior);
      }
      //Caso exista e não seja maior, nem menor, será igual, então realiza a remoção
      else{
         //Se o maior filho for null, sobrescreve o nodo atual com seu menor filho, sendo ele null ou não
         if (i.maior == null) {
            i = i.menor;
         }
         //Se o menor filho for null, sobrescreve o nodo atual com seu maior filho, sendo ele null ou não
         else if(i.menor == null){
            i = i.maior;
         }
         //Caso tenha os 2 filhos, coloca no lugar do nodo atual o maior filho à esquerda
         else{
            i.menor = maiorEsq(i, i.menor);
         }
      }
      return i;
   }
   
   private Node maiorEsq(Node i, Node j){
      //Quando o nodo não possuir filho maior, irá fazer as trocas necessárias
      if (j.maior == null) {
         i.f = j.f;
         j = j.menor;
      }
      //Enquanto o nodo possuir um filho maior, chamará recursivamente a função;
      else{
         j.maior = maiorEsq(i, j.maior);
      }
      return j;
   }
   
   public boolean search(String f){
      
      return search(f,raiz);
   }
   private boolean search(String f, Node i){
      boolean rsp;
      //Caso o nodo atual não exista, então o item procurado não existe
      if (i == null) {
         rsp = false;
      }
      //Caso o item procurado for menor que o item no nodo atual, chama recursivamente para à esquerda
      else if (f.compareTo(i.f.getTituloOriginal()) < 0) {
         System.out.print("esq ");
         rsp = search(f,i.menor);
      }
      //Caso o item procurado for maior que o item no nodo atual, chama recursivamente para à direita
      else if (f.compareTo(i.f.getTituloOriginal()) > 0) {
         System.out.print("dir ");
         rsp = search(f,i.maior);
      }
      //Caso o nodo atual exista, e seu item não for nem maior nem menor que o procurado, então é igual, retornando true
      else{
         rsp = true;
      }

      return rsp;
   }

   public void caminhaCentral(){
      caminhaCentral(raiz,0);
   }
   private void caminhaCentral(Node i,int nivel){
      if (i != null) {
         caminhaCentral(i.menor,nivel+1);
         System.out.println("nivel " + nivel + ": " +i.f.getTituloOriginal());
         caminhaCentral(i.maior,nivel+1);
      }
   }

   public void caminhaPos(){
      caminhaPos(raiz,0);
   }
   private void caminhaPos(Node i,int nivel){
      if (i != null) {
         System.out.println("nivel " + nivel + ": " +i.f.getTituloOriginal());
         caminhaPos(i.menor,nivel+1);
         caminhaPos(i.maior,nivel+1);
      }
   }

}

class Node{
   public Filme f;
   public Node menor;
   public Node maior;

   Node(){
      this(null,null,null);
   }
   
   Node(Filme f){
      this(f,null,null);
   }

   Node(Filme f, Node menor, Node maior){
      this.f = f;
      this.menor = menor;
      this.maior = maior;
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
   private RandomAccessFile html; // Arquivo que armazenará o cÃ³digo da página, para ser analisado
   private String htmlUtil = new String(); // String que armazena o html com as informaçÃµes
   private boolean hasKeywords = true; // Flag para verificar se possui palavras chaves ou não

   // Construtor com os parÃ¢metros
   Filme(String htmlUtil) {
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
         }
      }

      String aux = new String(); // String que armazenará a linha
      aux = html.readLine();
      /*
       * Realiza o escaneamento do Nome
       */
      // Pesquisa pela linha que dará inÃ­cio à sessão de linhas Ãºteis
      while (aux.indexOf("header poster") == -1) {
         aux = html.readLine(); // Passa para a prÃ³xima linha
      }
      // Realiza a leitura até encontrar a linha com o titulo
      while (aux.indexOf("<a href=") == -1) {
         aux = html.readLine();
      }
      aux = this.removeTag(aux); // remove as tags da linha
      this.setNome(reduceSpace(aux)); // Retira os espaços desnecessários da linha do tÃ­tulo
      /*---------------------------------------------------------------------------*/

      /*
       * Realiza o escaneamento da Data de Lançamento
       */
      // Realiza as leituras de linhas até achar <div class="facts">
      while (aux.indexOf("facts") == -1) {
         aux = html.readLine(); // Pula para a prÃ³xima linha
      }

      // Realiza as leituras de linhas até achar <div class="release">
      while (aux.indexOf("release") == -1) {
         aux = html.readLine(); // Pula para a prÃ³xima linha
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
         aux = html.readLine(); // Pula para a prÃ³xima linha
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
      // Realizar a leitura do TÃ­tulo original apÃ³s ler tudo, não há um padrão certo
      /*
       * Realiza o escaneamento da Situação
       */
      while (aux.indexOf("Situa") == -1) {
         aux = html.readLine(); // Realiza a leitura da prÃ³xima linha
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
      // Posiciona o vetor no inÃ­cio da lista <ul> ou irá ler que não há palavras
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
               keyWordsIndex++; // Incrementa o indice para adicionar na prÃ³xima posição
            }
            aux = html.readLine(); // Lê a prÃ³xima linha
         }

         setKeyWordsList(keyWordsList);
      }

      /*
       * Reescaneia o arquivo, procurando o tÃ­tulo original
       */
      html.seek(0); // Reposiciona o ponteiro para o inicio
      try {
         while (aux.indexOf("tulo original") == -1) {
            aux = html.readLine(); // Lê a proxima linha
         }
         // Atribui a string encontrada ao tÃ­tulo original
         aux = reduceSpace(removeTag(aux)).substring(17);
         setTituloOriginal(aux);
      } catch (Exception e) { // Caso a string vire null, apenas atribuirá a string nome ao tÃ­tulo original
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
      for (int i = s.length() - 4; i >= 0; i--) { // realiza a leitura do nÃºmero desconsiderando a parte decimal
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
