package frontEnd.AST;

import frontEnd.syntaxAnalysis.Visitor;

// Não estamos usando essa declaração sequencial
public class DeclaraçãoSequencialNode extends DeclaraçãoNode {
	public DeclaraçãoNode D_1;
	public DeclaraçãoNode D_2;
	
	public void visit(Visitor v) {
		v.visitDeclaraçãoSequencial(this);
	}
	public DeclaraçãoSequencialNode() {
		// TODO Auto-generated constructor stub
	}

}
