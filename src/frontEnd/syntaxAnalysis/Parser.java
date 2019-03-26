package frontEnd.syntaxAnalysis;
import frontEnd.fileReader.TextFileReader;
import frontEnd.lexicalAnalysis.Scanner;
import frontEnd.lexicalAnalysis.Token;
import frontEnd.AST.*;

public class Parser {

	Scanner scanner;
	Token currentToken;
	
	public Parser(TextFileReader file){
		scanner = new Scanner(file);
	}
	public ProgramaNode parse(){
		ProgramaNode progAST = null;
		currentToken = scanner.scan();
		progAST = parsePrograma();
		return progAST;
	}
	public String indent() {
		String retorno = new String("\t");
		return retorno;
	}
	public void cabeçalhoErro() {
		System.out.println(indent() + "!ERRO - ANÁLISE SINTÁTICA");
		System.out.println(indent() + "  * Linha: " + currentToken.getLine() + ", Posição: " + currentToken.getColumn());
		System.out.print(indent() +   "  └ ");
	}
	public void interromperAnálise() {
		System.out.println(indent() + "A ANÁLISE SINTÁTICA FOI INTERROMPIDA DEVIDO A ERROS OCORRIDOS");
		System.exit(0);
	}
	public Token accept(int expectedType) {
		Token token_lido = new Token(currentToken);
		if (currentToken.getType() == expectedType)
			currentToken = scanner.scan();
		else {	//	Erro na análise sintática
				//	Não foi possível achar regra de derivação - token inesperado
			cabeçalhoErro();
			System.out.println("Token lido inesperado: [" + currentToken.getSpelling() +
					"] (token do tipo " + currentToken.getType() + ")" + 
					" enquanto era experado um \"" + Token.spellings[expectedType] + 
					"\" (token do tipo " + expectedType + ")." 
					);
			interromperAnálise();
		}
		return token_lido;
	}
	public Token accept() {	//	acceptIt();
		Token token_lido = new Token(currentToken);
		currentToken = scanner.scan();
		return token_lido;
	}
	
	private ComandoNode parseAtribuição() {	//	<atribuição> ::= 
										//		<variável> := <expressão>
		ComandoNode atribAST = null;
		VariávelNode varAST = null;
		ExpressãoNode expAST = null;
		varAST = parseVariável();
		accept(Token.OPATTRIB);
		expAST = parseExpressão();
		atribAST = new ComandoAtribuiçãoNode(varAST, expAST);
		return atribAST;
	}
//	private LiteralNode parseBoolLit() {	//	<bool-lit> ::= true | false
//		LiteralNode litAST = null;
//		Token tokenAST = null;
//		if (currentToken.getType() == Token.TRUE)
//			tokenAST = accept();
//		else if (currentToken.getType() == Token.FALSE)
//			tokenAST = accept();
//		else {
//			System.out.println("ERROR - SYNTAX\nUnexpected token read: [" + currentToken.getSpelling() +
//					"] (token do tipo " + currentToken.getType() + "), in line " + currentToken.getLine() + 
//					" column "+ currentToken.getColumn() + 
//					", while it was expected either a(n) \"" + 
//					Token.spellings[Token.TRUE] + "\" (token do tipo " + Token.TRUE + ") or \"" +
//					Token.spellings[Token.FALSE] + "\" (token do tipo " + Token.FALSE + ")." 
//					);
//		}
//		litAST = new LiteralNode(tokenAST);
//		return litAST;
//	}
//	private void parseChamadaDeFunção() {	//	<chamada-de-função> ::= 
//											//		id "(" ( <lista-de-expressões> | <vazio> ) ")"
//		accept(Token.ID);
//		accept(Token.LPARENTHESIS);
//											//	first1( <lista-de-expressões> ) = {id, true, false, int-lit, float-lit, "(" }
//		if (	currentToken.getType() == Token.ID || currentToken.getType() == Token.TRUE ||
//				currentToken.getType() == Token.FALSE || currentToken.getType() == Token.INTLITERAL || 
//				currentToken.getType() == Token.FLOATLITERAL || currentToken.getType() == Token.LPARENTHESIS
//				)
//			accept();
//											//	follow1( ( <lista-de-expressões> | <vazio> ) ) = { “)” }
//		else if (currentToken.getType() == Token.RPARENTHESIS) 
//			accept();
//		else {
//			System.out.println("ERROR - SYNTAX\nUnexpected token read: [" + currentToken.getSpelling() +
//					"] (token do tipo " + currentToken.getType() + "), in line " + currentToken.getLine() + 
//					" column "+ currentToken.getColumn() + 
//					", while it was expected either a(n) \"" + 
//					Token.spellings[Token.TRUE] + "\" (token do tipo " + Token.TRUE + ") or \"" +
//					Token.spellings[Token.FALSE] + "\" (token do tipo " + Token.FALSE + ")." 
//					);
//		}
//	}
//	private void parseChamadaDeProcedimento() {	//	<chamada-de-procedimento> ::= 
//												//		id "(" ( <lista-de-expressões> | <vazio> ) ")"
//		accept(Token.ID);
//		accept(Token.LPARENTHESIS);
//												//	first1( <lista-de-expressões> ) = {id, true, false, int-lit, float-lit, "(" }
//		if (	currentToken.getType() == Token.ID || currentToken.getType() == Token.TRUE ||
//				currentToken.getType() == Token.FALSE || currentToken.getType() == Token.INTLITERAL ||
//				currentToken.getType() == Token.FLOATLITERAL || currentToken.getType() == Token.LPARENTHESIS
//				)
//			accept();
//												//	follow1( ( <lista-de-expressões> | <vazio> ) ) = { “)” }
//		accept(Token.RPARENTHESIS);	
//	}
	private ComandoNode parseComando() {	//	<comando> ::= <atribuição> 
									//		| <condicional> 
		ComandoNode comAST = null;							//		| <iterativo> 
									//		| <comando-composto>
		if (currentToken.getType() == Token.ID) {
			comAST =parseAtribuição();
		}
		else if (currentToken.getType() == Token.IF)
			comAST = parseCondicional();
		else if (currentToken.getType() == Token.WHILE)
			comAST = parseIterativo();
		else if (currentToken.getType() == Token.BEGIN)
			comAST = parseComandoComposto();
		else {
			cabeçalhoErro();
			System.out.println("Token lido inesperado: [" + currentToken.getSpelling() +
					"] (token do tipo " + currentToken.getType() + ")" + 
					" enquanto era esperado um \"" + 
					Token.spellings[Token.ID] + "\" (token do tipo " + Token.ID + "), \"" +
					Token.spellings[Token.IF] + "\" (token do tipo " + Token.IF + "), \"" +
					Token.spellings[Token.WHILE] + "\" (token do tipo " + Token.WHILE + ") ou \"" +
					Token.spellings[Token.BEGIN] + "\" (token do tipo " + Token.BEGIN + ")." 
					);
			interromperAnálise();
		}
		return comAST;
	}
	private ComandoNode parseComandoComposto() {	//	<comando-composto> ::= 
											//		begin <lista-de-comandos> end
		ComandoNode comcAST = null;
		ListaDeComandosNode listAST = null;
		accept(Token.BEGIN);
		listAST = parseListaDeComandos();
		accept(Token.END);
		comcAST = new ComandoCompostoNode(listAST);
		return comcAST;
	}
	private ComandoNode parseCondicional() {	//	<condicional> ::= 	
										//		if <expressão> then <comando> ( else <comando> | <vazio> )
		ComandoNode condAST = null, com1AST = null, com2AST = null;
		accept(Token.IF);
		ExpressãoNode expAST = parseExpressão();
		accept(Token.THEN);
		com1AST = parseComando();
		if (currentToken.getType() == Token.ELSE) {	//	Considerando que os elses se referem
			accept();								//	aos "ifs" mais internos
			com2AST = parseComando();
		}
		condAST = new ComandoCondicionalNode(expAST, com1AST, com2AST);
		return condAST;
	}
	private CorpoNode parseCorpo() {	//	<corpo> ::=
								//		<declarações> <comando-composto>
		CorpoNode corpoAST = null; 
		DeclaraçãoNode declAST = null;
		ComandoNode comanAST = null;
		declAST = parseDeclarações();
		comanAST = parseComandoComposto();
		corpoAST = new CorpoNode(declAST, comanAST);
		return corpoAST;
	}
	private DeclaraçãoNode parseDeclaração() {	//	<declaração> ::=
										//		<declaração-de-variável> 
										//		| <declaração-de-função> 
										//		| <declaração-de-procedimento>
		DeclaraçãoNode declAST = null;
		if (currentToken.getType() == Token.VAR)
			declAST = parseDeclaraçãoDeVariável();
//		else if (currentToken.getType() == Token.FUNCTION)
//			parseDeclaraçãoDeFunção();
//		else if (currentToken.getType() == Token.PROCEDURE)
//			parseDeclaraçãoDeProcedimento();
		else {
			cabeçalhoErro();
			System.out.println("Token lido inesperado: [" + currentToken.getSpelling() +
					"] (token do tipo " + currentToken.getType() + ")" +
					" enquanto era esperado um \"" + 
					Token.spellings[Token.VAR] + "\" (token do tipo " + Token.VAR + ")." 
					);
			interromperAnálise();
		}
		return declAST;
	}
//	private void parseDeclaraçãoDeFunção() {	//	<declaração-de-função> ::= 
//												//		function id "(" ( <lista-de-parâmetros> | <vazio> ) ")" : <tipo-simples> ; <corpo>
//
//		accept(Token.FUNCTION);
//		accept(Token.ID);
//		accept(Token.LPARENTHESIS);
//		if (currentToken.getType() == Token.VAR || currentToken.getType() == Token.ID)
//			parseListaDeParâmetros();	
//		accept(Token.RPARENTHESIS);
//		accept(Token.COLON);
//		parseTipoSimples();
//		accept(Token.SEMICOLON);
//		parseCorpo();
//	}
//	private void parseDeclaraçãoDeProcedimento() {	//	<declaração-de-procedimento> ::= 
//													//		procedure id "(" ( <lista-de-parâmetros> | <vazio> ) ")" ; <corpo>
//		accept(Token.PROCEDURE);
//		accept(Token.ID);
//		accept(Token.LPARENTHESIS);
//		if (currentToken.getType() == Token.VAR || currentToken.getType() == Token.ID)
//			parseListaDeParâmetros();
//		accept(Token.RPARENTHESIS);
//		accept(Token.SEMICOLON);
//		parseCorpo();
//	}
	private DeclaraçãoNode parseDeclaraçãoDeVariável() {	//	<declaração-de-variável> ::= 
		DeclaraçãoNode declAST = null;									//		var <lista-de-ids> : <tipo>
		ListaDeIdsNode listAST = null;
		TipoNode tipoAST = null;
		accept(Token.VAR);
		listAST = parseListaDeIds();
		accept(Token.COLON);
		tipoAST = parseTipo();
		declAST = new DeclaraçãoDeVariávelNode(listAST,tipoAST);
		return declAST;
	}

	private DeclaraçãoNode parseDeclarações() {	//	<declarações> ::= 
		DeclaraçãoNode primdeclAST = null, ultideclAST = null, declAST = null;	//		( <declaração> ; )*
		while(currentToken.getType() == Token.VAR) {
			declAST = parseDeclaração();
			if (primdeclAST == null)
				primdeclAST = declAST;
			else
				ultideclAST.próximaD = declAST;
			ultideclAST = declAST;
			accept(Token.SEMICOLON);
		}
		return primdeclAST;
	}
	private ExpressãoNode parseExpressão() {	//	<expressão> ::= 
									//		<expressão-simples> ( ε | <op-rel> <expressão-simples> ) 
		ExpressãoNode expAST = null;
		ExpressãoSimplesNode exs1AST = null, exs2AST = null;
		OperadorNode opAST = null;
		exs1AST = parseExpressãoSimples();
		if (currentToken.getType() == Token.OPLOWERTHN || currentToken.getType() == Token.OPGREATTHN || 
			currentToken.getType() == Token.OPLOWOREQ || currentToken.getType() == Token.OPGREOREQ || 
			currentToken.getType() == Token.OPEQUAL || currentToken.getType() == Token.OPDIFF
				) { 	//	{ <, >, <=, >=, =, <> }
			opAST = parseOpRel();
			exs2AST = parseExpressãoSimples();
		}
		expAST = new ExpressãoNode(exs1AST, opAST, exs2AST);
		return expAST;
	}
	private ExpressãoSimplesNode parseExpressãoSimples() {	//	<expressão-simples> ::= 
											//		<termo> ( <op-ad> <termo> )*
		ExpressãoSimplesNode exsAST = null;
		SequênciaTermosNode primsqAST = null, ultisqAST = null, sqAST = null;
		TermoNode termAST = null;
		termAST = parseTermo();
		
		while (currentToken.getType() == Token.OPSUM || currentToken.getType() == Token.OPSUB ||
			currentToken.getType() == Token.OR 
			) {
			OperadorNode oseqAST = null;
			TermoNode tseqAST = null;
			oseqAST = parseOpAd();
			tseqAST = parseTermo();
			sqAST = new SequênciaTermosNode(oseqAST, tseqAST, null);
			
			if (primsqAST == null)
				primsqAST = sqAST;
			else
				ultisqAST.próximaS = sqAST;
			ultisqAST = sqAST;			
		}
		
		exsAST = new ExpressãoSimplesNode(termAST, primsqAST);
		return exsAST;
	}
	private FatorNode parseFator() {	//	<fator> ::= <variável> 
								//		| <literal>  | "(" <expressão> ")" 
		FatorNode fatorAST = null;
		if (currentToken.getType() == Token.ID) {
			fatorAST = parseVariável();
		}
		else if (currentToken.getType() == Token.TRUE || currentToken.getType() == Token.FALSE 
				|| currentToken.getType() == Token.INTLITERAL || currentToken.getType() == Token.FLOATLITERAL) {
			fatorAST = parseLiteral();
		}
		else if (currentToken.getType() == Token.LPARENTHESIS) {
			accept();
			fatorAST = parseExpressão();
			accept(Token.RPARENTHESIS);
		}
		else {
			cabeçalhoErro();
			System.out.println("Token lido inesperado: [" + currentToken.getSpelling() +
					"] (token do tipo " + currentToken.getType() + ")" + 
					" enquanto era esperado um \"" + 
					Token.spellings[Token.ID] + "\" (token do tipo " + Token.ID + "), \"" +
					Token.spellings[Token.TRUE] + "\" (token do tipo " + Token.TRUE + "), \"" +
					Token.spellings[Token.FALSE] + "\" (token do tipo " + Token.FALSE + "), \"" +
					Token.spellings[Token.INTLITERAL] + "\" (token do tipo " + Token.INTLITERAL + "), \"" +
					Token.spellings[Token.FLOATLITERAL] + "\" (token do tipo " + Token.FLOATLITERAL + ") ou \"" +
					Token.spellings[Token.LPARENTHESIS] + "\" (token do tipo " + Token.LPARENTHESIS + ")." 
					);
			interromperAnálise();
		}
		return fatorAST;
	}
	private ComandoNode parseIterativo() {	//	<iterativo> ::= while <expressão> do <comando>
		ComandoNode iterAST = null;
		accept(Token.WHILE);
		ExpressãoNode expAST = parseExpressão();
		accept(Token.DO);
		ComandoNode com1AST = parseComando();
		iterAST = new ComandoIterativoNode(expAST, com1AST);
		return iterAST;
	}
	private ListaDeComandosNode parseListaDeComandos() {	//	<lista-de-comandos> ::= ( <comando> ; )*
		ListaDeComandosNode primlistcomAST = null, ultilistcomAST = null, listcomAST = null;
		ComandoNode comAST = null;
		while (currentToken.getType() == Token.ID || currentToken.getType() == Token.IF ||
				currentToken.getType() == Token.WHILE || currentToken.getType() == Token.BEGIN) {
			comAST = parseComando();
			listcomAST = new ListaDeComandosNode(comAST, null);
			
			if (primlistcomAST == null)
				primlistcomAST = listcomAST;
			else
				ultilistcomAST.próximaLC = listcomAST;
			ultilistcomAST = listcomAST;

			accept(Token.SEMICOLON);
		}
		return primlistcomAST;
	}
//	private void parseListaDeExpressões() {	//	<lista-de-expressões> ::= <expressão> ( , <expressão> )*
//		parseExpressão();
//		while (currentToken.getType() == Token.COMMA) {
//			accept();
//			parseExpressão();
//		}
//	}
	private ListaDeIdsNode parseListaDeIds() {	//	<lista-de-ids> ::= id ( , id )*
		ListaDeIdsNode primelistAST =null, ultimolistAST = null, listAST = null;
		Token idAST = null;
		idAST = accept(Token.ID);
		primelistAST = new ListaDeIdsNode(idAST);
		ultimolistAST = primelistAST; 
		while (currentToken.getType() == Token.COMMA) {
			accept();
			idAST = accept(Token.ID);
			listAST = new ListaDeIdsNode(idAST);
			ultimolistAST.próximaLI = listAST;
			
			ultimolistAST = listAST;
		}
		return primelistAST;
	}
//	private void parseListaDeParâmetros() {	//	<lista-de-parâmetros> ::= <parâmetros> ( ; <parâmetros> ) * 
//		parseParâmetros();
//		while (currentToken.getType() == Token.SEMICOLON) {
//			accept();
//			parseParâmetros();
//		}
//	}
	private LiteralNode parseLiteral() {	//	<literal> ::= <bool-lit> | int-lit | float-lit 
		LiteralNode litAST = null;
		Token tokenAST = null;
		if (currentToken.getType() == Token.TRUE)
			tokenAST = accept();
		else if (currentToken.getType() == Token.FALSE) 
			tokenAST = accept();
		else if (currentToken.getType() == Token.INTLITERAL)
			tokenAST = accept();
		else if (currentToken.getType() == Token.FLOATLITERAL)
			tokenAST = accept();
		else {
			cabeçalhoErro();
			System.out.println("Token lido inesperado: [" + currentToken.getSpelling() +
					"] (token do tipo " + currentToken.getType() + ")" + 
					" enquanto era esperado um \"" + 
					Token.spellings[Token.TRUE] + "\" (token do tipo " + Token.TRUE + "), \"" +
					Token.spellings[Token.FALSE] + "\" (token do tipo " + Token.FALSE + "), \"" +
					Token.spellings[Token.INTLITERAL] + "\" (token do tipo " + Token.INTLITERAL + ") ou \"" +
					Token.spellings[Token.FLOATLITERAL] + "\" (token do tipo " + Token.FLOATLITERAL + ")." 
					);
			interromperAnálise();
		}
		litAST = new LiteralNode(tokenAST);		
		return litAST;
	}
	private OperadorNode parseOpAd() {	// 	<op-ad> ::= + | - | or
		OperadorNode opAST = null;
		Token tokenAST = null;
		switch(currentToken.getType()) {
			case Token.OPSUM: case Token.OPSUB: case Token.OR:
				tokenAST = accept();
				break;
			default:
				cabeçalhoErro();
				System.out.println("Token lido inesperado: [" + currentToken.getSpelling() +
						"] (token do tipo " + currentToken.getType() + ")" + 
						" enquanto era esperado um \"" + 
						Token.spellings[Token.OPSUM] + "\" (token do tipo " + Token.OPSUM + "), \"" +
						Token.spellings[Token.OPSUB] + "\" (token do tipo " + Token.OPSUB + ") ou \"" +
						Token.spellings[Token.OR] + "\" (token do tipo " + Token.OR + ")." 
						);
				interromperAnálise();
		}
		opAST = new OperadorNode(tokenAST);
		return opAST;
	}
	private OperadorNode parseOpMul() {	//	<op-mul> ::= *  | /  | and
		OperadorNode opAST = null;
		Token tokenAST = null;
		switch(currentToken.getType()) {
			case Token.OPMULT: case Token.OPDIV: case Token.AND:
				tokenAST = accept();
				break;
			default:
				cabeçalhoErro();
				System.out.println("Token lido inesperado: [" + currentToken.getSpelling() +
						"] (token do tipo " + currentToken.getType() + ")" + 
						" enquanto era esperado um \"" + 
						Token.spellings[Token.OPMULT] + "\" (token do tipo " + Token.OPMULT + "), \"" +
						Token.spellings[Token.OPDIV] + "\" (token do tipo " + Token.OPDIV + ") ou \"" +
						Token.spellings[Token.AND] + "\" (token do tipo " + Token.AND + ")." 
						);
				interromperAnálise();
		}
		opAST = new OperadorNode(tokenAST);
		return opAST;
	}
	private OperadorNode parseOpRel() {	//	<op-rel> ::= <  | >  | <=  | >= | = | <>
		OperadorNode opAST = null;
		Token tokenAST = null;
		switch(currentToken.getType()) {
			case Token.OPLOWERTHN: case Token.OPGREATTHN: case Token.OPLOWOREQ: 
			case Token.OPGREOREQ: case Token.OPEQUAL: case Token.OPDIFF:
				tokenAST = accept();
				break;
			default:
				cabeçalhoErro();
				System.out.println("Token lido inesperado: [" + currentToken.getSpelling() +
						"] (token do tipo " + currentToken.getType() + ")" + 
						" enquanto era esperado um \"" + 
						Token.spellings[Token.OPLOWERTHN] + "\" (token do tipo " + Token.OPLOWERTHN + "), \"" +
						Token.spellings[Token.OPGREATTHN] + "\" (token do tipo " + Token.OPGREATTHN + "), \"" +
						Token.spellings[Token.OPLOWOREQ] + "\" (token do tipo " + Token.OPLOWOREQ + "), \"" +
						Token.spellings[Token.OPGREOREQ] + "\" (token do tipo " + Token.OPGREOREQ + "), \"" +
						Token.spellings[Token.OPEQUAL] + "\" (token do tipo " + Token.OPEQUAL + ") ou \"" +
						Token.spellings[Token.OPDIFF] + "\" (token do tipo " + Token.OPDIFF + ")." 
						);
				interromperAnálise();
		}	
		opAST = new OperadorNode(tokenAST);
		return opAST;
	}
//	private void parseParâmetros() {	//	<parâmetros> ::= 
//										//		( var | <vazio> ) <lista-de-ids> : <tipo-simples>
//		if (currentToken.getType() == Token.VAR)
//			accept();
//		parseListaDeIds();
//		accept(Token.COLON);
//		parseTipoSimples();
//	}
	
	private ProgramaNode parsePrograma() { 	// <programa> ::= program id ; <corpo> .
		ProgramaNode progAST = null;
		Token idAST = null;
		CorpoNode corpoAST = null;
		accept(Token.PROGRAM);

		idAST = accept(Token.ID);
		accept(Token.SEMICOLON);
		corpoAST = parseCorpo();
		accept(Token.DOT);
		progAST = new ProgramaNode(idAST, corpoAST);
		return progAST;				
	}
	
	private SeletorNode parseSeletor() {	//	<seletor> ::= ( [ <expressão> ] )*
		SeletorNode primselAST = null, ultiselAST = null, selAST = null;
		ExpressãoNode expAST = null;
		
		while(currentToken.getType() == Token.LBRACKET) {
			accept();
			expAST = parseExpressão();
			
			selAST = new SeletorNode(expAST, null);
			if (primselAST == null)
				primselAST = selAST;
			else
				ultiselAST.próximoS = selAST;
			ultiselAST = selAST;
			
			accept(Token.RBRACKET);
		}
		return primselAST;
	}
	private TermoNode parseTermo() {	//	<termo> ::= <fator> ( <op-mul> <fator> )*
		TermoNode termAST = null;
		SequênciaFatoresNode primsqAST = null, ultisqAST = null, sqAST = null;
		FatorNode fatorAST = null;
		fatorAST = parseFator();
		
		while (currentToken.getType() == Token.OPMULT || currentToken.getType() == Token.OPDIV
				|| currentToken.getType() == Token.AND) {
			OperadorNode oseqAST = null;
			FatorNode fseqAST = null;
			oseqAST = parseOpMul();
			fseqAST = parseFator();
			
			sqAST = new SequênciaFatoresNode(oseqAST, fseqAST, null);
			
			if (primsqAST == null)
				primsqAST = sqAST;
			else
				ultisqAST.próximaS = sqAST;
			ultisqAST = sqAST;			
		}
		
		termAST = new TermoNode(fatorAST, primsqAST);
		return termAST;
	}
	private TipoNode parseTipo() {	//	<tipo> ::= <tipo-agregado> | <tipo-simples>
		TipoNode tipoAST = null;
		if (currentToken.getType() == Token.ARRAY)
			tipoAST = parseTipoAgregado();
		else if (currentToken.getType() == Token.INTEGER || currentToken.getType() == Token.REAL
				|| currentToken.getType() == Token.BOOLEAN)
			tipoAST = parseTipoSimples();
		else {
			cabeçalhoErro();
			System.out.println("Token lido inesperado: [" + currentToken.getSpelling() +
					"] (token do tipo " + currentToken.getType() + ")" + 
					" enquanto era esperado um \"" + 
					Token.spellings[Token.ARRAY] + "\" (token do tipo " + Token.ARRAY + "), \"" +
					Token.spellings[Token.INTEGER] + "\" (token do tipo " + Token.INTEGER + "), \"" +
					Token.spellings[Token.REAL] + "\" (token do tipo " + Token.REAL + ") ou \"" +
					Token.spellings[Token.BOOLEAN] + "\" (token do tipo " + Token.BOOLEAN + ")." 
					);
			interromperAnálise();
		}
		return tipoAST;
	}
	private TipoAgregadoNode parseTipoAgregado() {	//	<tipo-agregado> ::= 
										//		array [ int-lit .. int-lit ] of <tipo>
		TipoAgregadoNode tagrAST = null;
		TipoNode tipoAST = null;
		Token IDX1 = null, IDX2 = null;
		accept(Token.ARRAY);
		accept(Token.LBRACKET);
		IDX1 = accept(Token.INTLITERAL);
		accept(Token.TILL);
		IDX2 = accept(Token.INTLITERAL);
		accept(Token.RBRACKET);
		accept(Token.OF);
		tipoAST = parseTipo();
		tagrAST = new TipoAgregadoNode(IDX1, IDX2, tipoAST);
		return tagrAST;
	}
	private TipoSimplesNode parseTipoSimples() {	//	<tipo-simples> ::= 
										//		integer | real 	| boolean
		TipoSimplesNode tsimpAST = null;
		Token tokenAST = null;
		switch(currentToken.getType()) {
			case Token.INTEGER: case Token.REAL: case Token.BOOLEAN:
				tokenAST = accept();
				break;
			default:
				cabeçalhoErro();
				System.out.println("Token lido inesperado: [" + currentToken.getSpelling() +
						"] (token do tipo " + currentToken.getType() + ")" + 
						" enquanto era esperado um \"" + 
						Token.spellings[Token.INTEGER] + "\" (token do tipo " + Token.INTEGER + "), \"" +
						Token.spellings[Token.REAL] + "\" (token do tipo " + Token.REAL + ") ou \"" +
						Token.spellings[Token.BOOLEAN] + "\" (token do tipo " + Token.BOOLEAN + ")." 
						);
				interromperAnálise();
		}
		tsimpAST = new TipoSimplesNode(tokenAST);
		return tsimpAST;
	}
	private VariávelNode parseVariável() {	//	<variável> ::= 
									//		id <seletor>
		VariávelNode varAST = null;
		SeletorNode selAST = null;
		Token tokenAST = null;
		tokenAST = accept(Token.ID);
		selAST = parseSeletor();
		varAST = new VariávelNode(tokenAST, selAST);
		return varAST;
	}
//	private void parseVazio() {	//	<vazio> ::= 
//								//		ε
//		
//	}
}
