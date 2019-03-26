package frontEnd.lexicalAnalysis;
import frontEnd.fileReader.TextFileReader;

public class Scanner {
	
	private char 			currentChar;
	private int 			currentType;
	private StringBuffer 	currentSpelling;
	private TextFileReader 	fileText;
	int						currentLine, currentColumn;
	public String indent() {
		String retorno = new String("\t");
		return retorno;
	}
	
	public void cabeçalhoErro() {
		System.out.println(indent() + "!ERRO - ANÁLISE LÉXICA");
		System.out.println(indent() + "  * Linha: " + getCurrentLine() + ", Posição: " + getCurrentColumn());
		System.out.print(indent() +   "  └ ");
	}
	
	public Scanner(TextFileReader fileText) {
		this.fileText = fileText;
		setCurrentChar(fileText.getNextChar());
		setCurrentType(0);
		setCurrentSpelling(new StringBuffer(""));
		setCurrentLine(1);
		setCurrentColumn(1);	//	Começa na coluna 1 porque já foi feita a leitura de um caracter
	}
	
	public void setCurrentLine(int currentLine) {
		this.currentLine = currentLine;
	}
	public void setCurrentColumn(int currentColumn) {
		this.currentColumn = currentColumn;
	}
	public int getCurrentLine() {
		return this.currentLine;
	}
	public int getCurrentColumn() {
		return this.currentColumn;
	}	
	public char getCurrentChar() {
		return currentChar;
	}
	public void setCurrentChar(char currentChar) {
		this.currentChar = currentChar;
	}
	
	public char getLookahead(){
		return fileText.lookahead();
	}

	public int getCurrentType() {
		return currentType;
	}

	public void setCurrentType(int currentType) {
		this.currentType = currentType;
	}

	public StringBuffer getCurrentSpelling() {
		return currentSpelling;
	}

	public void setCurrentSpelling(StringBuffer currentSpelling) {
		this.currentSpelling = currentSpelling;
	}
	
	private void take(char expectedChar) {
		if(getCurrentChar() == expectedChar) {
			take();
		}
		else { 	//	Erro de caracter não experado
			cabeçalhoErro();
			System.out.println("Esperado: " + expectedChar 
					+ " na linha " + getCurrentLine() + 
					" coluna "+ getCurrentColumn());
		}
	}
	
	private void take() { 	//	takeIt()
		if(getCurrentChar()=='\n') { //quebra de linha
			setCurrentLine(getCurrentLine()+1);
			setCurrentColumn(1);
		}
		else
			setCurrentColumn(getCurrentColumn()+1);
		
		currentSpelling.append(getCurrentChar());
		setCurrentChar(fileText.getNextChar());
	}
	
	private boolean isDigit (char c) {	//	Verifica se é um digito
		if ((c >= '0' && c <= '9'))
			return true;
		else
			return false;
	}
	private boolean isLetter (char c) {  // Verifica se é uma letra válida
		if ((c >= 'a' && c <='z') || (c >= 'A' && c <='Z'))
			return true;
		else
			return false;
	}
	private boolean isGraphic (char c) { 	//	Verifica se é qualquer caracter gráfico
		if (c < TextFileReader.UTF8_SPACE ||
				(c >= '\u007F' && c <= '\u009F')) 
			return false;
		else
			return true;
	}
	private boolean isControl (char c) { 	//	Verifica se é qualquer caracter gráfico
		if (c < TextFileReader.UTF8_SPACE ||
				(c >= '\u007F' && c <= '\u009F')) 
			return true;
		else
			return false;
	}
	private boolean isEOF(char c) { // Quando o TextFileReader (BufferedReader) chega no final do arquivo ele retorna esse caracter
		return (c == (char) -1);
	}
	
	private void scanSeparator () { 	//	Tratamento de comentários e separadores múltiplos
		switch (getCurrentChar()) {
			case '!':
				take();
//				while (isGraphic(getCurrentChar()) || getCurrentChar() == '\t')	// Ignorar caractere gráfico
				while (getCurrentChar() != '\n') {	// Vai até a quebra de linha
					take();
				}
			case ' ': case '﻿': case '\t': case '\r': case '\n' : case '\u0088' : case '\u0089' :
			case '\u008A' : case '\u000B' : 
				take();
			default:
				// O padrão é dar erro? 
				// Acho que vai entrar em loop infinito quando for um caractere não gráfico
			break;
		}
	}
	
	private int scanToken () {
		if (isLetter(getCurrentChar())) { 	//	Identifica o conjunto de simbolos de caracter
			take();							//	{a,b,...,z,A,B,...,Z}
			while(isLetter(getCurrentChar()) || isDigit(getCurrentChar()))
				take();
			switch (getCurrentSpelling().toString()) {	
				case "true":
					return Token.TRUE;
				case "false":
					return Token.FALSE;
				case "begin":
					return Token.BEGIN;
				case "end":
					return Token.END;
				case "if":
					return Token.IF;
				case "then":
					return Token.THEN;
				case "else":
					return Token.ELSE;
				case "var":
					return Token.VAR;
				case "while":
					return Token.WHILE;
				case "do":
					return Token.DO;
				case "or":
					return Token.OR;
				case "and":
					return Token.AND;
				case "program":
					return Token.PROGRAM;
				case "array":
					return Token.ARRAY;
				case "of":
					return Token.OF;
				case "integer":
					return Token.INTEGER;
				case "real":
					return Token.REAL;
				case "boolean":
					return Token.BOOLEAN;
				default:
					return Token.ID;
			}
		}
		else if (isDigit(getCurrentChar())){	//	Identifica o conjunto de digitos
			take();								//	{0,1,...,9}
			while(isDigit(getCurrentChar()))
					take();
			if (getCurrentChar() == '.') {
				take();
				while(isDigit(getCurrentChar()))
						take();
				return Token.FLOATLITERAL;
			}
			else 
				return Token.INTLITERAL;	
		}
		else if (getCurrentChar() == '.') { 
			take();
			if (getCurrentChar() == '.'){
				take();
				return Token.TILL;	
			} 
			else if (isDigit(getCurrentChar())){
				while (isDigit(getCurrentChar()))
					take();
				return Token.FLOATLITERAL;
			}
			else 
				return Token.DOT;		
		}
		else if (getCurrentChar() == '[') {
			take();
			return Token.LBRACKET;
		}
		else if (getCurrentChar() == ']') {
			take();
			return Token.RBRACKET;
		}
		else if (getCurrentChar() == '(') {
			take();
			return Token.LPARENTHESIS;
		}
		else if (getCurrentChar() == ')') {
			take();
			return Token.RPARENTHESIS;
		}
		else if (getCurrentChar() == ',') {
			take();
			return Token.COMMA;
		}
		else if (getCurrentChar() == ':') {
			take();
			if (getCurrentChar() == '=') {
				take();
				return Token.OPATTRIB;
			}
			return Token.COLON;
		}
		else if (getCurrentChar() == ';') {
			take();
			return Token.SEMICOLON;
		}
		else if (getCurrentChar() == '+') {
			take();
			return Token.OPSUM;
		}
		else if (getCurrentChar() == '-') {
			take();
			return Token.OPSUB;
		}
		else if (getCurrentChar() == '*') {
			take();
			return Token.OPMULT;
		}
		else if (getCurrentChar() == '/') {
			take();
			return Token.OPDIV;
		}
		else if (getCurrentChar() == '<') {
			take();
			if (getCurrentChar() == '=') {
				take();
				return Token.OPLOWOREQ;
			}
			else if (getCurrentChar() == '>') {
				take();
				return Token.OPDIFF;
			}
			else 
				return Token.OPLOWERTHN;
		}
		else if (getCurrentChar() == '>') {
			take();
			if (getCurrentChar() == '=') {
				take();
				return Token.OPGREOREQ;
			}
			else 
				return Token.OPGREATTHN;
		}
		else if (getCurrentChar() == '=') {
			take();
			return Token.OPEQUAL;
		}
		else if (isEOF(getCurrentChar())) {
			take();
			return Token.EOF;
		}
		else { 	// 	Erro no análise léxica, 
				// 	Não foi possível classificar como token da linguagem
			cabeçalhoErro();
			System.out.println("O caractere lido [" + getCurrentChar() 
			+ "] (código de caractere " + (int) getCurrentChar() + ") "
					+ "não pode ser utilizado no Mini-Pascal."
			);
			/*
			System.out.println("ERROR - LEXICAL\nThe character read: [" + getCurrentChar() 
					+ "] (character code " + (int) getCurrentChar() + "), in line " + getCurrentLine() + 
					" column "+ (getCurrentColumn()-currentSpelling.length()-1) + 
					" cannot be used in Mini-pascal."
					);
					*/
			take();
			return Token.ERRO;
			//			return -1; 	//	Tem que ser reportado erro léxico
		}
	}
	
	public Token scan() {
//		char character = '﻿'; 
//		char character = getCurrentChar();
//		int ascii = (int) character;
//		
//		System.out.println("ASCII: " + ascii);
//		System.out.println(isEOF(getCurrentChar()));
//		if(isEOF(getCurrentChar())) 
//			return null;
//		else {
			while(	getCurrentChar() == '!' 
					|| getCurrentChar() == ' '
					|| getCurrentChar() == '\r'
					|| getCurrentChar() == '\n'
					|| getCurrentChar() == '\t'
					)
				scanSeparator();
			  
//			if(isEOF(getCurrentChar()))
//				return null; 
//			else {
				setCurrentSpelling(new StringBuffer(""));
				setCurrentType(scanToken()); 
				return new Token(	getCurrentType(), 
						currentSpelling.toString(), 
						getCurrentLine(), 
						getCurrentColumn() -currentSpelling.length()
						);
				
//			}
//		}	
	}		
}
