package four.lexer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import four.file.FileBuffer;
import four.file.Position;
import four.file.Reporter;
import four.file.Section;
import four.file.Unicode;

public class Lexer {
	
	private final FileBuffer  file;
	private final Reporter    report;
	private final TrieNode    trie  = new TrieNode();
	private final List<Token> regex = new ArrayList<Token>();
	private Position curr = new Position();
	
	private class TrieNode {
		Token token = Token.EOF;
		Map<Integer, TrieNode> next = new HashMap<Integer, TrieNode>();
		TrieNode   next(int c) { return next.get(c); }
		boolean hasNext(int c) { return next.containsKey(c); }
		void    addNext(int c) { next.put(c, new TrieNode()); }
	}
	
	private class Result {
		final Token token; final int length;
		Result(Token t, int l) { token = t; length = l; }
	}
	
	public Lexer(FileBuffer file, Reporter report) {
		this.file   = file;
		this.report = report;
		for (Token t : Token.values()) add(t);
	}
	
	public Symbol next() {
		Symbol symbol;
		do {
			symbol = popNext();
		} while (symbol.token.priority < 0);
		return symbol;
	}
	
	public boolean isEOF() {
		return curr.position == file.length();
	}
	
	private Symbol popSymbol(Token token, int length) {
		CharSequence lexeme = file.subSequence(curr.position, curr.position + length);
		Section section = new Section(curr, curr.advance(lexeme));
		curr = section.end;
		return new Symbol(token, section, lexeme);
	}
	
	private Symbol popNext() {
		CharSequence buffer = file.subSequence(curr.position, file.length());
		if (buffer.length() == 0) return new Symbol(Token.EOF, curr, "");
		Result result = new Result(Token.EOF, 0);
		result = nextRegex(buffer, result);
		result =  nextTrie(buffer, result);
		if (result.length == 0) return error(buffer);
		return popSymbol(result.token, result.length);
	}
	
	private Symbol error(CharSequence buffer) {
		Unicode unicode = new Unicode(buffer);
		String hex = Integer.toHexString(unicode.next()).toUpperCase();
		Symbol symbol = popSymbol(null, unicode.index());
		report.error(symbol.section, "Invalid character U+" + hex + ".");
		return popNext();
	}
	
	private void add(Token token) {
		for (String str : token.literals) addTrieNode(token, str);
		if (token.regex != null) regex.add(token);
	}
	
	private void addTrieNode(Token token, String str) {
		TrieNode node = trie;
		for (Unicode i = new Unicode(str); i.hasNext();) {
			int code = i.next();
			if (!node.hasNext(code)) node.addNext(code);
			node = node.next(code);
		}
		if (node.token.priority < token.priority) {
			node.token = token;
		}
	}
	
	private Result nextRegex(CharSequence buffer, Result best) {
		for (Token token : regex) {
			Matcher matcher = token.regex.matcher(buffer);
			if (!matcher.find()) continue;
			Result r = new Result(token, matcher.end());
			if (r.length > best.length || r.token.priority > best.token.priority) {
				best = r;
			}
		}
		return best;
	}
	
	private Result nextTrie(CharSequence buffer, Result best) {
		TrieNode node = trie;
		for (Unicode i = new Unicode(buffer); i.hasNext();) {
			node = node.next(i.next());
			if (node == null) break;
			Result r = new Result(node.token, i.index());
			if (r.length > best.length || r.token.priority > best.token.priority) {
				best = r;
			}
		}
		return best;
	}
	
}
