package it.poc.fabrick.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "fabrick")
@Data
public class YamlConfig {

    private String baseUrl;
    private String authSchema;
    private String apiKey;
    private String timeZone;

}
