package frontEnd.AST;

public class ComandoCondicionalNode extends ComandoNode {
	public ExpressãoNode E;
	public ComandoNode C1, C2; // Comandos, se verdadeiro C1, se falso C2 (não obrigatório)
	public ComandoCondicionalNode() {
		// TODO Auto-generated constructor stub
	}

}
