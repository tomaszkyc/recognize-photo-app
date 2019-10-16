package com.tomaszkyc.app.core.services;

import com.tomaszkyc.app.core.services.apiconnection.APIConnectionService;
import com.tomaszkyc.app.core.services.apiconnection.AzureAPIConnectionServiceImpl;
import com.tomaszkyc.app.core.services.exportresults.ExportResultsService;
import com.tomaszkyc.app.core.services.exportresults.pdf.PdfExportResultsServiceImpl;
import com.tomaszkyc.app.core.services.imageanalize.ImageAnalizeService;
import com.tomaszkyc.app.core.services.imageanalize.ImageAnalizeServiceImpl;
import com.tomaszkyc.app.core.services.translation.TranslationService;
import com.tomaszkyc.app.core.services.translation.TranslationServiceImpl;
import com.tomaszkyc.app.model.APIClaims;

public class ServiceFactory {

    public static APIConnectionService getAPIConnectionService(APIClaims apiClaims) {

        return new AzureAPIConnectionServiceImpl( apiClaims );

    }

    public static TranslationService getTranslationService() {

        return new TranslationServiceImpl();

    }

    public static ImageAnalizeService getImageAnalizeService() {

        return new ImageAnalizeServiceImpl();

    }

    public static ExportResultsService getExportResultsService() {

        return new PdfExportResultsServiceImpl();

    }



}
