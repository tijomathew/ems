package org.rms.controllers;

import org.rms.enums.SystemRole;
import org.rms.helpers.RequestResponseHolder;
import org.rms.models.InOutInformer;
import org.rms.models.ParentNode;
import org.rms.models.StudentNode;
import org.rms.models.User;
import org.rms.services.ParentService;
import org.rms.services.RegistrationService;
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
 * Created by tijo on 24/10/16.
 */

@Controller
@RequestMapping("/")
public class CheckInController {

    @Autowired
    private ParentService parentService;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private RequestResponseHolder requestResponseHolder;

    @RequestMapping(value = "checkinsearch.action", method = RequestMethod.GET)
    public String checkInPageDisplay(Model model, HttpServletRequest httpServletRequest) {
        model.addAttribute("searchCheckInParentNode", new ParentNode());
        HttpSession httpSession = httpServletRequest.getSession();
        httpSession.setAttribute("hideErrorMessageDiv", true);
        httpSession.setAttribute("hideSuccessMessageDiv", true);
        return "checkinsearchentry";
    }

    @RequestMapping(value = "checkinview.action", method = RequestMethod.POST)
    public String checkInSearchProcess(@ModelAttribute("searchCheckInParentNode") ParentNode parentNode, HttpServletRequest httpServletRequest, Model model) {
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
                httpSession.setAttribute("checkInParent", retrievedParentNode);
                model.addAttribute("checkInViewParent", new ParentNode());
            } else

            {
                HttpSession httpSession = httpServletRequest.getSession();
                httpSession.setAttribute("hideErrorMessageDiv", false);
                model.addAttribute("searchCheckInParentNode", new ParentNode());
                return "checkinsearchentry";
            }

            return "checkinview";
        } catch (Exception e) {
            HttpSession httpSession = httpServletRequest.getSession();
            httpSession.setAttribute("hideErrorMessageDiv", false);
            model.addAttribute("searchCheckInParentNode", new ParentNode());
            return "checkinsearchentry";
        }
    }

    @RequestMapping(value = "checkinupdate.action", method = RequestMethod.POST)
    public String checkInUpdateProcess(@ModelAttribute("checkInViewParent") ParentNode parentNode, HttpServletRequest httpServletRequest, Model model) {
        ParentNode retrievedParentNode = parentService.getParentNode(parentNode.getId());
        if (retrievedParentNode != null) {
            for (StudentNode studentNode : retrievedParentNode.getStudentNodeList()) {
                for (StudentNode checkInStudentNode : parentNode.getStudentNodeList()) {
                    if (checkInStudentNode.getId().longValue() == studentNode.getId().longValue() && checkInStudentNode.getCheckIn() != null && checkInStudentNode.getCheckIn()) {
                        if (!studentNode.getHasInEntryOnDate()) {
                            InOutInformer inOutInformer = new InOutInformer();
                            inOutInformer.setDate(getCurrentDateAsString());
                            inOutInformer.setInTime(new Date());
                            User userFromCurrentSession = requestResponseHolder.getAttributeFromSession(SystemRole.RMS_CURRENT_USER.toString(), User.class);
                            inOutInformer.setDoneInBy(userFromCurrentSession.getId());
                            inOutInformer.setStudentNode(studentNode);
                            studentNode.addInOutInformer(inOutInformer);
                        }
                    }
                }
            }

            registrationService.saveRegistrationEntry(retrievedParentNode);
            HttpSession httpSession = httpServletRequest.getSession();
            httpSession.setAttribute("hideSuccessMessageDiv", false);
        } else {
            HttpSession httpSession = httpServletRequest.getSession();
            httpSession.setAttribute("hideErrorMessageDiv", false);
        }

        model.addAttribute("searchCheckInParentNode", new ParentNode());
        return "checkinsearchentry";
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
