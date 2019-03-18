package frontEnd.lexicalAnalysis;

import frontEnd.syntaxAnalysis.Visitor;

public class Token{
	
	private int 	type;
	private String	spelling;
	private int		line,
					column;
	
	public Token(int type, String spelling, int line, int column){
		setType(type);
		setSpelling(spelling);
		setLine(line);
		setColumn(column);
	}
	
	public Token(Token to_copy){
		setType(to_copy.type);
		setSpelling(to_copy.spelling);
		setLine(to_copy.line);
		setColumn(to_copy.column);
	}

	public void visit(Visitor v){
		v.visitToken(this);
	}
	
	private void setType(int newType){
		this.type	= newType;
	}
	
	private void setSpelling(String newValue){
		this.spelling	= newValue;
	}
	
	private void setLine(int newLine){
		this.line	= newLine; 
	}
	
	private void setColumn(int newColumn){
		this.column	= newColumn;
	}
	
	public int getType() {
		return this.type;
	}
	
	public String getSpelling() {
		return this.spelling;
	}
	
	public int getLine() {
		return this.line;
	}
	
	public int getColumn() {
		return this.column;
	}
	
	public String toString() {
		return new String("\nType = " + getType() +
				" (" + spellings[getType()] + ")" +
				"\nSpelling = " + getSpelling() +
				"\nLength = " + getSpelling().length() +
				"\nHash = " + getSpelling().hashCode() +
				"\nCurrent Line = " + getLine() +
				"\nCurrent Column = " + getColumn()
				);
		
//		int ascii = (int) character;
	}
	
	public boolean equals (Object other) {
		Token otherToken = (Token) other;
		return (this.getType() == otherToken.getType() && this.getSpelling().equals(otherToken.getSpelling()));
	}
	
	public final static int
		ID				= 0,	// identifier
		TRUE			= 1,	// true
		FALSE			= 2,	// false
		BEGIN			= 3,	// begin
		END				= 4,	// end
		IF				= 5,	// if
		THEN			= 6,	// then
		ELSE			= 7,	// else
		INTLITERAL		= 8,	// int-lit
		FLOATLITERAL	= 9,	// float-lit
		VAR				= 10,	// var
		WHILE			= 11,	// while
		DO				= 12,	// do
		OR				= 13,	// OR op
		AND				= 14,	// AND op
		PROGRAM			= 15,	// program
		ARRAY			= 16,	// array
		OF				= 17,	// of
		INTEGER			= 18,	// integer
		REAL			= 19,	// real
		BOOLEAN			= 20,	// boolean
		DOT				= 21,	// .
		TILL			= 22,	// ..
		LBRACKET		= 23,	// [
		RBRACKET		= 24,	// ]
		LPARENTHESIS	= 25,	// (
		RPARENTHESIS	= 26,	// )
		COMMA			= 27, 	// ,
		COLON			= 28, 	// : 
		SEMICOLON		= 29, 	// ;
		OPSUM			= 30, 	// +
		OPSUB			= 31, 	// -
		OPMULT			= 32,	// *
		OPDIV			= 33, 	// /
		OPLOWERTHN		= 34, 	// <
		OPGREATTHN		= 35,	// >
		OPLOWOREQ		= 36, 	// <=
		OPGREOREQ		= 37, 	// >=
		OPEQUAL			= 38, 	// =
		OPDIFF			= 39, 	// <>
		OPATTRIB		= 40, 	// :=
		
		EOF				= 41, 	// EOF - Fim do arquivo
		ERRO			= 42;
		
	
	public final static String[] spellings = {
			"id",
			"true",
			"false",
			"begin",
			"end",
			"if",
			"then",
			"else",
			"int-lit",
			"float-lit",
			"var",
			"while",
			"do",
			"or",
			"and",
			"program",
			"array",
			"of",
			"integer",
			"real",
			"boolean",
			".",
			"..",
			"[",
			"]",
			"(",
			")",
			",",
			":",
			";",
			"+",
			"-",
			"*",
			"/",
			"<",
			">",
			"<=",
			">=",
			"=",
			"<>",
			":=",
			"<eof>",
			"error"
			//"<eot>"
	};
	
}
