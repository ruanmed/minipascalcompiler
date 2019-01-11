package frontEnd.fileReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class TextFileReader {
	BufferedReader text;
	public static final String UTF8_BOM = "\uFEFF";
	public TextFileReader(String path) {
		System.out.println("The path read is -->"+path);
		try  {
			//text = new BufferedReader(new FileReader(path));	
			text = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
		}
		catch(Exception error) {
			System.out.println("Error reading the file.");
		}
		
		try {
			text.mark(1000000);//marca a posição inicial da stream
		}
		catch(Exception E) {
			System.out.println("Error at setting initial mark");
		}
		try {	//	Eliminando caracter Unicode Character 'ZERO WIDTH NO-BREAK SPACE' (U+FEFF)
				// 	O notepad coloca ele no começo do arquivo com a codificação UTF8-BOM
			String current = new Character((char) text.read()).toString();
//			System.out.println("\n AQUIIII ->>>" + current + "<<---- \n");
			if (current.hashCode() != UTF8_BOM.hashCode())
				text.reset();
			else 
				text.mark(10000000);
		}
		catch(Exception E) {
			System.out.println("Error at setting initial mark");
		}
		
	}
	public char getNextChar() { 
		char current = (char) -1;
		try {
			current = (char) text.read();
			text.mark(2);
		}
		catch (Exception E)	{
			System.out.println("Error on character read from file");			
		}
		return current;
	}
	public char lookahead()	{
		char current = (char) -1;
		try	{
			current = (char) text.read();
			text.reset(); 
		}
		catch (Exception E) {
			System.out.println("Error on character read from file");
		}
		return current;
	}
	
	public String toString() {
		String str = new String();
		String aux = new String();
		do {
			try {
				aux = text.readLine();
			}
			catch(Exception E) { // ADICIONAR MENSAGEM PARA ARQUIVO INEXISTENTE
				System.out.println("Error on line read from file");
			}
			if (aux != null)
				str = str+aux+"\n";
		} while(aux != null);
		
		try {
			text.reset();
		}
		catch(Exception E) {
			System.out.println("Error while reseting file stream position.");
		}
		return str;
	}
}
