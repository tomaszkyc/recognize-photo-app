package com.tomaszkyc.app.core.services.exportresults.pdf.converter;

import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.tomaszkyc.app.core.services.ServiceFactory;
import com.tomaszkyc.app.core.services.exportresults.pdf.CellUtils;
import com.tomaszkyc.app.core.services.translation.TranslationService;
import com.tomaszkyc.app.core.util.UIStringFormatter;
import com.tomaszkyc.app.model.Description;
import com.tomaszkyc.app.ui.model.UIResult;

import java.util.Arrays;
import java.util.List;

public class DescriptionConverterTable implements TableGenericConverter<Description, PdfPTable> {

    private static final long VALUE_COLUMN_MAX_NUMBER_OF_CHAR = 25;
    private static final String VALUE_COLUMN_MULTIPLE_VALUES_DELIMITER = ",";


    private TranslationService translationService;

    public DescriptionConverterTable() {
        this.translationService = ServiceFactory.getTranslationService();
    }

    @Override
    public PdfPTable convert(Description element) {
        PdfPTable table = new PdfPTable(2); // 3 columns.
        table.setWidthPercentage(100); //Width 100%
        table.setHeaderRows(1);

        //add header to table
        String tableTitle = translationService.getTranslation("app.export-results.pdf.raport.description-table.title");
        PdfPCell headerCell = new PdfPCell( new Phrase(tableTitle));
        headerCell.setColspan(2);
        table.addCell( headerCell );

        //add data to table using existing UIResult convertes
        List<UIResult> uiResults = new com.tomaszkyc.app.core.util.converter.DescriptionConverter().
                convertToCollection(element);

        for( UIResult uiResult : uiResults ) {

            String key = uiResult.getName();
            String value = uiResult.getValue();

            if ( value.contains(",") ) {
                value = UIStringFormatter.formatAsNewLineDelimitedText(value, VALUE_COLUMN_MULTIPLE_VALUES_DELIMITER, VALUE_COLUMN_MAX_NUMBER_OF_CHAR);
            }

            table.addCell( new PdfPCell( new Phrase( key ) ) );
            table.addCell( new PdfPCell( new Phrase( value ) ) );

        }



        //complete table
        table.completeRow();



        return table;
    }
}
