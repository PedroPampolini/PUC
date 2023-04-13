
/*
Autor: Pedro Pampolini Mendicino
Data de Cria��o: 17/06/2022      �ltima atualiza��o: 17/06/2022
Objetivo: Cria uma �rvore bin�ria balanceada Bicolor da classe Filme utilizando o atributo Titulo Original como chave.
Foram implementados os m�todos de inser��o, dois caminhamentos e pesquisa.
*/

import java.io.*;
import java.nio.charset.*;
import java.util.Date;
import java.util.Scanner;
import java.text.SimpleDateFormat;


public class TP04Q04 {


   public static boolean isFim(String s) {
      return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
   }

   public static void main(String[] args) throws Exception {
      Scanner sc = new Scanner(System.in);
      //Primeira parte, que insere os itens iniciais
      String[] nomesFilmes = new String[50]; //Um vetor que armazenar� os nomes dos arquivos html, para abertura futura
      int maxFilmes = 0;   //vari�vel que armazena quantos items h�
      //Insere o primeiro nome de arquivo html
      nomesFilmes[maxFilmes] = sc.nextLine();
      maxFilmes++;

      //L� do terminal e grava
      while (isFim(nomesFilmes[maxFilmes - 1]) == false) {
         nomesFilmes[maxFilmes] = sc.nextLine();
         maxFilmes++;
      }
      maxFilmes--;   //decrementa o "FIM" lido

      BinaryTree arvore = new BinaryTree();
      //Insere os filmes lidos anteriormente na lista
      for (int i = 0; i < maxFilmes; i++) {
         Filme f = new Filme(nomesFilmes[i]);
         f.getAll();
         arvore.insert(f.clone());
      }

      //Segunda parte, que pesquisa os itens na �rvore
      String itemPesquisa = sc.nextLine();
      BufferedWriter file = new BufferedWriter(new FileWriter("BinaryTree.txt"));
      while (sc.hasNext()) {
         System.out.println(itemPesquisa);
         System.out.println((arvore.search(itemPesquisa) ? "SIM" : "NAO"));
         itemPesquisa = sc.nextLine();
      }
      sc.close();
   }
}

class BinaryTree{
   Node raiz;

   BinaryTree(){
      raiz = null;
   }

   public void insert(Filme f) throws Exception {
      //Caso da raiz vazia
      if(raiz == null){
         raiz = new Node(f);
      }
      //Caso raiz exista mas n�o tenha filhos
      else if(raiz.menor == null && raiz.maior == null){
         if(f.getTituloOriginal().compareTo(raiz.f.getTituloOriginal()) < 0){
            raiz.menor = new Node(f);
         }
         else{
            raiz.maior = new Node(f);
         }
      }
      //Caso a raiz tenha apenas o maior filho
      else if(raiz.menor == null){
         //Caso o filme seja menor que a raiz
         if(f.getTituloOriginal().compareTo(raiz.f.getTituloOriginal()) < 0){
            //Salva o menor filho da raiz como o novo elemento
            raiz.menor = new Node(f);
         }
         //Caso o filme esteja entre a raiz e o maior filho da raiz
         else if(f.getTituloOriginal().compareTo(raiz.maior.f.getTituloOriginal()) < 0){
            //Coloca a raiz como seu menor filho e troca o seu valor pelo novo inserido
            raiz.menor = new Node(raiz.f);
            raiz.f = f;
         }
         //Caso o filme seja maior que o maior filho da raiz
         else{
            //faz um "shift" com a raiz e seu maior filho para a esquerda
            raiz.menor = new Node(raiz.f);
            raiz.f = raiz.maior.f;
            raiz.maior.f = f;
         }
         raiz.menor.cor = raiz.maior.cor = false;
      }
      //Caso a raiz tenha apenas o menor filho
      else if(raiz.maior == null){
         //Caso o filme seja maior que a raiz
         if(f.getTituloOriginal().compareTo(raiz.f.getTituloOriginal()) > 0){
            raiz.maior = new Node(f);
         }
         //Caso o filme esteja entre a raiz e o menor filho da raiz
         else if(f.getTituloOriginal().compareTo(raiz.menor.f.getTituloOriginal()) > 0){
            raiz.maior = new Node(raiz.f);
            raiz.f = f;
         }
         //Caso o filme seja menor que a raiz e menor que seu menor filho
         else{ 
            raiz.maior = new Node(raiz.f);
            raiz.f = raiz.menor.f;
            raiz.menor.f = f;
         }
         raiz.menor.cor = raiz.maior.cor = false;
      }
      //Caso os 3 primeiros elementos, raiz e seus filhos j� tenham sido inseridos
      else{
         insert(f, null, null, null, raiz);
      }
      raiz.cor = false;
   }
   private void insert(Filme f, Node bisavo, Node avo, Node pai, Node i) throws Exception{
      //Caso o Nodo atual esteja vazio, insere nele
      if(i == null){
         //Caso o elemento seja menor que o pai, insere no menor filho
         if(f.getTituloOriginal().compareTo(pai.f.getTituloOriginal()) < 0){
            i = pai.menor = new Node(f,true);
         }
         //Caso seja maior, insere no filho maior
         else{
            i = pai.maior = new Node(f,true);
         }
         if(pai.cor == true){
            balancear(bisavo, avo, pai, i);
         }
      }
      else{
         //Verifica se o Nodo atual faz parte de um quatro-n�, se fizer, fragmenta-o
         if(isFourNode(i) == true){
            i.cor = true;
            i.maior.cor = i.menor.cor = false;
            if(i == raiz){
               i.cor = false;
            }
            else if(pai.cor == true){
               balancear(bisavo, avo, pai, i);
            }
            
         }
         //Ap�s fragmentar ou n�o o n�, chama o m�todo recursivamente para inserir no maior ou menor Filho
         if(f.getTituloOriginal().compareTo(i.f.getTituloOriginal()) < 0){
            insert(f, avo, pai, i, i.menor);
         }
         else if(f.getTituloOriginal().compareTo(i.f.getTituloOriginal()) > 0){
            insert(f, avo, pai, i, i.maior);
         }
         else{
               throw new Exception("Elemento ja existente");
            }
      }
   }
   
   private void balancear(Node bisavo, Node avo, Node pai, Node i){
      //Se o pai tamb�m tiver cor, deve-se balancear a �rvore
      if(pai.cor == true){
         //Se o pai for maior que o av�, verifica a posi��o do filho para rotacionar
         if(avo.f.getTituloOriginal().compareTo(pai.f.getTituloOriginal()) < 0){
            //Se o filho for maior que o pai, rotaciona apenas a esquerda
            if(pai.f.getTituloOriginal().compareTo(i.f.getTituloOriginal()) < 0){
               avo = rotacionaEsq(avo);
            }
            //Caso o filho seja menor que o pai, mas maior que o av�, rotaciona para a direita
            //E ent�o rotaciona para � esquerda
            else{
               avo = rotacionaDirEsq(avo);
            }
         }
         //Se o pai for menor que o av�, verifica a posi��o do filho
         else{
            //Se o filho for menor que o pai e o av�, rotaciona apenas � direita
            if(pai.f.getTituloOriginal().compareTo(i.f.getTituloOriginal()) > 0){
               avo = rotacionaDir(avo);
            }
            //Caso o filho seja menor que o av�, mas maior que o pai, rotaciona para � esquerda
            //E ent�o rotaciona para � direita
            else{
               avo = rotacionaEsqDir(avo);
            }
         }
         if(bisavo == null){
            raiz = avo;
         }
         else if(avo.f.getTituloOriginal().compareTo(bisavo.f.getTituloOriginal()) < 0){
            bisavo.menor = avo;
         }
         else{
            bisavo.maior = avo;
         }
         avo.cor = false;
         avo.menor.cor = avo.maior.cor = true;
      }
   }

   private Node rotacionaDir(Node n){
      Node menor = n.menor;
      Node menorMaior = menor.maior;
      menor.maior = n;
      n.menor = menorMaior;
      return menor;
   }
   private Node rotacionaEsq(Node n){
      Node maior = n.maior;
      Node maiorMenor = maior.menor;
      maior.menor = n;
      n.maior = maiorMenor;
      return maior;
   }
   private Node rotacionaDirEsq(Node i){
      i.maior = rotacionaDir(i.maior);
      return rotacionaEsq(i);
   }
   private Node rotacionaEsqDir(Node i){
      i.menor = rotacionaEsq(i.menor);
      return rotacionaDir(i);
   }

   public boolean search(String f){
      System.out.print("raiz ");
      return search(f,raiz);
   }
   private boolean search(String f, Node i){
      boolean rsp;
      //Caso o nodo atual n�o exista, ent�o o item procurado n�o existe
      if (i == null) {
         rsp = false;
      }
      //Caso o item procurado for menor que o item no nodo atual, chama recursivamente para � esquerda
      else if (f.compareTo(i.f.getTituloOriginal()) < 0) {
         System.out.print("esq ");
         rsp = search(f,i.menor);
      }
      //Caso o item procurado for maior que o item no nodo atual, chama recursivamente para � direita
      else if (f.compareTo(i.f.getTituloOriginal()) > 0) {
         System.out.print("dir ");
         rsp = search(f,i.maior);
      }
      //Caso o nodo atual exista, e seu item n�o for nem maior nem menor que o procurado, ent�o � igual, retornando true
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

   public void caminhaPre(){
      caminhaPre(raiz,0);
   }
   private void caminhaPre(Node i,int nivel){
      if (i != null) {
         System.out.println("nivel " + nivel + ": " +i.f.getTituloOriginal() + " Cor: " + (i.cor ? "SIM" : "NAO"));
         caminhaPre(i.menor,nivel+1);
         caminhaPre(i.maior,nivel+1);
      }
   }

   private boolean isFourNode(Node i){
      return(i.menor != null && i.maior != null && i.menor.cor == true && i.maior.cor == true);
   }

}

class Node{
   public Filme f;
   public Node menor;
   public Node maior;
   public boolean cor;

   Node(){
      this(null,null,null,false);
   }
   
   Node(Filme f){
      this(f,null,null,false);
   }

   Node(Filme f, boolean cor){
      this(f,null,null,cor);
   }

   Node(Filme f, Node menor, Node maior,boolean cor){
      this.f = f;
      this.menor = menor;
      this.maior = maior;
      this.cor = cor;
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
   private float orcamento; // Vari�vel que armazena
   private String[] keyWords = new String[50]; // Array de strings que armazenar� todas as palavras chaves
   private RandomAccessFile html; // Arquivo que armazenar� o c�digo da p�gina, para ser analisado
   private String htmlUtil = new String(); // String que armazena o html com as informa��es
   private boolean hasKeywords = true; // Flag para verificar se possui palavras chaves ou n�o

   // Construtor com os parâmetros
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

      String aux = new String(); // String que armazenar� a linha
      aux = html.readLine();
      /*
       * Realiza o escaneamento do Nome
       */
      // Pesquisa pela linha que dar� in�cio � sess�o de linhas �teis
      while (aux.indexOf("header poster") == -1) {
         aux = html.readLine(); // Passa para a pr�xima linha
      }
      // Realiza a leitura at� encontrar a linha com o titulo
      while (aux.indexOf("<a href=") == -1) {
         aux = html.readLine();
      }
      aux = this.removeTag(aux); // remove as tags da linha
      this.setNome(reduceSpace(aux)); // Retira os espa�os desnecess�rios da linha do t�tulo
      /*---------------------------------------------------------------------------*/

      /*
       * Realiza o escaneamento da Data de Lan�amento
       */
      // Realiza as leituras de linhas at� achar <div class="facts">
      while (aux.indexOf("facts") == -1) {
         aux = html.readLine(); // Pula para a pr�xima linha
      }

      // Realiza as leituras de linhas at� achar <div class="release">
      while (aux.indexOf("release") == -1) {
         aux = html.readLine(); // Pula para a pr�xima linha
      }
      aux = html.readLine(); // pula mais uma linha, pois a data se encontra na linha abaixo � abertura da
                             // tag
      setLancamentoStr(reduceSpace(removeTag(aux))); // remove espa�os e tags desnecess�rios
      setLancamento(parseDate(lancamentoStr)); // Converte a String em formato "Date"
      /*---------------------------------------------------------------------------*/

      /*
       * Realiza o escaneamento dos G�neros
       */
      // Realiza as leituras de linhas at� achar <div class="genres">
      while (aux.indexOf("genres") == -1) {
         aux = html.readLine(); // Pula para a pr�xima linha
      }
      // Pula as linhas at� encontrar a que possui os g�neros
      while (aux.indexOf("<a href") == -1) {
         aux = html.readLine();
      }
      aux = removeTag(aux); // Remove a tag da linha
      aux = removeStrings(aux, "&nbsp;"); // Remove strings desncess�rias
      setGenero(reduceSpace(aux));
      /*---------------------------------------------------------------------------*/

      /*
       * Realiza o escaneamento da Dura��o
       */
      // Pula as linhas at� encontrar <div class="runtime">
      while (aux.indexOf("runtime") == -1) {
         aux = html.readLine();
      }
      aux = html.readLine(); // Pula uma quebra de linha desnecess�ria
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
       * Realiza a leitura das linhas at� chegar na linha <section
       * class="facts left_column">
       */
      while (aux.indexOf("facts left_column") == -1) {
         aux = html.readLine();
      }
      // Realizar a leitura do T�tulo original ap�s ler tudo, n�o h� um padr�o certo
      /*
       * Realiza o escaneamento da Situa��o
       */
      while (aux.indexOf("Situa") == -1) {
         aux = html.readLine(); // Realiza a leitura da pr�xima linha
      }
      aux = reduceSpace(removeTag(aux)); // Limpa as tags e os espa�os em excesso
      aux = aux.substring(11, aux.length()); // Remove "Situa��o" da string
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
       * Realiza o escaneamento do Or�amento
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
      // Posiciona o vetor no in�cio da lista <ul> ou ir� ler que n�o h� palavras
      // chave
      while (aux.indexOf("<li") == -1 && aux.indexOf("Nenhuma") == -1) {
         aux = html.readLine();
      }
      // Verifica se a linha lida � a que mostra se n�o h� palavras chave
      if (aux.indexOf("Nenhuma") != -1) {
         hasKeywords = false;
      }
      if (hasKeywords) { // Apenas ler� se houver palavras chaves
         int keyWordsIndex = 0; // Index da posi��o das palavras-chave
         String[] keyWordsList = new String[50];
         // Ir� pegar o valor de todos os valores da lista <ul>
         while (aux.indexOf("</ul>") == -1) {
            if (aux.indexOf("<a") != -1) {
               keyWordsList[keyWordsIndex] = removeTag(aux).trim(); // Insere a palavra limpa
               keyWordsIndex++; // Incrementa o indice para adicionar na pr�xima posi��o
            }
            aux = html.readLine(); // L� a pr�xima linha
         }

         setKeyWordsList(keyWordsList);
      }

      /*
       * Reescaneia o arquivo, procurando o t�tulo original
       */
      html.seek(0); // Reposiciona o ponteiro para o inicio
      try {
         while (aux.indexOf("tulo original") == -1) {
            aux = html.readLine(); // L� a proxima linha
         }
         // Atribui a string encontrada ao t�tulo original
         aux = reduceSpace(removeTag(aux)).substring(17);
         setTituloOriginal(aux);
      } catch (Exception e) { // Caso a string vire null, apenas atribuir� a string nome ao t�tulo original
         setTituloOriginal(this.getNome());
      }

      html.close();

   }

   // M�todos Get
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
         // Clona cada caracter da String na posi��o i
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

   // M�todos Set
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
    * M�todos que ser�o utilizados como facilitadores para a leitura dos itens
    */
   public String removeTag(String s) {
      String newS = new String(); // String sem as tags
      int inTag = 0; // Vari�vel que verifica se est� lendo uma tag ou um texto

      // Percorre toda a string para recuperar apenas texto
      for (int i = 0; i < s.length(); i++) {
         if (s.charAt(i) == '<') { // Verifica se est� entrando em uma tag
            inTag++; // Incrementa para n�o permitir a leitura
         } else if (s.charAt(i) == '>') { // Verifica se saiu da tag
            inTag--; // Decrementa para se aproximar da permiss�o da leitura
         } else if (inTag == 0) { // Se inTag == 0, significa que est� lendo um texto
            newS += "" + s.charAt(i);
         }
      }

      return newS;
   }

   // M�todo que remove os espa�os em excesso das strings, inclusive o espa�o no
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

   // M�todo que converte uma string no formato dd/mm/aaaa (local)
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

   // M�todo que converte uma string em inteiro
   private int parseInt(String s) {
      int result = 0;
      for (int i = 0; i < s.length(); i++) {
         result += ((int) s.charAt(i) - 48) * Math.pow(10, s.length() - 1 - i);
      }
      return result;
   }

   // M�todo que converte uma String em um Float
   public float parseFloat(String s) {
      float num = 0.0f;
      int indexPot = 0;
      for (int i = s.length() - 4; i >= 0; i--) { // realiza a leitura do n�mero desconsiderando a parte decimal
         if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
            num += (s.charAt(i) - 48) * Math.pow(10, indexPot++);
         }
      }
      return num;
   }

   // M�todo que retira strings de uma string
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

   // M�todo que transforma tudo em string
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
