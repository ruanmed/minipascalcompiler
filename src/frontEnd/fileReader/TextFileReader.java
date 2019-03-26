package frontEnd.fileReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class TextFileReader {
	BufferedReader text;
	public static final String UTF8_BOM = "\uFEFF";
	public static final char UTF8_SPACE = '\u0020';
	
	public void cabeçalhoErro() {
		System.out.println("!ERRO - LEITURA DE ARQUIVO");
//		System.out.println("-> Linha: " + currentToken.getLine() + ", Posição: " + currentToken.getColumn());
		
	}
	
	public TextFileReader(String path) {
		try  {
			//text = new BufferedReader(new FileReader(path));	
			text = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
		}
		catch(Exception error) {
			cabeçalhoErro();
			System.out.println("-> Não foi possível abrir o arquivo para leitura.");
		}
		
		try {
			text.mark(1000000);//marca a posição inicial da stream
		}
		catch(Exception E) {
			cabeçalhoErro();
			System.out.println("-> Não foi possível configurar a marcação da posição incial do arquivo.");
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
			cabeçalhoErro();
			System.out.println("-> Não foi possível configurar a marcação da posição incial do arquivo.");
		}
		
	}
	public char getNextChar() { 
		char current = (char) -1;
		try {
			current = (char) text.read();
			text.mark(10000000);
		}
		catch (Exception E)	{
			cabeçalhoErro();
			System.out.println("-> Não foi possível ler um dos caracteres do arquivo.");			
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
			cabeçalhoErro();
			System.out.println("-> Não foi possível ler um dos caracteres do arquivo.");
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
				cabeçalhoErro();
				System.out.println("-> Não foi possível ler uma das linhas do arquivo.");
			}
			if (aux != null)
				str = str+aux+"\n";
		} while(aux != null);
		
		reset();
		return str;
	}
	
	public void reset() {
		try {
			text.reset();
		}
		catch(Exception E) {
			cabeçalhoErro();
			System.out.println("-> Não foi possível resetar o posição do buffer do arquivo.");
		}
	}
	
	public void close() {
		try {
			text.close();
		}
		catch(Exception E) {
			cabeçalhoErro();
			System.out.println("-> Não foi possível fechar o arquivo.");
		}
	}
}
