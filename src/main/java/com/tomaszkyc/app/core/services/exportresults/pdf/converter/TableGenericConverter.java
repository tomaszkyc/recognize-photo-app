package com.tomaszkyc.app.core.services.exportresults.pdf.converter;

import com.tomaszkyc.app.core.util.converter.GenericConverter;

import java.util.List;

public interface TableGenericConverter<I, O> {

    O convert(I element);

}