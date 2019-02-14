package frontEnd.AST;

public class CorpoNode extends Node {
	public DeclaraçãoNode D;
	public ComandoNode CC;
	public CorpoNode(DeclaraçãoNode D, ComandoNode CC) {
		// TODO Auto-generated constructor stub
		this.D = D;
		this.CC = CC;
	}

}
