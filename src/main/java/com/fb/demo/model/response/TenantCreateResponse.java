package com.fb.demo.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@lombok.Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TenantCreateResponse {
  private Integer id;
  private String message;
}
