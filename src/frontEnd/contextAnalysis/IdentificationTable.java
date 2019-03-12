package frontEnd.contextAnalysis;
import frontEnd.AST.*;
import frontEnd.lexicalAnalysis.*;

import frontEnd.syntaxAnalysis.*;
import java.util.ArrayList;

import java.util.Stack;
import java.util.List;

public class IdentificationTable {

	private Stack<TableLine> table = new Stack<TableLine>(); 
	private int currentLevel = 0;
	
	public IdentificationTable() {
		// TODO Auto-generated constructor stub
		currentLevel = 0;
	}
	
	public void enter(String identifier, Node attribute) {
		TableLine novaLinha = new TableLine();
		novaLinha.attribute = attribute;
		novaLinha.identifier = identifier;
		novaLinha.level = currentLevel;
		int c = 0;
		for (; c < table.size(); c++) {
			TableLine currentLine = table.get(c); 
			if (currentLine.level >= currentLevel && currentLine.identifier == identifier) {
				System.out.println("ERRO - CONTEXTO\nIdentificador [" + identifier +
						"] já declarado.");
				break;
			}
		}
		// Podemos verificar se aconteceu algum erro e não adicionar, se necessário
		// if (c == table.size()) // somente adicionar se não tiver ocorrido nenhum erro de contexto
		table.add(novaLinha);
	}
	
	public Node retrieve(String identifier) {
		Node attribute = null;
		int c = table.size();
		for (; c > 0; c--) {
			TableLine currentLine = table.get(c-1);
			if (currentLine.level <= currentLevel && currentLine.identifier == identifier) {
				attribute = currentLine.attribute;
				break;
			}
		}
		if (c != 0) // Caso não ache o identificador no loop acima, indica erro de contexto
			System.out.println("ERRO - CONTEXTO\nIdentificador [" + identifier + "] não declarado.");
		return attribute;
	}
}
