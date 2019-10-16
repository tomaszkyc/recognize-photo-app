package com.tomaszkyc.app.core.services.exportresults.pdf.converter;

import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.tomaszkyc.app.core.services.ServiceFactory;
import com.tomaszkyc.app.core.services.exportresults.pdf.CellUtils;
import com.tomaszkyc.app.core.services.translation.TranslationService;
import com.tomaszkyc.app.model.Color;
import com.tomaszkyc.app.ui.model.UIResult;

import java.util.Arrays;
import java.util.List;

public class ColorConverterTable implements TableGenericConverter<Color, PdfPTable> {


    private TranslationService translationService;

    public ColorConverterTable() {
        this.translationService = ServiceFactory.getTranslationService();
    }

    @Override
    public PdfPTable convert(Color element) {

        PdfPTable table = new PdfPTable(2); // 3 columns.
        table.setWidthPercentage(100); //Width 100%
        table.setHeaderRows(1);

        //add header to table
        String tableTitle = translationService.getTranslation("app.export-results.pdf.raport.category-table.title");
        PdfPCell headerCell = new PdfPCell( new Phrase(tableTitle));
        headerCell.setColspan(2);
        table.addCell( headerCell );

        //add data to table using existing UIResult convertes
        List<UIResult> uiResults = new com.tomaszkyc.app.core.util.converter.ColorConverter().
                                    convertToCollection(element);

        for( UIResult uiResult : uiResults ) {

            String key = uiResult.getName();
            String value = uiResult.getValue();

            table.addCell( new PdfPCell( new Phrase( key ) ) );
            table.addCell( new PdfPCell( new Phrase( value ) ) );

        }



        //complete table
        table.completeRow();



        return table;
    }
}
