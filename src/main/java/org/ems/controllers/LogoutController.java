package org.rms.controllers;

import org.rms.enums.SystemRole;
import org.rms.helpers.RequestResponseHolder;
import org.rms.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by bibin on 23/10/16.
 */
@Controller
@RequestMapping("/")
public class LogoutController {

    @Autowired
    private RequestResponseHolder requestResponseHolder;

    @RequestMapping(value = "logout.action", method = RequestMethod.GET)
    public String loginPageDisplay(Model model) {
        model.addAttribute("loginUser", new User());
        User currentUser = requestResponseHolder.getAttributeFromSession(SystemRole.RMS_CURRENT_USER.toString(), User.class);
        if (currentUser != null) {
            requestResponseHolder.removeAttributeFromSession(SystemRole.RMS_CURRENT_USER.toString());
            requestResponseHolder.getCurrentSession().invalidate();
        }
        return "login";
    }
}
