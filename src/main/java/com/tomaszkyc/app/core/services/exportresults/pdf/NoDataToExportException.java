package com.tomaszkyc.app.core.services.exportresults.pdf;

import com.tomaszkyc.app.core.exception.BaseException;

public class NoDataToExportException extends BaseException {
    public NoDataToExportException(String msg) {
        super("No data found to export.");
    }
}
