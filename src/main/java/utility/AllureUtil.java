//package utility;
//
//import io.qameta.allure.Allure;
//
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;

//public class AllureUtil {
//
//	public static void attachLogFile(String filePath) {
//		try {
//			File logFile = new File(filePath);
//			if (logFile.exists()) {
//				byte[] content = Files.readAllBytes(logFile.toPath());
//				Allure.addAttachment("Test Log", new ByteArrayInputStream(content));
//			}
//		} catch (IOException e) {
//			System.err.println("Failed to attach log to Allure: " + e.getMessage());
//		}
//	}
//}
