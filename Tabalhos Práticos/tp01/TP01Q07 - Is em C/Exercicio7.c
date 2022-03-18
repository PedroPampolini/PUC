#include <stdio.h>
#include <string.h>
#include <stdbool.h>
/**
 Autor: Pedro Pampolini Mendicino
 Criação: 13/02/2022    Última atualização: 14/02/2022
 Objetivo do Programa:  um programa que recebe uma string e verifica se faz parte de uma das seguinte categorias:
 1) Composta somente por consoantes
 2) Composta somente por vogais
 3) É um número inteiro
 4) É um número real
 */

bool isFim(char str[]){
    return(strlen(str) == 3 && str[0] == 'F' && str[1] == 'I' && str[2] == 'M');
}

//Recebe a string e grava no vetor do parâmetro
void getString(char *str){
    char c;
    int i = 0;
    while (c != '\n')
    {
        scanf("%c",&c);
        *(str + i) = c;
        i++;
    }
    fflush(stdin);
    *(str + i -1) = '\0';
}
//verifica se  um caractere é uma vogal ou não
bool isVogal(char c){
    return(c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U' || c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u');
}
//Verifica se um caractere é uma letra ou não
bool isLetter(char c){
    return(( c >= 'A' && c <= 'Z') || ( c >= 'a' && c <= 'z'));
}
//Verifica se um caractere é uma consoante ou não
bool isConsonant(char c){
    return(isLetter(c) && !isVogal(c));
}
//Verifica se um caractere é um número ou não
bool isNumber(char c){
    return(c >= '0' && c <= '9');
}
//Verifica se uma string é um número inteiro ou não
bool isInteger(char str[]){
    bool res = true;
    //Se Algum dos caracteres não pertencerem às definições de cada conjunto,
    //Será retornado falso
    for (int i = 0; i < strlen(str); i++)
    {
        if (!isNumber(str[i]))
        {
            res = false;
            i = strlen(str);
        }
        
    }
    return res; 
}
//Verifica se uma string é um número real ou não
bool isReal(char str[]){
    bool res = true;
    //Se Algum dos caracteres não pertencerem às definições de cada conjunto,
    //Será retornado falso
    int commaPointCounter = 0;
    for (int i = 0; i < strlen(str); i++)
    {
        if (!(isNumber(str[i]) || str[i] == '.' || str[i] == ','))
        {
            res = false;
            i = strlen(str);
        }
        if (str[i] == '.' || str[i] == ',')
        {
            commaPointCounter++;
        }
        
    }
    if (commaPointCounter > 1)
    {
        res = false;
    }
    
    
    return res; 
}
//Verifica se uma string possui apenas vogais
bool onlyConsonant(char str[]){
    bool res = true;
    //Se Algum dos caracteres não pertencerem às definições de cada conjunto,
    //Será retornado falso
    for (int i = 0; i < strlen(str); i++)
    {
        if (!isConsonant(str[i]))
        {
            res = false;
            i = strlen(str);
        }
        
    }
    return res;    
}
//Verifica se uma string possui apenas consoantes
bool onlyVogal(char str[]){
    bool res = true;
    //Se Algum dos caracteres não pertencerem às definições de cada conjunto,
    //Será retornado falso
    for (int i = 0; i < strlen(str); i++)
    {
        if (!isVogal(str[i]))
        {
            res = false;
            i = strlen(str);
        }
        
    }
    return res;    
}

int main()
{
    char str[500];
    getString(str);

    //Enquanto a palavra digitada não for FIM, continua o processo
    while (!isFim(str))
    {
        printf("%s",onlyVogal(str) ? "SIM " : "NAO ");
        printf("%s",onlyConsonant(str) ? "SIM " : "NAO ");
        printf("%s",isInteger(str) ? "SIM " : "NAO ");
        printf("%s",isReal(str) ? "SIM " : "NAO ");
        printf(" \n");
        getString(str);

    }
    
    
    return 0;
}