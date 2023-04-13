/*
Autor: Pedro Pampolini Mendicino
Data de Criação: 18/04/2022      Última atualização: 18/04/2022
Objetivo: Recebe uma lista de nome de arquivos, abre e realiza a leitura dos mesmos salvando os valores e o objetos da
classe Filme em uma lista duplamente encadeada. Então utilizando o algorítmo de ordenação QuickSort, ordena a lista 
*/
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

/*Manipulação de strings*/
char* removePattern(char s[], char c1, char c2){
    //Alloca o espaço para a próxima string
    char* newS = (char*)malloc(sizeof(char) * strlen(s));
    bool flag = false;   //Flag para verificação se está dentro do padrão ou não
    int newIndex = 0;   //Índex da nova string

    //Percorre toda a string inteira
    for (int i = 0; i < strlen(s); i++)
    {
        if (s[i] == c1) //Caso o caracter atual seja o primeiro do padrão, seta como true o flag
        {
            flag = true;

        }
        //Caso o caracter atual seja o ultimo do padrão e se estiver dentro do padrão, seta como false o flag
        if (s[i] == c2 && flag == true)
        {
            flag = false;
            //i++;
        }
        //Se não estiver dentro do padrão, escreve a string na nova string
        if (flag == false && s[i] != c2)
        {
            newS[newIndex] = s[i];
            newIndex++;
        }
    }
    newS[newIndex] = '\0';  //Marca o final da string

    return newS;
}

char* reduceSpace(char s[]){
    //Alloca o espaço para a próxima string
    char* newS = (char*)malloc(sizeof(char) * strlen(s));
    int newIndex = 0;   //Index da nova string

    for (int i = 0; i < strlen(s); i++)
    {
        if(s[i] != ' '){
            newS[newIndex] = s[i]; //atribuição
            newIndex++;    //Incremento do indice para a próxima posição ;
        }
        else if (s[i+1] != ' ' && newIndex != 0)
        {
            newS[newIndex] = s[i]; //atribuição
            newIndex++;    //Incremento do indice para a próxima posição 
        }   
    }

    if (newS[newIndex - 1] == ' ')
    {
        newS[newIndex - 1] = '\0';
    }
    else
    {
        newS[newIndex] = '\0';
    }

    return newS;
}

char* substring(char* s, int init, int final){
    //Inicializa a nova string reservando o espaço necessário
    char* newS = (char*)malloc(sizeof(char) * strlen(s) );
    if (init < 0)   //caso o indice inicial seja menor que zero, não permite a operação
    {
        printf("Error: minimum index < 0");
        newS = NULL;    //Seta null para a string, sinalizando o erro
    }
    else if (final > strlen(s))//caso o indice final seja menor que o tamanho da string, não permite a operação
    {
        printf("Error: maximum index > string length");
        newS = NULL;    //Seta null para a string, sinalizando o erro
    }
    else
    {
        //Percorre a string entre os índices escolhidos para a operação
        for (int i = init; i < final; i++)
        {
            newS[i - init] = s[i];
        }
        
    }
    newS[final - init] = '\0'; //Escreve o parâmetro de parada da string '\0'

    return newS;
    
}

/*----------------------*/


typedef struct Date
{
    int dia,
    mes,
    ano;
} Date;


typedef struct Filme
{
    char nome[500];
    char tituloOriginal[500];
    Date lancamento;
    int duracao;
    char genero[500];
    char idiomaOriginal[500];
    char situacao[500];
    double orcamento;
    char keywords[20][500];
    bool hasKeywords;
    int quantKeywords;

    //Variaveis de teste
    char tmpDuracao[500];
    char tmpLancamento[500];
    char tmpOrcamento[500];

} Filme;

typedef struct Node
{
    Filme* f;
    struct Node* next;
    struct Node* prev;
}Node;


typedef struct LinkedList
{
    Node* first;
    Node* last;
    int size;
}LinkedList;

/*-----------------*/
/*---construtores--*/

Node* newNode(Filme* f){
    Node* n = (Node*)malloc(sizeof(Node));
    n->f = f;
    n->next = NULL;
    n->prev = NULL;
}

LinkedList* newLinkedList(){
    LinkedList* list = (LinkedList*)malloc(sizeof(LinkedList));
    list->first = newNode(NULL);
    list->last = list->first;
    list->size = 0;
}

Filme* newFilme(){
    Filme* f = (Filme*)malloc(sizeof(Filme));
    //nome
    for (int i = 0; i < 500; i++)
    {
        f->nome[i] = '\0';
    }
    //tituloOriginal
    for (int i = 0; i < 500; i++)
    {
        f->tituloOriginal[i] = '\0';
    }
    //genero
    for (int i = 0; i < 500; i++)
    {
        f->genero[i] = '\0';
    }
    //idiomaOriginal
    for (int i = 0; i < 500; i++)
    {
        f->idiomaOriginal[i] = '\0';
    }
    //situacao
    for (int i = 0; i < 500; i++)
    {
        f->situacao[i] = '\0';
    }
    //Palavras Chave
    for (int j = 0; j < 20; j++)
    {
        for (int i = 0; i < 500; i++)
        {
            f->keywords[j][i] = '\0';
        }
    }
    
    f->lancamento.ano = 0;
    f->lancamento.mes = 0;
    f->lancamento.dia = 0;

    f->duracao = 0;
    f->orcamento = 0;
    f->hasKeywords = false;
    f->quantKeywords = 0;

    //Varia
    //tmpDuracaoveis de teste
    for (int i = 0; i < 500; i++)
    {
        f->tmpDuracao[i] = '\0';
    }
    //tmpLancamento
    for (int i = 0; i < 500; i++)
    {
        f->tmpLancamento[i] = '\0';
    }
    //tmpOrcamento
    for (int i = 0; i < 500; i++)
    {
        f->tmpOrcamento[i] = '\0';
    }

    return f;
}

/*-----------------*/

/*-----Parsers-----*/
bool isNumber(char c){
    return(c <= '9' && c >= '0');
}

Date strToDate(char s[]){
    Date d;
    d.dia = atoi(substring(s,0,2));
    d.mes = atoi(substring(s,3,5));
    d.ano = atoi(substring(s,6,10));

    return d;
}

int strToMinutes(char s[]){
    int min = 0;
    if (strstr(s,"h") != NULL)
    {
        min = (s[0] - 48) * 60;
        min += atoi(substring(s,3,strlen(s)));
    }
    else
    {
        min = atoi(s);
    }
    return min;    
}

float orcToFloat(char s[]){
    int index = 0;
    char newS[strlen(s)];
    float number = 0;
    //Percorre toda string eliminando os caracteres desnecessários
    for (int i = 0; i < strlen(s); i++)
    {
        if (isNumber(s[i]))     //Se for um número, adciona na nova string
        {
            newS[index] = s[i];
            index++;
        }
        else if (s[i] == '.')
        {
            i = strlen(s);
        }
        number = strtod(newS,NULL);
    }
    return number;
}
/*-----------------*/


void getAll(Filme *f, char* nome){
    char path[50] = "/tmp/filmes/";  //Variável que armazena o path do arquivo
    strcat(path,nome);  //Concatena o diretório e o arquivo em uma string
    FILE* arq;
    arq = fopen(path,"r");  //Abre o arquivo em modo de leitura
    if(arq == NULL){
        printf("Arquivo Inexistente\n");
    }
    else{
        char aux[1000];// = (char*)malloc(sizeof(char) * 1000);
        f->hasKeywords = true;
        while (fgets(aux,1000,arq))
        {
            //Condicional para a linha do nome do filme e do titulo original
            if (strstr(aux,"ott_true") != NULL)
            {
                //Salta as linhas até estar na linha que contém o título
                while (strstr(aux,"<a href=") == NULL)
                {
                    fgets(aux,1000,arq);    //Realiza a leitura da linha
                }
                aux[strlen(aux) - 1] = '\0';    //Remove o '\n'
                strcpy(aux,removePattern(aux,'<','>'));     //Remove as tags da linha
                strcpy(aux,reduceSpace(aux));   //retira o espaço desnecessário
                strcpy(f->nome,aux);    //Grava a string no campo do nome do struct Filme
                strcpy(f->tituloOriginal,aux);  //Grava a string no campo do titulo original do struct Filme
            }
            //Condicional para a linha do nome do filme e do título original
            else if (strstr(aux,"ott_false") != NULL)
            {
                //Salta as linhas até estar na linha que contém o título
                while (strstr(aux,"<a href=") == NULL)
                {
                    fgets(aux,1000,arq);    //Realiza a leitura da linha
                }
                aux[strlen(aux) - 1] = '\0';    //Remove o '\n'
                strcpy(aux,removePattern(aux,'<','>'));     //Remove as tags da linha
                strcpy(aux,reduceSpace(aux));   //retira o espaço desnecessário
                strcpy(f->nome,aux);    //Grava a string no campo do nome do struct Filme
                strcpy(f->tituloOriginal,aux);  //Grava a string no campo do titulo original do struct Filme
            }
            //Condicional para a linha do Titulo original apenas
            else if(strstr(aux,"tulo original") != NULL){
                aux[strlen(aux) - 1] = '\0';    //Remove o '\n'
                strcpy(aux,removePattern(aux,'<','>'));     //Remove as tags da linha
                strcpy(aux,substring(aux,20,strlen(aux)));     //Remove "Título original" da linha
                strcpy(aux,reduceSpace(aux));   //retira o espaço desnecessário
                strcpy(f->tituloOriginal,aux);  //Grava a linha no título original do filme
                
            }
            //Condicional para a linha da data de lançamento
            else if (strstr(aux,"<span class=\"release\">") != NULL)
            {
                fgets(aux,1000,arq);        //realiza a leitura na próxima linha
                aux[strlen(aux) - 1] = '\0';    //Remove o \n
                strcpy(aux,reduceSpace(aux));   //retira o espaço desnecessário
                f->lancamento = strToDate(aux);
                strcpy(f->tmpLancamento,aux);   //Grava a linha na string com a data de lançamento do filme
            } 
            //Condicional para a linha da duração do filme
            else if (strstr(aux,"runtime") != NULL)
            {
                //Pula as duas linhas para ler o runtime
                fgets(aux,1000,arq);        //realiza a leitura na próxima linha
                fgets(aux,1000,arq);        //realiza a leitura na próxima linha
                strcpy(aux,reduceSpace(aux));   //retira o espaço desnecessário
                aux[strlen(aux) - 1] = '\0';    //Remove o \n
                f->duracao = strToMinutes(aux);
                strcpy(f->tmpDuracao,aux);      //Grava a linha na duração em caracteres do Filme
            }
            //Condicional para a linha com os generos do filme
            else if (strstr(aux,"<span class=\"genres\">") != NULL)
            {
                //Pula as duas linhas para ler o runtime
                fgets(aux,1000,arq);        //realiza a leitura na próxima linha
                fgets(aux,1000,arq);        //realiza a leitura na próxima linha
                aux[strlen(aux) - 1] = '\0';    //Remove o \n
                strcpy(aux,removePattern(aux,'<','>'));     //Remove as tags da linha
                strcpy(aux,removePattern(aux,'&',';'));     //Remove os caracteres '&nbsp;' da linha
                strcpy(aux,reduceSpace(aux));   //retira o espaço desnecessário
                strcpy(f->genero,aux);      //Grava a linha nos generos do Filme
            }
            //Condicional para a linha com o idioma Original do filme
            else if (strstr(aux,"<strong><bdi>Idioma original</bdi>") != NULL)
            {
                aux[strlen(aux) - 1] = '\0';    //Remove o \n
                strcpy(aux,removePattern(aux,'<','>'));     //Remove as tags da linha
                strcpy(aux,substring(aux,17,strlen(aux)));  //Remove "Idioma original" da linha
                strcpy(aux,reduceSpace(aux));   //retira o espaço desnecessário
                strcpy(f->idiomaOriginal,aux);  //Grava a linha no idioma original do Filme
            }
            //Condicional para a linha com a situação do filme
            else if (strstr(aux,"<strong><bdi>Situa") != NULL)
            {
                aux[strlen(aux) - 1] = '\0';    //Remove o \n
                strcpy(aux,removePattern(aux,'<','>'));     //Remove as tags da linha
                strcpy(aux,substring(aux,14,strlen(aux)));     //Remove "Situação" da linha
                strcpy(aux,reduceSpace(aux));   //retira o espaço desnecessário
                strcpy(f->situacao,aux);    //Grava a linha na situação do Filme
            }
            //Condicional para a linha com o orçamento do filme
            else if (strstr(aux,"amento</bdi></strong>") != NULL)
            {
                aux[strlen(aux) - 1] = '\0';    //Remove o \n
                strcpy(aux,removePattern(aux,'<','>'));     //Remove as tags da linha
                strcpy(aux,substring(aux,11,strlen(aux)));     //Remove "Orçamento " da linha
                f->orcamento = orcToFloat(aux);     //Converte do orçamento em string para float
                strcpy(f->tmpOrcamento,aux);        //Grava a linha no orçamento em caracteres do Filme         
            }
            //Condicional para a lista com as palavras-chave do filme
            else if (strstr(aux,"Palavras-chave") != NULL)
            {
                
                int KWIndex = 0;

                fgets(aux,1000,arq);        //realiza a leitura na próxima linha
                while (strstr(aux,"<li") == NULL)
                {
                    //Verifica se a linha indica que não há palavras chave
                    if (strstr(aux,"<p><bdi>Nenhuma palavra-chave foi adicionada.</bdi></p>") != NULL)
                    {
                        f->hasKeywords = false;     //Grava que não há palavras chave para o filme
                    }
                    
                    fgets(aux,1000,arq);        //realiza a leitura na próxima linha
                }

                
                
                while (f->hasKeywords && strstr(aux,"</ul>") == NULL)
                {
                    if (strstr(aux,"<li"))
                    {
                        aux[strlen(aux) - 1] = '\0';    //Remove o \n
                        strcpy(aux,removePattern(aux,'<','>'));     //Remove as tags da linha
                        strcpy(aux,reduceSpace(aux));   //retira o espaço desnecessário
                        strcpy(f->keywords[KWIndex],aux);   //Salva a palavra chave no próximo índice do vetor
                        KWIndex++;  //Salta para a próxima posição
                    }             
                    
                    fgets(aux,1000,arq);        //realiza a leitura na próxima linha
                }
                
                f->quantKeywords = KWIndex;
            }

            //aux = '\0';
        }

        fclose(arq);
        
    }
    
}

void printFilme(Filme f){
    printf("%s ",f.nome);
    printf("%s ",f.tituloOriginal);

    if (f.lancamento.dia <= 9)
    {
        printf("0%d/",f.lancamento.dia);
    }
    else
    {
        printf("%d/",f.lancamento.dia);
    }
    if (f.lancamento.mes <= 9)
    {
        printf("0%d/",f.lancamento.mes);
    }
    else
    {
        printf("%d/",f.lancamento.mes);
    }
    printf("%d ",f.lancamento.ano);
    
    printf("%d ",f.duracao);
    printf("%s ",f.genero);
    printf("%s ",f.idiomaOriginal);
    printf("%s ",f.situacao);    
    printf("%g ",f.orcamento);
    printf("[");
    if (f.hasKeywords)
    {
        printf("%s",f.keywords[0]);
        for (int i = 1; i < f.quantKeywords; i++)
        {
            if (f.keywords[i] != NULL)
            {
                printf(", %s",f.keywords[i]);
            }
        }
    }   
    
    printf("]\n");
}

void printDesformatado(Filme f){
    printf("nome: %s tamanho: %d\n",f.nome, strlen(f.nome));
    printf("tituloOriginal: %s\n",f.tituloOriginal);
    printf("tmpLancamento: %s\n",f.tmpLancamento);
    //printf("dia: %d mes: %d ano: %d \n",f.lancamento.dia,f.lancamento.mes,f.lancamento.ano);
    printf("tmpDuracao: %s\n",f.tmpDuracao);
    printf("duracao: %d\n",f.duracao);
    printf("genero: %s\n",f.genero);
    printf("idiomaOriginal: %s\n",f.idiomaOriginal);
    printf("situacao: %s\n",f.situacao);    
    printf("tmpOrcamento: %s\n",f.tmpOrcamento);
    //printf("orcamento: %g\n",f.orcamento);
    printf("keywords: [");
    if (f.hasKeywords)
    {
        printf("%s",f.keywords[0]);
        for (int i = 1; i < f.quantKeywords; i++)
        {
            if (f.keywords[i] != NULL)
            {
                printf(", %s",f.keywords[i]);
            }
        }
    }   
    
    printf("]\n");/**/
    printf("\n");
}

Filme* clone(Filme* f){
    Filme* clonado = newFilme();
    //Clona o nome
    for (int i = 0; i < strlen(f->nome); i++)
    {
        clonado->nome[i] = f->nome[i];
    }

    //Clona o titulo original
    for (int i = 0; i < strlen(f->tituloOriginal); i++)
    {
        clonado->tituloOriginal[i] = f->tituloOriginal[i];
    }

    //Clona a data de lançamento
    clonado->lancamento.ano = f->lancamento.ano;
    clonado->lancamento.mes = f->lancamento.mes;
    clonado->lancamento.dia = f->lancamento.dia;

    //Clona duração
    clonado->duracao = f->duracao;

    //Clona genero
    for (int i = 0; i < strlen(f->genero); i++)
    {
        clonado->genero[i] = f->genero[i];
    }

    //Clona idioma original
    for (int i = 0; i < strlen(f->idiomaOriginal); i++)
    {
        clonado->idiomaOriginal[i] = f->idiomaOriginal[i];
    }
    
    //Clona situação
    for (int i = 0; i < strlen(f->situacao); i++)
    {
        clonado->situacao[i] = f->situacao[i];
    }

    //clona orçamento
    clonado->orcamento = f->orcamento;

    //Clona keywords
    for (int i = 0; i < 20; i++)
    {
        for (int j = 0; j < 500; j++)
        {
            clonado->keywords[i][j] = f->keywords[i][j];
        }
    }

    return clonado;
    
}

/*-----------------------------------------------*/

void insertFirst(LinkedList** list, Filme* f){
    Node* tmp = newNode(f);
    
    tmp->next = (*list)->first->next;
    (*list)->first->next = tmp;
    tmp->prev = (*list)->first;
    if ((*list)->first != (*list)->last)
    {
        tmp->next->prev = tmp;
    }
    else{
        (*list)->last = tmp;
    }
    (*list)->size++;    
}

void insertLast(LinkedList** list, Filme* f){
    (*list)->last->next = newNode(f);
    (*list)->last->next->prev = (*list)->last;
    (*list)->last = (*list)->last->next;
    (*list)->size++;
}

void printNomes(LinkedList *list){
    Node* tmp = list->first->next;
    for(; tmp != NULL; tmp = tmp->next){
        printFilme(*(tmp->f));
    }
}

//Faz o swap mudando cada um dos atributos
void swap(Filme* f1, Filme* f2){
    Filme* tmp = (Filme*)malloc(sizeof(Filme));

    ///Filme 1 para tmp
    tmp->duracao = f1->duracao;
    tmp->hasKeywords = f1->hasKeywords;
    tmp->orcamento = f1->orcamento;
    tmp->quantKeywords = f1->quantKeywords;
    strcpy(tmp->genero,f1->genero);
    strcpy(tmp->idiomaOriginal,f1->idiomaOriginal);
    strcpy(tmp->nome,f1->nome);
    strcpy(tmp->situacao,f1->situacao);
    strcpy(tmp->tituloOriginal,f1->tituloOriginal);
    tmp->lancamento.ano = f1->lancamento.ano;
    tmp->lancamento.mes = f1->lancamento.mes;
    tmp->lancamento.dia = f1->lancamento.dia;
    for (int i = 0; i < 20; i++)
    {
        strcpy(tmp->keywords[i],f1->keywords[i]);
    }

    //filme2 para filme 1
    f1->duracao = f2->duracao;
    f1->hasKeywords = f2->hasKeywords;
    f1->orcamento = f2->orcamento;
    f1->quantKeywords = f2->quantKeywords;
    strcpy(f1->genero,f2->genero);
    strcpy(f1->idiomaOriginal,f2->idiomaOriginal);
    strcpy(f1->nome,f2->nome);
    strcpy(f1->situacao,f2->situacao);
    strcpy(f1->tituloOriginal,f2->tituloOriginal);
    f1->lancamento.ano = f2->lancamento.ano;
    f1->lancamento.mes = f2->lancamento.mes;
    f1->lancamento.dia = f2->lancamento.dia;
    for (int i = 0; i < 20; i++)
    {
        strcpy(f1->keywords[i],f2->keywords[i]);
    }

    //tmp para filme2
    f2->duracao = tmp->duracao;
    f2->hasKeywords = tmp->hasKeywords;
    f2->orcamento = tmp->orcamento;
    f2->quantKeywords = tmp->quantKeywords;
    strcpy(f2->genero,tmp->genero);
    strcpy(f2->idiomaOriginal,tmp->idiomaOriginal);
    strcpy(f2->nome,tmp->nome);
    strcpy(f2->situacao,tmp->situacao);
    strcpy(f2->tituloOriginal,tmp->tituloOriginal);
    f2->lancamento.ano = tmp->lancamento.ano;
    f2->lancamento.mes = tmp->lancamento.mes;
    f2->lancamento.dia = tmp->lancamento.dia;
    for (int i = 0; i < 20; i++)
    {
        strcpy(f2->keywords[i],tmp->keywords[i]);
    }
}

//Uma função que retorna um nodo em tal posição
Node* getNode(LinkedList** list, int pos){
    if (pos >= (*list)->size || pos < 0)
    {
        printf("Index out of bounds!!: max: %d index: %d\n",(*list)->size,pos);
        return NULL;
    }
    
    Node* tmp = newNode(NULL);  //Cria um nodo que será apontado para o primeiro ou ultimo elemento
    if (pos < (*list)->size/2)
    {
        tmp = (*list)->first->next; //Posiciona o ponteiro temporário como primeiro item efetivo na lista
        for (int i = 0; i < pos; tmp = tmp->next, i++);  //Caminha até a posição certa      
    }
    else{
        tmp = (*list)->last;    //Posiciona o ponteiro temporário como ultimo item da lista
        for (int i = (*list)->size - 1; i > pos; tmp = tmp->prev, i--); //Caminha até a posição certa
    }
    return tmp;
}

//Uma função que retorna o filme em determinada posição
Filme* getF(LinkedList** list, int pos){
    return getNode(list, pos)->f;
}

//Compara 2 filmes utilizando o atributo determinado ou o atributo de nome, caso houver empate
int cmpFilme(Filme f1, Filme f2){
    int resp = 0;
    //Compara o atributo situacao
    resp = strcmp(f1.situacao,f2.situacao);
    //Caso sejam iguais, compara o atributo de nome
    if (resp == 0)
    {
        resp = strcmp(f1.nome,f2.nome);
    }
    
}

void quickSortRecursive(LinkedList** list, int esq, int dir){
    int mid = (esq  + dir)/2;
    int iEsq = esq;
    int iDir = dir;
    while (iEsq <= iDir)
    {
        //Caminha para a direita com o indice da esquerda enquanto o filme do meio for maior que o filme analisado
        while(cmpFilme(*getF(list,iEsq),*getF(list,mid)) < 0){
            iEsq++;
        }
        //Caminha para a esquerda com o indice da direita enquanto o filme do meio for maior que o filme analisado
        while(cmpFilme(*getF(list,iDir),*getF(list,mid)) > 0){
            iDir--;
        }
        //Caso os indices não tenham se cruzado, faz o swap e caminha os indices
        if (iEsq <= iDir)
        {
            swap(getNode(list,iEsq)->f, getNode(list,iDir)->f);
            iEsq++;
            iDir--;
        }
        
    }
    //Caso o indice da direita não tenha ultrapassado o limite esquerdo, chama a recurssão
    if(esq < iDir){
        quickSortRecursive(list,esq,iDir);
    }
    //Caso o indice da esquerda não tenha ultrapassado o limite direito, chama a recurssão
    if(dir < iEsq){
        quickSortRecursive(list,iEsq,dir);
    }
}


void quickSort(LinkedList** list){
    quickSortRecursive(list,0,(*list)->size - 1);
}

int main(){
    char nomesFilmes[100][100];     //Inicializa o vetor que armazena o nome dos filmes
    size_t len = 0;
    int max = 0;    //Indice para ler o nome dos filmes com a quantidade de filmes lidos
    fgets(nomesFilmes[max],1000,stdin);
    nomesFilmes[max][strlen(nomesFilmes[max]) - 1] = '\0';
    
    //Leitura dos primeiros filmes
    while (strcmp(nomesFilmes[max],"FIM") != 0)
    {
        max++;  //incrementa o maximo de filmes
        fgets(nomesFilmes[max],1000,stdin);
        nomesFilmes[max][strlen(nomesFilmes[max]) - 1] = '\0';
    }
    
    LinkedList *catalogo = newLinkedList();
    for (int i = 0; i < max; i++)
    {
        Filme *temp = newFilme();
        getAll(temp,nomesFilmes[i]);
        insertLast(&catalogo,temp);
    }
    
    quickSort(&catalogo);

    printNomes(catalogo);
    return 0;
}
