package frontEnd.AST;

import frontEnd.syntaxAnalysis.Visitor;

public class ListaDeComandosNode extends Node { // Acredito que isso vai ser inútil.
	public ComandoNode C;
	public ListaDeComandosNode próximaLC;
	
	public void visit(Visitor v) {
		v.visitListaDeComandos(this);
	}
	public ListaDeComandosNode(ComandoNode C, ListaDeComandosNode próximaLC) {
		this.C = C;
		this.próximaLC = próximaLC;
	}
}
