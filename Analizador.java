package br.com.analizador;
//separar lendo o tamaho da palavra por espaço

public class Analizador {
	boolean comentarioFim = true;
	Tokens token = new Tokens();

	// metodo abaixo mostra o proximo caractere
	public char nextChar(String letra) {
		// verifica se palavra tem mais de um caracter e retorna o proximo
		char caracter = (letra.length() > 1) ? letra.charAt(1) : '¬';
		return caracter;
	}

	// mostra a primeira letra
	public char firstChar(String letra) {
		if (letra == "")
			return '¬';
		else
			return letra.charAt(0);
	}

	// abaixo ler linha e procura por tokens
	public void lerLinha(String linha) {
		// pega primeira letra
		char firstLetter = firstChar(linha);
		switch (firstLetter) {
		case '¬':
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
				break;
			} else {
				// não é se e nem senão
				break;
			}
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
				// System.out.println("erro lerlinha()");
			}
		}
	}

	// abaixo verifica cadeia de caracteres
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
		if (!achouAspas)
			this.token.erro();
		// System.out.println("erro ao criar string");
	}

	// abaixo verifica se é um caractere
	public void verificaChar(String linha) {
		// 'x
		String novaLetra = retornaNovaString(linha, 2);
		char firstLetter = firstChar(novaLetra);
		switch (firstLetter) {
		case '\'':
			this.token.caractere(linha.charAt(1));
			novaString(linha, 3);
			break;
		default:
			this.token.erro();
			// System.out.println("Erro no verificaChar()");
		}
	}

	// abaixo forma um inteiro
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
					break;
				} else if (tevePonto > 1) {
					// erro ao criar um numero, foi colocado mais de 2 pontos
					// System.out.println("Mais de um ponto");
					this.token.erro();
					break;
				} else {
					this.token.inteiro(novoNumero);
					novaString(letra, i);
					break;
				}

			} else if (eOperador(letra.charAt(i))) {
				teveOperador = true;
				if (tevePonto == 1) {
					this.token.real(novoNumero);
					novaString(letra, i);
					break;
				} else if (tevePonto > 1) {
					// erro ao criar um numero, foi colocado mais de 2 pontos
					// System.out.println("Mais de um ponto");
					this.token.erro();
					break;
				} else {
					this.token.inteiro(novoNumero);
					novaString(letra, i);
					break;
				}
			} else {
				this.token.erro();
				// System.out.println("erro criar variavel iniciando por
				// numero");
				erro = true;
				break;
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
				// System.out.println("Mais de um ponto");
			} else {
				this.token.inteiro(novoNumero);
				novaString(letra, i);
			}
		}
	}

	// abaixo verifica principal
	public void verificaPrincipal(String linha) {
		// pr
		String novaLetra = retornaNovaString(linha, 2);
		char firstLetter = firstChar(novaLetra);
		switch (firstLetter) {
		case 'i':
			if (nextChar(novaLetra) == 'n') {
				verificaPrincipal2(linha);
				break;
			} else {
				formadorIdentificador(linha);
				break;
			}
		default:
			formadorIdentificador(linha);
			break;
		}
	}

	// abaixo verifica principal
	public void verificaPrincipal2(String linha) {
		// prin
		String novaLetra = retornaNovaString(linha, 4);
		char firstLetter = firstChar(novaLetra);
		switch (firstLetter) {
		case 'c':
			if (nextChar(novaLetra) == 'i') {
				verificaPrincipal3(linha);
				break;
			} else {
				formadorIdentificador(linha);
				break;
			}
		default:
			formadorIdentificador(linha);
			break;
		}
	}

	// abaixo verifica principal
	public void verificaPrincipal3(String linha) {
		// princi
		String novaLetra = retornaNovaString(linha, 6);
		char firstLetter = firstChar(novaLetra);
		switch (firstLetter) {
		case 'p':
			if (nextChar(novaLetra) == 'a') {
				verificaPrincipal4(linha);
				break;
			} else {
				formadorIdentificador(linha);
				break;
			}
		default:
			formadorIdentificador(linha);
			break;
		}
	}

	// abaixo verifica principal
	public void verificaPrincipal4(String linha) {
		// principa
		String novaLetra = retornaNovaString(linha, 8);
		char firstLetter = firstChar(novaLetra);
		switch (firstLetter) {
		case 'l':
			if (nextChar(novaLetra) == ' ' || nextChar(novaLetra) == '¬') {
				this.token.principal();
				novaString(linha, 9);
				break;
			}
			if (eOperador(nextChar(novaLetra))) {
				this.token.principal();
				novaString(linha, 9);
				break;
			}
		default:
			formadorIdentificador(linha);
			break;
		}
	}

	// abaixo verifica t
	public void verificaT(String linha) {
		// te
		String novaLetra = retornaNovaString(linha, 2);
		char firstLetter = firstChar(novaLetra);
		switch (firstLetter) {
		case 'c':
			if (nextChar(novaLetra) == 'l') {
				verificaTeclado(linha);
				break;
			} else {
				formadorIdentificador(linha);
				break;
			}
		case 'l':
			if (nextChar(novaLetra) == 'a') {
				verificaTela(linha);
				break;
			} else {
				formadorIdentificador(linha);
				break;
			}
		default:
			formadorIdentificador(linha);
			break;
		}
	}

	// abaixo verifica tela
	public void verificaTela(String linha) {
		// tela
		String novaLetra = retornaNovaString(linha, 4);
		char firstLetter = firstChar(novaLetra);
		switch (firstLetter) {
		case ' ':
			this.token.saidaPadrao();
			novaString(linha, 4);
			break;
		case '¬':
			this.token.saidaPadrao();
			break;
		default:
			// verifica se é atributo
			if (eOperador(firstLetter)) {
				this.token.saidaPadrao();
				novaString(linha, 4);
			} else {
				formadorIdentificador(linha);
				break;
			}
		}
	}

	// abaixo verifica teclado
	public void verificaTeclado(String linha) {
		// tecl
		String novaLetra = retornaNovaString(linha, 4);
		char firstLetter = firstChar(novaLetra);
		switch (firstLetter) {
		case 'a':
			if (nextChar(novaLetra) == 'd') {
				verificaTeclado2(linha);
				break;
			} else {
				formadorIdentificador(linha);
				break;
			}
		default:
			formadorIdentificador(linha);
			break;
		}
	}

	// abaixo verifica teclado
	public void verificaTeclado2(String linha) {
		// teclad
		String novaLetra = retornaNovaString(linha, 6);
		char firstLetter = firstChar(novaLetra);
		switch (firstLetter) {
		case 'o':
			if (nextChar(novaLetra) == ' ' || nextChar(novaLetra) == '¬') {
				this.token.entradaPadrao();
				novaString(linha, 7);
				break;
			}
			if (eOperador(nextChar(novaLetra))) {
				this.token.entradaPadrao();
				novaString(linha, 7);
				break;
			}
		default:
			formadorIdentificador(linha);
			break;
		}
	}

	// abaixo verifica vazio
	public void verificaVazio(String linha) {
		// va
		String novaLetra = retornaNovaString(linha, 2);
		char firstLetter = firstChar(novaLetra);
		switch (firstLetter) {
		case 'z':
			if (nextChar(novaLetra) == 'i') {
				verificaVazio2(linha);
				break;
			} else {
				formadorIdentificador(linha);
				break;
			}
		default:
			formadorIdentificador(linha);
			break;
		}
	}

	// abaixo verifica vazio
	public void verificaVazio2(String linha) {
		// vazi
		String novaLetra = retornaNovaString(linha, 4);
		char firstLetter = firstChar(novaLetra);
		switch (firstLetter) {
		case 'o':
			if (nextChar(novaLetra) == ' ' || nextChar(novaLetra) == '¬') {
				this.token.tipo("vazio");
				novaString(linha, 5);
				break;
			}
			if (eOperador(nextChar(novaLetra))) {
				this.token.tipo("vazio");
				novaString(linha, 5);
				break;
			}
		default:
			formadorIdentificador(linha);
			break;
		}
	}

	// abaixo verifica caracter
	public void verificaCaracter(String linha) {
		String novaLetra = retornaNovaString(linha, 2);
		char firstLetter = firstChar(novaLetra);
		switch (firstLetter) {
		case 'r':
			if (nextChar(novaLetra) == 'a') {
				verificaCaracter2(linha);
				break;
			} else {
				formadorIdentificador(linha);
				break;
			}
		default:
			formadorIdentificador(linha);
			break;
		}
	}

	// abaixo verifica caracter
	public void verificaCaracter2(String linha) {
		// cara
		String novaLetra = retornaNovaString(linha, 4);
		char firstLetter = firstChar(novaLetra);
		switch (firstLetter) {
		case 'c':
			if (nextChar(novaLetra) == 't') {
				verificaCaracter3(linha);
				break;
			} else {
				formadorIdentificador(linha);
				break;
			}
		default:
			formadorIdentificador(linha);
			break;
		}
	}

	// verifica caracter
	public void verificaCaracter3(String linha) {
		// caract
		String novaLetra = retornaNovaString(linha, 6);
		char firstLetter = firstChar(novaLetra);
		switch (firstLetter) {
		case 'e':
			if (nextChar(novaLetra) == 'r') {
				verificaCaracter4(linha);
				break;
			} else {
				formadorIdentificador(linha);
				break;
			}
		default:
			formadorIdentificador(linha);
			break;
		}
	}

	// abaixo veririfca caracter
	public void verificaCaracter4(String linha) {
		// caracter
		String novaLetra = retornaNovaString(linha, 8);
		char firstLetter = firstChar(novaLetra);
		switch (firstLetter) {
		case ' ':
			this.token.tipo("caracter");
			novaString(linha, 8);
			break;
		case '¬':
			this.token.tipo("caracter");
			break;
		default:
			// verifica se é atributo
			if (eOperador(firstLetter)) {
				this.token.tipo("caracter");
				novaString(linha, 8);
				break;
			} else {
				formadorIdentificador(linha);
				break;
			}
		}
	}

	// abaixo verifica r

	public void verificaR(String linha) {
		String novaLetra = retornaNovaString(linha, 2);
		char firstLetter = firstChar(novaLetra);
		switch (firstLetter) {
		case 'a':
			if (nextChar(novaLetra) == 'l') {
				verificaReal(linha);
				break;
			} else {
				formadorIdentificador(linha);
				break;
			}
		case 't':
			if (nextChar(novaLetra) == 'o') {
				vefificaRetornar(linha);
				break;
			} else {
				formadorIdentificador(linha);
				break;
			}
		default:
			formadorIdentificador(linha);
			break;
		}
	}

	// abaixo verifica retornar
	public void vefificaRetornar(String linha) {
		// reto
		String novaLetra = retornaNovaString(linha, 4);
		char firstLetter = firstChar(novaLetra);
		switch (firstLetter) {
		case 'r':
			if (nextChar(novaLetra) == 'n') {
				vefificaRetornar2(linha);
				break;
			} else {
				formadorIdentificador(linha);
				break;
			}
		default:
			formadorIdentificador(linha);
			break;
		}
	}

	// abaixo continua verificando retornar
	public void vefificaRetornar2(String linha) {
		// retorn
		String novaLetra = retornaNovaString(linha, 6);
		char firstLetter = firstChar(novaLetra);
		switch (firstLetter) {
		case 'a':
			if (nextChar(novaLetra) == 'r') {
				vefificaRetornar3(linha);
				break;
			} else {
				formadorIdentificador(linha);
				break;
			}
		default:
			formadorIdentificador(linha);
			break;
		}
	}

	// abaixo verifica retorna
	public void vefificaRetornar3(String linha) {
		// retornar
		String novaLetra = retornaNovaString(linha, 8);
		char firstLetter = firstChar(novaLetra);
		switch (firstLetter) {
		case ' ':
			this.token.retorno();
			novaString(linha, 8);
			break;
		case '¬':
			this.token.retorno();
			break;
		default:
			// verifica se é atributo
			if (eOperador(firstLetter)) {
				this.token.retorno();
				novaString(linha, 8);
			} else {
				formadorIdentificador(linha);
				break;
			}
		}
	}

	// abaixo verifica real
	public void verificaReal(String linha) {
		String novaLetra = retornaNovaString(linha, 4);
		char firstLetter = firstChar(novaLetra);
		switch (firstLetter) {
		case ' ':
			this.token.tipo("real");
			novaString(linha, 4);
			break;
		case '¬':
			this.token.tipo("real");
			break;
		default:
			// verifica se é atributo
			if (eOperador(firstLetter)) {
				this.token.tipo("real");
				novaString(linha, 4);
			} else {
				formadorIdentificador(linha);
				break;
			}
		}
	}

	// abaixo verifica enquanto
	public void verificaEnquanto(String linha) {
		String novaLetra = retornaNovaString(linha, 2);
		char firstLetter = firstChar(novaLetra);
		switch (firstLetter) {
		case 'q':
			if (nextChar(novaLetra) == 'u') {
				verificaEnquanto2(linha);
				break;
			} else {
				formadorIdentificador(linha);
				break;
			}
		default:
			formadorIdentificador(linha);
			break;
		}
	}

	// continua verificando enquanto
	public void verificaEnquanto2(String linha) {
		String novaLetra = retornaNovaString(linha, 4);
		char firstLetter = firstChar(novaLetra);
		switch (firstLetter) {
		case 'a':
			if (nextChar(novaLetra) == 'n') {
				verificaEnquanto3(linha);
				break;
			} else {
				formadorIdentificador(linha);
				break;
			}
		default:
			formadorIdentificador(linha);
			break;
		}
	}

	// continua verificando enquanto
	public void verificaEnquanto3(String linha) {
		// enquan
		String novaLetra = retornaNovaString(linha, 6);
		char firstLetter = firstChar(novaLetra);
		switch (firstLetter) {
		case 't':
			if (nextChar(novaLetra) == 'o') {
				verificaEnquanto4(linha);
				break;
			} else {
				formadorIdentificador(linha);
				break;
			}
		default:
			formadorIdentificador(linha);
			break;
		}
	}

	// continua verificando enquanto
	public void verificaEnquanto4(String linha) {
		// enquanto
		String novaLetra = retornaNovaString(linha, 8);
		char firstLetter = firstChar(novaLetra);
		switch (firstLetter) {
		case ' ':
			this.token.enquanto();
			novaString(linha, 8);
			break;
		case '¬':
			this.token.enquanto();
			break;
		default:
			// verifica se é atriuto
			if (eOperador(firstLetter)) {
				this.token.enquanto();
				novaString(linha, 8);
			} else {
				formadorIdentificador(linha);
				break;
				// System.out.println(nextChar("erro enquanto4"));
			}
		}
	}

	// abaixo verifica para
	public void verificaPara(String linha) {
		String novaLetra = retornaNovaString(linha, 2);
		char firstLetter = firstChar(novaLetra);
		switch (firstLetter) {
		case 'r':
			if (nextChar(novaLetra) == 'a') {
				verificaPara2(linha);
				break;
			} else {
				formadorIdentificador(linha);
				break;
			}
		default:
			formadorIdentificador(linha);
			// tenta ver se é um operador
		}

	}

	// abaixo tenta ver se determinada palavra forma uma variavel
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
				break;
			} else if (novaLetra.charAt(i) == ' ') {
				this.token.identificador(novaVariavel);
				novaString(novaLetra, i);
				teveEspaco = true;
				break;
			} else {
				this.token.erro();
				// System.out.println("erro formadorIdentificador");
				break;
			}
			i++;
		}
		if (!teveOperador && !teveEspaco)
			this.token.identificador(novaVariavel);
	}

	// abaixo continua a verificação de para

	public void verificaPara2(String linha) {
		String novaLetra = retornaNovaString(linha, 4);
		char firstLetter = firstChar(novaLetra);
		switch (firstLetter) {
		case ' ':
			this.token.para();
			novaString(linha, 4);
			break;
		case '¬':
			this.token.para();
			break;
		default:
			// verifica se é atributo
			if (eOperador(firstLetter)) {
				this.token.para();
				novaString(linha, 4);
			} else {
				formadorIdentificador(linha);
				break;
			}
		}
	}

	// abaixo verifica int
	public void verificaInt(String linha) {
		String novaLetra = retornaNovaString(linha, 2);
		char firstLetter = firstChar(novaLetra);
		switch (firstLetter) {
		case 't':
			if (nextChar(novaLetra) == ' ' || nextChar(novaLetra) == '¬') {
				this.token.tipo("int");
				novaString(linha, 3);
				break;
			}
			if (eOperador(nextChar(novaLetra))) {
				this.token.tipo("int");
				novaString(linha, 3);
				break;
			}
		default:
			formadorIdentificador(linha);
			break;
		}
	}

	// abaixo verifica letra: s
	public void verificaS(String linha) {
		String novaLetra = retornaNovaString(linha, 2);
		char firstLetter = firstChar(novaLetra);

		switch (firstLetter) {
		case ' ':
			this.token.se();
			novaString(linha, 3);
			break;
		case '¬':
			this.token.se();
			// novaString(linha, 3);
			break;
		case 'n':
			// System.out.println(nextChar(linha));
			if (nextChar(novaLetra) == 'a') {
				verificaSenao(linha, 4);
				break;
			} else {
				formadorIdentificador(linha);
				break;
			}
		default:
			// verifica se é atriuto
			if (eOperador(firstLetter)) {
				this.token.se();
				novaString(linha, 2);
			} else {
				formadorIdentificador(linha);
				break;
			}
		}
	}

	// verifica se caracter é operador
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

	// abaixo verifica se é uma letra do alfabeto
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

	// abaixo verifica se é um numero
	public boolean eNumero(char letra) {
		switch (letra) {
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			return true;
		default:
			return false;
		}
	}

	// abaixo verifica se é um caracter valido
	public boolean eChValido(char letra) {
		switch (letra) {
		case '_':
		case '$':
			return true;
		default:
			return false;
		}
	}

	// varifica se a palavra é um senao
	public void verificaSenao(String linha, int posicao) {
		String novaLetra = retornaNovaString(linha, 4);
		char firstLetter = firstChar(novaLetra);
		switch (firstLetter) {
		case 'o':
			if (nextChar(novaLetra) == ' ' || nextChar(novaLetra) == '¬') {
				this.token.senao();
				novaString(linha, 5);
				break;
			}
			if (eOperador(nextChar(novaLetra))) {
				this.token.senao();
				novaString(linha, 5);
				break;
			}
		default:
			// verifica se é atriuto
			if (eOperador(firstLetter)) {
				this.token.senao();
				novaString(linha, 4);
			} else {
				formadorIdentificador(linha);
				break;
			}
		}
	}

	// abaixo retorno uma nova string apartir de posição desejada
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

	// abaixo verifica menor
	public void verificaMenor(String linha) {
		if (nextChar(linha) == '-') {
			this.token.atribuicao();
			novaString(linha, 2);
		} else if (nextChar(linha) == '=') {
			this.token.menorIgual();
			novaString(linha, 2);
		} else if (nextChar(linha) == ' ' || nextChar(linha) == '¬') {
			this.token.menor();
			novaString(linha, 2);
		} else {
			this.token.menor();
			novaString(linha, 1);
		}
	}

	// abaixo veririca maior
	public void verificaMaior(String linha) {
		if (nextChar(linha) == '=') {
			this.token.maiorIgual();
			novaString(linha, 2);
		} else if (nextChar(linha) == ' ' || nextChar(linha) == '¬') {
			this.token.maior();
			novaString(linha, 2);
		} else {
			this.token.maior();
			novaString(linha, 1);
		}
	}

	// abaixo verifica barra
	public void verificaBarra(String linha) {
		if (nextChar(linha) == '/') {
			this.token.comentarioLinha();
			novaString(linha, 2);
		} else if (nextChar(linha) == ' ' || nextChar(linha) == '¬') {
			this.token.divisao();
			novaString(linha, 2);
		} else if (nextChar(linha) == '*') {
			// indica que teve inicio um comentario paragrafo, e vai procurar
			// seu fim
			this.token.inicioComentarioParagrafo();
			verificaComentarioParagrafo(linha);
		} else {
			this.token.divisao();
			novaString(linha, 1);
		}
	}

	// abaixo verifica comentario linha
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
			if (encontrouFim)
				break;
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

	// abaixo pega resto da string que não foi ainda identificada como token e
	// cria nova string
	public void novaString(String linha, int posicao) {
		int i = posicao;
		String novaPalavra = "";
		if (linha != "") {
			for (i = posicao; i < linha.length(); i++) {
				novaPalavra += linha.charAt(i);
			}
		}
		lerLinha(novaPalavra);
	}

}
