package com.fb.demo.mail;

import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class YahooProvider implements EmailProvider {

    public Map<String, String> getYahooProperties() {
        propertiesMap.put("mail.smtp.auth", "true");
        propertiesMap.put("mail.smtp.starttls.enable", "true");
        propertiesMap.put("mail.debug", "false");
        return propertiesMap;
    }
}
