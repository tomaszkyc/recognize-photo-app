package com.tomaszkyc.app.core.services.translation;

public interface TranslationService {

    String getTranslation(String key) throws NoTranslationFoundException;
}
