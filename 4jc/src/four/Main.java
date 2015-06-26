package four;

import java.io.File;
import java.io.IOException;

import four.file.FileBuffer;
import four.file.Reporter;
import four.lexer.Lexer;
import four.lexer.Symbol;
import four.lexer.Token;

public class Main {
	
	public static void main(String args[]) {
		
		if (args.length != 1) {
			System.err.println("No input files.");
			System.exit(-1);
		}
		
		String filename = args[0];
		if (!new File(filename).exists()) {
			System.err.println("File " + filename + " doesn't exist.");
			System.exit(-1);
		}
		
		try {
			
			FileBuffer file = new FileBuffer(filename);
			Lexer lexer = new Lexer(file, new Reporter(file));
			while (!lexer.isEOF()) {
				Symbol s = lexer.next();
				if (s.token == Token.NEWLINE) {
					System.out.println("[" + s.section + "] " + s.token);
				} else {
					System.out.println("[" + s.section + "] " + s.token + " [" + s.lexeme + "]");
				}
			}
			
		} catch (IOException e) {
			System.err.println("Failed to read " + filename + ": " + e.getMessage());
		}
		
	}
	
}
