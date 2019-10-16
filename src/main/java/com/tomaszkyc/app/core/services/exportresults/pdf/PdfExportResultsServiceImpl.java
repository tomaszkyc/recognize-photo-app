package com.tomaszkyc.app.core.services.exportresults.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.tomaszkyc.app.core.services.ServiceFactory;
import com.tomaszkyc.app.core.services.exportresults.ExportResultsService;
import com.tomaszkyc.app.core.services.exportresults.pdf.converter.*;
import com.tomaszkyc.app.core.services.translation.TranslationService;
import com.tomaszkyc.app.model.Category;
import com.tomaszkyc.app.model.ImageInformation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PdfExportResultsServiceImpl implements ExportResultsService {

    private static final Font SMALL_BOLD = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);

    private TranslationService translationService;

    private static final Logger log = LogManager.getLogger(PdfExportResultsServiceImpl.class);

    private ImageInformation imageInformation;
    private File analizedImage;

    public ImageInformation getImageInformation() {
        return imageInformation;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    private Document document;

    public PdfExportResultsServiceImpl() {
        this.translationService = ServiceFactory.getTranslationService();
        this.document = new Document();
    }

    @Override
    public void exportResults(String filePath) {

        log.debug("Starting export PDF file to path: " + filePath);

        //if no data - throw exception
        if ( this.imageInformation == null ) {
            throw new NoDataToExportException("");
        }

        //if filepath empty - throw exception
        if (StringUtils.isEmpty( filePath ) ) {
            throw new CreatePdfException("Given filepath is null or empty");
        }

        try{
            //create Document object
            log.debug("Starting creating document...");
            createDocument(filePath);

            //add title
            log.debug("Starting adding title to document...");
            addTitleToDocument();

            //add small description
            log.debug("Starting adding document description...");
            addDocumentDescription();

            //add image
            log.debug("Starting adding image to document...");
            addImageToDocument();

            //add text about table with details
            log.debug("Starting adding text about table with details");
            addTextToDocument();

            //add all data found in API response
            log.debug("Starting adding to document information about image");
            addImageInformationDataToDocument();

            //save document
            log.debug("Staring saving document...");
            saveDocument();

        }
        catch(Exception exception) {
            throw new CreatePdfException( exception.getMessage() );
        }

        log.debug("Finished export PDF file to path: " + filePath);
    }

    private void addImageInformationDataToDocument() throws DocumentException {

        //throw new NotYetImplementedException("add image information data to document not implemented");

        //add some space between image and table
        Paragraph freespaceParagraph = new Paragraph();
        addEmptyLine(freespaceParagraph, 2);
        getDocument().add( freespaceParagraph );


        createCategoryTable();

        createDescriptionTable();

        addMetadataTable();

        addColorTable();

    }


    private void addColorTable() throws DocumentException {

        //add categories table
        PdfPTable table = new PdfPTable(1);
        ColorConverterTable converter = new ColorConverterTable();

        //add header to table
        String tableTitle = translationService.getTranslation("app.export-results.pdf.raport.grouped.color-table.title");
        PdfPCell headerCell = new PdfPCell( new Phrase(tableTitle, FontFactory.getFont(FontFactory.TIMES_BOLD)));
        headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell.setExtraParagraphSpace(2);
        headerCell.setColspan(1);
        table.addCell( headerCell );

        table.addCell( converter.convert( getImageInformation().getColor() ) );

        //complete table
        table.completeRow();

        //add to document
        getDocument().add(table);


    }

    private void addMetadataTable() throws DocumentException {

        //add categories table
        PdfPTable table = new PdfPTable(1);
        MetadataConverterTable converter = new MetadataConverterTable();

        //add header to table
        String tableTitle = translationService.getTranslation("app.export-results.pdf.raport.grouped.metadata-table.title");
        PdfPCell headerCell = new PdfPCell( new Phrase(tableTitle, FontFactory.getFont(FontFactory.TIMES_BOLD)));
        headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell.setExtraParagraphSpace(2);
        headerCell.setColspan(1);
        table.addCell( headerCell );

        table.addCell( converter.convert( getImageInformation().getMetadata() ) );

        //complete table
        table.completeRow();

        //add to document
        getDocument().add(table);

    }

    private void createDescriptionTable() throws DocumentException {

        //add categories table
        PdfPTable table = new PdfPTable(1);
        DescriptionConverterTable converter = new DescriptionConverterTable();

        //add header to table
        String tableTitle = translationService.getTranslation("app.export-results.pdf.raport.grouped.description-table.title");
        PdfPCell headerCell = new PdfPCell( new Phrase(tableTitle, FontFactory.getFont(FontFactory.TIMES_BOLD)));
        headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell.setExtraParagraphSpace(2);
        headerCell.setColspan(1);
        table.addCell( headerCell );

        table.addCell( converter.convert( getImageInformation().getDescription() ) );

        //complete table
        table.completeRow();

        //add to document
        getDocument().add(table);

    }

    private void createCategoryTable() throws DocumentException {

        //add categories table
        PdfPTable table = new PdfPTable(1);
        CategoryConverterTable categoryConverter = new CategoryConverterTable();

        //add header to table
        String tableTitle = translationService.getTranslation("app.export-results.pdf.raport.grouped.categories-table.title");
        PdfPCell headerCell = new PdfPCell( new Phrase(tableTitle, FontFactory.getFont(FontFactory.TIMES_BOLD)));
        headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell.setExtraParagraphSpace(2);
        headerCell.setColspan(1);
        table.addCell( headerCell );

        for(Category category : getImageInformation().getCategories()) {

            table.addCell( categoryConverter.convert( category ) );

        }

        //complete table
        table.completeRow();

        //add to document
        getDocument().add(table);
    }

    @Override
    public void setImageInformation(ImageInformation imageInformation) {
        this.imageInformation = imageInformation;
    }

    @Override
    public void setImage(File imageFile) {
        this.analizedImage = imageFile;
    }

    @Override
    public File getImage() {
        return this.analizedImage;
    }

    private void addTitleToDocument() throws DocumentException {

        String title = translationService.getTranslation("app.export-results.pdf.document-title");
        Paragraph titleParagraph = new Paragraph();
        Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
                Font.BOLD);

        addEmptyLine(titleParagraph, 1);

        titleParagraph.add( new Paragraph(title, titleFont) );

        getDocument().add( titleParagraph );

    }

    private void addDocumentDescription() throws DocumentException {

        String text = translationService.getTranslation("app.export-results.pdf.raport-description");

        //get actual user name
        String userName = System.getProperty("user.name", "");
        userName = WordUtils.capitalize(userName);

        //get actual date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String actualDate = LocalDateTime.now().format(formatter);

        //create string from above information
        String textToAdd = String.format( text, userName, actualDate );

        //create paragraph
        Paragraph descriptionParagraph = new Paragraph();
        addEmptyLine(descriptionParagraph, 2);



        descriptionParagraph.add( new Paragraph( textToAdd, SMALL_BOLD) );
        getDocument().add( descriptionParagraph );


    }

    private void addTextToDocument() throws DocumentException {

        String text = translationService.getTranslation("app.export-results.pdf.raport.table-description");
        //create paragraph
        Paragraph descriptionParagraph = new Paragraph();
        addEmptyLine(descriptionParagraph, 8);



        descriptionParagraph.add( new Paragraph( text, SMALL_BOLD) );
        getDocument().add( descriptionParagraph );
    }


    private void addImageToDocument() throws DocumentException, IOException {

        //add some space between prev content and image
        Paragraph imageParagraph = new Paragraph();
        addEmptyLine(imageParagraph, 1);
        getDocument().add( imageParagraph );

        //add image
        com.itextpdf.text.Image imageToAdd = com.itextpdf.text.Image.getInstance( getImage().getAbsolutePath() );
        imageToAdd.scaleAbsolute(500, 500);
        getDocument().add( imageToAdd );

    }



    private static void addEmptyLine(Paragraph paragraph, int numberOfEmptyLinesToAdd) {
        for (int i = 0; i < numberOfEmptyLinesToAdd; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    private void createDocument(String filePath) throws FileNotFoundException, DocumentException {

        PdfWriter writer = PdfWriter.getInstance( getDocument(), new FileOutputStream(filePath));
        HeaderFooterPageEvent headerFooterPageEvent = new HeaderFooterPageEvent();
        writer.setPageEvent(headerFooterPageEvent);
        getDocument().open();

    }

    private void saveDocument() {

        if ( getDocument().isOpen() ) {
            getDocument().close();
        }

    }



}
