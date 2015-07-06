package four.file;

public class Unicode {
	
	private final CharSequence string;
	private int index = 0;
	
	public Unicode(CharSequence string) {
		this.string = string;
	}
	
	public boolean hasNext() {
		return index < string.length();
	}
	
	public int index() {
		return index;
	}
	
	public int next() {
		int c = Character.codePointAt(string, index);
		index += Character.charCount(c);
		return c;
	}
	
	public int peek() {
		return Character.codePointAt(string, index);
	}
	
}
