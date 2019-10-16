package com.tomaszkyc.app.core.services.apiconnection;

import com.tomaszkyc.app.core.exception.BaseException;

public class BadStatusOnAPIConnectionException extends BaseException {
    public BadStatusOnAPIConnectionException(String msg) {
        super("There was an invalid status code in API response: " + msg);
    }
}
