package analisador_lexico;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author fernando
 */
public class Arquivo extends Tokens {

    private String origem;
    private String destino;

    public Arquivo(String origem, String destino) {
        this.origem = origem;
        this.destino = destino;
    }

    /**
     * Lê um arquivo e retorna cada linha dele como uma posição em um ArrayList
     * @return
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public ArrayList<String> LerArquivo() throws FileNotFoundException, IOException {
        ArrayList<String> texto = new ArrayList<>();
        // porcura por arquivo no local: ex: /home/usuario/analizador/olamundo.cpt
        File arquivo = new File(this.origem);
        if (!arquivo.exists()) { // procura se aquivo existe
            System.out.println("Arquivo não encontrado");
        } else {
            FileReader fr = new FileReader(arquivo);
            BufferedReader br = new BufferedReader(fr);
            // lê a primeira linha
            String linha = br.readLine();

            // lê enquanto houver linhas para serem lidas
            while (linha != null) {
                texto.add(linha);
                // le proxima linha
                linha = br.readLine();
            }
            // fecha arquivo
            br.close();
            fr.close();
        }
        return texto;
    }

    /**
     * escreve em um arquivo de saida ex: /home/usuario/analizador/saida.ctk
     *
     * @throws IOException
     */
    public void EscreverArquivo() throws IOException {
        File saida = new File(this.destino);
        if (!saida.exists()) {
            saida.createNewFile();
        }
        FileWriter fw = new FileWriter(saida, false);
        BufferedWriter bw = new BufferedWriter(fw);
        // escreve no arquivo
        // System.out.println(tokens.saida);
        bw.write(Tokens.saida);
        // bw.newLine();
        // fecha os cursores
        bw.close();
        fw.close();
    }
}
