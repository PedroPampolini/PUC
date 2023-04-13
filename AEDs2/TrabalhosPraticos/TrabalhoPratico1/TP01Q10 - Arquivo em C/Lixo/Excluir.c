#include <stdio.h>
#include <stdbool.h>

void proximoAno(int ano)
{
  if(ano < 2062)
  {
    printf("2062\n");
  }
  else if(((ano - 1986) % 76) == 0)
  {
    printf("%d\n", (ano + 76));
  }
  else
  {
    printf("%d\n", (76 - ((ano - 1986) % 76) + ano));
  }
}

int main(void) 
{
  int ano;

  while (ano != 0)
    {
      scanf("%d", &ano);
      if(ano != 0)
      {
        proximoAno(ano);
      }
    }
  
  return 0;
}