package frontEnd.AST;

import frontEnd.syntaxAnalysis.Visitor;

public class SequênciaFatoresNode extends Node {
	public OperadorNode O;
	public FatorNode F;
	public SequênciaFatoresNode próximaS;
	public int tipo;
	
	public void visit(Visitor v) {
		v.visitSequênciaFatores(this);
	}
	public SequênciaFatoresNode(OperadorNode O, FatorNode F, SequênciaFatoresNode próximaS) {
		// TODO Auto-generated constructor stub
		this.O = O;
		this.F = F;
		this.próximaS = próximaS;
	}

}
