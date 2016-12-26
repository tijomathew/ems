package org.ems.services;

import com.itextpdf.text.DocumentException;
import org.ems.models.ParentNode;
import org.ems.models.StudentNode;
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
    public File getReport(String massCentre, String date) throws DocumentException {
        List<ParentNode> parentNodeList = parentService.getParentNodes(massCentre, date);
        File pdfFile = pdfExportService.createPdfReport(parentNodeList, massCentre, date);
        return pdfFile;
    }
}
