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

import java.util.List;
import java.util.ArrayList;

import java.util.Stack;


public class Printer implements Visitor {
	
	public int i = 0;	    
	public Stack<Boolean> levelt;
	public char end[] = {'├','└'};
	
	public Printer() {
		// TODO Auto-generated constructor stub
//		levelt.push(Boolean.valueOf(true));
		levelt = new Stack<Boolean>();
	}
	
	void inc(boolean link) {
		if (link) levelt.add(Boolean.valueOf(true));
		else {
			levelt.add(Boolean.valueOf(false));
//			System.out.println ("here");
		}
		i++;
	}
	void dec() {
		levelt.pop();
		i--;
	}
	
	void indent() {
		System.out.print (String.format("%03d", i) + " ");

//		System.out.println (levelt);
		for (int j=0; j<i; j++) {
			if (j == i-1) {
				if (levelt.get(j))
					System.out.print (end[0]); // ├ 
				else
					System.out.print (end[1]); // ├ 
			}
			else {
				if (levelt.get(j))
					System.out.print ("|");
				else
					System.out.print (" ");
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
		indent();
			System.out.println("[COM-ATRIBUIÇÃO]");
		inc(false);
			if (CA.V != null) CA.V.visit(this);
			inc(true);
				indent();
					System.out.println("=");
			dec();
			inc(false);
				if (CA.E != null) CA.E.visit(this);
			dec();
		dec();
	}

	@Override
	public void visitComandoComposto(ComandoCompostoNode CC) {
		// TODO Auto-generated method stub
		if (CC.LC != null) CC.LC.visit(this);
	}

	@Override
	public void visitComandoCondicional(ComandoCondicionalNode CC) {
		// TODO Auto-generated method stub
		
		indent();
			System.out.println("[COM-CONDICIONAL]");
		inc(true);
			indent();
				System.out.println("[EXPRESSÃO]");
			inc(false);
				if (CC.E != null) CC.E.visit(this);
			dec();
		
			indent();
				System.out.println("[C1]");
			inc(false);
				if (CC.C1 != null) CC.C1.visit(this);
			dec();
			
			dec();
			inc(false);
			indent();
				System.out.println("[C2]");
			inc(false);
				if (CC.C2 != null) CC.C2.visit(this);
			dec();
		
		dec();
	}

	@Override
	public void visitComandoIterativo(ComandoIterativoNode CC) {
		// TODO Auto-generated method stub
		indent();
			System.out.println("[COM-ITERATIVO]");
		inc(true);
		
			indent();
				System.out.println("[E]");
			inc(false);
				if (CC.E != null) CC.E.visit(this);
			dec();
		
		dec();
		inc(false);
		
//			indent();
//				System.out.println("[C]");
//			inc(false);
				if (CC.C != null) CC.C.visit(this);
//			dec();
		dec();
	}

	@Override
	public void visitComando(ComandoNode C) {
		// TODO Auto-generated method stub
		if (C != null) C.visit(this);
	}

	@Override
	public void visitCorpo(CorpoNode C) {
		// TODO Auto-generated method stub
		indent();
			System.out.println("[CORPO]");
		inc(true);
			if (C.D != null) C.D.visit(this);
		dec();
		inc(false);
			if (C.CC != null) C.CC.visit(this);
		dec();
	}

	@Override
	public void visitDeclaraçãoDeVariável(DeclaraçãoDeVariávelNode D) {
		// TODO Auto-generated method stub
		indent();
			System.out.println("[DECL-VAR]");
		inc(true);
			if (D.T != null) D.T.visit(this);
				
			if (D.LI != null) {
				if (D.LI.próximaLI == null) {
					dec();
					inc(false);
				}
				D.LI.visit(this);
			}
		dec();
		inc(false);
			if (D.próximaD != null) {
				D.próximaD.visit(this);
			}
		dec();
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
			inc(false);
			ES.ST.visit(this);
			dec();
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
		indent();
			System.out.println("[LC]");
		if (LC.próximaLC == null) inc(false);
		else inc(true);
		
//			indent();
//				System.out.println("[C]");
//			inc(false);
			
				if (LC.C != null) LC.C.visit(this);
//			dec();
			dec();
			inc(false);
			if (LC.próximaLC != null) {
				if (LC.próximaLC.C != null) {
//					indent();
//						System.out.println("[próximaLC]");			
//					inc(false);
						LC.próximaLC.visit(this);
//					dec();
				}
			}
		dec();
	}

	@Override
	public void visitListaDeIds(ListaDeIdsNode LI) {
		// TODO Auto-generated method stub		
		indent();
		System.out.println("[LI]");
		inc(false);
			if (LI.I != null) {
				indent();
				System.out.println(LI.I.getSpelling());
			}
			
			if (LI.próximaLI != null) {
				
				inc(false);
				LI.próximaLI.visit(this);
				dec();
			}
		dec();
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
				System.out.println("[PROGRAMA]");
			inc(true);
				indent();
					if (P.N != null) System.out.println("TOKEN " + P.N.getSpelling());
			dec();
			inc(false);
				if (P.C != null) P.C.visit(this);
			dec();
		}
	}

	@Override
	public void visitSeletor(SeletorNode S) {
		// TODO Auto-generated method stub
		if (S.E != null) S.E.visit(this);
		if (S.próximoS != null) {
			inc(false);
			S.próximoS.visit(this);
			dec();
		}
	}

	@Override
	public void visitSequênciaFatores(SequênciaFatoresNode SF) {
		// TODO Auto-generated method stub
		if (SF.O != null) SF.O.visit(this);
		if (SF.F != null) SF.F.visit(this);
		if (SF.próximaS != null) {
			inc(false);
			SF.próximaS.visit(this);
			dec();
		}
	}

	@Override
	public void visitSequênciaTermos(SequênciaTermosNode ST) {
		// TODO Auto-generated method stub
		
		dec();
		inc(true);
		if (ST.O != null) ST.O.visit(this);
		dec();
		inc(false);		
		if (ST.T != null) ST.T.visit(this);
		if (ST.próximaS != null) {
			inc(false);
			ST.próximaS.visit(this);
			dec();
		}
	}

	@Override
	public void visitTermo(TermoNode T) {
		// TODO Auto-generated method stub
		if (T.F != null) T.F.visit(this);
		if (T.SF != null) {
			inc(true);
			T.SF.visit(this);
			dec();
		}
	}

	@Override
	public void visitTipoAgregado(TipoAgregadoNode TA) {
		// TODO Auto-generated method stub
		indent();
		System.out.println("[TIPO-AGREGADO]");
		inc(false);
			if (TA.T != null) TA.T.visit(this);
			if (TA.INDEX_1 != null) TA.INDEX_1.visit(this);
			if (TA.INDEX_2  != null) TA.INDEX_2.visit(this);
		dec();
	}

	@Override
	public void visitTipo(TipoNode T) {
		// TODO Auto-generated method stub
		if (T != null) T.visit(this);
	}

	@Override
	public void visitTipoSimples(TipoSimplesNode TS) {
		// TODO Auto-generated method stub
		indent();
		System.out.println("[TIPO-SIMPLES]");
		inc(false);
			if (TS.N != null) TS.N.visit(this);
		dec();
	}
	
	@Override
	public void visitToken(Token T) {
		// TODO Auto-generated method stub
		if (T != null) {
			indent();
//			System.out.println("[" + T.getType() + "] " + T.getSpelling());
			System.out.println(T.getSpelling());
		}
	}

	@Override
	public void visitVariável(VariávelNode V) {
		// TODO Auto-generated method stub
		if (V.N != null) V.N.visit(this);
		inc(true);
		if (V.S != null) V.S.visit(this);
		dec();
	}

}
