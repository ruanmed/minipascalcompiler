package frontEnd.AST;

import frontEnd.syntaxAnalysis.Visitor;

public class CorpoNode extends Node {
	public DeclaraçãoNode D;
	public ComandoNode CC;
	
	public void visit(Visitor v) {
		v.visitCorpo(this);
	}
	public CorpoNode(DeclaraçãoNode D, ComandoNode CC) {
		// TODO Auto-generated constructor stub
		this.D = D;
		this.CC = CC;
	}
}
