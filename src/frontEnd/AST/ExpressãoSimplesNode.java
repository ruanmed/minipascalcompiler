package frontEnd.AST;

import frontEnd.syntaxAnalysis.Visitor;

public class ExpressãoSimplesNode extends Node {
	public TermoNode T;
	public SequênciaTermosNode ST;
	public int tipo;
	
	public void visit(Visitor v){
		v.visitExpressãoSimples(this);
	}
	public ExpressãoSimplesNode(TermoNode T, SequênciaTermosNode ST) {
		this.T = T;
		this.ST = ST;
	}
}
