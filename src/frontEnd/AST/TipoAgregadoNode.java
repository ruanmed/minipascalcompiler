package frontEnd.AST;

import frontEnd.lexicalAnalysis.Token;
import frontEnd.syntaxAnalysis.Visitor;

public class TipoAgregadoNode extends TipoNode {
	public Token INDEX_1;
	public Token INDEX_2;
	public TipoNode T;
	
	public void visit(Visitor v) {
		v.visitTipoAgregado(this);
	}
	public TipoAgregadoNode(Token INDEX_1, Token INDEX_2, TipoNode T) {
		this.INDEX_1 = INDEX_1;
		this.INDEX_2 = INDEX_2;
		this.T = T;
	}
}
