package frontEnd.contextAnalysis;
import frontEnd.AST.*;
import frontEnd.lexicalAnalysis.*;

public class TableLine {
	public int level;
	public Token identifier; // Alternamos para Token para dar mais informações na geração de erros.
	public boolean used; // se o identificador declarado foi utilizado ou não
	public Node attribute;
	
	public TableLine() {
		// TODO Auto-generated constructor stub
	}
	
	public String toString() {
		String resultado = new String((new Integer(level)).toString() + "\t " + 
							identifier.getSpelling() + "\t\t " + 
							used +  "\t\t " + attribute);
		return resultado;
	}
}
