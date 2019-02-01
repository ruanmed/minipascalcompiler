package frontEnd.AST;

public class ComandoIterativoNode extends ComandoNode {
	public ExpressãoNode E; // Condição
	public ComandoNode C; // Comando a ser executando enquanto a condição for verdadeira
	public ComandoIterativoNode() {
		
	}
}
