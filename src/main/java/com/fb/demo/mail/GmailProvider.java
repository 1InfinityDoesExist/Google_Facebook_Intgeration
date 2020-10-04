package com.fb.demo.mail;

import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class GmailProvider implements EmailProvider {

    public Map<String, String> getGmailProerties() {
        propertiesMap.put("mail.smtp.auth", "true");
        propertiesMap.put("mail.smtp.starttls.enable", "true");
        propertiesMap.put("mail.smtp.setTLS", "true");
        return propertiesMap;
    }

}
