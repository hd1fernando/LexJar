/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisador_lexico;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author fernando
 */
public class Teste {

    public static void main(String[] args) throws IOException {
        String origem = "src/analisador_lexico/arquivos/olaMundo.cpt";
        String destino = "src/analisador_lexico/arquivos/saida.ctk";
        Arquivo arquivo = new Arquivo(origem, destino);
        ArrayList<String> arr = new ArrayList<>();
        arr = arquivo.LerArquivo();
        for(String linha : arr){
            System.out.println(linha);
        }
    }
}
