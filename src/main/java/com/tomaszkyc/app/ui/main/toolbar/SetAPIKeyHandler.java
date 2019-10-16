package com.tomaszkyc.app.ui.main.toolbar;

import com.tomaszkyc.app.MainApp;
import com.tomaszkyc.app.core.services.ServiceFactory;
import com.tomaszkyc.app.core.services.translation.TranslationService;
import com.tomaszkyc.app.model.APIClaims;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.util.Optional;

public class SetAPIKeyHandler {

    private static final Logger log = LogManager.getLogger(SetAPIKeyHandler.class);

    private TranslationService translationService;

    public APIClaims getApiClaims() {
        return apiClaims;
    }

    public void setApiClaims(APIClaims apiClaims) {
        this.apiClaims = apiClaims;
    }

    private APIClaims apiClaims;

    public SetAPIKeyHandler(APIClaims apiClaims) {
        this.apiClaims = apiClaims;
        this.translationService = ServiceFactory.getTranslationService();
    }

    private void addIcon(Dialog dialog) {
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(
                new Image( MainApp.class.getResource("/images/icon.png").toString() ));
    }


    public void show() {

        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle( translationService.getTranslation("app.menubar.edit.set-api-key.title") );
        dialog.setHeaderText( translationService.getTranslation("app.menubar.edit.set-api-key.header") );
        dialog.setContentText( translationService.getTranslation("app.menubar.edit.set-api-key.content-text") );

        //add icon to window
        addIcon(dialog);

        // Set the button types.
        ButtonType saveAPICredentialsButton = new ButtonType(translationService.getTranslation("app.menubar.edit.set-api-key.save-button")
                                    , ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveAPICredentialsButton, ButtonType.CANCEL);


        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField apiEndpoint = new TextField();
        apiEndpoint.setPrefWidth(500);
        apiEndpoint.setPromptText( translationService.getTranslation("app.menubar.edit.set-api-key.api-endpoint-field.text") );
        TextField apiKey = new TextField();
        apiKey.setPrefWidth(500);
        apiKey.setPromptText( translationService.getTranslation("app.menubar.edit.set-api-key.api-key-field.text") );

        grid.add(new Label(translationService.getTranslation("app.menubar.edit.set-api-key.api-endpoint-field-label.text")), 0, 0);
        grid.add(apiEndpoint, 1, 0);
        grid.add(new Label(translationService.getTranslation("app.menubar.edit.set-api-key.api-key-field-label.text")), 0, 1);
        grid.add(apiKey, 1, 1);


        Node saveAPIClaimsButton = dialog.getDialogPane().lookupButton(saveAPICredentialsButton);
        saveAPIClaimsButton.setDisable(true);

        //set validation
        apiEndpoint.textProperty().addListener((observable, oldValue, newValue) -> {
            saveAPIClaimsButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        //set focus on api endpoint field
        Platform.runLater(apiEndpoint::requestFocus);

        // Convert the result to a "API ENDPOINT"-"API KEY"-pair when the button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveAPICredentialsButton) {
                return new Pair<>(apiEndpoint.getText(), apiKey.getText());
            }
            return null;
        });

        //check if some values are inside claims
        if (StringUtils.isNotBlank( this.apiClaims.getApiEndpoint() )) {
            apiEndpoint.setText( this.apiClaims.getApiEndpoint() );
        }
        if (StringUtils.isNotBlank( this.apiClaims.getApiKey() )) {
            apiKey.setText( this.apiClaims.getApiKey() );
        }


        //show dialog
        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent( stringClaims -> {
            this.apiClaims.setApiEndpoint( stringClaims.getKey() );
            this.apiClaims.setApiKey( stringClaims.getValue() );
            log.debug("Claims saved!");
        } );

    }

}
