package frontEnd.AST;

public class TermoNode extends Node {
	public FatorNode F;
	public SequênciaFatoresNode SF;
	public TermoNode(FatorNode F, SequênciaFatoresNode SF) {
		this.F = F;
		this.SF = SF;
	}
}
