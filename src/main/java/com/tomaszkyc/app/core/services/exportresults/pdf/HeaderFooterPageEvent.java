package com.tomaszkyc.app.core.services.exportresults.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.tomaszkyc.app.core.services.ServiceFactory;
import com.tomaszkyc.app.core.services.translation.TranslationService;

public class HeaderFooterPageEvent extends PdfPageEventHelper {


    private TranslationService translationService;


    public HeaderFooterPageEvent() {
        this.translationService = ServiceFactory.getTranslationService();
    }

    public void onStartPage(PdfWriter writer, Document document) { }

    public void onEndPage(PdfWriter writer, Document document) {

        Phrase linkToAppPhrase = new Phrase("\u00A9 github.com/tomaszkyc", new Font(Font.FontFamily.HELVETICA, 8));
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, linkToAppPhrase, 110, 30, 0);

        Phrase pagePhrase = new Phrase(String.format( translationService.getTranslation("app.export-results.pdf.raport.footer.page.text") , writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8));
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, pagePhrase, 550, 30, 0);
    }

}