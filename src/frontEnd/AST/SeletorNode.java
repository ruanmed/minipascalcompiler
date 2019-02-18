package frontEnd.AST;

import frontEnd.syntaxAnalysis.Visitor;

public class SeletorNode extends Node { // É uma lista de seletores -  [<exp1>] [<exp2>] ...
	public ExpressãoNode E;
	public SeletorNode próximoS;
	
	public void visit(Visitor v) {
		v.visitSeletor(this);
	}
	public SeletorNode(ExpressãoNode E, SeletorNode próximoS) {
		this.E = E;
		this.próximoS = próximoS;
	}
}
