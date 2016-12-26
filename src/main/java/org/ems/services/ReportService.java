package org.ems.services;

import com.itextpdf.text.DocumentException;

import java.io.File;

/**
 * Created by cufa-03 on 19/10/16.
 */
public interface ReportService {

    File getReport(String massCentre, String date) throws DocumentException;
}
