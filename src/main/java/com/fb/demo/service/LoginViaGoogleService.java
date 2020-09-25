package com.fb.demo.service;

import org.springframework.stereotype.Service;

@Service
public interface LoginViaGoogleService {

    public String getRedirectUrl(String tenant) throws Exception;

    public String getGoogleAccessToken(String code, String tenant) throws Exception;

}
