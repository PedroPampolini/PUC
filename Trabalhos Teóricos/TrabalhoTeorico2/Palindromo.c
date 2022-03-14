#include <stdio.h>
#include <string.h>
#include <stdbool.h>

bool isFim(char str[]){
    return(strlen(str) == 3 && str[0] == 'F' && str[1] == 'I' && str[2] == 'M');
}

bool isPalindrome(char str[]){
    bool resp = true;
    int limite = strlen(str);
    for (int i = 0; i < limite/2; i++)
    {
        if (str[i] != str[limite -i -1])
        {
            resp = false;
            i = limite;
        }  
    }
    return resp;    
}

int main(){
    char str[500];
    scanf(" %[^\n]",str);
    while(!isFim(str)){
        printf(isPalindrome(str) ? "SIM\n" : "NAO\n");
        scanf(" %[^\n]",str);
    }
    
    return 0;
}