package com.fb.demo.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fb.demo.entity.EmailTemplate;
import com.fb.demo.entity.Tenant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailPropUpdateRequest {

    private String host;
    private String userName;
    private String email;
    private String password;
    private int sslPort;
    private int port;
    private Tenant parentTenant;
    private String application;
    private String provider;
    private EmailTemplate emailTemplate;
}
