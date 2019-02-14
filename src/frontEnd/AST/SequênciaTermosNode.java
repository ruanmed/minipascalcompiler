package frontEnd.AST;

public class SequênciaTermosNode extends Node {
	public OperadorNode O; 
	public TermoNode T;
	public SequênciaTermosNode próximaS; // próxima opcional
	public SequênciaTermosNode(OperadorNode O, TermoNode T, SequênciaTermosNode próximaS) {
		// TODO Auto-generated constructor stub
		this.O = O;
		this.T = T;
		this.próximaS = próximaS;
	}

}
