package com.tomaszkyc.app.core.thread;

import com.tomaszkyc.app.core.services.apiconnection.APIConnectionService;
import com.tomaszkyc.app.core.services.imageanalize.ImageAnalizeService;
import com.tomaszkyc.app.model.ImageInformation;
import com.tomaszkyc.app.ui.main.RootLayoutController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class AnalizeImageThread extends Thread {

    private static final Logger log = LogManager.getLogger(AnalizeImageThread.class);


    public ImageAnalizeService getImageAnalizeService() {
        return imageAnalizeService;
    }

    public void setImageAnalizeService(ImageAnalizeService imageAnalizeService) {
        this.imageAnalizeService = imageAnalizeService;
    }

    public APIConnectionService getApiConnectionService() {
        return apiConnectionService;
    }

    public void setApiConnectionService(APIConnectionService apiConnectionService) {
        this.apiConnectionService = apiConnectionService;
    }

    public AnalizeImageThread(ImageAnalizeService imageAnalizeService, APIConnectionService apiConnectionService, File imageFile) throws Exception {
        this.imageAnalizeService = imageAnalizeService;
        this.apiConnectionService = apiConnectionService;
        this.imageFile = imageFile;
    }

    private ImageAnalizeService imageAnalizeService;
    private APIConnectionService apiConnectionService;

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    private File imageFile;

    public ImageInformation getImageInformation() {
        return imageInformation;
    }

    public void setImageInformation(ImageInformation imageInformation) {
        this.imageInformation = imageInformation;
    }

    private ImageInformation imageInformation;

    public String getAnalizeErrorMessage() {
        return analizeErrorMessage;
    }

    public void setAnalizeErrorMessage(String analizeErrorMessage) {
        this.analizeErrorMessage = analizeErrorMessage;
    }

    private String analizeErrorMessage;

    @Override
    public void run() {

        try {
            this.imageAnalizeService.setAPIConnectionService( this.apiConnectionService );
            this.imageInformation = this.imageAnalizeService.analize( getImageFile() );
        } catch (Exception e) {
            log.error("Error on checking image: " + e.getMessage(), e);
            this.analizeErrorMessage = e.getMessage();
        }


    }

}
