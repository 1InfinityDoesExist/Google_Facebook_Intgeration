package com.fb.demo.service;

import org.springframework.stereotype.Service;

@Service
public interface LoginViaFacebookService {

    public String getRedirectUrl(String tenant) throws Exception;

    public String getFacebookAccessToken(String code, String tenant) throws Exception;

}
