package frontEnd.AST;

import frontEnd.lexicalAnalysis.*;

public class VariávelNode extends FatorNode {
	public Token N;
	public SeletorNode S; // É uma lista de seletores
	public VariávelNode(Token N, SeletorNode S) {
		this.N = N;
		this.S = S;
	}
}
