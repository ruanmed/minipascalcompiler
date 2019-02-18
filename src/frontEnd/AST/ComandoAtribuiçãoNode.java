package frontEnd.AST;

import frontEnd.syntaxAnalysis.Visitor;

public class ComandoAtribuiçãoNode extends ComandoNode {
	public VariávelNode V;
	public ExpressãoNode E;
	
	public void visit(Visitor v) {
		v.visitComandoAtribuição(this);
	}
	public ComandoAtribuiçãoNode(VariávelNode V, ExpressãoNode E) {
		// TODO Auto-generated constructor stub
		this.V = V;
		this.E = E;
	}
}
