package frontEnd.contextAnalysis;
import frontEnd.AST.*;
import frontEnd.lexicalAnalysis.*;

import frontEnd.syntaxAnalysis.*;
import java.util.ArrayList;

import java.util.Stack;
import java.util.List;

public class TabelaDeIdentificação {

	private Stack<TableLine> table = new Stack<TableLine>(); 
	private int currentLevel = 0;
	
	public String indent() {
		String retorno = new String("\t");
		return retorno;
	}
	public void cabeçalhoErro() {
		System.out.println(indent() + "!ERRO - ANÁLISE DE CONTEXTO");
		System.out.print(indent() +   "  └ ");
	}
	public void cabeçalhoErro(int linha, int posição) {
		System.out.println(indent() + "!ERRO - ANÁLISE DE CONTEXTO");
		System.out.println(indent() + "  * Linha: " + linha + ", Posição: " + posição);
		System.out.print(indent() +   "  └ ");
	}
	public TabelaDeIdentificação() {
		// TODO Auto-generated constructor stub
		currentLevel = 0;
		table = new Stack<TableLine>();
	}
	
	public void enter(Token identifier, Node attribute) {
		TableLine novaLinha = new TableLine();
		novaLinha.attribute = attribute;
		novaLinha.identifier = identifier;
		novaLinha.level = currentLevel;
		novaLinha.used = false;
		int c = 0;
		for (; c < table.size(); c++) {
			TableLine currentLine = table.get(c); 
			if (currentLine.level >= currentLevel && currentLine.identifier.equals(identifier)) {
				cabeçalhoErro(identifier.getLine(), identifier.getColumn());
				System.out.println("[E2] Identificador [" + identifier.getSpelling() +
						"] foi declarado anteriormente.");
				break;
			}
		}
		// Podemos verificar se aconteceu algum erro e não adicionar, se necessário
		// if (c == table.size()) // somente adicionar se não tiver ocorrido nenhum erro de contexto
		table.add(novaLinha);
	}
	
	public Node retrieve(Token identifier) {
		Node attribute = null;
		int c = table.size();
		for (; c > 0; c--) {
			TableLine currentLine = table.get(c-1);
			if (currentLine.level <= currentLevel && currentLine.identifier.equals(identifier)) {
				attribute = currentLine.attribute;
				currentLine.used = true;
				break;
			}
		}
		if (c == 0) { // Caso não ache o identificador no loop acima, indica erro de contexto
			cabeçalhoErro(identifier.getLine(), identifier.getColumn()); 
			System.out.println("[E1,E3] Identificador [" + identifier.getSpelling() + 
					"] não foi declarado anteriormente.");
		}
		return attribute;
	}
	
	@Override 
	public String toString() {
		String resultado = new String("n\t nível\t identificador\t utilizado\t atributo\n");
		for (int c = 0; c < table.size(); c++) {
			TableLine currentLine = table.get(c); 
			resultado += String.format("%04d", c) + "\t " + currentLine.toString() + "\n";
		}
		return resultado;
	}
	
	public final static int // Definição dos tipos primitivos;
		BOOLEAN = 20,
		INTEGER = 18,
		REAL 	= 19;
}
