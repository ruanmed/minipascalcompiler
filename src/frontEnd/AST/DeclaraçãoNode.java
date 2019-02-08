package frontEnd.AST;

public abstract class DeclaraçãoNode extends Node {
	public DeclaraçãoNode próximaD;
	
	public DeclaraçãoNode() {
		// TODO Auto-generated constructor stub
		this.próximaD = null;
	}
	
	public DeclaraçãoNode(DeclaraçãoNode próximaD) {
		// TODO Auto-generated constructor stub
		this.próximaD = próximaD;
	}

}
