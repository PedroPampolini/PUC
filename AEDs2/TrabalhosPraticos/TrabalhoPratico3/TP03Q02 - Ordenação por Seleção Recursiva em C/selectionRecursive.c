#include <stdio.h>
#include <stdlib.h>
#include <string.h>
void selectionSortRecursive(int* num, int size, int pos){
    if (pos < size)
    {
        for (int i = pos; i < size; i++)
        {
            if (num[pos] > num[i])
            {
                int tmp = num[pos];
                num[pos] = num[i];
                num[i] = tmp;
            }
        }
        selectionSortRecursive(num,size,pos+1);        
    }
    
}

void selectionSort(int num[], int size){
    selectionSortRecursive(num, size, 0);
}

/*void selectionSortRecursive(char** nomes, int size, int pos){
    printf("Entrou\n");
    if (pos < size)
    {
        for (int i = 0; i < size; i++)
        {
            
            if (strcmp(nomes[pos], nomes[i]) < 0)
            {
                
                char* tmp = malloc(sizeof(char) * 50);
                memcpy(tmp,nomes[pos],50);
                printf("Debug\n");
                memcpy(nomes[pos], nomes[i],50);
                memcpy(nomes[i], tmp,50);
            }
        }
        selectionSortRecursive(nomes,size,pos+1);        
    }
    
}

void selectionSort(char** nomes, int size){
    selectionSortRecursive(nomes, size, 0);
}*/


int main(){
    //char *num[] = {"Pedro", "Clara", "Cassia", "Jose", "Yuri", "Fulano","Barbara","Davi","Kvothe", "Ivone"};
    int num[] = {3,4,2,5,10,7,6,8,9,1};
    for (int i = 0; i < 10; i++)
    {
        printf("%d ",num[i]);
    }
    printf("\n");
    
    selectionSort(num,10);

    for (int i = 0; i < 10; i++)
    {
        printf("%d ",num[i]);
    }
    printf("\n");
    
    return 0;
}