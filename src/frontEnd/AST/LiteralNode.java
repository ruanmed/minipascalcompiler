package frontEnd.AST;

import frontEnd.lexicalAnalysis.*;
import frontEnd.syntaxAnalysis.Visitor;

public class LiteralNode extends FatorNode {
	public Token L; // pode ser bool-lit, int-lit, float-lit
	
	public void visit(Visitor v) {
		v.visitLiteral(this);
	}
	public LiteralNode(Token L) {
		this.L = L;
	}
}
