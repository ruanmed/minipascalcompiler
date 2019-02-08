package frontEnd.AST;

public class CorpoNode extends Node {
	public DeclaraçãoNode D;
	public ComandoCompostoNode CC;
	public CorpoNode(DeclaraçãoNode D, ComandoCompostoNode CC) {
		// TODO Auto-generated constructor stub
		this.D = D;
		this.CC = CC;
	}

}
