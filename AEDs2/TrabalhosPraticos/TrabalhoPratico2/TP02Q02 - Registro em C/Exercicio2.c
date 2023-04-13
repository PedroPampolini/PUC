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

    if (arq == NULL)
    {
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
    printf("nome: %s\n",f.nome);
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

int main(){
    char nomesFilmes[100][100];     //Inicializa o vetor que armazena o nome dos filmes
    size_t len = 0;
    int max = 0;    //Indice para ler o nome dos filmes com a quantidade de filmes lidos
    
    fgets(nomesFilmes[max],1000,stdin);
    nomesFilmes[max][strlen(nomesFilmes[max]) - 1] = '\0';
    
    while (strcmp(nomesFilmes[max],"FIM") != 0)
    {
        max++;  //incrementa o maximo de filmes
        fgets(nomesFilmes[max],1000,stdin);
        nomesFilmes[max][strlen(nomesFilmes[max]) - 1] = '\0';
    }
    
    Filme catalogo[max];

    for (int i = 0; i < max; i++)
    {
        getAll(&catalogo[i],nomesFilmes[i]);
    }
    
    for (int i = 0; i < max; i++)
    {
        printFilme(catalogo[i]);
    }
    

    
    return 0;
}
