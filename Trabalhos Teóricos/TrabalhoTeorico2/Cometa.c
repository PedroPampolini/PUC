#include <stdio.h>

int calculaAno(int ano){
    return ano + (76 - ((ano - 1986) % 76));
}

int main(){
    int ano;
    scanf("%d",&ano);
    while (ano != 0)
    {
        printf("%d\n",calculaAno(ano));
        scanf("%d",&ano);
    }
    
    return 0;
}