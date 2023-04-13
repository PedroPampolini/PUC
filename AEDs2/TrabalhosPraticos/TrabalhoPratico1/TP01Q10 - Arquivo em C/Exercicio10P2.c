/*
Autor: Pedro Pampolini Mendicino
Data de criação: 22/02/2022   Última atualização: 22/02/2022
Objetivo: Recebe um primeiro número inteiro para determinar quantas entradas serão lidas, então lê as entradas, salva
seus valores em um arquivo e reabre o mesmo, lendo seus arquivos de baixo para cima, recursivamente
*/
#include <stdio.h>
#include <stdbool.h>

//Verifica se um número é inteiro ou não
bool isInteger(double number){
    int prov = (int)number;
    return (number == prov);
}

//Lê X inteiros do teclado e escreve em um arquivo 
void writeNumbers(int maximo){
    double atual;
    FILE* writeNumbers = fopen("numbers.txt","w");  //Abre o arquivo em modo de escrita
    
    //Até que o máximo de entradas seja escrito, irá executar o programa
    for (int i = 0; i < maximo; i++)
    {
        scanf("%lf",&atual);        //Lê a entrada
        fprintf(writeNumbers,"%lf\n",atual);
    }
    fclose(writeNumbers);   //Fecha o arquivo para salvamento
}

void readNumbeFile(long pos,int atual,int max){
    FILE* readNumbers = fopen("numbers.txt","r");
    double number;  //Número que seja empilhado pela recursividade e que será impresso reversamente
    char charPointer;   //variável que scaneia o arquivo como caractere
    char charLastPointer;   
    //enquanto não chegar no máximo de entradas, irá continuar lendo
    if (atual < max)
    {
        fseek(readNumbers,pos,SEEK_SET);    //posiciona o ponteiro do arquivo na posição indicada, o inicio da linha
        fscanf(readNumbers,"%lf",&number);  //Lê a primeira linha como double
        fseek(readNumbers,pos,SEEK_SET);    //Posiciona novamente na posição indicada, inicio da linha
        do
        {
            //Irá ler o caractere até chegar em uma quebra de linha, \n
            fscanf(readNumbers,"%c",&charPointer);  
            pos++;  //Incrementa a posição, indicando qual será o caractere de quebra de linha
        }while(charPointer != '\n' && charPointer != EOF);  //Verifica a quebra de linha e o fim do arquivo

        fclose(readNumbers);    //fecha o arquivo para que possa ser aberto com a recursividade
        //Caso tenha sido uma quebra de linha, e não um EOF, chamará novamente a função
        if (charPointer == '\n')
        {
            readNumbeFile(pos,atual+1,max); //Chama a função incrementando qual o double foi lido
        }
        
        
    }
    //Imprime o double que foi empilhado por conta da recursividade
    if (atual < max)
    {
        if (isInteger(number))  //Caso seja inteiro, imprime sem a virgula ou o ponto
        {
            printf("%d\n",(int)number);
        }
        else
        {
            printf("%g\n",number);      //Caso seja decimal, imprime com a casa decimal
        }
    }
    
    
}

int main(){
    int maximo;
    //printf("tentativa 1: ");
    scanf("%d",&maximo);
    writeNumbers(maximo);
    //printf("leitura\n");
    readNumbeFile(0,0,maximo);
    return 0;
}