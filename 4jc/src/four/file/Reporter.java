package four.file;

import java.io.PrintStream;

import four.Settings;

public class Reporter {
	
	private final FileBuffer file;
	private final PrintStream warn, error;
	
	public Reporter(FileBuffer file) {
		this.file  = file;
		this.warn  = System.err;
		this.error = System.err;
	}
	
	public Reporter(FileBuffer file, PrintStream warn, PrintStream error) {
		this.file  = file;
		this.warn  = warn;
		this.error = error;
	}
	
	public void error(String message) {
		error.println(file.name() + ": error: " + message);
		error.flush();
	}
	
	public void error(Position pos, String message) {
		error.println(file.name() + ":" + pos + ": error: " + message);
		indicator(pos, error);
		error.flush();
	}
	
	public void error(Section sec, String message) {
		error(sec.start, message);
		error.flush();
	}
	
	public void warning(String message) {
		warn.println(file.name() + ": warning: " + message);
		warn.flush();
	}
	
	public void warning(Position pos, String message) {
		warn.println(file.name() + ":" + pos + ": warning: " + message);
		indicator(pos, error);
		warn.flush();
	}
	
	public void warning(Section sec, String message) {
		warning(sec.start, message);
		warn.flush();
	}
	
	private static final String tab =
			new String(new char[Settings.indicatorTabSize]).replace('\0', ' ');
	
	void indicator(Position pos, PrintStream out) {
		CharSequence line = file.line(pos.line - 1);
		int start = Math.max(pos.column - Settings.indicatorMaxLine, 0);
		int end   = Math.min(pos.column + Settings.indicatorMaxLine, line.length());
		line = line.subSequence(start, end);
		if (line.length() == 0) return;
		out.print(Settings.indicatorPrefix);
		out.println(line.toString().replaceAll("\t", tab));
		out.print(Settings.indicatorPrefix);
		for (int i = start + 1; i < pos.column; i++) out.print(' ');
		out.println(Settings.indicator);
	}
	
}
