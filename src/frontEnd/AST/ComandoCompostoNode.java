package frontEnd.AST;

import frontEnd.syntaxAnalysis.Visitor;

public class ComandoCompostoNode extends ComandoNode {
	public ListaDeComandosNode LC; // Lista de comandos
	
	public void visit(Visitor v) {
		v.visitComandoComposto(this);
	}
	public ComandoCompostoNode(ListaDeComandosNode LC) {
		// TODO Auto-generated constructor stub
		this.LC = LC;
	}
}
