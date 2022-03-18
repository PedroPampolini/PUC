/*
Autor: Pedro Pampolini Mendicino
Data de criação: 23/02/2022 Última atualização: 23/02/2022
Objetivo: Recebe uma String e verifica se é um palíndromo ou não
*/
#include <stdio.h>
#include <string.h>
#include <stdbool.h>
//Compara se a string recebida é "FIM"
bool isFim(char* s){
    return(strlen(s) == 3 && s[0] == 'F' && s[1] == 'I' && s[2] == 'M');
}

bool isPalindrome(char* s,int pos){
    int max = strlen(s);    //Armazena o comprimento máximo da string para evitar loops
    bool resp = true;   //Resposta retornada
    //Verifica toda a primeira metade da string com a segunda metade
    if (pos < max/2)
    {
        //Caso os caracteres espelhados sejam diferentes, irá retornar false
        if (s[pos] != s[max -1 -pos])
        {
            resp = false;
        }
        //Caso sejam iguais, irá analisar os próximos
        else{
            resp = isPalindrome(s,pos+1);
        }
    }
    return resp;
    
}

int main(){
    char text[500];
    scanf(" %[^\n]c", text);        //Lê a primeira entrada
    //Enquanto a entrada for diferente de 'SIM', irá continuar as leituras
    while(!isFim(text)){
        //Imprime 'SIM' ou 'NAO' dependendo da saida da função
        printf(isPalindrome(text,0)? "SIM\n" : "NAO\n");
        scanf(" %[^\n]c",text);     //Lê a próxima linha
    }
    return 0;
}