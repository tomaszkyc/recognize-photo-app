package com.tomaszkyc.app.core.services.imageanalize;

import com.tomaszkyc.app.core.services.apiconnection.APIConnectionService;
import com.tomaszkyc.app.model.ImageInformation;

import java.io.File;

public interface ImageAnalizeService {

    ImageInformation analize(File imageFile) throws Exception;

    void setAPIConnectionService(APIConnectionService apiConnectionService) throws Exception;

    APIConnectionService getAPIConnectionService() throws Exception;

}
