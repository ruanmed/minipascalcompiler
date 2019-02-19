import frontEnd.fileReader.TextFileReader;
import frontEnd.lexicalAnalysis.Scanner;
import frontEnd.lexicalAnalysis.Token;
import frontEnd.syntaxAnalysis.Parser;
import frontEnd.syntaxAnalysis.Printer;
import frontEnd.AST.*;

public class MpC {

	public static void main(String[] args) {
		String text = new String();
		if(args[0].equals("-help"))	{
			System.out.println("To compile type java MpC and the directory of the code file");
			System.out.println("If there's spaces in the folder's names it's mandatory to put the directory between quotation marks");
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
			}
			else {
				System.out.println("The file read was empty.");
			}	
		}
	}
}
