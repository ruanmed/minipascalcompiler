package frontEnd.AST;

import frontEnd.syntaxAnalysis.Visitor;

public class DeclaraçãoDeVariávelNode extends DeclaraçãoNode {
	public ListaDeIdsNode LI; // Lista de identificadores
	public TipoNode T;
	
	public void visit(Visitor v) {
		v.visitDeclaraçãoDeVariável(this);
	}
	public DeclaraçãoDeVariávelNode(ListaDeIdsNode LI, TipoNode T, DeclaraçãoNode próximaD) {
		// TODO Auto-generated constructor stub
		super(próximaD);
		this.LI = LI;
		this.T = T;
	}
	public DeclaraçãoDeVariávelNode(ListaDeIdsNode LI, TipoNode T) {
		// TODO Auto-generated constructor stub
		super(null);
		this.LI = LI;
		this.T = T;
	}

}
