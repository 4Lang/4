package four.file;

public class FileSequence implements CharSequence {
	
	private final char[] array;
	private final int    start;
	private final int    length;
	
	public FileSequence(char[] array, int start, int length) {
		if (start < 0 || length < 0 || start + length > array.length) {
			String msg = "Invalid range [" + start + ", " + length + "] for length " + array.length;
			throw new IndexOutOfBoundsException(msg);
		}
		this.array  = array;
		this.start  = start;
		this.length = length;
	}
	
	@Override public char charAt(int index) {
		if (index < 0 || index >= length) {
			String msg = "Invalid index " + index + " for length " + array.length;
			throw new IndexOutOfBoundsException(msg);
		}
		return array[start + index];
	}
	
	@Override public int length() {
		return length;
	}
	
	@Override public CharSequence subSequence(int start, int end) {
		if (end > length) {
			String msg = "Invalid range [" + start + ", " + end + "] for length " + array.length;
			throw new IndexOutOfBoundsException(msg);
		}
		return new FileSequence(array, this.start + start, end - start);
	}
	
	@Override public String toString() {
		StringBuilder str = new StringBuilder(length);
		for (int i = 0; i < length; i++) str.append(array[start + i]);
		return str.toString();
	}
	
}
