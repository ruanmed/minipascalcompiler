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

import frontEnd.lexicalAnalysis.Token;

public class Printer implements Visitor {
	
	public int i = 0;
	
	public int mode = 0;
	
	public int level[] = {0,0,0,0,0}; 
	
	public char end[] = {'├','└'};
	
	public Printer() {
		// TODO Auto-generated constructor stub
	}
	
	void indent() {
		System.out.print (i + " ");
		for (int j=0; j<i; j++) {
			if (j == i-1) 
				System.out.print (end[(mode>=1)?1:0]); // ├  └
			else {
				if (j >= (i - mode))
					System.out.print (" ");
				else
					System.out.print ("|");
			}
		}
	}
	
	void indent2() {
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
		
		if (CA.V != null) CA.V.visit(this);
		i++;
		indent();
		System.out.println("=");
		if (CA.E != null) CA.E.visit(this);
		i--;
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
		i++;
		if (CC.C1 != null) CC.C1.visit(this);
		if (CC.C2 != null) CC.C2.visit(this);
		i--;
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
		
		if (D.próximaD == null) 
			mode++;
		
		if (D.T != null) D.T.visit(this);
		i++;		
		if (D.LI != null) D.LI.visit(this);
		i--;
		
		if (D.próximaD == null) 
			mode--;	
		
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
		if (E.E1 != null) E.E1.visit(this);
		if (E.O != null) E.O.visit(this);
		if (E.E2 != null) E.E2.visit(this);
	}

	@Override
	public void visitExpressãoSimples(ExpressãoSimplesNode ES) {
		// TODO Auto-generated method stub
		if (ES.T != null) ES.T.visit(this);
		if (ES.ST != null) {
			i++;
			ES.ST.visit(this);
			i--;
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
		if (LC.C != null) LC.C.visit(this);
		
		if (LC.próximaLC != null) {
//			i++;
			LC.próximaLC.visit(this);
//			i--;
		}
	}

	@Override
	public void visitListaDeIds(ListaDeIdsNode LI) {
		// TODO Auto-generated method stub
		if (LI.próximaLI == null) mode++;
		
		if (LI.I != null) {
			indent();
			System.out.println(LI.I.getSpelling());
		}
		
		if (LI.próximaLI != null) {
			mode++;
			
			i++;
			LI.próximaLI.visit(this);
			i--;
		}
		mode--;
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
			indent();
			System.out.println(O.O.getSpelling());
		}
	}

	@Override
	public void visitPrograma(ProgramaNode P) {
		// TODO Auto-generated method stub
		if (P != null) {
			indent();
			if (P.N != null) System.out.println("program " + P.N.getSpelling());
			i++;
			if (P.C != null) P.C.visit(this);
			i--;
		}
	}

	@Override
	public void visitSeletor(SeletorNode S) {
		// TODO Auto-generated method stub
		if (S.E != null) S.E.visit(this);
		if (S.próximoS != null) {
			i++;
			S.próximoS.visit(this);
			i--;
		}
	}

	@Override
	public void visitSequênciaFatores(SequênciaFatoresNode SF) {
		// TODO Auto-generated method stub
		if (SF.O != null) SF.O.visit(this);
		if (SF.F != null) SF.F.visit(this);
		if (SF.próximaS != null) {
			i++;
			SF.próximaS.visit(this);
			i--;
		}
	}

	@Override
	public void visitSequênciaTermos(SequênciaTermosNode ST) {
		// TODO Auto-generated method stub
		if (ST.O != null) ST.O.visit(this);
		if (ST.T != null) ST.T.visit(this);
		if (ST.próximaS != null) {
			i++;
			ST.próximaS.visit(this);
			i--;
		}
	}

	@Override
	public void visitTermo(TermoNode T) {
		// TODO Auto-generated method stub
		if (T.F != null) T.F.visit(this);
		if (T.SF != null) {
			i++;
			T.SF.visit(this);
			i--;
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
			indent();
			System.out.println(T.getSpelling());
		}
	}

	@Override
	public void visitVariável(VariávelNode V) {
		// TODO Auto-generated method stub
		if (V.N != null) V.N.visit(this);
		i++;
		if (V.S != null) V.S.visit(this);
		i--;
	}

}
