package com.fb.demo.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GoogleDevCreateResponse {
    private Integer id;
    private String msg;
}
