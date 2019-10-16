package com.tomaszkyc.app.core.util.converter;

import com.tomaszkyc.app.core.services.ServiceFactory;
import com.tomaszkyc.app.core.services.translation.TranslationService;
import com.tomaszkyc.app.model.Caption;
import com.tomaszkyc.app.model.Description;
import com.tomaszkyc.app.ui.model.UIResult;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class DescriptionConverter implements GenericConverter<Description, UIResult> {


    private TranslationService translationService;

    public DescriptionConverter() {
        this.translationService = ServiceFactory.getTranslationService();
    }


    @Override
    public List<UIResult> convertToCollection(Description element) {

        List<UIResult> uiResults = new ArrayList<>();

        StringJoiner tagsJoiner = new StringJoiner(",");
        tagsJoiner.setEmptyValue("");
        for(String tag : element.getTags()) {
            tagsJoiner.add( tag );
        }

        UIResult  tags = new UIResult( translationService.getTranslation("app.results-window.description.tags"), tagsJoiner.toString() );
        uiResults.add( tags );



        //convert caption list to UIResult and add to list
        CaptionConverter captionConverter = new CaptionConverter();

        List<Caption> elementCaptions = element.getCaptions();
        for(Caption caption : elementCaptions) {

            //convert
            List<UIResult> convertedCaptions = captionConverter.convertToCollection( caption );


            //add to final list
            uiResults.addAll( convertedCaptions );

        }




        return uiResults;
    }
}
