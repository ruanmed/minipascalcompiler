package frontEnd.AST;

import frontEnd.syntaxAnalysis.Visitor;

public abstract class FatorNode extends Node {
	
	public int tipo;
	
	public void visit(Visitor v) {
		v.visitFator(this);
	}
	public FatorNode() {
		
	}
}
