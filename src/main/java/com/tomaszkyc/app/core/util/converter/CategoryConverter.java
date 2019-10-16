package com.tomaszkyc.app.core.util.converter;

import com.tomaszkyc.app.core.services.ServiceFactory;
import com.tomaszkyc.app.core.services.translation.TranslationService;
import com.tomaszkyc.app.model.Category;
import com.tomaszkyc.app.ui.model.UIResult;

import java.util.ArrayList;
import java.util.List;

public class CategoryConverter implements GenericConverter<Category, UIResult> {

    private TranslationService translationService;

    public CategoryConverter() {
        this.translationService = ServiceFactory.getTranslationService();
    }


    @Override
    public List<UIResult> convertToCollection(Category element) {

        List<UIResult> uiResults = new ArrayList<>();

        UIResult name = new UIResult( translationService.getTranslation("app.results-window.category.name"), element.getName());
        UIResult score = new UIResult( translationService.getTranslation( "app.results-window.category.score"), String.valueOf( element.getScore().doubleValue() ) );

        uiResults.add( name );
        uiResults.add( score );


        return uiResults;
    }
}
