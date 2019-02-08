package frontEnd.AST;

import frontEnd.lexicalAnalysis.Token;

public class ListaDeIdsNode extends Node {
	public Token I;
	public ListaDeIdsNode próximaLI;
	public ListaDeIdsNode(Token I) {
		this.I = I;
		this.próximaLI = null;
	}
	public ListaDeIdsNode(Token I, ListaDeIdsNode próximaLI) {
		this.I = I;
		this.próximaLI = próximaLI;
	}
}
