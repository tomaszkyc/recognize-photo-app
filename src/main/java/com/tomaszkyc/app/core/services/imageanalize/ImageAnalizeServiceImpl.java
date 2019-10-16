package com.tomaszkyc.app.core.services.imageanalize;

import com.tomaszkyc.app.MainApp;
import com.tomaszkyc.app.core.services.apiconnection.APIConnectionService;
import com.tomaszkyc.app.model.ImageInformation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class ImageAnalizeServiceImpl implements ImageAnalizeService {

    private static final Logger log = LogManager.getLogger(ImageAnalizeServiceImpl.class);



    public ImageAnalizeServiceImpl() {
        this.apiConnectionService = null;
    }

    public void setApiConnectionService(APIConnectionService apiConnectionService) {
        this.apiConnectionService = apiConnectionService;
    }

    private APIConnectionService apiConnectionService;

    @Override
    public ImageInformation analize(File imageFile) throws Exception {

        return this.apiConnectionService.analizeImage(imageFile);

    }

    @Override
    public void setAPIConnectionService(APIConnectionService apiConnectionService) throws Exception {
        this.apiConnectionService = apiConnectionService;
    }

    @Override
    public APIConnectionService getAPIConnectionService() throws Exception {
        return apiConnectionService;
    }
}
