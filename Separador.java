package br.com.analizador;

public class Separador {

	public boolean vazio = false;

	// procura se há comentario do tipo que ocupa apenas uma linha
	public String procuraComentario(String linha) {
		// método recebe como parametro uma linha

		int limitador = linha.length(); // analiza o tamanho em caracteres da
										// linha
		String retorno = linha; // recebe o valor da linha

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

				}
			}
		}
		// se o programa entende que foi encontrado comentarios, então executa o
		// codigo abaixo.

		// explicação: encontrar um comentário do tip // em uma linha iplica que
		// tudo que houver depois de será comentário de não deverá ser analidado
		// pelo analizador léxico

		if (limitador < linha.length()) {
			retorno = "";
			// abaixo armazena a palavra na string retorno, ou seja, palavras
			// que estavam antes do cometário, se houver
			// isso indica que elas são codigos q devem ser passados a frente
			// continuando sendo analizados pelo analizador lexico
			for (int i = 0; i <= limitador; i++) {
				retorno += linha.charAt(i);
			}
		}
		return retorno;
	}
	
	/*** 24/11/2017 ***/

	// metodo abaixo elimina espaço em branco no começo de string
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
