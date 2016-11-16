package org.rms.controllers;

import org.rms.enums.SystemRole;
import org.rms.helpers.RequestResponseHolder;
import org.rms.models.ParentNode;
import org.rms.models.User;
import org.rms.services.LoginService;
import org.rms.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * Created by bibin on 13/10/16.
 */

@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @Autowired
    private RequestResponseHolder requestResponseHolder;

    @RequestMapping(value = "login.action", method = RequestMethod.GET)
    public String loginPageDisplay(Model model) {
        model.addAttribute("loginUser", new User());
        return "login";
    }

    @RequestMapping(value = "loggedin.action", method = RequestMethod.POST)
    public String verifyUser(@ModelAttribute("loginUser") @Valid User user, BindingResult result, Model model) {
        User loggedInUser;

        try {
            loggedInUser = loginService.verifyLoggedInUser(user.getEmail(), user.getPassword());
            if (loggedInUser != null) {
                if (loggedInUser.getAlreadyLoggedIn()) {
                    if (loggedInUser.getSystemRole().equals(SystemRole.ADMIN)) {
                        requestResponseHolder.setAttributeToSession(SystemRole.RMS_CURRENT_USER.toString(), loggedInUser);
                        return "registerationcounts";
                    } else if (loggedInUser.getSystemRole().equals(SystemRole.ORGANIZER)) {
                        requestResponseHolder.setAttributeToSession(SystemRole.RMS_CURRENT_USER.toString(), loggedInUser);
                        return "redirect:/searchviewentry.action";
                    }
                } else {
                    model.addAttribute("changePasswordUser", loggedInUser);
                    return "changepassword";
                }

            } else {
                result.addError(new ObjectError("loginErrorDisplay", new String[]{"LoginCredentialsError"}, new String[]{}, "Username or Password is invalid in our system. Please re-enter correct one."));
            }

        } catch (IllegalArgumentException ex) {
            System.out.println("The authentication and authorization of the user is failed in the system");
        }

        return "login";
    }

    @RequestMapping(value = "changepassword.action", method = RequestMethod.POST)
    public
    @ResponseBody
    Object changePassword(@ModelAttribute("changePasswordUser") User user) {
        if (user.getNewPassword() != null && user.getConfirmPassword() != null && user.getNewPassword().equals(user.getConfirmPassword())) {
            User currentUserToUpdate = loginService.getUserByEmail(user.getEmail());
            currentUserToUpdate.setAlreadyLoggedIn(Boolean.TRUE);
            currentUserToUpdate.setPassword(user.getNewPassword());
            userService.addUserSM(currentUserToUpdate);
        } else {
            return "fail";
        }
        return "success";
    }
}
