package com.fb.demo.service;

import org.springframework.stereotype.Service;

@Service
public interface ValidationAndVerificationOfATService {

    public void verifyAndValidateFacebookAccessToken(String accessToken,
                    String tenant) throws Exception;

    public void verifyAndValidateGoogleAccessToken(String accessToken) throws Exception;

}
