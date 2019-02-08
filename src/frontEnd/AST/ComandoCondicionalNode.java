package frontEnd.AST;

public class ComandoCondicionalNode extends ComandoNode {
	public ExpressãoNode E;
	public ComandoNode C1, C2; // Comandos, se verdadeiro C1, se falso C2 (não obrigatório)
	public ComandoCondicionalNode(ExpressãoNode E, ComandoNode C1, ComandoNode C2) {
//		super(null);
		this.E = E;
		this.C1 = C1;
		this.C2 = C2;
	}
//	public ComandoCondicionalNode(ExpressãoNode E, ComandoNode C1, ComandoNode C2, ComandoNode próximoC) {
////		super(próximoC);
//		this.E = E;
//		this.C1 = C1;
//		this.C2 = C2;
//	}
}
