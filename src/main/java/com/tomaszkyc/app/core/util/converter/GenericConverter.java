package com.tomaszkyc.app.core.util.converter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface GenericConverter<I, O> {

    List<O> convertToCollection(I element);
}
