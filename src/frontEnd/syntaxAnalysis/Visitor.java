package frontEnd.syntaxAnalysis;

import frontEnd.AST.*;
import frontEnd.lexicalAnalysis.*;

public interface Visitor {
	public void visitComandoAtribuição(ComandoAtribuiçãoNode CA);
	public void visitComandoComposto(ComandoCompostoNode CC);
	public void visitComandoCondicional(ComandoCondicionalNode CC);
	public void visitComandoIterativo(ComandoIterativoNode CC);
	public void visitComando(ComandoNode C);
	public void visitCorpo(CorpoNode C);
	public void visitDeclaraçãoDeVariável(DeclaraçãoDeVariávelNode D);
	public void visitDeclaração(DeclaraçãoNode D);
	public void visitDeclaraçãoSequencial(DeclaraçãoSequencialNode D);
	public void visitExpressão(ExpressãoNode E);
	public void visitExpressãoSimples(ExpressãoSimplesNode ES);
	public void visitFator(FatorNode F);
	public void visitListaDeComandos(ListaDeComandosNode LC);
	public void visitListaDeIds(ListaDeIdsNode LI);
	public void visitLiteral(LiteralNode L);
	public void visitNode(Node N);
	public void visitOperador(OperadorNode O);
	public void visitPrograma(ProgramaNode P);
	public void visitSeletor(SeletorNode S);
	public void visitSequênciaFatores(SequênciaFatoresNode SF);
	public void visitSequênciaTermos(SequênciaTermosNode ST);
	public void visitTermo(TermoNode T);
	public void visitTipoAgregado(TipoAgregadoNode TA);
	public void visitTipo(TipoNode T);
	public void visitTipoSimples(TipoSimplesNode TS);
	public void visitToken(Token T);
	public void visitVariável(VariávelNode V);
}
