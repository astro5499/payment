package natcash.business;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

@SpringBootApplication
public class BusinessP2PTransfer {
	private static final Logger logger = LogManager.getLogger(BusinessP2PTransfer.class);
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Properties props = new Properties();
        props.load(new FileInputStream("../etc/application.properties"));
        String port = props.getProperty("server.port", "8080");
        System.setProperty("server.port", port);
		SpringApplication.run(BusinessP2PTransfer.class, args);
	}

}
