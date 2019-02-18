package frontEnd.AST;

import frontEnd.syntaxAnalysis.Visitor;

public abstract class DeclaraçãoNode extends Node {
	public DeclaraçãoNode próximaD;
	
	public void visit(Visitor v) {
		v.visitDeclaração(this);
	}
	public DeclaraçãoNode() {
		// TODO Auto-generated constructor stub
		this.próximaD = null;
	}
	
	public DeclaraçãoNode(DeclaraçãoNode próximaD) {
		// TODO Auto-generated constructor stub
		this.próximaD = próximaD;
	}

}
