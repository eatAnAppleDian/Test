package com.orange.exception;

import com.orange.vo.ResultCode;

/**
 * @author orange
 */
public class ResourceNotFoundException extends ApplicationException {
    public ResourceNotFoundException() {
        super(ResultCode.NOT_FOUND);
    }

    public ResourceNotFoundException(String message) {
        super(ResultCode.NOT_FOUND, message);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(ResultCode.NOT_FOUND, cause);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(ResultCode.NOT_FOUND, message, cause);
    }
}