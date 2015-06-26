package four.lexer;

import java.util.regex.Pattern;

public enum Token {
	
	EOF(0),
	
	LINE_COMMENT_C (0, P("//.*")),
	BLCK_COMMENT_C (0, P("/\\*.*?\\*/")),
	LINE_COMMENT   (0, P("{U1F4AD}.*")),
	BLCK_COMMENT   (0, P("{U1F532}.*?{U1F533}")),
	
	NEWLINE     (1, "\n"),
	WHITESPACE  (2, P("[\\t\\f ]+")),
	IDENTIFIER  (3, P("\\w+")),
	INT_LITERAL (4, P("[1-9]{U20E3}?([0-9]{U20E3}?)*")),
	FLT_LITERAL (5, P("[0-9]{U20E3}?(\\.|{U1F4A7})([0-9]{U20E3}?)*")),
	STR_LITERAL (6, P("{U1F4AC}.*?{U1F4AC}")),
	
	DOT        (7, ".",   U(0x1F4A7)         ),
	ASSIGN     (7, "=",   U(0x2B05 )         ),
	RSH_ASSIGN (7, ">>=", U(0x23EA,  0x2B05) ),
	LSH_ASSIGN (7, "<<=", U(0x23E9,  0x2B05) ),
	AND_ASSIGN (7, "&=",  U(0x2764,  0x2B05) ),
	OR_ASSIGN  (7, "|=",  U(0x1F494, 0x2B05) ),
	XOR_ASSIGN (7, "^=",  U(0x1F53C, 0x2B05) ),
	SUB_ASSIGN (7, "-=",  U(0x2796,  0x2B05) ),
	ADD_ASSIGN (7, "+=",  U(0x2795,  0x2B05) ),
	MUL_ASSIGN (7, "*=",  U(0x2716,  0x2B05) ),
	DIV_ASSIGN (7, "/=",  U(0x2797,  0x2B05) ),
	MOD_ASSIGN (7, "%=",  U(0x0023,  0x20E3, 0x2B05)),
	
	LE_OP  (7, "<=", U(0x25C0, 0x2194)),
	GE_OP  (7, ">=", U(0x25B6, 0x2194)),
	RSH_OP (7, ">>", U(0x23E9)),
	LSH_OP (7, "<<", U(0x23EA)),
	AND_OP (7, "&&", U(0x2764, 0x2764)),
	OR_OP  (7, "||", U(0x1F494, 0x1F494)),
	INC_OP (7, "++", U(0x23EB)),
	DEC_OP (7, "--", U(0x23EC)),
	EQ_OP  (7, "==", U(0x2194)),
	NEQ_OP (7, "!=", U(0x1F6AB, 0x2194)),
	
	TILDE (7, "~", U(0x3030)),
	NOT   (7, "!", U(0x1F6AB)),
	AND   (7, "&", U(0x2764)),
	OR    (7, "|", U(0x1F494)),
	SUB   (7, "-", U(0x2796)),
	ADD   (7, "+", U(0x2795)),
	MUL   (7, "*", U(0x2716)),
	DIV   (7, "/", U(0x2797)),
	MOD   (7, "%", U(0x0023, 0x20E3)),
	XOR   (7, "^", U(0x1F53C)),
	LT    (7, "<", U(0x25C0)),
	GT    (7, ">", U(0x25B6)),
	
	COLON    (7, ":",   U(0x27A1)),
	LPARENT  (7, "(",   U(0x1F31C)),
	RPARENT  (7, ")",   U(0x1F31B)),
	LBRACKET (7, "[",   U(0x1F449)),
	RBRACKET (7, "]",   U(0x1F448)),
	UBRACKET (7, "[]",  U(0x1F448)),
	DBRACKET (7, "[_]", U(0x1F447)),
	
	FUNCTION (7, "function", U(0x1F340)),
	RETURN   (7, "return",   U(0x2934)),
	IF       (7, "if",       U(0x2934)),
	ELSE     (7, "else",     U(0x2757)),
	ELSEIF   (7, "elseif",   U(0x2049)),
	WHILE    (7, "while",    U(0x1F503)),
	BREAK    (7, "break",    U(0x270B)),
	CONTINUE (7, "continue", U(0x21AA)),
	GOTO     (7, "goto",     U(0x1F3C3)),
	TRUE     (7, "true",     U(0x1F44D)),
	FALSE    (7, "false",    U(0x1F44E)),
	NULL     (7, "null",     U(0x2B55)),
	
	;
	
	public final int priority;
	public final String[] literals;
	public final Pattern  regex;
	
	private Token(int priority, String... literals) {
		this.priority = priority;
		this.literals = literals;
		this.regex    = null;
	}
	
	private Token(int priority, Pattern regex, String... literals) {
		this.priority = priority;
		this.literals = literals;
		this.regex    = regex;
	}
	
	private static Pattern P(String regex) {
		return Pattern.compile("^(?:" + U(regex) + ")", Pattern.UNICODE_CHARACTER_CLASS);
	}
	
	private static String U(int... code) {
		StringBuilder str = new StringBuilder();
		for (int c : code) str.append(Character.toChars(c));
		return str.toString();
	}
	
	private static String U(String in) {
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < in.length(); i++) {
			if (i+2 < in.length() && in.charAt(i) == '{' && in.charAt(i+1) == 'U') {
				int j = i+2;
				while (in.charAt(j) != '}') j++;
				str.append(Character.toChars(Integer.parseInt(in.substring(i+2, j), 16)));
				i = j;
			} else {
				str.append(in.charAt(i));
			}
		}
		return str.toString();
	}
	
}
