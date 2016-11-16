package org.rms.controllers;

import org.rms.models.ParentNode;
import org.rms.models.StudentNode;
import org.rms.models.User;
import org.rms.services.ChildService;
import org.rms.services.MailService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cufa-01 on 19/10/16.
 */

@Controller
@RequestMapping("/")
public class EditRegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private MailService mailService;

    @Autowired
    private ChildService childService;

    @Autowired
    private ParentService parentService;

    @RequestMapping(value = "editregistration.action", method = RequestMethod.POST)
    public String registerationProcess(@ModelAttribute("parentNodeForm") ParentNode parentNode, HttpServletRequest httpServletRequest) {
        List<StudentNode> updatedStudentNodeList = parentNode.getStudentNodeList();

        ParentNode retrievedParentNode = registrationService.getRegisteredEntry(parentNode.getEmail());

        List<Long> retrievedStudentNodeIDs = new ArrayList<>();

        for (StudentNode retrievedStudentNode : retrievedParentNode.getStudentNodeList()) {
            retrievedStudentNodeIDs.add(retrievedStudentNode.getId());
        }

        List<StudentNode> removeStudentNodeFromDB = new ArrayList<>();

        if (!retrievedStudentNodeIDs.isEmpty()) {
            for (StudentNode studentNode : updatedStudentNodeList) {
                if (studentNode.getId() != null) {
                    if (retrievedStudentNodeIDs.contains(studentNode.getId())) {
                        retrievedStudentNodeIDs.remove(studentNode.getId());
                    }
                }
            }
        }

        if (!retrievedStudentNodeIDs.isEmpty()) {
            removeStudentNodeFromDB = childService.getChildsByIds(retrievedStudentNodeIDs);
        }

        if (!removeStudentNodeFromDB.isEmpty()) {
            for (StudentNode studentNode : removeStudentNodeFromDB) {
                studentNode.setParentNode(null);
                registrationService.deleteStudentNode(studentNode);
            }
        }

        List<StudentNode> removeEmptyStudentList = new ArrayList<>();

        for (StudentNode studentNode : updatedStudentNodeList) {
            if (studentNode.getFirstName() != null && studentNode.getLastName() != null) {
                if (!studentNode.getFirstName().isEmpty() && !studentNode.getLastName().isEmpty()) {
                    studentNode.setParentNode(parentNode);
                } else {
                    removeEmptyStudentList.add(studentNode);
                }
            } else {
                removeEmptyStudentList.add(studentNode);
            }
        }

        for (StudentNode studentNode : removeEmptyStudentList) {
            if (updatedStudentNodeList.contains(studentNode)) {
                updatedStudentNodeList.remove(studentNode);
            }
        }

        ParentNode savedParentNode = registrationService.saveRegistrationEntry(parentNode);
        List<StudentNode> studentNodesToUpdateWithBandCode = savedParentNode.getStudentNodeList();

        List<StudentNode> updatedStudentNodes = new ArrayList<>();

        Integer lengthOfMassCentreName = savedParentNode.getMassCentreName().length();
        String concatMassCentreCode = lengthOfMassCentreName >= 3 ? savedParentNode.getMassCentreName().substring(0, 3) : savedParentNode.getMassCentreName();

        for (StudentNode studentNode : studentNodesToUpdateWithBandCode) {
            String bandCode = concatMassCentreCode.toUpperCase() + "-" + savedParentNode.getId() + "-" + studentNode.getId();
            studentNode.setBandCode(bandCode);
            updatedStudentNodes.add(studentNode);
        }
        savedParentNode.setStudentNodeList(updatedStudentNodes);

        Boolean emailSentForRegisteredParent = mailService.sendRegistrationDetailsWithConsentForm(savedParentNode);

        if (emailSentForRegisteredParent) {
            savedParentNode.setEmailSent(true);
        } else {
            savedParentNode.setEmailSent(false);
        }

        registrationService.saveRegistrationEntry(savedParentNode);

        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("parentNodeEntry", savedParentNode);

        return "registrationsuccess";

    }

    @RequestMapping(value = "getEditParentEntryForm.action", method = RequestMethod.GET)
    public String emailPageDisplay(Model model, HttpServletRequest httpServletRequest) {
        model.addAttribute("searchEditParent", new ParentNode());
        HttpSession httpSession = httpServletRequest.getSession();
        httpSession.setAttribute("hideErrorMessageDiv", true);
        return "editsearchformentry";
    }

    @RequestMapping(value = "getRegisteredEntry.action", method = {RequestMethod.POST})
    public String getRegisteredFamilyDetails(@ModelAttribute("searchEditParent") ParentNode registeredParent, Model model, HttpServletRequest httpServletRequest) {
        try {
            ParentNode parentNode = parentService.getCheckInOutParentNodeDetails(registeredParent);
            if (parentNode != null) {
                HttpSession httpSession = httpServletRequest.getSession();
                httpSession.setAttribute("selectedParentNode", parentNode);
                model.addAttribute("parentNodeForm", parentNode);
                return "editregistration";
            } else {
                HttpSession httpSession = httpServletRequest.getSession();
                httpSession.setAttribute("hideErrorMessageDiv", false);
                model.addAttribute("searchEditParent", new ParentNode());
                return "editsearchformentry";
            }
        } catch (Exception e) {
            HttpSession httpSession = httpServletRequest.getSession();
            httpSession.setAttribute("hideErrorMessageDiv", false);
            model.addAttribute("searchEditParent", new ParentNode());
            return "editsearchformentry";
        }
    }

    @RequestMapping(value = "editregisteration.action", method = RequestMethod.GET)
    public String editRegistrationPage(Model model) {
        model.addAttribute("parentNodeForm", new ParentNode());
        return "editregistration";
    }
}
