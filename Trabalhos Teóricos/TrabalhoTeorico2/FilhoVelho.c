#include <stdio.h>

int filhoVelho(int idades[]){
    int velho = 0;
    idades[3] = idades[0] - idades[1] - idades[2];
    for (int i = 1; i < 4; i++)
    {
        if (velho < idades[i])
        {
            velho = idades[i];
        }
    }
    return velho;
}

int main(){
    int idades[4];
    scanf("%d",&idades[0]);
    while (idades[0] > 0)
    {
        scanf("%d",&idades[1]);
        scanf("%d",&idades[2]);
        printf("%d\n",filhoVelho(idades));
        scanf("%d",&idades[0]);
    }
    
    return 0;
}