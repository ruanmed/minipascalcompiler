package frontEnd.syntaxAnalysis;

import frontEnd.AST.ComandoAtribuiçãoNode;
import frontEnd.AST.ComandoCompostoNode;
import frontEnd.AST.ComandoCondicionalNode;
import frontEnd.AST.ComandoIterativoNode;
import frontEnd.AST.ComandoNode;
import frontEnd.AST.CorpoNode;
import frontEnd.AST.DeclaraçãoDeVariávelNode;
import frontEnd.AST.DeclaraçãoNode;
import frontEnd.AST.DeclaraçãoSequencialNode;
import frontEnd.AST.ExpressãoNode;
import frontEnd.AST.ExpressãoSimplesNode;
import frontEnd.AST.FatorNode;
import frontEnd.AST.ListaDeComandosNode;
import frontEnd.AST.ListaDeIdsNode;
import frontEnd.AST.LiteralNode;
import frontEnd.AST.Node;
import frontEnd.AST.OperadorNode;
import frontEnd.AST.ProgramaNode;
import frontEnd.AST.SeletorNode;
import frontEnd.AST.SequênciaFatoresNode;
import frontEnd.AST.SequênciaTermosNode;
import frontEnd.AST.TermoNode;
import frontEnd.AST.TipoAgregadoNode;
import frontEnd.AST.TipoNode;
import frontEnd.AST.TipoSimplesNode;
import frontEnd.AST.VariávelNode;

public class Printer implements Visitor {
	
	public int i = 0;
	
	public Printer() {
		// TODO Auto-generated constructor stub
	}
	
	void indent() {
		System.out.print (i + " ");
		for (int j=0; j<i; j++)
			System.out.print ("|");
//		System.out.print ("->");
	}
	
	public void print(ProgramaNode P) {
		System.out.println ("---> Iniciando impressão da árvore");
		P.visit(this);
	}

	@Override
	public void visitComandoAtribuição(ComandoAtribuiçãoNode CA) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitComandoComposto(ComandoCompostoNode CC) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitComandoCondicional(ComandoCondicionalNode CC) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitComandoIterativo(ComandoIterativoNode CC) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitComando(ComandoNode C) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitCorpo(CorpoNode C) {
		// TODO Auto-generated method stub
		if (C.D != null) C.D.visit(this);
		if (C.CC != null) C.CC.visit(this);
	}

	@Override
	public void visitDeclaraçãoDeVariável(DeclaraçãoDeVariávelNode D) {
		// TODO Auto-generated method stub
		indent();
		if (D.T != null) D.T.visit(this);
		i++;
		if (D.LI != null) D.LI.visit(this);
		i--;
		if (D.próximaD != null) {
			i++;
			D.próximaD.visit(this);
			i--;
		}
		
	}

	@Override
	public void visitDeclaração(DeclaraçãoNode D) {
		// TODO Auto-generated method stub
		if (D != null) D.visit(this);
	}

	@Override
	public void visitDeclaraçãoSequencial(DeclaraçãoSequencialNode D) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitExpressão(ExpressãoNode E) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitExpressãoSimples(ExpressãoSimplesNode ES) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitFator(FatorNode F) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitListaDeComandos(ListaDeComandosNode LC) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitListaDeIds(ListaDeIdsNode LI) {
		// TODO Auto-generated method stub
		if (LI.I != null) {
			indent();
			System.out.println(LI.I.getSpelling());
		}
		if (LI.próximaLI != null) {
			i++;
			LI.próximaLI.visit(this);
			i--;
		}
		
	}

	@Override
	public void visitLiteral(LiteralNode L) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitNode(Node N) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitOperador(OperadorNode O) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitPrograma(ProgramaNode P) {
		// TODO Auto-generated method stub
		if (P != null) {
			indent();
			if (P.N != null) System.out.println(P.N.getSpelling());
			i++;
			if (P.C != null) P.C.visit(this);
			i--;
		}
	}

	@Override
	public void visitSeletor(SeletorNode S) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitSequênciaFatores(SequênciaFatoresNode SF) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitSequênciaTermos(SequênciaTermosNode ST) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitTermo(TermoNode T) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitTipoAgregado(TipoAgregadoNode TA) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitTipo(TipoNode T) {
		// TODO Auto-generated method stub
		if (T != null) T.visit(this);
	}

	@Override
	public void visitTipoSimples(TipoSimplesNode TS) {
		// TODO Auto-generated method stub
		if (TS.N != null) System.out.println(TS.N.getSpelling());
	}

	@Override
	public void visitVariável(VariávelNode V) {
		// TODO Auto-generated method stub

	}

}
