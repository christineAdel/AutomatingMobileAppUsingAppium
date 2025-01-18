package anroid.zain.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
	private static Properties properties;

	public static void loadConfig(String filePath) throws IOException {
		properties = new Properties();
		FileInputStream input = new FileInputStream(filePath);
		properties.load(input);
		input.close();
	}

	public static String getProperty(String key) {
		return properties.getProperty(key);
	}
}
