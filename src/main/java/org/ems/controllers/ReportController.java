package org.rms.controllers;

import org.rms.displaywrappers.ChildWrapper;
import org.rms.helpers.GridRow;
import org.rms.helpers.JsonBuilder;
import org.rms.models.StudentNode;
import org.rms.services.ChildService;
import org.rms.services.ParentService;
import org.rms.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cufa-03 on 19/10/16.
 */
@Controller
@RequestMapping("/")
public class ReportController {

    @Autowired
    private ParentService parentService;

    @Autowired
    private ChildService childService;

    @Autowired
    private ReportService reportService;


    @RequestMapping(value = "reportpage.action", method = RequestMethod.GET)
    public String registrationPageDisplay(Model model) {
        return "report";
    }

    @RequestMapping(value = "/viewchildreport.action", method = RequestMethod.GET)
    @ResponseBody
    public Object getChildDetails(@RequestParam(value = "page") Integer page, @RequestParam(value = "rows") Integer rows, HttpServletResponse response,
                                  Model model, @RequestParam(value = "sord") String sortOrder, @RequestParam(value = "sidx") String sortName,
                                  @RequestParam(value = "filters", required = false) String filter,
                                  @RequestParam(value = "parentId") Long parentId) {
        List<StudentNode> childNodes = childService.getChildDetails(parentId);
        Integer childNodesCount = childNodes.size();
        List<StudentNode> ChildNodeSubList = new ArrayList<StudentNode>();
        if (childNodesCount > 0) {
            ChildNodeSubList = JsonBuilder.generateSubList(page, rows, childNodesCount, childNodes);
        }
        List<GridRow> ChildGridRows = new ArrayList<GridRow>(ChildNodeSubList.size());
        if (!ChildNodeSubList.isEmpty()) {
            for (StudentNode childNode : ChildNodeSubList) {
                ChildGridRows.add(new ChildWrapper(childNode));
            }
        }
        return JsonBuilder.convertToJson(rows, page, childNodesCount, ChildGridRows);
    }

    @RequestMapping(value = "/pdfreport.action", method = RequestMethod.POST)
    @ResponseBody
    public Object pdfCustomLogReport(HttpServletRequest request, HttpServletResponse response,
                                     @RequestParam(value = "massCentre", required = false) String massCentre,
                                     @RequestParam(value = "date", required = false) String date,
                                     @RequestParam(value = "category", required = false) String category) {


        try {
            File pdfFile = reportService.getReport(massCentre, date, category);
            if (null != pdfFile) {
                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "attachment; filename=Syro_Malabar_Catholic_Church_Retreat_Report.pdf");
                FileInputStream in = new FileInputStream(pdfFile);
                OutputStream out = response.getOutputStream();
                byte[] buffer = new byte[8192];
                int length;

                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
                in.close();
                out.close();
            } else {
                return null;
            }

        } catch (Exception e) {
            System.out.println("ERROR: " + e);
        }

        return null;

    }


}
