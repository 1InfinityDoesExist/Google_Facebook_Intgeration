package com.fb.demo.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FbDeveloperDetailsUpdateRequest {
    private String clientId;
    private String clientSecret;
    private String redirectUrl;
}
