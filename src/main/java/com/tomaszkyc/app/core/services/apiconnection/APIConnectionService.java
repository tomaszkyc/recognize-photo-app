package com.tomaszkyc.app.core.services.apiconnection;

import com.tomaszkyc.app.model.APIClaims;
import com.tomaszkyc.app.model.ImageInformation;

import java.io.File;

public interface APIConnectionService {

    APIClaims getAPIClaims() throws Exception;

    void setAPIClaims(APIClaims apiClaims) throws Exception;

    ImageInformation analizeImage(File imageFile) throws Exception;

}
