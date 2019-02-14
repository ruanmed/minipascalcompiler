package frontEnd.AST;

public class SequênciaFatoresNode extends Node {
	public OperadorNode O;
	public FatorNode F;
	public SequênciaFatoresNode próximaS;
	public SequênciaFatoresNode(OperadorNode O, FatorNode F, SequênciaFatoresNode próximaS) {
		// TODO Auto-generated constructor stub
		this.O = O;
		this.F = F;
		this.próximaS = próximaS;
	}

}
