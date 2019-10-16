package com.tomaszkyc.app.ui.main;

import com.tomaszkyc.app.MainApp;
import com.tomaszkyc.app.core.services.ServiceFactory;
import com.tomaszkyc.app.core.services.apiconnection.APIConnectionService;
import com.tomaszkyc.app.core.services.exportresults.ExportResultsService;
import com.tomaszkyc.app.core.services.imageanalize.ImageAnalizeService;
import com.tomaszkyc.app.core.services.translation.TranslationService;
import com.tomaszkyc.app.core.thread.AnalizeImageThread;
import com.tomaszkyc.app.model.APIClaims;
import com.tomaszkyc.app.model.ImageInformation;
import com.tomaszkyc.app.ui.exception.UIExceptionHandler;
import com.tomaszkyc.app.ui.main.toolbar.SetAPIKeyHandler;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class RootLayoutController {

    private static final Logger log = LogManager.getLogger(RootLayoutController.class);

    private TranslationService translationService;

    private APIClaims apiClaims;

    public ImageInformation getImageInformation() {
        return imageInformation;
    }

    public void setImageInformation(ImageInformation imageInformation) {
        this.imageInformation = imageInformation;
    }

    private ImageInformation imageInformation;

    public File getChoosenPhotoToAnalize() {
        return choosenPhotoToAnalize;
    }

    public void setChoosenPhotoToAnalize(File choosenPhotoToAnalize) {
        this.choosenPhotoToAnalize = choosenPhotoToAnalize;
    }

    private File choosenPhotoToAnalize;

    public ImageView getImageToAnalize() {
        return imageToAnalize;
    }

    public void setImageToAnalize(ImageView imageToAnalize) {
        this.imageToAnalize = imageToAnalize;
    }

    @FXML
    private ImageView imageToAnalize;

    public Button getStartAnalizeImageButton() {
        return startAnalizeImageButton;
    }

    public void setStartAnalizeImageButton(Button startAnalizeImageButton) {
        this.startAnalizeImageButton = startAnalizeImageButton;
    }

    public Button getShowResultsButton() {
        return showResultsButton;
    }

    public void setShowResultsButton(Button showResultsButton) {
        this.showResultsButton = showResultsButton;
    }

    public Button getExportResultsButton() {
        return exportResultsButton;
    }

    public void setExportResultsButton(Button exportResultsButton) {
        this.exportResultsButton = exportResultsButton;
    }

    @FXML
    private Button startAnalizeImageButton;

    @FXML
    private Button showResultsButton;

    @FXML
    private Button exportResultsButton;

    public ProgressIndicator getProgressIndicator() {
        return progressIndicator;
    }

    public void setProgressIndicator(ProgressIndicator progressIndicator) {
        this.progressIndicator = progressIndicator;
    }

    @FXML
    private ProgressIndicator progressIndicator;


    public MainApp getMainApp() {
        return mainApp;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        this.translationService = ServiceFactory.getTranslationService();
    }

    private MainApp mainApp;

    public RootLayoutController() {

    }

    public RootLayoutController(MainApp mainApp) {

        this.mainApp = mainApp;

    }

    @FXML
    private void initialize() {
        this.startAnalizeImageButton.setDisable(true);
        this.showResultsButton.setDisable(true);
        this.exportResultsButton.setDisable(true);

        //hide progress bar
        getProgressIndicator().setVisible(false);

        this.apiClaims = new APIClaims();


    }


    @FXML
    private void handleOpen() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter(
                "JPG files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter(
                "PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(jpgFilter);
        fileChooser.getExtensionFilters().add(pngFilter);

        // Show open file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if ( file != null && file.exists() ) {
            //Creating an image
            log.debug("Choosen filepath: " + file.getAbsolutePath());
            Image image = new Image(new FileInputStream( file.getAbsolutePath() ));
            this.imageToAnalize.setImage( image );
            this.startAnalizeImageButton.setDisable(false);
            setChoosenPhotoToAnalize(file);

            //show progress bar
            getProgressIndicator().setVisible(true);
            getProgressIndicator().setProgress(0);
            getShowResultsButton().setDisable(true);
            getExportResultsButton().setDisable(true);
        }
        else{
            log.debug("No file choosen");
            //setChoosenPhotoToAnalize(null);

            if ( this.getImageInformation() == null ) {
                this.showResultsButton.setDisable(true);
                getExportResultsButton().setDisable(true);
            }


        }

    }

    @FXML
    private void handleSetAPIKey() {
        log.debug("Set API Key clicked!");

        SetAPIKeyHandler setAPIKeyHandler = new SetAPIKeyHandler(this.apiClaims);
        setAPIKeyHandler.show();


    }

    @FXML
    private void handleAbout() {
        log.debug("About clicked!");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);


        // Add icon to alert
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        addIcon( stage );
        alert.setTitle( translationService.getTranslation("app.title") );
        alert.setHeaderText( translationService.getTranslation("app.menubar.help.about.header") );

        //modification
        FlowPane flowPane = new FlowPane();
        Label lbl = new Label( translationService.getTranslation("app.menubar.help.about.content-text") );
        Hyperlink link = new Hyperlink( translationService.getTranslation("app.menubar.help.about.author-link") );
        link.setOnAction( e -> getMainApp().openUrl( link.getText() ) );

        //link.setPadding( new Insets(10, 10, 10, 10) );
        flowPane.getChildren().addAll( lbl, link);
        flowPane.setPadding( new Insets(10, 10, 10, 10) );

        alert.getDialogPane().contentProperty().set(flowPane);



        alert.showAndWait();
    }


    @FXML
    private void handleExit() {
        System.exit(0);
    }

    private void addIcon(Stage stage) {

        stage.getIcons().add(
                new Image( MainApp.class.getResource("/images/icon.png").toString() ));
    }


    @FXML
    private void handleStartAnalizeImage()  {

        boolean wasSuccessfullImage = false;

        log.debug("Button start analize image clicked!");
        this.showResultsButton.setDisable(true);
        getExportResultsButton().setDisable(true);
        getProgressIndicator().setProgress(0);
        try {
            ImageAnalizeService imageAnalizeService = ServiceFactory.getImageAnalizeService();
            APIConnectionService apiConnectionService = ServiceFactory.getAPIConnectionService( this.apiClaims );

            AnalizeImageThread analizeImageThread = new AnalizeImageThread(imageAnalizeService,
                                                                            apiConnectionService,
                                                                            getChoosenPhotoToAnalize());

            analizeImageThread.start();
            while( analizeImageThread.isAlive() ) {
                try{
                    Thread.sleep(100);
                    log.debug("Waiting for response from api...");
                }
                catch(InterruptedException ignored) { }
            }
            this.imageInformation = analizeImageThread.getImageInformation();

            //check if there was errors on analize image
            if (StringUtils.isNotEmpty( analizeImageThread.getAnalizeErrorMessage() )) {
                throw new Exception( analizeImageThread.getAnalizeErrorMessage() );
            }


            getProgressIndicator().setProgress(1);
            log.debug("Done...");
            getShowResultsButton().setDisable(false);
            getExportResultsButton().setDisable(false);
            //throw new Exception("Error on connection to AZURE services. Please thy again later...");
        } catch (Exception e) {
            getProgressIndicator().setProgress(0);
            log.debug("Done with errors...");
            getShowResultsButton().setDisable(true);
            getExportResultsButton().setDisable(true);
            UIExceptionHandler.handleException(e);


        }



    }


    @FXML
    private void handleShowResults() {

        log.debug("Show results clicked!");
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("ui/main/ShowResults.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle( translationService.getTranslation( "app.results-window.title" ) );
            dialogStage.setResizable(false);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner( getMainApp().getPrimaryStage() );
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            //add icon
            dialogStage.getIcons().add( new Image( MainApp.class.getResource("/images/icon.png").toString() ) );

            ShowResultsHandler showResultsHandler = loader.getController();
            showResultsHandler.setMainApp( getMainApp() );
            showResultsHandler.setImageInformation( getImageInformation() );
            showResultsHandler.prepareDataForShow();

            dialogStage.showAndWait();

        } catch (IOException e) {
            UIExceptionHandler.handleException(e);
        }


    }


    @FXML
    private void handleExportResults() {

        try{
            log.debug("Export results clicked!");

            FileChooser fileChooser = new FileChooser();

            // Set extension filter
            FileChooser.ExtensionFilter pdfFilter = new FileChooser.ExtensionFilter(
                    "PDF file (*.pdf)", "*.pdf");

            fileChooser.getExtensionFilters().add(pdfFilter);

            // Show open file dialog
            File file = fileChooser.showSaveDialog( mainApp.getPrimaryStage() );
            if ( file != null ) {
                String filePath = file.getAbsolutePath();
                ExportResultsService exportResultsService = ServiceFactory.getExportResultsService();
                exportResultsService.setImageInformation(getImageInformation());
                exportResultsService.setImage( getChoosenPhotoToAnalize() );
                exportResultsService.exportResults( filePath );


                //in this place we know that file was exported successfully
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle( translationService.getTranslation("app.export-results.alert.window-title") );
                alert.setHeaderText(null);
                alert.setContentText( String.format( translationService.getTranslation("app.export-results.alert.text"), filePath ) );
                // Add icon to alert
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                addIcon( stage );


                alert.showAndWait();
            }



        } catch (Exception exception) {
            UIExceptionHandler.handleException( exception );
        }






    }


}
