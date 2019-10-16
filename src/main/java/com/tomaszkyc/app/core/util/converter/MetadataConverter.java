package com.tomaszkyc.app.core.util.converter;

import com.tomaszkyc.app.core.services.ServiceFactory;
import com.tomaszkyc.app.core.services.translation.TranslationService;
import com.tomaszkyc.app.model.Metadata;
import com.tomaszkyc.app.ui.model.UIResult;

import java.util.ArrayList;
import java.util.List;

public class MetadataConverter implements GenericConverter<Metadata, UIResult> {

    private TranslationService translationService;

    public MetadataConverter() {
        this.translationService = ServiceFactory.getTranslationService();
    }



    @Override
    public List<UIResult> convertToCollection(Metadata element) {

        List<UIResult> uiResults = new ArrayList<>();

        UIResult width = new UIResult( translationService.getTranslation("app.results-window.metadata.width"), element.getWidth().toString() );
        UIResult height = new UIResult( translationService.getTranslation( "app.results-window.metadata.height" ), element.getHeight().toString() );
        UIResult format = new UIResult( translationService.getTranslation( "app.results-window.metadata.format"), element.getFormat()  );

        uiResults.add( width );
        uiResults.add( height );
        uiResults.add( format );





        return uiResults;
    }
}
