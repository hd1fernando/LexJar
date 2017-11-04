package br.com.analizador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Inicio {
	public static void main(String[] args) throws IOException {
		String  origem = "/home/fernando/analizador/olaMundo.cpt";
		String destino = "/home/fernando/analizador/saida.ctk";
		Tokens tokens = new Tokens();
		
		// porcura por arquivo no local: ex: /home/usuario/analizador/olamundo.cpt
		File arquivo = new File(origem);
		if (!arquivo.exists()) { // procura se aquivo existe
			System.out.println("Arquivo não encontrado");
		} else {// se sim, começa a lê-lo
			FileReader fr = new FileReader(arquivo);
			BufferedReader br = new BufferedReader(fr);
			// lê a primeira linha
			String linha = br.readLine();
			// int numLinha = 1;
			Separador separa = new Separador();
			Analizador analizador = new Analizador();

			// lê enquanto houver linhas para serem lidas
			while (linha != null) {

				// prucora se não houve comenario paragrafo na linha anterior
				if (analizador.comentarioFim) {
					// nao houve comentario linha na linha anterior, eu esse é a
					// primeira linha ou ele ja foi fechado

					// procura se há comentario na linha e o elimina
					linha = separa.procuraComentario(linha);

					// procura se há espaço no inicio da linha e o elimina
					linha = separa.eliminaEspacoInicio(linha);

					// procura se há espaço no fim da linha e o elimina
					if (!separa.vazio)
						linha = separa.eliminaEspacoFim(linha);

					// caso a unica linha do programa seja um tab ou espaço nao
					// ira executar
					if (!separa.vazio) {
						if (linha.charAt(0) != ' ' && linha.charAt(0) != '\t')
							analizador.lerLinha(linha);
					}
				} else {
					analizador.verificaComentarioParagrafo(linha);
				}

				// quebra uma linha no aquivo de tokens
				if (!separa.vazio) {
					if (linha.charAt(0) != ' ' && linha.charAt(0) != '\t')
						System.out.println(" ");
					tokens.saida += "\n";
				}
				// adiciona false no vazio novamente
				separa.vazio = false;

				// le proxima linha
				linha = br.readLine();
				// numLinha++;
			}
			// fecha arquivo
			br.close();
			fr.close();

			// abaixo escreve em um arquivo de saida ex: /home/usuario/analizador/saida.ctk
			File saida = new File(destino);
			if (!saida.exists()) {
				saida.createNewFile();
			}
			FileWriter fw = new FileWriter(saida, false);
			BufferedWriter bw = new BufferedWriter(fw);
			// escreve no arquivo
			// System.out.println(tokens.saida);
			bw.write(tokens.saida);
			// bw.newLine();
			// fecha os cursores
			bw.close();
			fw.close();

		}
	}

}
