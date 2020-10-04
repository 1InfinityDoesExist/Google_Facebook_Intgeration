package com.fb.demo.mail;

import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class RabbitholeProvider implements EmailProvider {

    public Map<String, String> getRabbitHoleProperties() {
        propertiesMap.put("smtp.ssl.port", "465");
        propertiesMap.put("smtp.port", "465");
        propertiesMap.put("smtp.message.threadcount", "5");
        return propertiesMap;
    }
}
