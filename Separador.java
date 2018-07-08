package analisador_lexico;

public class Separador {

    public boolean vazio = false;

    /**
     * procura se há comentario do tipo que ocupa apenas uma linha. Recebe como
     * parametro uma linha
     *
     * @param linha
     * @return
     */
    public String procuraComentario(String linha) {

        // analiza o tamanho em caracteres da linha
        int limitador = linha.length();

        // recebe o valor da linha
        String retorno = linha;

        // abaixo escaneia a palavra para procurar por comentario linha
        for (int i = 0; i < linha.length(); i++) {
            // procura se determinado caracter é uma barra e esse caracter não é
            // o último da string
            if (linha.charAt(i) == '/' && i + 1 < linha.length()) {
                // procura se o proximo string tambem é uma barra
                if (linha.charAt(i + 1) == '/') {
                    // há um comentário linha
                    // ler string até comentario e evia para linha novamente
                    limitador = i + 1;

                }
            }
        }

        // se o programa entende que foi encontrado comentários, então, executa o
        // código abaixo.
        // explicação: encontrar um comentário do tipo // em uma linha implica que
        // tudo que houver depois de será comentário e logo, não deverá ser analizado
        // pelo analizador léxico
        if (limitador < linha.length()) {
            retorno = "";
            // abaixo armazena a palavra na string retorno, ou seja, palavras
            // que estavam antes do cometário, se houver
            // isso indica que elas são codigos que devem ser passados a frente
            // continuando sendo analizados pelo analizador lexico
            for (int i = 0; i <= limitador; i++) {
                retorno += linha.charAt(i);
            }
        }
        return retorno;
    }

    /**
     * Elimina espaço em branco no começo de string
     *
     * @param linha
     * @return
     */
    public String eliminaEspacoInicio(String linha) {

        int limitador = linha.length();
        if (limitador == 0) {
            this.vazio = true;
            return linha;
        }
        String novaLinha = linha;
        int i = 0;
        while ((linha.charAt(i) == ' ' || linha.charAt(i) == '\t')) {
            i++;
            limitador = i;
            if (i == linha.length()) {
                break;
            }

        }
        if (limitador < linha.length()) {
            novaLinha = "";
            for (int j = limitador; j < linha.length(); j++) {
                novaLinha += linha.charAt(j);
            }
        }
        return novaLinha;
    }

    /**
     * Elimina espaço em branco no final da string
     *
     * @param linha
     * @return
     */
    public String eliminaEspacoFim(String linha) {
        if (linha.length() == 0) {
            this.vazio = true;
            return linha;
        }
        String novaLinha = linha;
        int i = linha.length() - 1;
        int limitador = -1;
        // abaixo verifica se o final da linha é um espaço para elimina-lo
        while ((linha.charAt(i) == ' ' || linha.charAt(i) == '\t') && i < linha.length()) {
            i--;
            limitador = i;
            if (i < linha.length()) {
                break;
            }
        }
        if (limitador != -1) {
            novaLinha = "";
            for (int j = 0; j <= limitador; j++) {
                novaLinha += linha.charAt(j);
            }
        }
        return novaLinha;
    }

}
