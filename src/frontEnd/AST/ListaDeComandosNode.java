package frontEnd.AST;

public class ListaDeComandosNode extends Node { // Acredito que isso vai ser inútil.
	public ComandoNode C;
	public ListaDeComandosNode próximaLC;
	public ListaDeComandosNode(ComandoNode C, ListaDeComandosNode próximaLC) {
		this.C = C;
		this.próximaLC = próximaLC;
	}
}
