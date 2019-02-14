package frontEnd.AST;

public class ExpressãoSimplesNode extends Node {
	public TermoNode T;
	public SequênciaTermosNode ST;
	public ExpressãoSimplesNode(TermoNode T, SequênciaTermosNode ST) {
		this.T = T;
		this.ST = ST;
	}
}
