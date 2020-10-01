package com.fb.demo.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fb.demo.entity.Gender;

@lombok.Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TenantCreateRequest {
    private String name;
    private String description;
    private String version;
    private String url;
    private String organizationName;
    private String registrationNumber;
    private String website;
    private String tenantType;
    private String organizationEmail;
    private String organizationMobileNumber;
    private Gender gender;
}
