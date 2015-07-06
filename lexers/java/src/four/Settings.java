package four;

import java.nio.charset.Charset;

public class Settings {

	public static Charset charset          = Charset.forName("UTF8");
	public static int     readBufferSize   = 4096;
	public static int     indicatorMaxLine = 80;
	public static int     indicatorTabSize = 4;
	public static String  indicatorPrefix  = "  ";
	public static String  indicator        = "^";
	
}
