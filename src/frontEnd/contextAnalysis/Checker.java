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
		// REGRA T1. O tipo da expressão avaliada deve ser compatível com o tipo da variável.
		// REGRA E1. A variável deve ter sido declarada previamente.
		if (CA.V != null) CA.V.visit(this); // Isto cobre a regra E1.
		
		if (CA.E != null) CA.E.visit(this);
		boolean erro = false;
		switch (CA.V.tipo) {
			case TabelaDeIdentificação.BOOLEAN:
				if (CA.E.tipo != TabelaDeIdentificação.BOOLEAN)
					erro = true;
				break;
			case TabelaDeIdentificação.INTEGER:
				if (CA.E.tipo != TabelaDeIdentificação.INTEGER)
					erro = true;
				break;
			case TabelaDeIdentificação.REAL:
				erro = true;
				break;
			default:
				break;
		}
		if (erro) System.out.println("ERRO - CONTEXTO\nAtribuição com tipos incompatíveis"
				+ " na linha " + CA.V.N.getLine() + " coluna " + CA.V.N.getColumn() + "." 
				+ "\nA variável " + CA.V.N.getSpelling() + " possui tipo " + CA.V.tipo + " [" + Token.spellings[CA.V.tipo] + "]" 
				+ " enquanto a expressão possui tipo " + CA.E.tipo + " [" + Token.spellings[CA.E.tipo] + "].");
	}

	@Override
	public void visitComandoComposto(ComandoCompostoNode CC) {
		// TODO Auto-generated method stub
		if (CC.LC != null) CC.LC.visit(this);
	}

	@Override
	public void visitComandoCondicional(ComandoCondicionalNode CC) {
		// TODO Auto-generated method stub
		// REGRA T2. O tipo da expressão avaliada deve ser um valor lógico (booleano).
		if (CC.E != null) CC.E.visit(this);
		if (CC.E.tipo != TabelaDeIdentificação.BOOLEAN) {
			System.out.println("ERRO - CONTEXTO\nTipos incompatíveis"
					+ " na linha " + " coluna " + " do comando condicional." 
					+ "\nA expressão possui tipo " + CC.E.tipo + " [" + Token.spellings[CC.E.tipo] + "]" 
					+ " enquanto era esperado o tipo " + TabelaDeIdentificação.BOOLEAN + " [" + Token.spellings[TabelaDeIdentificação.BOOLEAN] + "].");
		}
		if (CC.C1 != null) CC.C1.visit(this);
		if (CC.C2 != null) CC.C2.visit(this);
	}

	@Override
	public void visitComandoIterativo(ComandoIterativoNode CC) {
		// TODO Auto-generated method stub
		// REGRA T5. O tipo da expressão avaliada deve ser um valor lógico (booleano).
		if (CC.E != null) CC.E.visit(this);
		if (CC.E.tipo != TabelaDeIdentificação.BOOLEAN) {
			System.out.println("ERRO - CONTEXTO\nTipos incompatíveis"
					+ " na linha " + " coluna " + " do comando iterativo." 
					+ "\nA expressão possui tipo " + CC.E.tipo + " [" + Token.spellings[CC.E.tipo] + "]" 
					+ " enquanto era esperado o tipo " + TabelaDeIdentificação.BOOLEAN + " [" + Token.spellings[TabelaDeIdentificação.BOOLEAN] + "].");
		}
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
		// REGRA E2. Os identificadores presentes na lista de ids não podem ter sido declarados
		// anteriormente no mesmo bloco da declaração atual
		ListaDeIdsNode temp = D.LI;
		Node atributo = D;
		while (temp != null) {
			this.idTable.enter(temp.I, atributo); // Se já tiverem sido declarados, informará erro.
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
		// REGRA T3. Os tipos de ambas expressões simples avaliadas devem ser iguais
		if (E.E1 != null) E.E1.visit(this);
//		if (E.O != null) E.O.visit(this);
//		if (E.E2 != null) E.E2.visit(this);
		E.tipo = E.E1.tipo;	// Caso padrão tem o mesmo tipo da Expressão 1
		
		if (E.O != null && E.E2 != null) {
			E.E2.visit(this);
			if (E.E1.tipo != E.E2.tipo) {
				System.out.println("ERRO - CONTEXTO\nOperandos"
						+ " na linha " + E.O.O.getLine() + " coluna " + E.O.O.getColumn() 
						+ " possuem tipos incompatíveis para o operador relacional " + E.O.O.getSpelling() + " ." 
						+ "\nO primeiro operando possui tipo " + E.E1.tipo + " [" + Token.spellings[E.E1.tipo] + "]" 
						+ " enquanto o segundo operando possui tipo " + E.E2.tipo + " [" + Token.spellings[E.E2.tipo] + "].");
			}
			switch (E.O.O.getType()) {
				case Token.OPLOWERTHN: 
				case Token.OPGREATTHN: 
				case Token.OPLOWOREQ: 
				case Token.OPGREOREQ: 
				case Token.OPEQUAL: 
				case Token.OPDIFF:
					E.tipo = TabelaDeIdentificação.BOOLEAN;
				default:
			}
		}
	}

	@Override
	public void visitExpressãoSimples(ExpressãoSimplesNode ES) {
		// TODO Auto-generated method stub
		// REGRA T4. O tipo da expressão simples avaliada deve ser igual ao tipo do termo avaliado.
		if (ES.T != null) {
			ES.T.visit(this);
			ES.tipo = ES.T.tipo; // Caso padrão tem o mesmo tipo do primeiro Termo
		}
		
		if (ES.ST != null) {
			ES.ST.visit(this);
			if (ES.T.tipo != ES.ST.tipo) {	// REGRA T4
				System.out.println("ERRO - CONTEXTO\nOperandos"
						+ " na linha " + ES.ST.O.O.getLine() + " coluna " + ES.ST.O.O.getColumn() 
						+ " possuem tipos incompatíveis para o operador " + ES.ST.O.O.getSpelling() + " ." 
						+ "\nO primeiro operando possui tipo " + ES.T.tipo + " [" + Token.spellings[ES.T.tipo] + "]" 
						+ " enquanto o segundo operando possui tipo " + ES.ST.tipo + " [" + Token.spellings[ES.ST.tipo] + "].");
			}
			switch (ES.ST.O.O.getType()) {
				case Token.OPSUM: 
				case Token.OPSUB:
					ES.tipo = ES.T.tipo;
					break;
				case Token.OR:
					ES.tipo = TabelaDeIdentificação.BOOLEAN;
					break;
				default:
			}
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
//		if (L.L != null) L.L.visit(this);
		L.tipo = L.L.getType();	//	O tipo do literal é o tipo do token
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
			Node atributo = P;
			this.idTable.enter(P.N, atributo); // Se já tiverem sido declarados, informará erro.

			if (P.C != null) P.C.visit(this);
		}
	}

	@Override
	public void visitSeletor(SeletorNode S) {
		// TODO Auto-generated method stub
		// REGRA T6. O tipo da expressão avaliada entre colchetes deve ser o tipo simples integer.
		if (S.E != null) S.E.visit(this);
		if (S.E.tipo != TabelaDeIdentificação.INTEGER) {
			System.out.println("ERRO - CONTEXTO\nTipos incompatíveis"
					+ " na linha " + " coluna " + " do índice do seletor." 
					+ "\nA expressão possui tipo " + S.E.tipo + " [" + Token.spellings[S.E.tipo] + "]" 
					+ " enquanto era esperado o tipo " + TabelaDeIdentificação.INTEGER + " [" + Token.spellings[TabelaDeIdentificação.INTEGER] + "].");
		}
		if (S.próximoS != null) {
			S.próximoS.visit(this);
		}
	}

	@Override
	public void visitSequênciaFatores(SequênciaFatoresNode SF) {
		// TODO Auto-generated method stub
		if (SF.O != null) SF.O.visit(this);
		if (SF.F != null) {
			SF.F.visit(this);
			SF.tipo = SF.F.tipo;
		}
		if (SF.próximaS != null) {
			SF.próximaS.visit(this);
			if (SF.F.tipo != SF.próximaS.tipo) {	// REGRA T4
				System.out.println("ERRO - CONTEXTO\nOperandos"
						+ " na linha " + SF.próximaS.O.O.getLine() + " coluna " + SF.próximaS.O.O.getColumn() 
						+ " possuem tipos incompatíveis para o operador " + SF.próximaS.O.O.getSpelling() + " ." 
						+ "\nO primeiro operando possui tipo " + SF.F.tipo + " [" + Token.spellings[SF.F.tipo] + "]" 
						+ " enquanto o segundo operando possui tipo " + SF.próximaS.tipo + " [" + Token.spellings[SF.próximaS.tipo] + "].");
			}
			switch (SF.próximaS.O.O.getType()) {
				case Token.OPSUM: 
				case Token.OPSUB:
					SF.tipo = SF.F.tipo;
					break;
				case Token.OR:
					SF.tipo = TabelaDeIdentificação.BOOLEAN;
					break;
				default:
			}
		}
	}

	@Override
	public void visitSequênciaTermos(SequênciaTermosNode ST) {
		// TODO Auto-generated method stub
		if (ST.O != null) ST.O.visit(this);
		if (ST.T != null) {
			ST.T.visit(this);
			ST.tipo = ST.T.tipo; // Caso padrão
		}		
		if (ST.próximaS != null) {
			ST.próximaS.visit(this);
			if (ST.T.tipo != ST.próximaS.tipo) {	// REGRA T4
				System.out.println("ERRO - CONTEXTO\nOperandos"
						+ " na linha " + ST.próximaS.O.O.getLine() + " coluna " + ST.próximaS.O.O.getColumn() 
						+ " possuem tipos incompatíveis para o operador " + ST.próximaS.O.O.getSpelling() + " ." 
						+ "\nO primeiro operando possui tipo " + ST.T.tipo + " [" + Token.spellings[ST.T.tipo] + "]" 
						+ " enquanto o segundo operando possui tipo " + ST.próximaS.tipo + " [" + Token.spellings[ST.próximaS.tipo] + "].");
			}
			switch (ST.próximaS.O.O.getType()) {
				case Token.OPSUM: 
				case Token.OPSUB:
					ST.tipo = ST.T.tipo;
					break;
				case Token.OR:
					ST.tipo = TabelaDeIdentificação.BOOLEAN;
					break;
				default:
			}
		}
	}

	@Override
	public void visitTermo(TermoNode T) {
		// TODO Auto-generated method stub
		//REGRA T7. O tipo do termo avaliado deve ser igual ao tipo do fator avaliado.
		if (T.F != null) {
			T.F.visit(this);
			T.tipo = T.F.tipo; // Caso padrão
//			T.tipo =
//			if (T.F instanceof VariávelNode)
		}
		
		if (T.SF != null) {
			T.SF.visit(this);
			if (T.F.tipo != T.SF.tipo) {	// REGRA T4
				System.out.println("ERRO - CONTEXTO\nOperandos"
						+ " na linha " + T.SF.O.O.getLine() + " coluna " + T.SF.O.O.getColumn() 
						+ " possuem tipos incompatíveis para o operador " + T.SF.O.O.getSpelling() + " ." 
						+ "\nO primeiro operando possui tipo " + T.F.tipo + " [" + Token.spellings[T.F.tipo] + "]" 
						+ " enquanto o segundo operando possui tipo " + T.SF.tipo + " [" + Token.spellings[T.SF.tipo] + "].");
			}
			switch (T.SF.O.O.getType()) {
				case Token.OPMULT: 
				case Token.OPDIV:
					T.tipo = T.F.tipo;
					break;
				case Token.AND:
					T.tipo = TabelaDeIdentificação.BOOLEAN;
					break;
				default:
			}
		}
	}

	@Override
	public void visitTipoAgregado(TipoAgregadoNode TA) {
		// TODO Auto-generated method stub
		// REGRA T8.1 Ambos literais devem ser int-literal.
		// REGRA T8.2 O literal mais à esquerda deve ser menor ou igual ao literal mais à direita.
		// T8.1 já foi garantida pelas adapatações da gramática da linguagem.
		if (TA.T != null) TA.T.visit(this);
		if (TA.INDEX_1 != null) TA.INDEX_1.visit(this);
		if (TA.INDEX_2  != null) TA.INDEX_2.visit(this);
		if (Integer.parseInt(TA.INDEX_1.getSpelling()) > Integer.parseInt(TA.INDEX_2.getSpelling())) {
			System.out.println("ERRO - CONTEXTO\nÍndices inválidos"
					+ " na linha " + " coluna " + " na declaração do tipo agregado.");
		}
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
		// REGRA E3. Toda variável deve ter sido declarada previamente
		// REGRA T9. Caso o seletor não seja vazio, o identificador deve ser referente a uma
		// variável de tipo agregado declarada anteriormente.
		V.declaração = idTable.retrieve(V.N);	// Responsável pelas regras E1 e E3.
		if (V.declaração instanceof DeclaraçãoDeVariávelNode) {
			TipoNode ti = ((DeclaraçãoDeVariávelNode) V.declaração).T;

			if (ti instanceof TipoAgregadoNode) {
				TipoNode temp = ((TipoAgregadoNode) ti).T;
				while (temp instanceof TipoAgregadoNode) { // Vamos chegar no tipo primitivo do array
					temp = ((TipoAgregadoNode) temp).T;
				}
				V.tipo = ((TipoSimplesNode) temp).N.getType();
				// Se for um tipo agregado e não houverem seletores eu não sei o que deve acontecer
				if (V.S == null)  { // REGRA T9
//					System.out.println("ERRO - CONTEXTO\nO tipo da variável1 " + V.N.getSpelling()
//					+ " na linha " + V.N.getLine() + " coluna " + V.N.getColumn() 
//					+ " deve ser um array.");
//					+ "\nO primeiro operando possui tipo " + ST.T.tipo + " [" + Token.spellings[ST.T.tipo] + "]" 
//					+ " enquanto o segundo operando possui tipo " + ST.próximaS.tipo + " [" + Token.spellings[ST.próximaS.tipo] + "].");
		
				}
				else {
					V.S.visit(this);
				}
			}
			else if (ti instanceof TipoSimplesNode) {
//				System.out.println("TIPO SIMPLES");
				V.tipo = ((TipoSimplesNode) ti).N.getType(); 
				// Se for um tipo simples e houver um seletor, então deve retornar erro.
				if (V.S != null) { // REGRA T9
					System.out.println("ERRO - CONTEXTO\nO tipo da variável " + V.N.getSpelling()
					+ " na linha " + V.N.getLine() + " coluna " + V.N.getColumn() 
					+ " deve ser um array.");
//					+ "\nO primeiro operando possui tipo " + ST.T.tipo + " [" + Token.spellings[ST.T.tipo] + "]" 
//					+ " enquanto o segundo operando possui tipo " + ST.próximaS.tipo + " [" + Token.spellings[ST.próximaS.tipo] + "].");
				}
			} 
//				else if (ti instanceof TipoAgregadoNode) {
//					// Se for um tipo agregado e não tiver seletor, deve retornar erro.
//	//				V.tipo = ((TipoAgregadoNode) ti).T
//					// Pensando em criar um loop aqui, pq as visitar nos tipos do tipo agregado vão ser recursivas
//				}
		}
//		System.out.println(V.N.toString());
//		if (V.N != null) V.N.visit(this);
		if (V.S != null) V.S.visit(this);
	}
	
	public TabelaDeIdentificação getTabelaDeIdentificação() {
		return this.idTable;
	}
}

//1.3.1  Regras de escopo
//
//----> REGRA E1. A variável deve ter sido declarada previamente.
//
//----> REGRA E2. Os identificadores presentes na lista de ids não podem ter sido declarados
//anteriormente no mesmo bloco da declaração atual.
//
//----> REGRA E3. Toda variável deve ter sido declarada previamente.
//
//
//1.3.2  Regras de tipos
//----> REGRA T1. O tipo da expressão avaliada deve ser compatível com o tipo da variável.
//
//----> REGRA T2. O tipo da expressão avaliada deve ser um valor lógico (booleano).
//
//----> REGRA T3. Os tipos de ambas expressões simples avaliadas devem ser iguais.
//
//REGRA T4. O tipo da expressão simples avaliada deve ser igual ao tipo do termo
//
//----> REGRA T5. O tipo da expressão avaliada deve ser um valor lógico (booleano).
//
//----> REGRA T6. O tipo da expressão avaliada deve ser o tipo simples integer.
//
//REGRA T7. O tipo do termo avaliado deve ser igual ao tipo do fator avaliado.
//
//----> REGRA T8.1 Ambos literais devem ser int-literal.
//----> REGRA T8.2 O literal mais à esquerda deve ser menor ou igual ao literal mais à direita.
//
//REGRA T9. Caso o seletor não seja vazio, o identificador deve ser referente a uma
//variável de tipo agregado declarada anteriorm
