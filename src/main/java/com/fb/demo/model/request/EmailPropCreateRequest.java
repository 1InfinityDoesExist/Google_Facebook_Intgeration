package com.fb.demo.model.request;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fb.demo.entity.EmailTemplate;
import com.fb.demo.entity.Tenant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailPropCreateRequest implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String host;
    private String userName;
    private String email;
    private String password;
    private int sslPort;
    private int port;
    private Tenant parentTenant;
    private String application;
    private String provider;
}
