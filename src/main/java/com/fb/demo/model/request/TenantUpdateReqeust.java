package com.fb.demo.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TenantUpdateReqeust {
    private String description;
    private String version;
    private String url;
    private String organizationName;
    private String registrationNumber;
    private String website;
    private String tenantType;
    private String organizationEmail;
    private String organizationMobileNumber;
}
