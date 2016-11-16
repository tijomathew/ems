package org.rms.controllers;

import org.apache.commons.lang3.RandomStringUtils;
import org.rms.models.ParentNode;
import org.rms.models.User;
import org.rms.services.MailService;
import org.rms.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by cufa-01 on 19/10/16.
 */

@Controller
@RequestMapping("/")
public class EmailController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private MailService mailService;

    public static final String keySpace = "abcdefghijklmnopqrstuvxyzABCDEFGHIJKLMNOPQRSTUVXYZ123456789";

    @RequestMapping(value = "email.action", method = RequestMethod.GET)
    public String emailPageDisplay(Model model) {
        model.addAttribute("loginUser", new User());
        return "emailentryform";
    }


    @RequestMapping(value = "getEmail.action", method = {RequestMethod.POST})
    public String getRegisteredEmail(@ModelAttribute("loginUser") User registeredUser, Model model, HttpServletRequest request) {
        ParentNode parentNode = registrationService.getRegisteredEntry(registeredUser.getEmail());

        if (parentNode != null) {
            String generatedPassword = RandomStringUtils.random(8, keySpace);
            registeredUser.setPassword(generatedPassword);
            model.addAttribute("loginUser", registeredUser);
            mailService.sendNewUserPassword(registeredUser);
            return "passwordentryform";
        } else {
            return "emailentryfailure";
        }
    }

    @RequestMapping(value = "getRegisteredFamilyDetails.action", method = {RequestMethod.POST})
    public String getRegisteredFamilyDetails(@ModelAttribute("loginUser") User registeredUser, Model model, HttpServletRequest request) {
        ParentNode parentNode = registrationService.getRegisteredEntry(registeredUser.getEmail());
        if (parentNode != null && registeredUser.getPassword().equals(registeredUser.getNewPassword())) {
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("selectedParentNode", parentNode);
            model.addAttribute("parentNodeForm", parentNode);
            return "editregistration";
        } else {
            return "passwordfailure";
        }
    }

    @RequestMapping(value = "wrongpassword.action", method = {RequestMethod.POST})
    public String getWrongPasswordRedirection(@ModelAttribute("loginUser") User registeredUser, Model model, HttpServletRequest request) {
        model.addAttribute("loginUser", registeredUser);
        return "passwordentryform";
    }
}
