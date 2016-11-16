package org.rms.services;

import com.itextpdf.text.DocumentException;
import org.rms.models.ParentNode;

import java.io.File;
import java.util.List;

/**
 * Created by cufa-03 on 20/10/16.
 */
public interface PdfExportService {

    File createPdfReport(final List<ParentNode> parentNodes, String massCentre, String date) throws DocumentException;
}
