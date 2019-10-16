package com.tomaszkyc.app.core.util.converter;

import com.tomaszkyc.app.core.services.ServiceFactory;
import com.tomaszkyc.app.core.services.translation.TranslationService;
import com.tomaszkyc.app.model.Caption;
import com.tomaszkyc.app.ui.model.UIResult;

import java.util.ArrayList;
import java.util.List;

public class CaptionConverter implements GenericConverter<Caption, UIResult> {

    private TranslationService translationService;

    public CaptionConverter() {
        this.translationService = ServiceFactory.getTranslationService();
    }

    @Override
    public List<UIResult> convertToCollection(Caption element) {

        List<UIResult> uiResults = new ArrayList<>();

        UIResult text = new UIResult( translationService.getTranslation("app.results-window.caption.text.name"),
                                      element.getText());

        UIResult confidence = new UIResult( translationService.getTranslation( "app.results-window.caption.confidence.name" ),
                                    String.valueOf( element.getConfidence() ) );


        uiResults.add( text );
        uiResults.add( confidence );

        return uiResults;
    }


}
