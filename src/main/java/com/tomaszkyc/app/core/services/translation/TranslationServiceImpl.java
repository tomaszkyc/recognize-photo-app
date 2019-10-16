package com.tomaszkyc.app.core.services.translation;

import org.apache.commons.lang3.StringUtils;

import java.util.Locale;
import java.util.ResourceBundle;

public class TranslationServiceImpl implements TranslationService {


    public ResourceBundle getTranslations() {
        return translations;
    }

    public void setTranslations(ResourceBundle translations) {
        this.translations = translations;
    }

    private ResourceBundle translations;



    public TranslationServiceImpl() {
        this.translations = ResourceBundle.getBundle("MessagesBundle", new Locale("en", "US"));
    }


    @Override
    public String getTranslation(String key) {
        String translation = null;
        try{
            translation = translations.getString( key );
        }
        catch( Exception exception) {
            throw new NoTranslationFoundException(key);
        }
        return translation;
    }

}
