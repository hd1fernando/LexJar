Analizador Léxico Java
-------------------------------------------
> This is a simple lexical analyzer make in Java for a college project.

Este Analisador Léxico, escrito em Java, lê um programa fonte a partir de um arquivo com extensão ".cpt" (C português).
E gera um arquivo de saída gravado com a extensão ".ctk" (C tokens).

O Analisador Léxico reconhece os tokens apresentados na tabela abaixo

IDENTIFICADOR | TOKEN | LEXEMA
--------------|-------|--------
1| IDENTIFICADOR|
2| INTEIRO|
3| REAL|
4| CARACTER|
5| CADEIA_CARACTERES|
6| ATRIBUICAO| <-
7| SOMA| +
8| SUBRITACAO| -
9| MULTIPLICACAO| *
10| DIVISAO| /
11| POTENCIA| ^
12| SE| se
13| ENQUANTO| enquanto
14| PARA| para
15| INICIO_ESCOPO| {
16| FIM_ESCOPO| }
17| INICIO_PARAMETRO| (
18| FIM_PARAMETRO| )
19| TIPO| int, real, caracter, vazio
20| COMENTARIO_LINHA| //
21| INICIO_COMENTARIO_PARAGRAFO| /*
22| FIM_COMENTARIO_PARAGRAFO| */
23| FIM_COMANDO| ;
24| VIRGULA| ,
25| ENTRADA_PADRAO| Teclado
26| SAIDA_PADRAO| Tela
27| COMPARACAO| =
28| SENAO| senao
29| RETORNO_FUNCAO| retornar
30| PRINCIPAL| principal
31| RESTO_DIVISAO| %
32| MENOR| <
33| MENOR_IGUAL| <=
34| MAIOR| >
35| MAIOR_IGUAL| >=


Exemplos de programas a serem reconhecidos:

**Programa I:**

```sh
vazio principal () {
  int a, b;
  tela <- “Informe o primeiro número:”;
  a <- teclado;
  tela <- “Informe o segundo número:”;
  b <- teclado;
  se (a > b)
    tela <- “Maior: ” + a;
  senao
    tela <- “Maior: ” + b;
}
```

**Programa II:**
```sh
/* Programa para identificar o maior
dentre dois números.
*/
vazio principal () {
  int a, b;
  tela <- “Informe o primeiro número:”;
  a <- teclado;
  tela <- “Informe o segundo número:”;
  b <- teclado;
  tela <- “Maior: ” + maior (a, b);
}
int maior (int a, int b) {
  se (a > b)
    retornar (a);
  senao
    retornar (b);
}
```
**Programa III:** 
```sh
vazio principal () {
  int num;
  tela <- “Informe o número:”;
  num <- teclado;
  pares (num);
}
//Procedimento para identificar números pares menores que n.
vazio pares (int n) {
  int i <- 0;
  enquanto (i < n)
    se (i % 2 = 0)
      tela <- i;
}
```
**Programa IV:**
```sh
vazio principal () {
  int num;
  tela <- “Informe o número:”;
  num <- teclado;
  pares (num);
}

vazio pares (int n) {
  int i ;
  para (i <- 0; i < n; i <- i + 1)
    se (i % 2 = 0)
      tela <- i;
}
```
