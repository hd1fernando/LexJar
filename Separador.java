package br.com.analizador;

import java.util.ArrayList;

public class Separador {
	ArrayList<String> lista = new ArrayList<>();
	ArrayList<String> listaAspas = new ArrayList<>();
	public boolean vazio = false; 

	// procura se há comentario
	public String procuraComentario(String linha) {
		int limitador = linha.length();
		String retorno = linha;
		// abaixo escaneia a palavra para procurar por comentario linha
		for (int i = 0; i < linha.length(); i++) {
			// procura se determinado caracter é uma barra e esse caracter não é
			// o ultimo da string
			if (linha.charAt(i) == '/' && i + 1 < linha.length()) {
				// procura se o proximo string tambem é uma barra
				if (linha.charAt(i + 1) == '/') {
					// há um comentario linha
					// ler string ate comentario e eviar para linha novamente
					limitador = i + 1;

				} else {
					// não há um comentario linha
				}
			}
		}
		// se o programa entende que fou encontrado comentarios, então executa o
		// codigo abaixo
		if (limitador < linha.length()) {
			retorno = "";
			// abaixo armazena a palavra na string retorno
			for (int i = 0; i <= limitador; i++) {
				retorno += linha.charAt(i);
			}
		}
		return retorno;
	}

	// metodo abaixo elimina espaço em branco no começo de string
	public String eliminaEspacoInicio(String linha) {
		int limitador = linha.length();
		if(limitador == 0){
			this.vazio = true;
			return linha;
		}
		String novaLinha = linha;
		int i = 0;
		while ((linha.charAt(i) == ' ' || linha.charAt(i) == '\t')) {
			i++;
			limitador = i;
			if (i == linha.length())
				break;

		}
		if (limitador < linha.length()) {
			novaLinha = "";
			for (int j = limitador; j < linha.length(); j++) {
				novaLinha += linha.charAt(j);
			}
		}
		return novaLinha;

	}

	// abaixo elimina espaço em branco no final da string
	public String eliminaEspacoFim(String linha) {
		if(linha.length() == 0){
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
			if (i < linha.length())
				break;
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
