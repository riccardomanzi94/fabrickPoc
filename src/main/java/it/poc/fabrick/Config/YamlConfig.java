package it.poc.fabrick.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "fabrick")
@Data
public class YamlConfig {

    private String baseUrl;
    private String authSchema;
    private String apiKey;
    private String timeZone;

}
