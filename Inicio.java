package analisador_lexico;

import java.io.IOException;
import java.util.ArrayList;

public class Inicio {

    public static void main(String[] args) {
        String origem = "src/analisador_lexico/arquivos/olaMundo.cpt";
        String destino = "src/analisador_lexico/arquivos/saida.ctk";

        Tokens tokens = new Tokens();
        Separador separador = new Separador();
        Analizador analizador = new Analizador();
        Arquivo arquivo = new Arquivo(origem, destino);
        ArrayList<String> texto = new ArrayList<>();

        try {
            texto = arquivo.LerArquivo();

            // lê enquanto houver linhas para serem lidas
            for (String linha : texto) {
                linha = linha.trim();
                // prucura se não houve comenario paragrafo na linha anterior
                if (analizador.comentarioFim) {
                    // nao houve comentário linha na linha anterior, ou essa é a
                    // primeira linha, ou o comentário já foi fechado

                    // procura se há comentário na linha e o elimina
                    linha = separador.procuraComentario(linha);

                    // procura se há espaço no inicio da linha e o elimina
                    //linha = separador.eliminaEspacoInicio(linha);
                    // procura se há espaço no fim da linha e o elimina
                    if (!separador.vazio) {
                        linha = separador.eliminaEspacoFim(linha);
                    }

                    // caso a unica linha do programa seja um tab ou espaço nao
                    // irá executar
                    if (!separador.vazio) {
                        if (linha.charAt(0) != ' ' && linha.charAt(0) != '\t') {
                            analizador.lerLinha(linha);
                        }
                    }
                } else {
                    analizador.verificaComentarioParagrafo(linha);
                }

                // quebra uma linha no aquivo de tokens
                if (!separador.vazio) {
                    if (linha.charAt(0) != ' ' && linha.charAt(0) != '\t') {
                        System.out.println(" ");
                    }
                    tokens.saida += "\n";
                }
                // adiciona false no vazio novamente
                separador.vazio = false;

            }

            // abaixo  escreve em um arquivo de saida ex: /home/usuario/analizador/saida.ctk
            arquivo.EscreverArquivo();

        } catch (IOException ex) {
            System.err.println(ex.getCause());
            System.err.println(ex.getMessage());
        }
    }

}
