package com.fb.demo.model.request;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fb.demo.entity.EmailProp;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmilTemplateCreateRequest implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String name;
    private String format;
    private String subject;
    private EmailProp emailProp;
}
