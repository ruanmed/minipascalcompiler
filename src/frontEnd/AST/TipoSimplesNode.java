package frontEnd.AST;

import frontEnd.lexicalAnalysis.Token;

public class TipoSimplesNode extends TipoNode {
	public Token N;
	public TipoSimplesNode(Token N) {
		this.N = N;
	}
}
