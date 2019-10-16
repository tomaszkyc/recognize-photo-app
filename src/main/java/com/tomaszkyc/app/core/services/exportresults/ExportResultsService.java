package com.tomaszkyc.app.core.services.exportresults;

import com.tomaszkyc.app.model.ImageInformation;

import java.io.File;

public interface ExportResultsService {

    void exportResults(String filePath);

    void setImageInformation(ImageInformation imageInformation);

    void setImage(File imageFile);

    File getImage();


}
