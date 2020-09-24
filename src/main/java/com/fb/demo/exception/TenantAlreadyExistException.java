package com.fb.demo.exception;

import com.fb.demo.entity.Tenant;

public class TenantAlreadyExistException extends BaseException {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Tenant tenant;

  public TenantAlreadyExistException(String msg, Tenant tenant) {
    super(msg);
    this.tenant = tenant;
  }

  public Tenant getTenant() {
    return tenant;
  }
}
