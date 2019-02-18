package frontEnd.AST;

import frontEnd.syntaxAnalysis.Visitor;

public abstract class Node {
	
	public void visit(Visitor v) {
		v.visitNode(this);
	}
}
