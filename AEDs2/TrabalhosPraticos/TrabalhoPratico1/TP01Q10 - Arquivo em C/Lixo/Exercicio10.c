#include <stdio.h>
#include <stdbool.h>



bool isInteger(double number){
    int prov = (int)number;
    return(prov == number);
}

void leArquivo(){
    FILE* arquivo = fopen("temp.txt","r");
    fseek(arquivo,0,SEEK_END);
    int maximo = ftell(arquivo);
    double temp;
    char aux = ' ';
    int i = maximo-sizeof(double);
    int indexNovaLinha = i;
    double antigo;
    fseek(arquivo,i,0);
    fscanf(arquivo,"%lf",&temp);
    do
    {
        fflush(arquivo);
        fflush(stdout);
        if (aux == '\n')
        {
            indexNovaLinha = i+1;
            fseek(arquivo,indexNovaLinha,0);
            fscanf(arquivo,"%lf",&temp);
            if (isInteger(temp))
            {
                printf("%d\n",(int)temp);
            }
            else{
                printf("%g\n",temp);
            }
            i--;
        }
        i--;
        fseek(arquivo,i,0);
    }while (fscanf(arquivo,"%c",&aux) != EOF);
    fflush(arquivo);
    fflush(stdout);
    fseek(arquivo,0,0);
    fscanf(arquivo,"%lf",&temp);
    fclose(arquivo);
    
    if (isInteger(temp))
    {
        printf("%d\n",(int)temp);
    }
    else{
        printf("%g\n",temp);
    }
    
}

int main(){
    int maximo;
    double atual;
    FILE* arq = fopen("temp.txt","w");
    scanf("%d",&maximo);
    for (int i = 0; i < maximo; i++)
    {
        scanf("%lf",&atual);
        fprintf(arq,"%lf\n",atual);
    }
    fclose(arq);
    leArquivo();
    return 0;
}