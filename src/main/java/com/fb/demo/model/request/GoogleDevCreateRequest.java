package com.fb.demo.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GoogleDevCreateRequest {

    private String tenant;
    private String clientId;
    private String clientSecret;
    private String redirectUrl;
}
