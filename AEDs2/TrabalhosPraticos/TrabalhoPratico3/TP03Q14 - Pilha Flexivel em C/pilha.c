#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct Pessoa
{
    char nome[20];
    int idade;
    double altura;
}Pessoa;


typedef struct Node
{
    Pessoa* p;
    struct Node* next;
}Node;


typedef struct Stack
{
    Node* top;
    int size;
}Stack;

Pessoa* newPessoa(char _nome[], int _idade, double _altura){
    Pessoa* p = (Pessoa*)malloc(sizeof(Pessoa));

    for (int i = 0; i < strlen(_nome); i++)
    { 
        printf("nome: %s tam: %d\n",_nome,strlen(_nome));
        p->nome[i] = _nome[i];
    }
    p->idade = _idade;
    p->altura = _altura;

    return p;
}

Node* newNode(Pessoa* p){
    Node* n = (Node*)malloc(sizeof(Node));
    n->p = p;
    n->next = NULL;

    return n;
}

Stack* newStack(){
    Stack* s = (Stack*)malloc(sizeof(Stack));
    s->top = NULL;
    s->size = 0;
}

void push(Stack** pilha, Pessoa* p){
    Node* n = newNode(p);
    n->next = (*pilha)->top;
    (*pilha)->top = n;
}

Pessoa* pop(Stack** pilha){
    if ((*pilha)->size <= 0)
    {
        return NULL;
    }

    Node* tmp = (*pilha)->top;
    (*pilha)->top = (*pilha)->top->next;
    
    Pessoa* p = tmp->p;
    return p;    
}

void printStack(Stack* pilha){
    Node* tmp = pilha->top;
    for(; tmp != NULL; tmp = tmp->next){
        printf("nome: %s\tidade:%d\taltura: %.2lf\n",tmp->p->nome, tmp->p->idade, tmp->p->altura);
    }
}

int main(){
    Pessoa* p1 = newPessoa("Pedro Pampolini", 21, 1.75);
    Pessoa* p2 = newPessoa("Jose Mendicino", 58, 1.70);
    Pessoa* p3 = newPessoa("Clara Pampolini", 17, 1.65);
    Pessoa* p4 = newPessoa("Cassia Mendicino", 51, 1.51);

    Stack* pilha = newStack();

    push(&pilha, p1);
    
    printStack(pilha);
    push(&pilha, p2);
    push(&pilha, p3);
    push(&pilha, p4);
    printStack(pilha);
    return 0;
}