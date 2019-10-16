package com.tomaszkyc.app.core.services.exportresults.pdf;

import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPCell;

public class CellUtils {

    public static void centerTextInsideCell(PdfPCell cell) {

        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setExtraParagraphSpace(2);

    }


}
