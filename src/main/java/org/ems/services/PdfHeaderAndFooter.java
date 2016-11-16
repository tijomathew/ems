package org.rms.services;

/**
 * Created by Cufa User on 16/7/14.
 */

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class PdfHeaderAndFooter extends PdfPageEventHelper {


    protected Phrase footer;
    private PdfContentByte pdfContentByte;
    private PdfTemplate pdfTemplate;
    private BaseFont baseFont;
    String reportName = null;
    String date = null;
    private Map<String, String> lookUpMap;

    private static Font fontBold = FontFactory.getFont("Verdana", 9, Font.BOLD);
    private static Font fontNormal = FontFactory.getFont("Verdana", 8, Font.NORMAL);

    /*
     * constructor
     */
    public PdfHeaderAndFooter(String reportName, String date) {
        this.reportName = reportName;
        this.date = date;
        this.baseFont = fontNormal.getCalculatedBaseFont(BaseFont.NOT_EMBEDDED);
        lookUpMap = new HashMap<>();
        lookUpMap.put("all", "All");
        lookUpMap.put("dayOne", "Oct-29");
        lookUpMap.put("dayTwo", "Oct-30");
        lookUpMap.put("dayThree", "Oct-31");
        lookUpMap.put("dayFour", "Nov-1");

    }

    @Override
    public void onStartPage(PdfWriter writer, Document document) {
        super.onStartPage(writer, document);
        setTypeAndTemplate(writer);//need to set the content type and template for the footer page number template creation need to make sure these values are set before template creation
        //pdfTemplate = writer.getDirectContent().createTemplate(50, 50);
        writer.addPageDictEntry(PdfName.ROTATE, PdfPage.LANDSCAPE);
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        super.onEndPage(writer, document);
        setTypeAndTemplate(writer); //need to set the content type and template for the footer page number template creation, need to make sure these values are set since onEndPage is calling prior to onStartPage
        PdfContentByte cb = writer.getDirectContent();
        String pageNumText = "Page " + writer.getPageNumber() + " of ";
        try {
            pdfContentByte.beginText();
            pdfContentByte.setFontAndSize(baseFont, 8);
            pdfContentByte.setTextMatrix(document.getPageSize().getRight(130), document.getPageSize().getBottom(30)); //sets the template position
            pdfContentByte.showText(pageNumText);
            pdfContentByte.endText();
            float len = baseFont.getWidthPoint(pageNumText, 8);
            try {
                pdfContentByte.addTemplate(pdfTemplate, document.getPageSize().getRight(130) + len, document.getPageSize().getBottom(30)); //adding template to footer [Page # of ###]
            } catch (Exception e) {
                e.printStackTrace();
            }
            Rectangle page = document.getPageSize();
            PdfPTable head = new PdfPTable(2);
            head.setTotalWidth(new float[]{150, document.getPageSize().getWidth() - 150 - document.leftMargin() - document.rightMargin()});
            PdfPCell cell = new PdfPCell();
            cell.setBorder(0);
            cell.setPadding(4);
            head.addCell(cell);
            Phrase phrase = new Phrase();

            Chunk chunk = new Chunk("Syro Malabar Catholic Church Dublin, Ireland".concat("\n\n"), fontBold);
            phrase.add(chunk);
            chunk = new Chunk(reportName.toUpperCase().concat("\n\n"), fontBold);
            phrase.add(chunk);
            chunk = new Chunk(lookUpMap.get(date).concat("\n\n"), fontBold);
            phrase.add(chunk);
            phrase.add(Chunk.NEWLINE);
            phrase.add(Chunk.NEWLINE);

            cell = new PdfPCell(phrase);
            cell.setBorder(0);
            cell.setPaddingLeft(100);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            head.addCell(cell);
            head.writeSelectedRows(0, -1, document.leftMargin(), page.getHeight() - document.topMargin()
                    + head.getTotalHeight(), writer.getDirectContent());

        } catch (Exception x) {
            x.printStackTrace();
        }
        //Footer
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        String date = sdf.format(new Date());
        ColumnText.showTextAligned(cb, Element.ALIGN_BOTTOM, new Phrase(date, fontNormal), document.left(), document.getPageSize().getBottom(30), 0);
        ColumnText.showTextAligned(cb, Element.ALIGN_BOTTOM, new Phrase(reportName, fontNormal), document.right(400), document.getPageSize().getBottom(30), 0);

    }

    /*
        Overrided to set the page numbering template text
     */
    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
        super.onCloseDocument(writer, document);

        if (null != pdfTemplate) {
            pdfTemplate.beginText();
            pdfTemplate.setFontAndSize(baseFont, 8);
            pdfTemplate.setTextMatrix(0, 0);
            pdfTemplate.showText((writer.getPageNumber() - 1) + "");
            pdfTemplate.endText();
        }

    }

    /*
        Method sets the PDFContentType and PDFTemplate for page number template generation, should be an one time process
     */
    public void setTypeAndTemplate(PdfWriter pdfWriter) {
        if (null == this.pdfContentByte)
            this.pdfContentByte = pdfWriter.getDirectContent();
        if (null == this.pdfTemplate)
            this.pdfTemplate = this.pdfContentByte.createTemplate(50, 50);
    }

}