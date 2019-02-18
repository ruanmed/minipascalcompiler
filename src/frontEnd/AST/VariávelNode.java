package frontEnd.AST;

import frontEnd.lexicalAnalysis.*;
import frontEnd.syntaxAnalysis.Visitor;

public class VariávelNode extends FatorNode {
	public Token N;
	public SeletorNode S; // É uma lista de seletores
	
	public void visit(Visitor v) {
		v.visitVariável(this);
	}
	public VariávelNode(Token N, SeletorNode S) {
		this.N = N;
		this.S = S;
	}
}
