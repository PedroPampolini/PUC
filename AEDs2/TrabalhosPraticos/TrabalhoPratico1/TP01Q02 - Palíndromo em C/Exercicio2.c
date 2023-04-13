/**
 Autor: Pedro Pampolini Mendicino
 Criação: 13/02/2022    Última atualização: 14/02/2022
 Objetivo do Programa:  um programa que recebe uma string e verifica se é palíndromo ou não
 */
#include <stdio.h>
#include <string.h>
#include <stdbool.h>

//Função que verifica se chegou a última linha ou não
bool isFim(char str[]){
    return (strlen(str) == 3 && str[0] == 'F' && str[1] == 'I' && str[2] == 'M');
}


//Verifica se é Palíndromo ou não
bool isPalindrome(char str[]){
    bool resp = true;
    int limit = strlen(str);    //grava o length da string
    //Compara o primeiro caractere com o último, segundo com penúltimo...
    for (int i = 0; i < limit/2; i++)
    {
        //caso alguma comparação seja falsa, retorna falso
        if (str[i] != str[limit -1 -i])
        {
            resp = false;   //Caso os caracteres simétricos sejam diferentes, retornará falso
            i = limit/2;    //Salva o i com o valor necessário para sair do loop
        }
        
    }
    return resp; 
}



void getString(char *str){
    char c;
    int i = 0;

    //Lera os caracteres digitados até que um '\n' seja digitado
    while (c != '\n')
    {
        scanf("%c",&c); //grava o valor no char auxiliar
        *(str + i) = c; //salva o valor na posição da String
        i++;    //posiciona o índice na próxima posição
    }
    fflush(stdin);  //Limpa o buffer com os caracteres não lidos
    *(str + i -1) = '\0';   //seta o limitador da string
}

int main(){
    char str[500];  //Reserva um espaço para a leitura das strings
    
    getString(str); //Lê a primeira string
    while (!isFim(str))
    {
        printf((isPalindrome(str))? "SIM\n" : "NAO\n");
        getString(str);
    }
    
    
    return 0;
}