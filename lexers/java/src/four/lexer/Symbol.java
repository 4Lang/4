package four.lexer;

import four.file.Position;
import four.file.Section;

public class Symbol {
	
	public final Token token;
	public final Section section;
	public final CharSequence lexeme;
	
	public Symbol(Token token, Section section, CharSequence lexeme) {
		this.token   = token;
		this.section = section;
		this.lexeme  = lexeme;
	}
	
	public Symbol(Token token, Position position, CharSequence lexeme) {
		this.token   = token;
		this.section = new Section(position);
		this.lexeme  = lexeme;
	}
	
}
