package frontEnd.AST;

import frontEnd.syntaxAnalysis.Visitor;

public class ComandoIterativoNode extends ComandoNode {
	public ExpressãoNode E; // Condição
	public ComandoNode C; // Comando a ser executando enquanto a condição for verdadeira
	
	public void visit(Visitor v){
		v.visitComandoIterativo(this);
	}
	public ComandoIterativoNode(ExpressãoNode E, ComandoNode C) {
//		super(null);
		this.E = E;
		this.C = C;
	}
//	public ComandoIterativoNode(ExpressãoNode E, ComandoNode C, ComandoNode próximoC) {
//		super(próximoC);
//		this.E = E;
//		this.C = C;
//	}
}
