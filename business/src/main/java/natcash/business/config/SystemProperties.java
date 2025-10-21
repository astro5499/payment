package natcash.business.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("file:../etc/app.conf")
public class SystemProperties {

    @Value("${WALLET_URL}")
    private String url;

    @Value("${PRIVATE_KEY}")
    private String privateKey;

    public String getUrl() {
        return url;
    }

    public String getPrivateKey() {
        return privateKey;
    }
}
