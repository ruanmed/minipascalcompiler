package frontEnd.AST;

import frontEnd.lexicalAnalysis.*;

public class OperadorNode extends Node {
	public Token O;
	public OperadorNode(Token O) {
		this.O = O;
	}
}
