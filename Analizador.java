package analisador_lexico;
//separar lendo o tamaho da palavra por espaço

import static java.lang.Character.isDigit;

public class Analizador {

    boolean comentarioFim = true;
    private final char FIM_LINHA = '\0';
    Tokens token = new Tokens();

    /**
     * Mostra o próximo caractere
     *
     * @param letra
     * @return
     */
    public char nextChar(String letra) {
        // verifica se palavra tem mais de um caracter e retorna o proximo
        char caracter = (letra.length() > 1) ? letra.charAt(1) : this.FIM_LINHA;
        return caracter;
    }

    /**
     * mostra a primeira letra
     *
     * @param letra
     * @return
     */
    public char firstChar(String letra) {
        if (letra.equals("")) {
            return this.FIM_LINHA;
        }
        return letra.charAt(0);
    }

    /**
     * Lê linha e procura por tokens
     *
     * @param linha
     */
    public void lerLinha(String linha) {
        // pega primeira letra
        char firstLetter = firstChar(linha);
        switch (firstLetter) {
            case '\0':
                break;
            case ' ':
                novaString(linha, 1);
                break;
            case '+':
                this.token.soma();
                novaString(linha, 1);
                break;
            case '-':
                this.token.subtracao();
                novaString(linha, 1);
                break;
            case '*':
                this.token.multiplicacao();
                novaString(linha, 1);
                break;
            case '^':
                this.token.potencia();
                novaString(linha, 1);
                break;
            case '{':
                this.token.inicioEscopo();
                novaString(linha, 1);
                break;
            case '}':
                this.token.fimEscopo();
                novaString(linha, 1);
                break;
            case '(':
                this.token.inicioParametro();
                novaString(linha, 1);
                break;
            case ')':
                this.token.fimParametro();
                novaString(linha, 1);
                break;
            case ';':
                this.token.fimComando();
                novaString(linha, 1);
                break;
            case ',':
                this.token.virgula();
                novaString(linha, 1);
                break;
            case '=':
                this.token.comparacao();
                novaString(linha, 1);
                break;
            case '%':
                this.token.restoDivisao();
                novaString(linha, 1);
                break;
            case '<':
                verificaMenor(linha);
                break;
            case '>':
                verificaMaior(linha);
                break;
            case '/':
                verificaBarra(linha);
                break;
            case 's':
                if (nextChar(linha) == 'e') {
                    // verifica se se ou se senao
                    verificaS(linha);
                }
                // não é se e nem senão
                break;
            case 'i':
                if (nextChar(linha) == 'n') {
                    verificaInt(linha);
                } else {
                    formadorIdentificador(linha);
                }
                break;
            case 'p':
                if (nextChar(linha) == 'a') {
                    verificaPara(linha);
                } else if (nextChar(linha) == 'r') {
                    verificaPrincipal(linha);
                } else {
                    formadorIdentificador(linha);
                }
                break;

            case 'e':
                if (nextChar(linha) == 'n') {
                    verificaEnquanto(linha);
                } else {
                    formadorIdentificador(linha);
                }
                break;

            case 'r':
                if (nextChar(linha) == 'e') {
                    verificaR(linha);
                } else {
                    formadorIdentificador(linha);
                }
                break;

            case 'c':
                if (nextChar(linha) == 'a') {
                    verificaCaracter(linha);
                } else {
                    formadorIdentificador(linha);
                }
                break;

            case 'v':
                if (nextChar(linha) == 'a') {
                    verificaVazio(linha);
                } else {
                    formadorIdentificador(linha);
                }
                break;

            case 't':
                if (nextChar(linha) == 'e') {
                    verificaT(linha);
                } else {
                    formadorIdentificador(linha);
                }
                break;

            case '\'':
                if (eNumero(nextChar(linha)) || eLetra(nextChar(linha))) {
                    verificaChar(linha);
                } else {
                    System.out.println("erro ao iniciar caractere");
                }
                break;

            case '"':
                verificaCadeiaCaracteres(linha);
                break;
            case '“':
                verificaCadeiaCaracteres(linha);
                break;
            default:
                if (eNumero(firstLetter)) {
                    formadorNumero(linha);
                } else if (eLetra(firstLetter)) {
                    formadorIdentificador(linha);
                } else {
                    this.token.erro();
                }
        }
    }

    /**
     * Verifica cadeia de caracteres
     *
     * @param linha
     */
    public void verificaCadeiaCaracteres(String linha) {
        String novaLinha = retornaNovaString(linha, 1);
        String novaPalavra = "";
        int i = 0;
        boolean achouAspas = false;
        while (i < novaLinha.length()) {
            if (novaLinha.charAt(i) == '"' || novaLinha.charAt(i) == '”') {
                achouAspas = true;
                this.token.cadeiaCaractere(novaPalavra);
                novaString(novaLinha, i + 1);
                break;
            } else {
                novaPalavra += novaLinha.charAt(i);
            }
            i++;
        }
        if (!achouAspas) {
            this.token.erro();
        }
    }

    /**
     * Verifica se é um caractere
     *
     * @param linha
     */
    public void verificaChar(String linha) {
        String novaLetra = retornaNovaString(linha, 2);
        char firstLetter = firstChar(novaLetra);
        switch (firstLetter) {
            case '\'':
                this.token.caractere(linha.charAt(1));
                novaString(linha, 3);
                break;
            default:
                this.token.erro();
        }
    }

    /**
     * Forma inteiro
     *
     * @param letra
     */
    public void formadorNumero(String letra) {
        int i = 0;
        int tevePonto = 0;
        boolean teveOperador = false;
        boolean teveEspaco = false;
        boolean erro = false;
        String novoNumero = "";

        while (i < letra.length()) {
            if (eNumero(letra.charAt(i))) {
                novoNumero += letra.charAt(i);
            } else if (letra.charAt(i) == '.') {
                novoNumero += letra.charAt(i);
                tevePonto++;
            } else if (letra.charAt(i) == ' ') {
                teveEspaco = true;
                if (tevePonto == 1) {
                    this.token.real(novoNumero);
                    novaString(letra, i);
                } else if (tevePonto > 1) {
                    // erro ao criar um numero, foi colocado mais de 2 pontos
                    // System.out.println("Mais de um ponto");
                    this.token.erro();
                } else {
                    this.token.inteiro(novoNumero);
                    novaString(letra, i);
                }

            } else if (eOperador(letra.charAt(i))) {
                teveOperador = true;
                if (tevePonto == 1) {
                    this.token.real(novoNumero);
                    novaString(letra, i);
                } else if (tevePonto > 1) {
                    // erro ao criar um numero, foi colocado mais de 2 pontos
                    this.token.erro();
                } else {
                    this.token.inteiro(novoNumero);
                    novaString(letra, i);
                }
            } else {
                this.token.erro();
                // "erro criar variavel iniciando por número
                erro = true;
            }
            i++;
        }
        if (!teveOperador && !teveEspaco && !erro) {
            if (tevePonto == 1) {
                this.token.real(novoNumero);
                novaString(letra, i);
            } else if (tevePonto > 1) {
                // erro ao criar um numero, foi colocado mais de 2 pontos
                this.token.erro();
            } else {
                this.token.inteiro(novoNumero);
                novaString(letra, i);
            }
        }
    }

    /**
     * Verifica Principal
     *
     * @param linha
     */
    public void verificaPrincipal(String linha) {
        // pr
        String novaLetra = retornaNovaString(linha, 2);
        char firstLetter = firstChar(novaLetra);
        switch (firstLetter) {
            case 'i':
                if (nextChar(novaLetra) == 'n') {
                    verificaPrincipal2(linha);
                } else {
                    formadorIdentificador(linha);
                }
                break;

            default:
                formadorIdentificador(linha);
        }
    }

    /**
     * Continua a verificar principal
     *
     * @param linha
     */
    private void verificaPrincipal2(String linha) {
        // prin
        String novaLetra = retornaNovaString(linha, 4);
        char firstLetter = firstChar(novaLetra);
        switch (firstLetter) {
            case 'c':
                if (nextChar(novaLetra) == 'i') {
                    verificaPrincipal3(linha);
                } else {
                    formadorIdentificador(linha);
                }
                break;

            default:
                formadorIdentificador(linha);
        }
    }

    /**
     * Continua a verificar principal
     *
     * @param linha
     */
    public void verificaPrincipal3(String linha) {
        // princi
        String novaLetra = retornaNovaString(linha, 6);
        char firstLetter = firstChar(novaLetra);
        switch (firstLetter) {
            case 'p':
                if (nextChar(novaLetra) == 'a') {
                    verificaPrincipal4(linha);
                } else {
                    formadorIdentificador(linha);
                }
                break;

            default:
                formadorIdentificador(linha);
        }
    }

    /**
     * Continua a verificar principal
     *
     * @param linha
     */
    public void verificaPrincipal4(String linha) {
        // principa
        String novaLetra = retornaNovaString(linha, 8);
        char firstLetter = firstChar(novaLetra);
        switch (firstLetter) {
            case 'l':
                if (nextChar(novaLetra) == ' ' || nextChar(novaLetra) == this.FIM_LINHA) {
                    this.token.principal();
                    novaString(linha, 9);
                }
                if (eOperador(nextChar(novaLetra))) {
                    this.token.principal();
                    novaString(linha, 9);
                }
                break;

            default:
                formadorIdentificador(linha);
        }
    }

    /**
     * Verifica t
     *
     * @param linha
     */
    public void verificaT(String linha) {
        // te
        String novaLetra = retornaNovaString(linha, 2);
        char firstLetter = firstChar(novaLetra);
        switch (firstLetter) {
            case 'c':
                if (nextChar(novaLetra) == 'l') {
                    verificaTeclado(linha);
                } else {
                    formadorIdentificador(linha);
                }
                break;

            case 'l':
                if (nextChar(novaLetra) == 'a') {
                    verificaTela(linha);
                } else {
                    formadorIdentificador(linha);
                }
                break;

            default:
                formadorIdentificador(linha);
        }
    }

    /**
     * Verifica se a palavra é tela
     *
     * @param linha
     */
    public void verificaTela(String linha) {
        // tela
        String novaLetra = retornaNovaString(linha, 4);
        char firstLetter = firstChar(novaLetra);
        switch (firstLetter) {
            case ' ':
                this.token.saidaPadrao();
                novaString(linha, 4);
                break;
            case '\0':
                this.token.saidaPadrao();
                break;
            default:
                // verifica se é atributo
                if (eOperador(firstLetter)) {
                    this.token.saidaPadrao();
                    novaString(linha, 4);
                } else {
                    formadorIdentificador(linha);
                }
        }
    }

    /**
     * Verifica se a palavra é teclado
     *
     * @param linha
     */
    public void verificaTeclado(String linha) {
        // tecl
        String novaLetra = retornaNovaString(linha, 4);
        char firstLetter = firstChar(novaLetra);
        switch (firstLetter) {
            case 'a':
                if (nextChar(novaLetra) == 'd') {
                    verificaTeclado2(linha);
                } else {
                    formadorIdentificador(linha);
                }
                break;

            default:
                formadorIdentificador(linha);
        }
    }

    /**
     * Verifica se a palavra é teclado
     *
     * @param linha
     */
    public void verificaTeclado2(String linha) {
        // teclad
        String novaLetra = retornaNovaString(linha, 6);
        char firstLetter = firstChar(novaLetra);
        switch (firstLetter) {
            case 'o':
                if (nextChar(novaLetra) == ' ' || nextChar(novaLetra) == this.FIM_LINHA) {
                    this.token.entradaPadrao();
                    novaString(linha, 7);
                }
                if (eOperador(nextChar(novaLetra))) {
                    this.token.entradaPadrao();
                    novaString(linha, 7);
                }
                break;

            default:
                formadorIdentificador(linha);
        }
    }

    /**
     * Verifica se a palavra é vazio
     *
     * @param linha
     */
    public void verificaVazio(String linha) {
        // va
        String novaLetra = retornaNovaString(linha, 2);
        char firstLetter = firstChar(novaLetra);
        switch (firstLetter) {
            case 'z':
                if (nextChar(novaLetra) == 'i') {
                    verificaVazio2(linha);
                } else {
                    formadorIdentificador(linha);
                }
                break;

            default:
                formadorIdentificador(linha);
        }
    }

    /**
     * Verifica se a palavra é vazio
     *
     * @param linha
     */
    public void verificaVazio2(String linha) {
        // vazi
        String novaLetra = retornaNovaString(linha, 4);
        char firstLetter = firstChar(novaLetra);
        switch (firstLetter) {
            case 'o':
                if (nextChar(novaLetra) == ' ' || nextChar(novaLetra) == this.FIM_LINHA) {
                    this.token.tipo("vazio");
                    novaString(linha, 5);
                }
                if (eOperador(nextChar(novaLetra))) {
                    this.token.tipo("vazio");
                    novaString(linha, 5);
                }
                break;

            default:
                formadorIdentificador(linha);
        }
    }

    /**
     * Verifica se é um caraterer
     *
     * @param linha
     */
    public void verificaCaracter(String linha) {
        String novaLetra = retornaNovaString(linha, 2);
        char firstLetter = firstChar(novaLetra);
        switch (firstLetter) {
            case 'r':
                if (nextChar(novaLetra) == 'a') {
                    verificaCaracter2(linha);
                } else {
                    formadorIdentificador(linha);
                }
                break;

            default:
                formadorIdentificador(linha);
        }
    }

    /**
     * Verifica se é um caraterer
     *
     * @param linha
     */
    public void verificaCaracter2(String linha) {
        // cara
        String novaLetra = retornaNovaString(linha, 4);
        char firstLetter = firstChar(novaLetra);
        switch (firstLetter) {
            case 'c':
                if (nextChar(novaLetra) == 't') {
                    verificaCaracter3(linha);
                } else {
                    formadorIdentificador(linha);
                }
                break;

            default:
                formadorIdentificador(linha);
        }
    }

    /**
     * Verifica se é um caraterer
     *
     * @param linha
     */
    public void verificaCaracter3(String linha) {
        // caract
        String novaLetra = retornaNovaString(linha, 6);
        char firstLetter = firstChar(novaLetra);
        switch (firstLetter) {
            case 'e':
                if (nextChar(novaLetra) == 'r') {
                    verificaCaracter4(linha);
                } else {
                    formadorIdentificador(linha);
                }
                break;

            default:
                formadorIdentificador(linha);
                break;
        }
    }

    /**
     * Verifica se é um caraterer
     *
     * @param linha
     */
    public void verificaCaracter4(String linha) {
        // caracter
        String novaLetra = retornaNovaString(linha, 8);
        char firstLetter = firstChar(novaLetra);
        switch (firstLetter) {
            case ' ':
                this.token.tipo("caracter");
                novaString(linha, 8);
                break;
            case '\0':
                this.token.tipo("caracter");
                break;
            default:
                // verifica se é atributo
                if (eOperador(firstLetter)) {
                    this.token.tipo("caracter");
                    novaString(linha, 8);
                } else {
                    formadorIdentificador(linha);
                }
        }
    }

    /**
     * Verifica se é r
     *
     * @param linha
     */
    public void verificaR(String linha) {
        String novaLetra = retornaNovaString(linha, 2);
        char firstLetter = firstChar(novaLetra);
        switch (firstLetter) {
            case 'a':
                if (nextChar(novaLetra) == 'l') {
                    verificaReal(linha);
                } else {
                    formadorIdentificador(linha);
                }
                break;

            case 't':
                if (nextChar(novaLetra) == 'o') {
                    vefificaRetornar(linha);
                } else {
                    formadorIdentificador(linha);
                }
                break;

            default:
                formadorIdentificador(linha);
        }
    }

    // abaixo verifica retornar
    /**
     * Veririfica se a palavra é retornar
     *
     * @param linha
     */
    public void vefificaRetornar(String linha) {
        // reto
        String novaLetra = retornaNovaString(linha, 4);
        char firstLetter = firstChar(novaLetra);
        switch (firstLetter) {
            case 'r':
                if (nextChar(novaLetra) == 'n') {
                    vefificaRetornar2(linha);
                } else {
                    formadorIdentificador(linha);
                }
                break;

            default:
                formadorIdentificador(linha);
        }
    }

    public void vefificaRetornar2(String linha) {
        // retorn
        String novaLetra = retornaNovaString(linha, 6);
        char firstLetter = firstChar(novaLetra);
        switch (firstLetter) {
            case 'a':
                if (nextChar(novaLetra) == 'r') {
                    vefificaRetornar3(linha);
                } else {
                    formadorIdentificador(linha);
                }
                break;

            default:
                formadorIdentificador(linha);
        }
    }

    public void vefificaRetornar3(String linha) {
        // retornar
        String novaLetra = retornaNovaString(linha, 8);
        char firstLetter = firstChar(novaLetra);
        switch (firstLetter) {
            case ' ':
                this.token.retorno();
                novaString(linha, 8);
                break;
            case '\0':
                this.token.retorno();
                break;
            default:
                // verifica se é atributo
                if (eOperador(firstLetter)) {
                    this.token.retorno();
                    novaString(linha, 8);
                } else {
                    formadorIdentificador(linha);
                }
        }
    }

    /**
     * Verifica se a palavra é real
     *
     * @param linha
     */
    public void verificaReal(String linha) {
        String novaLetra = retornaNovaString(linha, 4);
        char firstLetter = firstChar(novaLetra);
        switch (firstLetter) {
            case ' ':
                this.token.tipo("real");
                novaString(linha, 4);
                break;
            case '\0':
                this.token.tipo("real");
                break;
            default:
                // verifica se é atributo
                if (eOperador(firstLetter)) {
                    this.token.tipo("real");
                    novaString(linha, 4);
                } else {
                    formadorIdentificador(linha);
                }
        }
    }

    /**
     * Verifica se a palavra é equanto
     *
     * @param linha
     */
    public void verificaEnquanto(String linha) {
        String novaLetra = retornaNovaString(linha, 2);
        char firstLetter = firstChar(novaLetra);
        switch (firstLetter) {
            case 'q':
                if (nextChar(novaLetra) == 'u') {
                    verificaEnquanto2(linha);
                } else {
                    formadorIdentificador(linha);
                }
                break;

            default:
                formadorIdentificador(linha);
        }
    }

    public void verificaEnquanto2(String linha) {
        String novaLetra = retornaNovaString(linha, 4);
        char firstLetter = firstChar(novaLetra);
        switch (firstLetter) {
            case 'a':
                if (nextChar(novaLetra) == 'n') {
                    verificaEnquanto3(linha);
                } else {
                    formadorIdentificador(linha);
                }
                break;

            default:
                formadorIdentificador(linha);
        }
    }

    public void verificaEnquanto3(String linha) {
        // enquan
        String novaLetra = retornaNovaString(linha, 6);
        char firstLetter = firstChar(novaLetra);
        switch (firstLetter) {
            case 't':
                if (nextChar(novaLetra) == 'o') {
                    verificaEnquanto4(linha);
                } else {
                    formadorIdentificador(linha);
                }
                break;

            default:
                formadorIdentificador(linha);
        }
    }

    public void verificaEnquanto4(String linha) {
        // enquanto
        String novaLetra = retornaNovaString(linha, 8);
        char firstLetter = firstChar(novaLetra);
        switch (firstLetter) {
            case ' ':
                this.token.enquanto();
                novaString(linha, 8);
                break;
            case '\0':
                this.token.enquanto();
                break;
            default:
                // verifica se é atriuto
                if (eOperador(firstLetter)) {
                    this.token.enquanto();
                    novaString(linha, 8);
                } else {
                    formadorIdentificador(linha);
                }
        }
    }

    public void verificaPara(String linha) {
        String novaLetra = retornaNovaString(linha, 2);
        char firstLetter = firstChar(novaLetra);
        switch (firstLetter) {
            case 'r':
                if (nextChar(novaLetra) == 'a') {
                    verificaPara2(linha);
                } else {
                    formadorIdentificador(linha);
                }
                break;

            default:
                formadorIdentificador(linha);
            // tenta ver se é um operador
        }

    }

    public void verificaPara2(String linha) {
        String novaLetra = retornaNovaString(linha, 4);
        char firstLetter = firstChar(novaLetra);
        switch (firstLetter) {
            case ' ':
                this.token.para();
                novaString(linha, 4);
                break;
            case '\0':
                this.token.para();
                break;
            default:
                // verifica se é atributo
                if (eOperador(firstLetter)) {
                    this.token.para();
                    novaString(linha, 4);
                } else {
                    formadorIdentificador(linha);
                }
        }
    }

    /**
     * Verifica se a palavra é int
     *
     * @param linha
     */
    public void verificaInt(String linha) {
        String novaLetra = retornaNovaString(linha, 2);
        char firstLetter = firstChar(novaLetra);
        switch (firstLetter) {
            case 't':
                if (nextChar(novaLetra) == ' ' || nextChar(novaLetra) == this.FIM_LINHA) {
                    this.token.tipo("int");
                    novaString(linha, 3);
                }
                if (eOperador(nextChar(novaLetra))) {
                    this.token.tipo("int");
                    novaString(linha, 3);
                }
                break;

            default:
                formadorIdentificador(linha);
        }
    }

    /**
     * Tenta verificar se determinada palavra forma uma variável
     *
     * @param novaLetra
     */
    public void formadorIdentificador(String novaLetra) {
        String novaVariavel = "";
        int i = 0;
        boolean teveVariavel = false;
        boolean teveOperador = false;
        boolean teveEspaco = false;
        while (i < novaLetra.length()) {
            if (eLetra(novaLetra.charAt(i)) || eNumero(novaLetra.charAt(i)) || eChValido(novaLetra.charAt(i))) {
                novaVariavel += novaLetra.charAt(i);
                teveVariavel = true;
            } else if (eOperador(novaLetra.charAt(i)) && teveVariavel) {
                this.token.identificador(novaVariavel);
                novaString(novaLetra, i);
                teveOperador = true;
                break;
            } else if (eOperador(novaLetra.charAt(i)) && !teveVariavel) {
                novaString(novaLetra, i);
            } else if (novaLetra.charAt(i) == ' ') {
                this.token.identificador(novaVariavel);
                novaString(novaLetra, i);
                teveEspaco = true;
            } else {
                this.token.erro();
            }
            i++;
        }
        if (!teveOperador && !teveEspaco) {
            this.token.identificador(novaVariavel);
        }
    }

    /**
     * Verifica se a lertra é s
     *
     * @param linha
     */
    public void verificaS(String linha) {
        String novaLetra = retornaNovaString(linha, 2);
        char firstLetter = firstChar(novaLetra);

        switch (firstLetter) {
            case ' ':
                this.token.se();
                novaString(linha, 3);
                break;
            case '\0':
                this.token.se();
                // novaString(linha, 3);
                break;
            case 'n':
                // System.out.println(nextChar(linha));
                if (nextChar(novaLetra) == 'a') {
                    verificaSenao(linha, 4);
                } else {
                    formadorIdentificador(linha);
                }
                break;

            default:
                // verifica se é atributo
                if (eOperador(firstLetter)) {
                    this.token.se();
                    novaString(linha, 2);
                } else {
                    formadorIdentificador(linha);
                }
        }
    }

    /**
     * Verifica se o caractére é um operador
     *
     * @param letra
     * @return
     */
    public boolean eOperador(char letra) {
        switch (letra) {
            case '+':
            case '*':
            case '^':
            case '{':
            case '}':
            case '(':
            case ')':
            case ';':
            case ',':
            case '=':
            case '%':
            case '<':
            case '>':
            case '/':
            case '-':
                return true;
            default:
                return false;

        }
    }

    /**
     * Verifica se é uma letra do alfabeto
     *
     * @param letra
     * @return
     */
    public boolean eLetra(char letra) {
        switch (letra) {
            case 'a':
            case 'b':
            case 'c':
            case 'd':
            case 'e':
            case 'f':
            case 'g':
            case 'h':
            case 'i':
            case 'j':
            case 'k':
            case 'l':
            case 'm':
            case 'n':
            case 'o':
            case 'p':
            case 'q':
            case 'r':
            case 's':
            case 't':
            case 'u':
            case 'v':
            case 'x':
            case 'w':
            case 'y':
            case 'z':
            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case 'E':
            case 'F':
            case 'G':
            case 'H':
            case 'I':
            case 'J':
            case 'K':
            case 'L':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'S':
            case 'T':
            case 'U':
            case 'V':
            case 'X':
            case 'W':
            case 'Y':
            case 'Z':
                return true;
            default:
                return false;
        }
    }

    /**
     * Verifica se é número
     *
     * @param letra
     * @return
     */
    public boolean eNumero(char letra) {
        return isDigit(letra);
    }

    /**
     * Veririfica se é um caractere válido
     *
     * @param letra
     * @return
     */
    public boolean eChValido(char letra) {
        switch (letra) {
            case '_':
            case '$':
                return true;
            default:
                return false;
        }
    }

    /**
     * Verifica se a palavra é um: senao
     *
     * @param linha
     * @param posicao
     */
    public void verificaSenao(String linha, int posicao) {
        String novaLetra = retornaNovaString(linha, 4);
        char firstLetter = firstChar(novaLetra);
        switch (firstLetter) {
            case 'o':
                if (nextChar(novaLetra) == ' ' || nextChar(novaLetra) == this.FIM_LINHA) {
                    this.token.senao();
                    novaString(linha, 5);
                }
                if (eOperador(nextChar(novaLetra))) {
                    this.token.senao();
                    novaString(linha, 5);
                }
                break;

            default:
                // verifica se é atributo
                if (eOperador(firstLetter)) {
                    this.token.senao();
                    novaString(linha, 4);
                } else {
                    formadorIdentificador(linha);
                }
                break;

        }
    }

    /**
     * Retorna uma nova string aparti da posição passada com argumento
     *
     * @param linha
     * @param posicao
     * @return
     */
    public String retornaNovaString(String linha, int posicao) {
        int i = posicao;
        String novaPalavra = "";
        if (linha != "") {
            for (i = posicao; i < linha.length(); i++) {
                novaPalavra += linha.charAt(i);
            }
        }
        return novaPalavra;
    }

    /**
     * Verifica o que vem depois do sinal de menor
     *
     * @param linha
     */
    public void verificaMenor(String linha) {
        if (nextChar(linha) == '-') {
            this.token.atribuicao();
            novaString(linha, 2);
        } else if (nextChar(linha) == '=') {
            this.token.menorIgual();
            novaString(linha, 2);
        } else if (nextChar(linha) == ' ' || nextChar(linha) == this.FIM_LINHA) {
            this.token.menor();
            novaString(linha, 2);
        } else {
            this.token.menor();
            novaString(linha, 1);
        }
    }

    /**
     * Verifica o que vem depois do sinal de maior
     *
     * @param linha
     */
    public void verificaMaior(String linha) {
        if (nextChar(linha) == '=') {
            this.token.maiorIgual();
            novaString(linha, 2);
        } else if (nextChar(linha) == ' ' || nextChar(linha) == this.FIM_LINHA) {
            this.token.maior();
            novaString(linha, 2);
        } else {
            this.token.maior();
            novaString(linha, 1);
        }
    }

    /**
     * Verifica o que vem depois do sinal de barra
     *
     * @param linha
     */
    public void verificaBarra(String linha) {
        if (nextChar(linha) == '/') {
            this.token.comentarioLinha();
            novaString(linha, 2);
        } else if (nextChar(linha) == ' ' || nextChar(linha) == this.FIM_LINHA) {
            this.token.divisao();
            novaString(linha, 2);
        } else if (nextChar(linha) == '*') {
            // indica que teve inicio um comentário parágrafo. Irá procurar
            // seu fim
            this.token.inicioComentarioParagrafo();
            verificaComentarioParagrafo(linha);
        } else {
            this.token.divisao();
            novaString(linha, 1);
        }
    }

    /**
     * Verifica comentário parágrafo
     *
     * @param linha
     */
    public void verificaComentarioParagrafo(String linha) {
        String novaString;
        if (this.comentarioFim) {
            novaString = retornaNovaString(linha, 2);
        } else {
            novaString = linha;
        }
        int i = 0;
        boolean encontrouFim = false;
        while (i < novaString.length()) {
            if (novaString.charAt(i) == '*') {
                // verifica se ainda da para ler proximo caractere
                if (i + 1 < novaString.length()) {
                    // verifica se proximo caractere é uma: /
                    switch (novaString.charAt(i + 1)) {
                        case '/':
                            this.token.fimComentarioParagrafo();
                            encontrouFim = true;
                            this.comentarioFim = true;
                            break;
                        default:
                    }
                }
            }
            if (encontrouFim) {
                break;
            }
            i++;
        }

        if (encontrouFim && i + 2 < novaString.length()) {
            novaString(novaString, i + 2);
            this.comentarioFim = true;
        }
        if (!encontrouFim) {
            this.comentarioFim = false;
        }

    }

    /**
     * Pega resto da string que não foi ainda identificada como token e cria
     * nova string
     *
     * @param linha
     * @param posicao
     */
    public void novaString(String linha, int posicao) {
        String novaPalavra = "";
        if (!linha.equals("")) {
            for (int i = posicao; i < linha.length(); i++) {
                novaPalavra += linha.charAt(i);
            }
        }
        lerLinha(novaPalavra);
    }

}
