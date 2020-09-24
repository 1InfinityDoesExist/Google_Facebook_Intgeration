package com.fb.demo.exception;

public class TenantNotFoundException extends BaseException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public TenantNotFoundException(String msg) {
        super(msg);
    }

}
