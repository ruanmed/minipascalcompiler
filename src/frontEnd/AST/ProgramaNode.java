package frontEnd.AST;

import frontEnd.lexicalAnalysis.Token;

public class ProgramaNode extends Node {
	public Token N;
	public CorpoNode C;
	
	public ProgramaNode(Token N, CorpoNode C) {
		this.N = N;
		this.C = C;
	}
}
