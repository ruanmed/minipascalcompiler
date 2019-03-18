package frontEnd.contextAnalysis;
import frontEnd.AST.*;
import frontEnd.lexicalAnalysis.Token;
import frontEnd.syntaxAnalysis.*;

public class Checker implements Visitor {

	private TabelaDeIdentificação idTable = new TabelaDeIdentificação();;
	
	public Checker() {
		// TODO Auto-generated constructor stub
		this.idTable = new TabelaDeIdentificação();
	}

	public void check (ProgramaNode P) {
		System.out.println ("---> Iniciando identificação de nomes");
		P.visit(this);
	}
	
	@Override
	public void visitComandoAtribuição(ComandoAtribuiçãoNode CA) {
		// TODO Auto-generated method stub
		if (CA.V != null) CA.V.visit(this);

		if (CA.E != null) CA.E.visit(this);
	}

	@Override
	public void visitComandoComposto(ComandoCompostoNode CC) {
		// TODO Auto-generated method stub
		if (CC.LC != null) CC.LC.visit(this);
	}

	@Override
	public void visitComandoCondicional(ComandoCondicionalNode CC) {
		// TODO Auto-generated method stub
		if (CC.E != null) CC.E.visit(this);
		if (CC.C1 != null) CC.C1.visit(this);
		if (CC.C2 != null) CC.C2.visit(this);
	}

	@Override
	public void visitComandoIterativo(ComandoIterativoNode CC) {
		// TODO Auto-generated method stub
		if (CC.E != null) CC.E.visit(this);
		if (CC.C != null) CC.C.visit(this);
	}

	@Override
	public void visitComando(ComandoNode C) {
		// TODO Auto-generated method stub
		if (C != null) C.visit(this);
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
		ListaDeIdsNode temp = D.LI;
		Node atributo = D;
		while (temp != null) {
			this.idTable.enter(temp.I, atributo);
			temp = temp.próximaLI;
		}
		if (D.próximaD != null) {
			D.próximaD.visit(this);
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
		if (E.E1 != null) E.E1.visit(this);
		if (E.O != null) E.O.visit(this);
		if (E.E2 != null) E.E2.visit(this);
	}

	@Override
	public void visitExpressãoSimples(ExpressãoSimplesNode ES) {
		// TODO Auto-generated method stub
		if (ES.T != null) ES.T.visit(this);
		if (ES.ST != null) {
			ES.ST.visit(this);
		}
	}

	@Override
	public void visitFator(FatorNode F) {
		// TODO Auto-generated method stub
		if (F != null) F.visit(this);
	}

	@Override
	public void visitListaDeComandos(ListaDeComandosNode LC) {
		// TODO Auto-generated method stub

//		if (LC.próximaLC == null) inc(false);
//		else inc(true);
		LC.C.visit(this);
		
		if (LC.próximaLC != null) {
			LC.próximaLC.visit(this);
		}
	}

	@Override
	public void visitListaDeIds(ListaDeIdsNode LI) {
		// TODO Auto-generated method stub		

			if (LI.I != null) {

			}
			
			if (LI.próximaLI != null) {
				LI.próximaLI.visit(this);
			}
	}

	@Override
	public void visitLiteral(LiteralNode L) {
		// TODO Auto-generated method stub
		if (L.L != null) L.L.visit(this);
	}

	@Override
	public void visitNode(Node N) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitOperador(OperadorNode O) {
		// TODO Auto-generated method stub
		if (O.O != null) {
//			System.out.println(O.O.getSpelling());
		}
	}

	@Override
	public void visitPrograma(ProgramaNode P) {
		// TODO Auto-generated method stub
		if (P != null) {
//			if (P.N != null) System.out.println("TOKEN " + P.N.getSpelling());

			if (P.C != null) P.C.visit(this);
		}
	}

	@Override
	public void visitSeletor(SeletorNode S) {
		// TODO Auto-generated method stub
		if (S.E != null) S.E.visit(this);
		if (S.próximoS != null) {
			S.próximoS.visit(this);
		}
	}

	@Override
	public void visitSequênciaFatores(SequênciaFatoresNode SF) {
		// TODO Auto-generated method stub
		if (SF.O != null) SF.O.visit(this);
		if (SF.F != null) SF.F.visit(this);
		if (SF.próximaS != null) {
			SF.próximaS.visit(this);
		}
	}

	@Override
	public void visitSequênciaTermos(SequênciaTermosNode ST) {
		// TODO Auto-generated method stub
		if (ST.O != null) ST.O.visit(this);
		if (ST.T != null) ST.T.visit(this);
		if (ST.próximaS != null) {
			ST.próximaS.visit(this);
		}
	}

	@Override
	public void visitTermo(TermoNode T) {
		// TODO Auto-generated method stub
		if (T.F != null) T.F.visit(this);
		if (T.SF != null) {
			T.SF.visit(this);
		}
	}

	@Override
	public void visitTipoAgregado(TipoAgregadoNode TA) {
		// TODO Auto-generated method stub
		if (TA.T != null) TA.T.visit(this);
		if (TA.INDEX_1 != null) TA.INDEX_1.visit(this);
		if (TA.INDEX_2  != null) TA.INDEX_2.visit(this);
	}

	@Override
	public void visitTipo(TipoNode T) {
		// TODO Auto-generated method stub
		if (T != null) T.visit(this);
	}

	@Override
	public void visitTipoSimples(TipoSimplesNode TS) {
		// TODO Auto-generated method stub
		if (TS.N != null) TS.N.visit(this);
	}
	
	@Override
	public void visitToken(Token T) {
		// TODO Auto-generated method stub
		if (T != null) {
			
		}
	}

	@Override
	public void visitVariável(VariávelNode V) {
		// TODO Auto-generated method stub
		V.declaração = idTable.retrieve(V.N);
//		System.out.println(V.N.toString());
		if (V.N != null) V.N.visit(this);
		if (V.S != null) V.S.visit(this);
	}
	
	public TabelaDeIdentificação getTabelaDeIdentificação() {
		return this.idTable;
	}
}
