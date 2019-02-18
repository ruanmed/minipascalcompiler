package frontEnd.AST;

import frontEnd.syntaxAnalysis.Visitor;

public class ExpressãoNode extends FatorNode {
	public ExpressãoSimplesNode E1;
	public OperadorNode O; // Operador relacional opcional
	public ExpressãoSimplesNode E2; // Expressão 2  opcional
	
	public void visit(Visitor v) {
		v.visitExpressão(this);
	}
	public ExpressãoNode(ExpressãoSimplesNode E1, OperadorNode O, ExpressãoSimplesNode E2) {
		// TODO Auto-generated constructor stub
		this.E1 = E1;
		this.O = O;
		this.E2 = E2;
	}

}
