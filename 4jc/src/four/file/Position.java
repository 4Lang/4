package four.file;

public class Position {
	
	public final int line;
	public final int column;
	public final int position;
	
	public Position() {
		line = column = 1;
		position = 0;
	}
	
	@Override public String toString() {
		return line + ":" + column;
	}
	
	@Override public boolean equals(Object obj) {
		if (!(obj instanceof Position)) return false;
		return ((Position) obj).position == position;
	}
	
	@Override public int hashCode() {
		return position;
	}
	
	public Position advance(CharSequence str) {
		int l = line, c = column;
		for (Unicode i = new Unicode(str); i.hasNext();) {
			if (i.next() == '\n') {
				l++;
				c = 1;
			} else {
				c++;
			}
		}
		return new Position(l, c, position + str.length());
	}
	
	private Position(int line, int column, int position) {
		this.line     = line;
		this.column   = column;
		this.position = position;
	}
	
}
