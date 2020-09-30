package com.fb.demo.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fb.demo.entity.EmailProp;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailTemplateUpdateRequest {
    private String name;
    private String format;
    private String subject;
    private EmailProp emailProp;
}
