#include <stdio.h>
#include <stdbool.h>

void leArquivo(int maximo){
    FILE* arquivo = fopen("temp.txt","r");
    double temp;
    fseek(arquivo,maximo-1,0);
    fscanf(arquivo,"%lf",&temp);
    printf("%lf\n",temp);
    for (int i = maximo; i >= 0; i++)
    {
        while(temp == 0.0)
        {
            i--;
            fseek(arquivo,i,0);
            fscanf(arquivo,"%lf",&temp);
        }
        while (temp != 0)
        {
            i--;
            fseek(arquivo,i,0);
            fscanf(arquivo,"%lf",&temp);
        }
        
        
        printf("%.3lf\n",temp);
    }
    //printf("%d",index);
    
    
}

bool isInteger(double number){
    int prov = (int)number;
    return(prov == number);
}

void testArquivo(int maximo){
    FILE* arquivo = fopen("temp.txt","r");
    double temp;
    char aux = ' ';
    int enterCounter = 0;
    /*for (int i = 0; i < maximo;i++)
    {
        fseek(arquivo,i,0);
        //fscanf(arquivo,"%lf",&temp);
        fscanf(arquivo,"%c",&aux);
        /*printf("%lf\n",temp);
        while(aux != '\n'){
            i++;
            fseek(arquivo,i,0);
            fscanf(arquivo,"%c",&aux);
        }
        if (aux == '\n')
        {
            enterCounter++;
        }
            
    }*/
    int i = maximo;
    int indexNovaLinha = i;
    double antigo;
    fseek(arquivo,i,0);
    fscanf(arquivo,"%lf",&temp);
    //printf("index: %i\t%lf\n",i,temp);
    do
    {
        if (aux == '\n')
        {
            enterCounter++;
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
    fseek(arquivo,0,0);
    fscanf(arquivo,"%lf",&temp);
    if (isInteger(temp))
    {
        printf("%d\n",(int)temp);
    }
    else{
        printf("%g\n",temp);
    }
}

int findFinalIndex(){
    FILE* arquivo = fopen("temp.txt","r");
    double temp;
    int index;
    //printf("Teste\n");
    fseek(arquivo,0,0);
    fscanf(arquivo,"%lf",&temp);
    //printf("%lf\n",temp);
    for (int i = 0; i < 900; i++)
    {
        
        while (temp != 0)
        {
            i++;
            fseek(arquivo,i,0);
            fscanf(arquivo,"%lf",&temp);
        }
        while(temp == 0.0)
        {
            i++;
            fseek(arquivo,i,0);
            fscanf(arquivo,"%lf",&temp);
        }
        //printf("index: %d\t valor:%lf\n",i,temp);
        
        index = i;
    }
    return index+1;
}

int main(){
    int maximo;
    double atual;
    int finalIndex;
    FILE* arq = fopen("temp.txt","w");
    scanf("%d",&maximo);
    for (int i = 0; i < maximo; i++)
    {
        scanf("%lf",&atual);
        fprintf(arq,"%lf\n",atual);
    }
    fclose(arq);
    maximo*=8;
    finalIndex = findFinalIndex();
    testArquivo(finalIndex);
    return 0;
}