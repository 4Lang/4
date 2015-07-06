package four.file;

public class Section {
	
	public final Position start;
	public final Position end;
	
	public Section(Position start, Position end) {
		this.start = start;
		this.end   = end;
	}
	
	public Section(Section start, Section end) {
		this.start = start.start;
		this.end   = end.end;
	}
	
	public Section(Position start) {
		this.start = start;
		this.end   = start;
	}
	
	@Override public String toString() {
		if (start.equals(end)) {
			return start.toString();
		} else {
			return start + " - " + end;
		}
	}
	
}
