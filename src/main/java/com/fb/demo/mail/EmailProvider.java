package com.fb.demo.mail;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public interface EmailProvider {

    Map<String, String> propertiesMap = new HashMap<String, String>();
}
