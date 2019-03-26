
import frontEnd.fileReader.TextFileReader;
import frontEnd.lexicalAnalysis.Scanner;
import frontEnd.lexicalAnalysis.Token;
//import frontEnd.lexicalAnalysis.Token;
import frontEnd.syntaxAnalysis.Parser;
import frontEnd.syntaxAnalysis.Printer;
import frontEnd.contextAnalysis.Checker;
import frontEnd.AST.*;

public class Compiler {
	private String filePath; 		//	Caminho completo do arquivo
	private TextFileReader reader; 	//	Leitura do arquivo com código fonte
	private Scanner scanner;		//	-l Análise léxica
	private Parser parser;			//	-s Análise sintática
	private Printer printer;		// 	-a Impressão AST
	private Checker checker;		// 	-c Análise de contexto
//	private Coder coder;			// 	-g Geração de código
	private ProgramaNode P;
	
	public Compiler() {
		
	}
	public void setFilePath(String path) {
		filePath = new String(path);
	}
	
	public void resetReader() {
		String text = new String();
		reader = new TextFileReader(filePath);
		text = reader.toString();
		if (text.isEmpty()) {
			System.out.println("O arquivo lido estava vazio.");
		}
	}
	
	public void análiseLéxica() {
		System.out.println("O caminho do arquivo-fonte especificado é ---> " + filePath);
		resetReader(); // Reinicia a leitura do arquivo
		System.out.println ("---> ANÁLISE LÉXICA - INÍCIO");
		scanner = new Scanner(reader);
		Token temp = scanner.scan();
		while (Token.EOF != temp.getType())
			temp = scanner.scan();
		reader.reset();
		scanner.toString();
		System.out.println ("<--- ANÁLISE LÉXICA - FIM");
		System.out.println ("");
	}
	
	public void análiseSintática() {
		análiseLéxica();
		resetReader();
		System.out.println ("---> ANÁLISE SINTÁTICA - INÍCIO");
		reader.reset();
		parser = new Parser(reader);
		P = parser.parse();
		System.out.println ("<--- ANÁLISE SINTÁTICA - FIM");
		System.out.println ("");
	}
	
	public void impressãoAST() {
		análiseSintática();
		System.out.println ("---> IMPRESSÃO DA AST - INÍCIO");
		printer = new Printer();
		printer.print(P);
		System.out.println ("<--- IMPRESSÃO DA AST - FIM");
		System.out.println ("");
	}
	
	public void análiseContexto() {
		impressãoAST();
		System.out.println ("---> ANÁLISE DE CONTEXTO - INÍCIO");
		checker = new Checker();
		checker.check(P);
		System.out.println ("<--- ANÁLISE DE CONTEXTO - FIM");
		System.out.println ("");
	}
	
	public void geraçãoCódigo() {
		análiseContexto();
		System.out.println ("---> GERAÇÃO DE CÓDIGO - INÍCIO");
//		coder = new Coder();
//		coder.code(P);
		System.out.println ("<--- GERAÇÃO DE CÓDIGO - FIM");
		System.out.println ("");
	}
}