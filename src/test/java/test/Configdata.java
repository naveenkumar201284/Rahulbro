package test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Configdata {
	private Properties props;
	String propertyFilePath=System.getProperty("user.dir") + "\\Resources\\ConfigFiles\\TestYouLogin.properties";
	FileReader reader=null;
	
	
	void display(){
		try {
			reader = new FileReader(propertyFilePath);
			props = new Properties();
			try {
				props.load(reader);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
		}	
	}

}
