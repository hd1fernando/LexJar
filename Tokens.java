package analisador_lexico;

public class Tokens {
    // token também deve conter linha e coluna

    public static String saida = "";

    public void identificador(String identificador) {
        System.out.printf("< 1 | " + identificador + " > ");
        saida += "< 1 | " + identificador + " > ";
    }

    public void inteiro(String inteiro) {
        System.out.printf("< 2 | %s > ", inteiro);
        saida += "< 2 | " + inteiro + " > ";
    }

    public void real(String real) {
        System.out.printf("< 3 | %s > ", real);
        saida += "< 3 | " + real + " > ";
    }

    public void caractere(char caractere) {
        System.out.printf("< 4 | %c > ", caractere);
        saida += "< 4 | " + caractere + " > ";
    }

    public void cadeiaCaractere(String cadeia) {
        System.out.printf("< 5 | %s > ", cadeia);
        saida += "< 5 | " + cadeia + " > ";
    }

    public void atribuicao() {
        System.out.printf("< 6 | ATRIBUICAO > ");
        saida += "< 6 | ATRIBUICAO > ";
    }

    public void soma() {
        System.out.printf("< 7 | SOMA > ");
        saida += "< 7 | SOMA > ";
    }

    public void subtracao() {
        System.out.printf("< 8 | SUBTRACAO > ");
        saida += "< 8 | SUBTRACAO > ";
    }

    public void multiplicacao() {
        System.out.printf("< 9 | MULTIPLICACAO > ");
        saida += "< 9 | MULTIPLICACAO > ";
    }

    public void divisao() {
        System.out.printf("< 10 | DIVISAO > ");
        saida += "< 10 | DIVISAO > ";
    }

    public void potencia() {
        System.out.printf("< 11 | POTENCIA > ");
        saida += "< 11 | POTENCIA > ";
    }

    public void se() {
        System.out.printf("< 12 | SE > ");
        saida += "< 12 | SE > ";
    }

    public void enquanto() {
        System.out.printf("< 13 | ENQUANTO > ");
        saida += "< 13 | ENQUANTO > ";
    }

    public void para() {
        System.out.printf("< 14 | PARA > ");
        saida += "< 14 | PARA > ";
    }

    public void inicioEscopo() {
        System.out.printf("< 15 | INICIO_ESCOPO > ");
        saida += "< 15 | INICIO_ESCOPO > ";
    }

    public void fimEscopo() {
        System.out.printf("< 16 | FIM_ESCOPO > ");
        saida += "< 16 | FIM_ESCOPO > ";
    }

    public void inicioParametro() {
        System.out.printf("< 17 | INICIO_PARAMETRO> ");
        saida += "< 17 | INICIO_PARAMETRO > ";
    }

    public void fimParametro() {
        System.out.printf("< 18 | FIM_PARAMETRO> ");
        saida += "< 18 | FIM_PARAMETRO> ";
    }

    public void tipo(String tipo) {
        System.out.printf("< 19 | " + tipo.toUpperCase() + "> ");
        saida += "< 19 | " + tipo.toUpperCase() + " > ";
    }

    public void comentarioLinha() {
        System.out.printf("< 20 | COMENTARIO_LINHA> ");
        saida += "< 20 | COMENTARIO_LINHA> ";
    }

    public void inicioComentarioParagrafo() {
        System.out.printf("< 21 | INICIO_COMENTARIO_PARAGRAFO> ");
        saida += "< 21 | INICIO_COMENTARIO_PARAGRAFO > ";
    }

    public void fimComentarioParagrafo() {
        System.out.printf("< 22 | FIM_COMENTARIO_PARAGRAFO> ");
        saida += "< 22 | FIM_COMENTARIO_PARAGRAFO > ";
    }

    public void fimComando() {
        System.out.printf("< 23 | FIM_COMANDO> ");
        saida += "< 23 | FIM_COMANDO > ";
    }

    public void virgula() {
        System.out.printf("< 24 | VIRGULA> ");
        saida += "< 24 | VIRGULA > ";
    }

    public void entradaPadrao() {
        System.out.printf("< 25 | ENTRADA_PADRAO> ");
        saida += "< 25 | ENTRADA_PADRAO > ";
    }

    public void saidaPadrao() {
        System.out.printf("< 26 | SAIDA_PADRAO> ");
        saida += "< 26 | SAIDA_PADRAO > ";
    }

    public void comparacao() {
        System.out.printf("< 27 | COMPARACAO> ");
        saida += "< 27 | COMPARACAO > ";
    }

    public void senao() {
        System.out.printf("< 28 | SENAO> ");
        saida += "< 28 | SENAO > ";
    }

    public void retorno() {
        System.out.printf("< 29 | RETORNO_FUNCAO> ");
        saida += "< 29 | RETORNO_FUNCAO > ";
    }

    public void principal() {
        System.out.printf("< 30 | PRINCIPAL> ");
        saida += "< 30 | PRINCIPAL > ";
    }

    public void restoDivisao() {
        System.out.printf("< 31 | RESTO_DIVISAO> ");
        saida += "< 31 | RESTO_DIVISAO > ";
    }

    public void menor() {
        System.out.printf("< 32 | MENOR> ");
        saida += "< 32 | MENOR > ";
    }

    public void menorIgual() {
        System.out.printf("< 33 | MENOR_IGUAL> ");
        saida += "< 33 | MENOR_IGUAL > ";
    }

    public void maior() {
        System.out.printf("< 34 | MAIOR > ");
        saida += "< 34 | MAIOR > ";
    }

    public void maiorIgual() {
        System.out.printf("< 35 | MAIOR_IGUAL > ");
        saida += "< 35 | MAIOR_IGUAL > ";
    }

    public void erro() {
        System.out.println("< 36 | ERRO >");
        saida += "< 36 | ERRO >";
    }
}
