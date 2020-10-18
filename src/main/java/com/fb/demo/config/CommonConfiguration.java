package com.fb.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CommonConfiguration {

    @Value("${restTemplate.timeout:10000}")
    private int timeout;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(clientHttpRegisterFactory());
    }

    private ClientHttpRequestFactory clientHttpRegisterFactory() {
        HttpComponentsClientHttpRequestFactory factory =
                        new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(timeout);
        factory.setConnectTimeout(timeout);
        return factory;
    }
}
