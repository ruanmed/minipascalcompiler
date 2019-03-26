public class MPC {
	private static void help() {
		System.out.println("COMPILADOR DE MINI-PASCAL");
		System.out.println("Opções de compilação disponíveis:");
		System.out.println("\t -l \t Análise léxica.");
		System.out.println("\t -s \t Análise sintática.");
		System.out.println("\t -a \t Impressão da AST.");
		System.out.println("\t -c \t Análise de contexto.");
		System.out.println("\t -g \t Geração de código.");
		System.out.println("");
		System.out.println("Para compilar é necessário digitar \"java MPC\", seguido da opção de compilação"
				+ " e o caminho completo para o arquivo de código-fonte Mini-Pascal.");
		System.out.println("Se existirem espaços no caminho do arquivo é necessário"
				+ " colocar o mesmo entre aspas duplas.");

		System.out.println("Segue abaixo exemplos de comandos para compilação até a"
				+ " fase de Análise sintática:");
		System.out.println("\t java MPC -s \"C:\\Users\\User\\Documents\\arquivofonte.mpascal\"");
		System.out.println("\t java MPC -s \"/home/Documentos/arquivofonte.mpascal\"");
	}
	
	public static void main(String[] args) {
		String path = new String();
		Compiler compiler = new Compiler();
		if (args.length <= 1 || args[0].equals("--help") || args[0].equals("-h")) {
			help();
		}
		else {
			path = args[1];
			if (args[0].equals("-l")){
				compiler.setFilePath(path);
				compiler.análiseLéxica();
			}
			else if (args[0].equals("-s")){
				compiler.setFilePath(path);
				compiler.análiseSintática();
	
			}
			else if (args[0].equals("-a")){
				compiler.setFilePath(path);
				compiler.impressãoAST();
			}
			else if (args[0].equals("-c")){
				compiler.setFilePath(path);
				compiler.análiseContexto();
			}
			else if (args[0].equals("-g")){
				compiler.setFilePath(path);
				compiler.geraçãoCódigo();
			}
			else {
				System.out.println("A opção especificada " + args[0] + " é inválida.");
				System.out.println("Siga as instruções do comando de ajuda abaixo para compilar.");
			}
		}
//			if(!text.isEmpty()) {
//				System.out.println("The file read follows:");
//				Scanner scanner = new Scanner(reader);
//				Token currentToken;
//				currentToken = scanner.scan();
//				while (currentToken.getType() != Token.EOF) {
//					System.out.println(currentToken.toString());
//					currentToken = scanner.scan();
//				}
				
				
//				System.out.println(text);

//				System.out.println(checker.getTabelaDeIdentificação().toString());
//			}
	}
}
