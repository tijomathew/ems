package org.rms.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.rms.models.ParentNode;
import org.rms.models.StudentNode;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;

/**
 * Created by cufa-03 on 20/10/16.
 */
@Service
public class PdfExportServiceImpl implements PdfExportService {
    private Document document;
    private PdfWriter writer;
    private PdfPTable table;
    Font fontBold;
    Font fontNormal;

    public PdfExportServiceImpl() {
        fontBold = FontFactory.getFont("Verdana", 7, Font.BOLD);
        fontNormal = FontFactory.getFont("Verdana", 7, Font.NORMAL);
    }

    @Override
    public File createPdfReport(List<ParentNode> parentNodes, String massCentre, String date) throws DocumentException {
        document = new Document(PageSize.A4_LANDSCAPE);
        File tempDestFile = createTempPdfFile();//create a temporary pdf file in the temp directory of web server
        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream(tempDestFile));//to get an instance of the PdfWriter
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        document.setPageSize(PageSize.A4.rotate());
        document.setMargins(75, 75, 100, 75);

        //A class to write the header and footer to the pdf
        PdfHeaderAndFooter event = new PdfHeaderAndFooter(massCentre, date);
        //writer.setBoxSize("headerBox", headerBox);
        writer.setPageEvent(event);

        document.open();
        table = new PdfPTable(10);//create a table with respective column size

        table.setWidths(new int[]{4, 15, 7, 7, 15, 7, 10, 15, 10, 10});
        table.setSpacingBefore(5);
        table.setSpacingAfter(5);
        addCellContentToPDFTable("No.", fontBold, Element.ALIGN_CENTER);
        addCellContentToPDFTable("Parent Name", fontBold, Element.ALIGN_CENTER);
        addCellContentToPDFTable("Phone No.1", fontBold, Element.ALIGN_CENTER);
        addCellContentToPDFTable("Phone No.2", fontBold, Element.ALIGN_CENTER);
        addCellContentToPDFTable("Child Name", fontBold, Element.ALIGN_CENTER);
        addCellContentToPDFTable("Category", fontBold, Element.ALIGN_CENTER);
        addCellContentToPDFTable("Band Code", fontBold, Element.ALIGN_CENTER);
        addCellContentToPDFTable("Days", fontBold, Element.ALIGN_CENTER);
        addCellContentToPDFTable("Check In", fontBold, Element.ALIGN_CENTER);
        addCellContentToPDFTable("Check Out", fontBold, Element.ALIGN_CENTER);
        int i = 1;
        PdfPCell cell;
        for (ParentNode parentNode : parentNodes) {

            int rowSpan = parentNode.getStudentNodeList().size();
            cell = new PdfPCell(new Phrase(new Chunk(String.valueOf(i), fontNormal)));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setRowspan(rowSpan > 0 ? rowSpan : 1);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(new Chunk(parentNode.getFullName().toString(), fontBold)));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setRowspan(rowSpan > 0 ? rowSpan : 1);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(new Chunk(parentNode.getPhoneNumber().toString(), fontNormal)));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setRowspan(rowSpan > 0 ? rowSpan : 1);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(new Chunk(parentNode.getAlternativePhoneNumber().toString(), fontNormal)));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setRowspan(rowSpan > 0 ? rowSpan : 1);
            table.addCell(cell);
            if (rowSpan > 0) {
                for (StudentNode studentNode : parentNode.getStudentNodeList()) {
                    addCellContentToPDFTable(studentNode.getFullName(), fontNormal, Element.ALIGN_LEFT);
                    addCellContentToPDFTable(studentNode.getRetreatSection(), fontNormal, Element.ALIGN_LEFT);
                    addCellContentToPDFTable(studentNode.getBandCode(), fontNormal, Element.ALIGN_LEFT);
                    addCellContentToPDFTable(studentNode.getAllRegisteredDays(), fontNormal, Element.ALIGN_LEFT);
                    addCellContentToPDFTable(studentNode.getInTimes(date), fontNormal, Element.ALIGN_LEFT);
                    addCellContentToPDFTable(studentNode.getOutTimes(date), fontNormal, Element.ALIGN_LEFT);
                }
            } else {
                addCellContentToPDFTable("", fontNormal, Element.ALIGN_LEFT);
                addCellContentToPDFTable("", fontNormal, Element.ALIGN_LEFT);
                addCellContentToPDFTable("", fontNormal, Element.ALIGN_LEFT);
                addCellContentToPDFTable("", fontNormal, Element.ALIGN_LEFT);
                addCellContentToPDFTable("", fontNormal, Element.ALIGN_LEFT);
            }
            i++;

        }
        table.setWidthPercentage(100);  // set the width of the table to 100% of page
        document.add(table);  // Now add all this to the document
        document.close();

        return tempDestFile;
    }

    public void addCellContentToPDFTable(Object obj, Font font, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(null != obj.toString() ? obj.toString().replaceAll("[€|$] ", "") : "", font));
        cell.setHorizontalAlignment(alignment);
        table.addCell(cell);//add the cell content to pdf

    }

    //function to create the temporary pdf file
    private File createTempPdfFile() {
        UUID uu_fileName = UUID.randomUUID();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(System.getProperty("java.io.tmpdir"));
        stringBuilder.append(System.getProperty("file.separator"));
        stringBuilder.append("retreat_report" + uu_fileName.toString() + ".pdf");
        File tempFile = new File(stringBuilder.toString());
        return tempFile;
    }
}
