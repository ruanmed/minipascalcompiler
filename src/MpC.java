import frontEnd.fileReader.TextFileReader;
//import frontEnd.lexicalAnalysis.Scanner;
//import frontEnd.lexicalAnalysis.Token;
import frontEnd.syntaxAnalysis.Parser;
import frontEnd.syntaxAnalysis.Printer;
import frontEnd.contextAnalysis.Checker;
import frontEnd.AST.*;

public class MpC {

	public static void main(String[] args) {
		String text = new String();
		if(args[0].equals("--help"))	{
			System.out.println("Para compilar digite java Mpc e o caminho do arquivo de código.");
			System.out.println("Se existirem espaços no caminho do arquivo é necessária colocar o memso entre aspas duplas.");
		}
		else {
			TextFileReader reader = new TextFileReader(args[0]);
			text = reader.toString();
			
			if(!text.isEmpty()) {
//				System.out.println("The file read follows:");
//				Scanner scanner = new Scanner(reader);
//				Token currentToken;
//				currentToken = scanner.scan();
//				while (currentToken.getType() != Token.EOF) {
//					System.out.println(currentToken.toString());
//					currentToken = scanner.scan();
//				}
				
				
//				System.out.println(text);
				Parser parser = new Parser(reader);
				ProgramaNode P;
				P = parser.parse();
				Printer printer = new Printer();
				printer.print(P);
				Checker checker = new Checker();
				checker.check(P);
				System.out.println(checker.getTabelaDeIdentificação().toString());
			}
			else {
				System.out.println("O arquivo lido estava vazio.");
			}	
		}
	}
}
