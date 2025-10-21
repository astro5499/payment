package natcash.business.config;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;

public class DatabaseConfig {
    private String url;
    private String username;
    private String password;
    private String driverClassName;

    public DatabaseConfig(String path) {
        try {
            File xmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            this.url = doc.getElementsByTagName("url").item(0).getTextContent();
            this.username = doc.getElementsByTagName("username").item(0).getTextContent();
            this.password = doc.getElementsByTagName("password").item(0).getTextContent();
            this.driverClassName = doc.getElementsByTagName("driverClassName").item(0).getTextContent();
        } catch (Exception e) {
            throw new RuntimeException("Error reading database.xml", e);
        }
    }

    // getters
    public String getUrl() { return url; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getDriverClassName() { return driverClassName; }
}