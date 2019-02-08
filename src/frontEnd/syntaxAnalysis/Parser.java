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
	public void parse(){
		currentToken = scanner.scan();
		parsePrograma();
	}
	public Token accept(int expectedType) {
		if (currentToken.getType() == expectedType)
			currentToken = scanner.scan();
		else {	//	Erro na análise sintática
				//	Não foi possível achar regra de derivação - token inesperado
			System.out.println("ERROR - SYNTAX\nUnexpected token read: [" + currentToken.getSpelling() +
					"] (token type " + currentToken.getType() + "), in line " + currentToken.getLine() + 
					" column "+ currentToken.getColumn() + 
					", while it was expected a(n) \"" + Token.spellings[expectedType] + 
					"\" (token type " + expectedType + ")." 
					);
		}
		Token token_lido = new Token(currentToken);
		return token_lido;
	}
	public Token accept() {	//	acceptIt();
		currentToken = scanner.scan();
		Token token_lido = new Token(currentToken);
		return token_lido;
	}
	
	private ComandoNode parseAtribuição() {	//	<atribuição> ::= 
										//		<variável> := <expressão>
		ComandoNode atribAST;
		parseVariável();
		accept(Token.OPATTRIB);
		parseExpressão();
		atribAST = new ComandoAtribuiçãoNode();
		return atribAST;
	}
	private void parseBoolLit() {	//	<bool-lit> ::= true | false
		if (currentToken.getType() == Token.TRUE)
			accept();
		else if (currentToken.getType() == Token.FALSE)
			accept();
		else {
			System.out.println("ERROR - SYNTAX\nUnexpected token read: [" + currentToken.getSpelling() +
					"] (token type " + currentToken.getType() + "), in line " + currentToken.getLine() + 
					" column "+ currentToken.getColumn() + 
					", while it was expected either a(n) \"" + 
					Token.spellings[Token.TRUE] + "\" (token type " + Token.TRUE + ") or \"" +
					Token.spellings[Token.FALSE] + "\" (token type " + Token.FALSE + ")." 
					);
		}
	}
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
//					"] (token type " + currentToken.getType() + "), in line " + currentToken.getLine() + 
//					" column "+ currentToken.getColumn() + 
//					", while it was expected either a(n) \"" + 
//					Token.spellings[Token.TRUE] + "\" (token type " + Token.TRUE + ") or \"" +
//					Token.spellings[Token.FALSE] + "\" (token type " + Token.FALSE + ")." 
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
		ComandoNode comAST;							//		| <iterativo> 
									//		| <comando-composto>
		if (currentToken.getType() == Token.ID) {
			parseAtribuição();
		}
		else if (currentToken.getType() == Token.IF)
			parseCondicional();
		else if (currentToken.getType() == Token.WHILE)
			parseIterativo();
		else if (currentToken.getType() == Token.BEGIN)
			parseComandoComposto();
		else {
			System.out.println("ERROR - SYNTAX\nUnexpected token read: [" + currentToken.getSpelling() +
					"] (token type " + currentToken.getType() + "), in line " + currentToken.getLine() + 
					" column "+ currentToken.getColumn() + 
					", while it was expected either a(n) \"" + 
					Token.spellings[Token.ID] + "\" (token type " + Token.ID + "), \"" +
					Token.spellings[Token.IF] + "\" (token type " + Token.IF + "), \"" +
					Token.spellings[Token.WHILE] + "\" (token type " + Token.WHILE + ") or \"" +
					Token.spellings[Token.BEGIN] + "\" (token type " + Token.BEGIN + ")." 
					);
		}
		return comAST;
	}
	private ComandoCompostoNode parseComandoComposto() {	//	<comando-composto> ::= 
											//		begin <lista-de-comandos> end
		ComandoCompostoNode comcAST;
		accept(Token.BEGIN);
		ListaDeComandosNode listAST = parseListaDeComandos();
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
		CorpoNode corpoAST; 
		DeclaraçãoNode declAST = parseDeclarações();
		ComandoCompostoNode comanAST = parseComandoComposto();
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
			System.out.println("ERROR - SYNTAX\nUnexpected token read: [" + currentToken.getSpelling() +
					"] (token type " + currentToken.getType() + "), in line " + currentToken.getLine() + 
					" column "+ currentToken.getColumn() + 
					", while it was expected a(n) \"" + 
					Token.spellings[Token.VAR] + "\" (token type " + Token.VAR + ")." 
					);
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
		DeclaraçãoNode declAST;									//		var <lista-de-ids> : <tipo>
		accept(Token.VAR);
		ListaDeIdsNode listAST = parseListaDeIds();
		accept(Token.COLON);
		TipoNode tipoAST = parseTipo();
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
		ExpressãoNode expAST;
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
		expAST = new ExpressãoNode(exs1AST, opAST, exs2AST)
		return expAST;
	}
	private ExpressãoSimplesNode parseExpressãoSimples() {	//	<expressão-simples> ::= 
											//		<termo> ( <op-ad> <termo> )*
		ExpressãoSimplesNode exsAST;
		parseTermo();
		if (currentToken.getType() == Token.OPSUM || currentToken.getType() == Token.OPSUB ||
			currentToken.getType() == Token.OR 
			) {
			parseOpAd();
			parseTermo();
		}
		return exsAST;
	}
	private void parseFator() {	//	<fator> ::= <variável> 
								//		| <literal>  | "(" <expressão> ")" 
		if (currentToken.getType() == Token.ID) {
			parseVariável();
		}
		else if (currentToken.getType() == Token.TRUE || currentToken.getType() == Token.FALSE 
				|| currentToken.getType() == Token.INTLITERAL || currentToken.getType() == Token.FLOATLITERAL) {
			parseLiteral();
		}
		else if (currentToken.getType() == Token.LPARENTHESIS) {
			accept();
			parseExpressão();
			accept(Token.RPARENTHESIS);
		}
		else {
			System.out.println("ERROR - SYNTAX\nUnexpected token read: [" + currentToken.getSpelling() +
					"] (token type " + currentToken.getType() + "), in line " + currentToken.getLine() + 
					" column "+ currentToken.getColumn() + 
					", while it was expected either a(n) \"" + 
					Token.spellings[Token.ID] + "\" (token type " + Token.ID + "), \"" +
					Token.spellings[Token.TRUE] + "\" (token type " + Token.TRUE + "), \"" +
					Token.spellings[Token.FALSE] + "\" (token type " + Token.FALSE + "), \"" +
					Token.spellings[Token.INTLITERAL] + "\" (token type " + Token.INTLITERAL + "), \"" +
					Token.spellings[Token.FLOATLITERAL] + "\" (token type " + Token.FLOATLITERAL + ") or \"" +
					Token.spellings[Token.LPARENTHESIS] + "\" (token type " + Token.LPARENTHESIS + ")." 
					);
		}
	}
	private ComandoNode parseIterativo() {	//	<iterativo> ::= while <expressão> do <comando>
		ComandoNode iterAST;
		accept(Token.WHILE);
		ExpressãoNode expAST = parseExpressão();
		accept(Token.DO);
		ComandoNode com1AST = parseComando();
		iterAST = ComandoIterativoNode(expAST, com1AST);
		return iterAST;
	}
	private ListaDeComandosNode parseListaDeComandos() {	//	<lista-de-comandos> ::= ( <comando> ; )*
		ListaDeComandosNode listcomAST;
		while (currentToken.getType() == Token.ID || currentToken.getType() == Token.IF ||
				currentToken.getType() == Token.WHILE || currentToken.getType() == Token.BEGIN) {
			parseComando();
			accept(Token.SEMICOLON);
		}
		return listcomAST;
	}
//	private void parseListaDeExpressões() {	//	<lista-de-expressões> ::= <expressão> ( , <expressão> )*
//		parseExpressão();
//		while (currentToken.getType() == Token.COMMA) {
//			accept();
//			parseExpressão();
//		}
//	}
	private ListaDeIdsNode parseListaDeIds() {	//	<lista-de-ids> ::= id ( , id )*
		ListaDeIdsNode primelistAST =null, ultimolistAST = null, listAST;
		Token idAST;
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
	private void parseLiteral() {	//	<literal> ::= <bool-lit> | int-lit | float-lit 
		if (currentToken.getType() == Token.TRUE || currentToken.getType() == Token.FALSE) 
			parseBoolLit();
		else if (currentToken.getType() == Token.INTLITERAL)
			accept();
		else if (currentToken.getType() == Token.FLOATLITERAL)
			accept();
		else {
			System.out.println("ERROR - SYNTAX\nUnexpected token read: [" + currentToken.getSpelling() +
					"] (token type " + currentToken.getType() + "), in line " + currentToken.getLine() + 
					" column "+ currentToken.getColumn() + 
					", while it was expected either a(n) \"" + 
					Token.spellings[Token.TRUE] + "\" (token type " + Token.TRUE + "), \"" +
					Token.spellings[Token.FALSE] + "\" (token type " + Token.FALSE + "), \"" +
					Token.spellings[Token.INTLITERAL] + "\" (token type " + Token.INTLITERAL + ") or \"" +
					Token.spellings[Token.FLOATLITERAL] + "\" (token type " + Token.FLOATLITERAL + ")." 
					);
		}	
	}
	private void parseOpAd() {	// 	<op-ad> ::= + | - | or
		switch(currentToken.getType()) {
			case Token.OPSUM: case Token.OPSUB: case Token.OR:
				accept();
				break;
			default:
				System.out.println("ERROR - SYNTAX\nUnexpected token read: [" + currentToken.getSpelling() +
						"] (token type " + currentToken.getType() + "), in line " + currentToken.getLine() + 
						" column "+ currentToken.getColumn() + 
						", while it was expected either a(n) \"" + 
						Token.spellings[Token.OPSUM] + "\" (token type " + Token.OPSUM + "), \"" +
						Token.spellings[Token.OPSUB] + "\" (token type " + Token.OPSUB + ") or \"" +
						Token.spellings[Token.OR] + "\" (token type " + Token.OR + ")." 
						);
		}		
	}
	private void parseOpMul() {	//	<op-mul> ::= *  | /  | and
		switch(currentToken.getType()) {
			case Token.OPMULT: case Token.OPDIV: case Token.AND:
				accept();
				break;
			default:
				System.out.println("ERROR - SYNTAX\nUnexpected token read: [" + currentToken.getSpelling() +
						"] (token type " + currentToken.getType() + "), in line " + currentToken.getLine() + 
						" column "+ currentToken.getColumn() + 
						", while it was expected either a(n) \"" + 
						Token.spellings[Token.OPMULT] + "\" (token type " + Token.OPMULT + "), \"" +
						Token.spellings[Token.OPDIV] + "\" (token type " + Token.OPDIV + ") or \"" +
						Token.spellings[Token.AND] + "\" (token type " + Token.AND + ")." 
						);
		}		
	}
	private OperadorNode parseOpRel() {	//	<op-rel> ::= <  | >  | <=  | >= | = | <>
		OperadorNode opAST;
		Token tokenAST = null;
		switch(currentToken.getType()) {
			case Token.OPLOWERTHN: case Token.OPGREATTHN: case Token.OPLOWOREQ: 
			case Token.OPGREOREQ: case Token.OPEQUAL: case Token.OPDIFF:
				tokenAST = accept();
				break;
			default:
				System.out.println("ERROR - SYNTAX\nUnexpected token read: [" + currentToken.getSpelling() +
						"] (token type " + currentToken.getType() + "), in line " + currentToken.getLine() + 
						" column "+ currentToken.getColumn() + 
						", while it was expected either a(n) \"" + 
						Token.spellings[Token.OPLOWERTHN] + "\" (token type " + Token.OPLOWERTHN + "), \"" +
						Token.spellings[Token.OPGREATTHN] + "\" (token type " + Token.OPGREATTHN + "), \"" +
						Token.spellings[Token.OPLOWOREQ] + "\" (token type " + Token.OPLOWOREQ + "), \"" +
						Token.spellings[Token.OPGREOREQ] + "\" (token type " + Token.OPGREOREQ + "), \"" +
						Token.spellings[Token.OPEQUAL] + "\" (token type " + Token.OPEQUAL + ") or \"" +
						Token.spellings[Token.OPDIFF] + "\" (token type " + Token.OPDIFF + ")." 
						);
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
		ProgramaNode progAST;
		accept(Token.PROGRAM);
		Token idAST = accept(Token.ID);
		accept(Token.SEMICOLON);
		CorpoNode corpoAST = parseCorpo();
		accept(Token.DOT);
		progAST = new ProgramaNode(idAST, corpoAST);
		return progAST;				
	}
	
	private void parseSeletor() {	//	<seletor> ::= ( [ <expressão> ] )*
		while(currentToken.getType() == Token.LBRACKET) {
			accept();
			parseExpressão();
			accept(Token.RBRACKET);
		}
	}
	private void parseTermo() {	//	<termo> ::= <fator> ( <op-mul> <fator> )*
		parseFator();
		while (currentToken.getType() == Token.OPMULT || currentToken.getType() == Token.OPDIV
				|| currentToken.getType() == Token.AND) {
			parseOpMul();
			parseFator();
		}
	}
	private TipoNode parseTipo() {	//	<tipo> ::= <tipo-agregado> | <tipo-simples>
		TipoNode tipoAST;
		if (currentToken.getType() == Token.ARRAY)
			parseTipoAgregado();
		else if (currentToken.getType() == Token.INTEGER || currentToken.getType() == Token.REAL
				|| currentToken.getType() == Token.BOOLEAN)
			parseTipoSimples();
		else {
			System.out.println("ERROR - SYNTAX\nUnexpected token read: [" + currentToken.getSpelling() +
					"] (token type " + currentToken.getType() + "), in line " + currentToken.getLine() + 
					" column "+ currentToken.getColumn() + 
					", while it was expected either a(n) \"" + 
					Token.spellings[Token.ARRAY] + "\" (token type " + Token.ARRAY + "), \"" +
					Token.spellings[Token.INTEGER] + "\" (token type " + Token.INTEGER + "), \"" +
					Token.spellings[Token.REAL] + "\" (token type " + Token.REAL + ") or \"" +
					Token.spellings[Token.BOOLEAN] + "\" (token type " + Token.BOOLEAN + ")." 
					);
		}
		return TipoNode;
	}
	private void parseTipoAgregado() {	//	<tipo-agregado> ::= 
										//		array [ int-lit .. int-lit ] of <tipo>
		accept(Token.ARRAY);
		accept(Token.LBRACKET);
		accept(Token.INTLITERAL);
		accept(Token.TILL);
		accept(Token.INTLITERAL);
		accept(Token.RBRACKET);
		accept(Token.OF);
		parseTipo();
	}
	private void parseTipoSimples() {	//	<tipo-simples> ::= 
										//		integer | real 	| boolean
		switch(currentToken.getType()) {
			case Token.INTEGER: case Token.REAL: case Token.BOOLEAN:
				accept();
				break;
			default:
				System.out.println("ERROR - SYNTAX\nUnexpected token read: [" + currentToken.getSpelling() +
						"] (token type " + currentToken.getType() + "), in line " + currentToken.getLine() + 
						" column "+ currentToken.getColumn() + 
						", while it was expected either a(n) \"" + 
						Token.spellings[Token.INTEGER] + "\" (token type " + Token.INTEGER + "), \"" +
						Token.spellings[Token.REAL] + "\" (token type " + Token.REAL + ") or \"" +
						Token.spellings[Token.BOOLEAN] + "\" (token type " + Token.BOOLEAN + ")." 
						);
		}		
	}
	private void parseVariável() {	//	<variável> ::= 
									//		id <seletor>
		accept(Token.ID);
		parseSeletor();
	}
	private void parseVazio() {	//	<vazio> ::= 
								//		ε
		
	}
}
