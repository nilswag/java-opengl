package online.nilsunilus.test.engine.utility;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	
	public static String timestamp() {
		SimpleDateFormat simple_date_format = new SimpleDateFormat("[dd/MM/yyyy HH:mm:ss] ");
		Date date = new Date();
		return simple_date_format.format(date);
	}
	
	private static String message(String message) {
		return timestamp() + message;
	}
	
	public static void log(String message, PrintStream print_stream) {
		print_stream.println("[Logger] " + message(message));
		if(print_stream == System.err)
			System.exit(-1);
	}

}
