package com.tomaszkyc.app.core.util.converter;

import com.tomaszkyc.app.core.services.ServiceFactory;
import com.tomaszkyc.app.core.services.translation.TranslationService;
import com.tomaszkyc.app.model.Color;
import com.tomaszkyc.app.ui.model.UIResult;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class ColorConverter implements GenericConverter<Color, UIResult> {

    private TranslationService translationService;

    public ColorConverter() {
        this.translationService = ServiceFactory.getTranslationService();
    }

    @Override
    public List<UIResult> convertToCollection(Color element) {
        List<UIResult> uiResults = new ArrayList<>();

        UIResult dominantColorForeground = new UIResult( translationService.getTranslation("app.results-window.color.dominant-color-foreground"), element.getDominantColorForeground() );
        UIResult dominantColorBackground = new UIResult( translationService.getTranslation("app.results-window.color.dominant-color-background"), element.getDominantColorBackground() );


        //create dominant colors string
        StringJoiner dominantColorsStr = new StringJoiner(",");
        dominantColorsStr.setEmptyValue("");
        for( String color : element.getDominantColors() ) {
            dominantColorsStr.add( color );
        }

        UIResult dominantColors = new UIResult( translationService.getTranslation("app.results-window.color.dominant-colors"), dominantColorsStr.toString() );
        UIResult accentColor = new UIResult( translationService.getTranslation( "app.results-window.color.accent-color"), element.getAccentColor()  );
        UIResult isBlackAndWhite = new UIResult(  translationService.getTranslation("app.results-window.color.is-black-and-white"), element.getIsBWImg().toString());


        uiResults.add( dominantColorForeground );
        uiResults.add( dominantColorBackground );
        uiResults.add( dominantColors );
        uiResults.add( accentColor );
        uiResults.add( isBlackAndWhite );

        return uiResults;
    }
}
