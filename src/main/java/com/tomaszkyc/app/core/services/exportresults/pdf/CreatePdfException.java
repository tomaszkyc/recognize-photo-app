package com.tomaszkyc.app.core.services.exportresults.pdf;

import com.tomaszkyc.app.core.exception.BaseException;

public class CreatePdfException extends BaseException {
    public CreatePdfException(String msg) {
        super("There was an error on creating PDF file. Details: " + msg);
    }
}
