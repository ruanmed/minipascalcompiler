package frontEnd.AST;

import frontEnd.lexicalAnalysis.*;

public class LiteralNode extends FatorNode {
	public Token L; // pode ser bool-lit, int-lit, float-lit
	public LiteralNode(Token L) {
		this.L = L;
	}
}
