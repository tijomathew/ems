package org.rms.services;

import com.itextpdf.text.DocumentException;
import org.rms.models.ParentNode;
import org.rms.models.StudentNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * Created by cufa-03 on 19/10/16.
 */
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ParentService parentService;

    @Autowired
    private PdfExportService pdfExportService;

    @Override
    public File getReport(String massCentre, String date, String category) throws DocumentException {
        List<ParentNode> parentNodeList = parentService.getParentNodes(massCentre, date, category);
        if (!category.equals("all")) {
            for (ParentNode parentNode : parentNodeList) {
                for (Iterator<StudentNode> itr = parentNode.getStudentNodeList().listIterator(); itr.hasNext(); ) {
                    if (!itr.next().getRetreatSection().equals(category)) {
                        itr.remove();
                    }
                }
            }
        }
        File pdfFile = pdfExportService.createPdfReport(parentNodeList, massCentre, date);
        return pdfFile;
    }
}
