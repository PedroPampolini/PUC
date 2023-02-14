# Algoritmo de Levenshtein

O algoritmo de Levenshtein é uma forma de calcular a distância entre duas palavras. Essa distância é representada pelo número de operações (inserções, deleções ou substituições) necessárias para transformar uma palavra em outra.

## Como funciona

O código cria uma matriz com o tamanho das duas palavras e inicializa a primeira linha e coluna da matriz com os valores de 0 até o tamanho da palavra. Em seguida, a matriz é preenchida de acordo com as seguintes regras:

- Se as letras nas posições correspondentes das palavras forem iguais, a posição na matriz será preenchida com o valor da posição na linha anterior e coluna anterior.
- Se as letras não forem iguais, a posição na matriz será preenchida com o mínimo valor entre a posição na linha anterior, coluna anterior e diagonal anterior + 1.

O resultado da distância de Levenshtein é o valor na última posição da matriz.

## Utilização

Para usar o algoritmo, basta criar uma instância da classe `Levenshtein` passando as duas palavras como argumento e inicializar a matriz de distância chamando o método `initMatriz`. Em seguida, é possível obter a distância de Levenshtein através do método `getDistancia` e imprimir a matriz através do método `imprimeMatriz`.
