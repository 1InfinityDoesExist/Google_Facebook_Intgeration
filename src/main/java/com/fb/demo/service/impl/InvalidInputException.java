package com.fb.demo.service.impl;

import com.fb.demo.exception.BaseException;

public class InvalidInputException extends BaseException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public InvalidInputException(String msg) {
        super(msg);
    }

}
