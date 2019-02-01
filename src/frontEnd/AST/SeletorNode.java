package frontEnd.AST;

public class SeletorNode extends Node { // É uma lista de seletores -  [<exp1>] [<exp2>] ...
	public ExpressãoNode E;
	public SeletorNode próximoS;
	public SeletorNode() {
		
	}
}
