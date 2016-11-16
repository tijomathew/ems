package org.rms.controllers;

import org.rms.models.ParentNode;
import org.rms.models.StudentNode;
import org.rms.services.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by bibin on 28/10/16.
 */

@Controller
@RequestMapping("/")
public class SearchController {

    @Autowired
    private ParentService parentService;

    @RequestMapping(value = "searchviewentry.action", method = RequestMethod.GET)
    public String checkInPageDisplay(Model model, HttpServletRequest httpServletRequest) {
        model.addAttribute("searchParentNode", new ParentNode());
        HttpSession httpSession = httpServletRequest.getSession();
        httpSession.setAttribute("hideErrorMessageDiv", true);
        return "searchentryform";
    }

    @RequestMapping(value = "searchview.action", method = RequestMethod.POST)
    public String checkInSearchProcess(@ModelAttribute("searchParentNode") ParentNode parentNode, HttpServletRequest httpServletRequest, Model model) {
        try {
            ParentNode retrievedParentNode = parentService.getCheckInOutParentNodeDetails(parentNode);

            if (retrievedParentNode != null) {
                //Remove other dates registered Child

                Iterator<StudentNode> studentNodeIterator = retrievedParentNode.getStudentNodeList().iterator();

                while (studentNodeIterator.hasNext()) {
                    StudentNode studentNode = studentNodeIterator.next();

                    if (getCurrentDateAsString().equals("Oct-29")) {
                        if (studentNode.getDayOne() == null)
                            studentNodeIterator.remove();
                    }
                    if (getCurrentDateAsString().equals("Oct-30")) {
                        if (studentNode.getDayTwo() == null)
                            studentNodeIterator.remove();
                    }
                    if (getCurrentDateAsString().equals("Oct-31")) {
                        if (studentNode.getDayThree() == null)
                            studentNodeIterator.remove();
                    }
                    if (getCurrentDateAsString().equals("Nov-1")) {
                        if (studentNode.getDayFour() == null)
                            studentNodeIterator.remove();
                    }
                }
                HttpSession httpSession = httpServletRequest.getSession();
                httpSession.setAttribute("searchParent", retrievedParentNode);
            } else {
                HttpSession httpSession = httpServletRequest.getSession();
                httpSession.setAttribute("hideErrorMessageDiv", false);
                model.addAttribute("searchParentNode", new ParentNode());
                return "searchentryform";
            }
            return "searchview";
        } catch (Exception e) {
            HttpSession httpSession = httpServletRequest.getSession();
            httpSession.setAttribute("hideErrorMessageDiv", false);
            model.addAttribute("searchParentNode", new ParentNode());
            return "searchentryform";
        }
    }

    private String getCurrentDateAsString() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM-dd");
        String currentDateInStringFormat = dateFormatter.format(currentDate);
        if (currentDateInStringFormat.equalsIgnoreCase("Nov-01")) {
            currentDateInStringFormat=currentDateInStringFormat.replace("0", "");
        }
        return currentDateInStringFormat;
    }
}
