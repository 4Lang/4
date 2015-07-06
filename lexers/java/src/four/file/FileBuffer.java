package four.file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import four.Settings;

public class FileBuffer implements CharSequence {
	
	private final String         name;
	private final char[]         buffer;
	private final CharSequence[] lines;
	
	public FileBuffer(String file) throws IOException {
		name   = file;
		buffer = read(new InputStreamReader(new FileInputStream(file), Settings.charset));
		lines  = findLines(buffer);
	}
	
	public CharSequence line(int line) {
		return lines[line];
	}
	
	public int lines() {
		return lines.length;
	}
	
	public String name() {
		return name;
	}
	
	private static CharSequence[] findLines(char[] buffer) {
		int count = 1, start = 0;
		for (char c : buffer) if (c == '\n') count++;
		CharSequence[] lines = new CharSequence[count];
		for (int i = 0, c = 0; i < buffer.length; i++) {
			if (buffer[i] == '\n') {
				lines[c++] = new FileSequence(buffer, start, i - start);
				start = i + 1;
			}
		}
		lines[count - 1] = new FileSequence(buffer, start, buffer.length - start);
		return lines;
	}
	
	private static char[] read(Reader file) throws IOException {
		BufferedReader reader = new BufferedReader(file);
		StringBuilder str = new StringBuilder(); int size = 0;
		char[] buffer = new char[Settings.readBufferSize]; 
		while ((size = reader.read(buffer)) >= 0) str.append(buffer, 0, size);
		reader.close();
		String string = str.toString().replaceAll("\r\n", "\n").replaceAll("\r", "\n");
		if (string.startsWith("\uFEFF")) string = string.substring(1);
		return string.toCharArray();
	}
	
	@Override public char charAt(int i) {
		return buffer[i];
	}
	
	@Override public int length() {
		return buffer.length;
	}
	
	@Override public CharSequence subSequence(int start, int end) {
		return new FileSequence(buffer, start, end - start);
	}
	
}
