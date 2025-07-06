package utility;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingUtil {

	// Example: Log.info("Test started");
	public static Logger getLogger(Class<?> clazz) {
		return LoggerFactory.getLogger(clazz);
	}


	private static final String LOG_DIRECTORY = "Reports/Logs";
	private static final int MAX_LOG_FILES = 10;

	public static void initializeLogsFolder() {
		File logDir = new File(LOG_DIRECTORY);

		if (!logDir.exists()) {
			logDir.mkdirs();
			System.out.println("📂 Created log directory: " + logDir.getAbsolutePath());
		}
	
		else {
			cleanOldLogs(logDir);
		}
	}

	private static void cleanOldLogs(File logDir) {
		File[] logFiles = logDir.listFiles((dir, name) -> name.endsWith(".log"));

		if (logFiles != null && logFiles.length > MAX_LOG_FILES) {
			// Sort by last modified descending (most recent first)
			Arrays.sort(logFiles, Comparator.comparingLong(File::lastModified).reversed());

			// Skip first MAX_LOG_FILES and delete the rest
			for (int i = MAX_LOG_FILES; i < logFiles.length; i++) {
				if (logFiles[i].delete()) {
					System.out.println("🗑️ Deleted old log: " + logFiles[i].getName());
				} else {
					System.err.println("❌ Failed to delete: " + logFiles[i].getName());
				}
			}
		}
	}
}
