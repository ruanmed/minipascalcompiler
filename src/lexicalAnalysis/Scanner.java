package lexicalAnalysis;
import fileReader.TextFileReader;

public class Scanner {
	
	private char 			currentChar;
	private int 			currentType;
	private StringBuffer 	currentSpelling;
	private TextFileReader 	fileText;
	int						currentLine,currentColumn;
		
	public Scanner(TextFileReader fileText){
		this.fileText = fileText;
		setCurrentLine(0);
		setCurrentColumn(0);
		setCurrentChar(fileText.getNextChar());
	}
	
	public void setCurrentLine(int currentLine) 
	{
		this.currentLine = currentLine;
	}
	public void setCurrentColumn(int currentColumn) 
	{
		this.currentColumn = currentColumn;
	}
	public int getCurrentLine() 
	{
		return this.currentLine;
	}
	public int getCurrentColumn() 
	{
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
		if(getCurrentChar() == expectedChar) 
		{
			currentSpelling.append(getCurrentChar());
			setCurrentChar(fileText.getNextChar());
			setCurrentColumn(getCurrentColumn()+1);
		}
		else { 	//	Erro de caracter não experado
			System.out.println("ERROR\n Expected: " + expectedChar 
					+ " in line " + getCurrentLine() + 
					" column "+ getCurrentColumn());
		}
	}
	
	private void take() { 	//	takeIt()
		currentSpelling.append(getCurrentChar());
		setCurrentChar(fileText.getNextChar());
		
		if(getCurrentChar()=='\n' ||getCurrentChar()=='\r' )//quebra de linha
		{
			setCurrentLine(getCurrentLine()+1);
			setCurrentColumn(0);
		}
		else
			setCurrentColumn(getCurrentColumn()+1);
	}
	
	private boolean isDigit (char c) {	//	Verifica se é um digito
		if ((c >= '0' && c <'9'))
			return true;
		else
			return false;
	}
	
	private boolean isLetter (char c) {
		if ((c >= 'a' && c <'z') || (c >= 'A' && c <'Z'))
			return true;
		else
			return false;
	}
	private boolean isGraphic (char c) { 	//	Verifica se é qualquer caracter gráfico
		if (c < ' ')
			return false;
		else
			return true;
	}
	private boolean isEOF(char c)
	{
		return (c == (char) -1);
	}
	
	private void scanSeparator () { 	//	Tratamento de comentários e separadores múltiplos
		switch (getCurrentChar()) {
			case '!':
				take();
				while (isGraphic(getCurrentChar()))	// Ignorar caracter gráfico
					take();
			break;
			case ' ': case '\r': case '\n' :
				take();
			break;
		}
	}
	
	private int scanToken () {
		if (isLetter(getCurrentChar())) { 	//	Identifica o conjunto de simbolos de caracter
			take();							//	{a,b,...,z,A,B,...,Z}
			while(isLetter(getCurrentChar()) || isDigit(getCurrentChar()))
				take();
			return Token.ID;
		}
		else if (isDigit(getCurrentChar())){	//	Identifica o conjunto de digitos
			take();								//	{0,1,...,9}
			while(isDigit(getCurrentChar()))
					take();
			if (getCurrentChar() == '.' && getLookahead() != '.') {
				take();
				while(isDigit(getCurrentChar()))
						take();
				return Token.FLOATLITERAL;
			}
			else 
				return Token.INTLITERAL;	
		}
		else if (getCurrentChar() == '.') { 
			if (getLookahead() == '.'){
				take();
				take();
				return Token.TILL;	
			} 
			else if (isDigit(getLookahead())){
				do { 
					take();
				}
				while (isDigit(getCurrentChar()));
				return Token.FLOATLITERAL;
			}
			else {
				take();
				return Token.DOT;
			}				
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
				return Token.ATTOP;
			}
			return Token.COLON;
		}
		else if (getCurrentChar() == ';') {
			take();
			return Token.SEMICOLON;
		}
		else if (getCurrentChar() == '+') {
			take();
			return Token.SUMOP;
		}
		else if (getCurrentChar() == '-') {
			take();
			return Token.SUBOP;
		}
		else if (getCurrentChar() == '*') {
			take();
			return Token.MULOP;
		}
		else if (getCurrentChar() == '/') {
			take();
			return Token.DIVOP;
		}
		else if (getCurrentChar() == '<') {
			take();
			if (getCurrentChar() == '=') {
				take();
				return Token.LOEOP;
			}
			else if (getCurrentChar() == '>') {
				take();
				return Token.DIFOP;
			}
			else 
				return Token.MULOP;
		}
		else if (getCurrentChar() == '>') {
			take();
			if (getCurrentChar() == '=') {
				take();
				return Token.GREOP;
			}
			else 
				return Token.GRTOP;
		}
		else if (getCurrentChar() == '=') {
			take();
			return Token.EQTOP;
		}
		return -1; 	//	Tem que ser reportado erro léxico
	}
	
	public Token scan () 
	{
		if(isEOF(getCurrentChar()))
			return null;
		else
		{
			while(	getCurrentChar() == '!' 
					|| getCurrentChar() == ' '
					|| getCurrentChar() == '\r'
					|| getCurrentChar() == '\n')
				scanSeparator();
			
			currentSpelling = new StringBuffer(" ");
			currentType = scanToken(); 
						
			return new Token(currentType, currentSpelling.toString(), getCurrentLine(), getCurrentColumn()-currentSpelling.length());
		}	
	}		
}
