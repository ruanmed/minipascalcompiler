package analyzeSyntactic;

import analyzeLexical.Token;

public class Scanner {
	
	private char 			currentChar;
	private int 			currentType;
	private StringBuffer 	currentSpelling;
	
	public Scanner(char currentChar, int currentType, StringBuffer currentSpelling) {
		this.currentChar = currentChar;
		this.currentType = currentType;
		this.currentSpelling = currentSpelling;
		
		// est� errado, estou com pregrui�a de colocar todos set's e get's
	}
	
	private void take (char expectedChar) {
		if(currentChar == expectedChar) {
			currentSpelling.append(currentChar);
			//currentChar = proximo caracter;
		}
	}
	
	private void takeIt () {
		currentSpelling.append(currentChar);
		//currentChar = proximo caracter
	}
	
	private boolean isDigit () {
		//retorna verdadeiro se � digito, e falso se n�o
		return true;
	}
	
	private boolean isLetter () {
		// retorna verdadeiro se � letra, falso se n�o
		return true;
	}
	
	private void scanSeparator () {
		
	}
	
	private int scanToken () {
		return 0;
	}
	
	public Token scan () {
		while(	currentChar == '!' 
				|| currentChar == ' ' 
				|| currentChar == '\n')
			scanSeparator();
		
		currentSpelling = new StringBuffer(" ");
		currentType = scanToken(); 
					
			return new Token(currentType, currentSpelling.toString(), 0, 0);
	}
}
