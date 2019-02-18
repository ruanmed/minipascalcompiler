package frontEnd.AST;

import frontEnd.syntaxAnalysis.Visitor;

public class SequênciaTermosNode extends Node {
	public OperadorNode O; 
	public TermoNode T;
	public SequênciaTermosNode próximaS; // próxima opcional
	
	public void visit(Visitor v) {
		v.visitSequênciaTermos(this);
	}
	public SequênciaTermosNode(OperadorNode O, TermoNode T, SequênciaTermosNode próximaS) {
		// TODO Auto-generated constructor stub
		this.O = O;
		this.T = T;
		this.próximaS = próximaS;
	}

}
