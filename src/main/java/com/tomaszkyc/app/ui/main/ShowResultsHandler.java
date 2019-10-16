package com.tomaszkyc.app.ui.main;

import com.tomaszkyc.app.MainApp;
import com.tomaszkyc.app.core.exception.BaseException;
import com.tomaszkyc.app.core.exception.NotYetImplementedException;
import com.tomaszkyc.app.core.services.ServiceFactory;
import com.tomaszkyc.app.core.services.translation.TranslationService;
import com.tomaszkyc.app.core.util.UIStringFormatter;
import com.tomaszkyc.app.core.util.converter.CategoryConverter;
import com.tomaszkyc.app.core.util.converter.ColorConverter;
import com.tomaszkyc.app.core.util.converter.DescriptionConverter;
import com.tomaszkyc.app.core.util.converter.MetadataConverter;
import com.tomaszkyc.app.model.Category;
import com.tomaszkyc.app.model.ImageInformation;
import com.tomaszkyc.app.ui.exception.UIExceptionHandler;
import com.tomaszkyc.app.ui.model.UIResult;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShowResultsHandler {

    private static final Logger log = LogManager.getLogger(ShowResultsHandler.class);

    private static final long VALUE_COLUMN_MAX_NUMBER_OF_CHAR = 25;
    private static final String VALUE_COLUMN_MULTIPLE_VALUES_DELIMITER = ",";

    private TranslationService translationService;

    public MainApp getMainApp() {
        return mainApp;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    private MainApp mainApp;

    public TreeTableView getResultsTable() {
        return resultsTable;
    }

    public void setResultsTable(TreeTableView resultsTable) {
        this.resultsTable = resultsTable;
    }

    @FXML
    private TreeTableView resultsTable;

    @FXML
    private TreeTableColumn<UIResult, String> propertyColumn;

    @FXML
    private TreeTableColumn<UIResult, String> valueColumn;


    public ImageInformation getImageInformation() {
        return imageInformation;
    }

    public void setImageInformation(ImageInformation imageInformation) {
        this.imageInformation = imageInformation;
    }

    private ImageInformation imageInformation;

    public ShowResultsHandler(){
        this.translationService = ServiceFactory.getTranslationService();
    }


    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

        this.propertyColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<UIResult, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<UIResult, String> parameter) {
                return new SimpleStringProperty( parameter.getValue().getValue().getName() );
            }
        });
        this.valueColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<UIResult, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<UIResult, String> parameter) {

                String inputValue = parameter.getValue().getValue().getValue();

                //if inside value is delimiter --> we will need to split text to new lines
                if ( inputValue.contains(",") ) {

                    inputValue = UIStringFormatter.formatAsNewLineDelimitedText(inputValue, VALUE_COLUMN_MULTIPLE_VALUES_DELIMITER, VALUE_COLUMN_MAX_NUMBER_OF_CHAR);

                }

                return new SimpleStringProperty( inputValue );
            }
        });


    }


    public void prepareDataForShow() {

        //create root node
        TreeItem<UIResult> rootNode = new TreeItem<>(new UIResult(translationService.getTranslation("app.results-window.properties-node.name"), ""));
        rootNode.setExpanded(true);

        //create categories nodes
        TreeItem<UIResult> categoriesRootNode = new TreeItem<>(new UIResult(translationService.getTranslation("app.results-window.categories-node.name"), ""));
        List<TreeItem<UIResult>> categoryNodes = createCategoriesNodes( getImageInformation() );
        categoriesRootNode.getChildren().setAll( categoryNodes );


        //create description nodes
        TreeItem<UIResult> descriptionRootNode = new TreeItem<>(new UIResult(translationService.getTranslation("app.results-window.description-node.name"), ""));
        List<TreeItem<UIResult>> descriptionNodes = createDescriptionNodes( getImageInformation() );
        descriptionRootNode.getChildren().setAll( descriptionNodes );


        //create metadata nodes
        TreeItem<UIResult> metadataRootNode = new TreeItem<>(new UIResult(translationService.getTranslation("app.results-window.metadata-node.name"), ""));
        List<TreeItem<UIResult>> metadataNodes = createMetadataNodes( getImageInformation() );

        metadataRootNode.getChildren().setAll( metadataNodes );


        //create color nodes
        TreeItem<UIResult> colorRootNode = new TreeItem<>(new UIResult(translationService.getTranslation("app.results-window.color-node.name"), ""));
        List<TreeItem<UIResult>> colorNodes = createColorNodes( getImageInformation() );
        colorRootNode.getChildren().setAll( colorNodes );




        //add all to root node
        rootNode.getChildren().setAll( categoriesRootNode, descriptionRootNode, metadataRootNode, colorRootNode );
        resultsTable.setRoot( rootNode );

    }





    private List<TreeItem<UIResult>> createCategoriesNodes(ImageInformation imageInformation ) {

        List<TreeItem<UIResult>> categoriesNodes = new ArrayList<>();

        CategoryConverter categoryConverter = new CategoryConverter();
        for( Category category : imageInformation.getCategories() ) {

            List<UIResult> uiResults = categoryConverter.convertToCollection( category );

            for( UIResult uiResult : uiResults ) {

                TreeItem<UIResult> node = new TreeItem<>( uiResult );
                categoriesNodes.add( node );

            }
        }


        return categoriesNodes;
    }

    private List<TreeItem<UIResult>> createDescriptionNodes(ImageInformation imageInformation ) {

        List<TreeItem<UIResult>> nodes = new ArrayList<>();

        DescriptionConverter converter = new DescriptionConverter();
        List<UIResult> uiResults = converter.convertToCollection( imageInformation.getDescription() );

        for( UIResult uiResult : uiResults ) {

            TreeItem<UIResult> node = new TreeItem<>( uiResult );
            nodes.add( node );

        }

        return nodes;
    }


    private List<TreeItem<UIResult>> createMetadataNodes(ImageInformation imageInformation ) {

        List<TreeItem<UIResult>> nodes = new ArrayList<>();

        MetadataConverter converter = new MetadataConverter();
        List<UIResult> uiResults = converter.convertToCollection( imageInformation.getMetadata() );

        for( UIResult uiResult : uiResults ) {

            TreeItem<UIResult> node = new TreeItem<>( uiResult );
            nodes.add( node );

        }

        return nodes;
    }

    private List<TreeItem<UIResult>> createColorNodes(ImageInformation imageInformation ) {

        List<TreeItem<UIResult>> nodes = new ArrayList<>();

        ColorConverter converter = new ColorConverter();
        List<UIResult> uiResults = converter.convertToCollection( imageInformation.getColor() );

        for( UIResult uiResult : uiResults ) {

            TreeItem<UIResult> node = new TreeItem<>( uiResult );
            nodes.add( node );

        }

        return nodes;
    }



}
