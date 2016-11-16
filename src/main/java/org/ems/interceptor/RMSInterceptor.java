package org.rms.interceptor;

import org.rms.enums.SystemRole;
import org.rms.helpers.RequestResponseHolder;
import org.rms.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * User: tijo.
 */
public class RMSInterceptor implements HandlerInterceptor {

    @Autowired
    private RequestResponseHolder requestResponseHolder;

    @Resource(name = "adminLinks")
    private List<String> adminLinks;

    @Resource(name = "organizersLinks")
    private List<String> organizersLinks;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        boolean indicatorToProceed = false;
        String urlAction = httpServletRequest.getRequestURI().replace(httpServletRequest.getContextPath() + "/", "");
        if (urlAction.contains("resources")) {
            indicatorToProceed = true;
        }
        if (!indicatorToProceed) {
            if (urlAction.equalsIgnoreCase("login.action") || urlAction.equalsIgnoreCase("logout.action") || urlAction.equalsIgnoreCase("loggedin.action") || urlAction.equalsIgnoreCase("changepassword.action")) {
                indicatorToProceed = true;
            } else {
                User userFromCurrentSession = requestResponseHolder.getAttributeFromSession(SystemRole.RMS_CURRENT_USER.toString(), User.class);
                if (userFromCurrentSession != null) {
                    switch (userFromCurrentSession.getSystemRole()) {
                        case ADMIN:
                            indicatorToProceed = checkURLIsAllowedForCurrentUser(urlAction, adminLinks);
                            break;
                        case ORGANIZER:
                            indicatorToProceed = checkURLIsAllowedForCurrentUser(urlAction, organizersLinks);
                            break;
                    }
                }
            }
            if (!indicatorToProceed) {
                requestResponseHolder.setAttributeToSession("showURLAccessDenied", new String("This URL cannot be accessed by this User Role"));
                httpServletResponse.sendRedirect("login.action");
                httpServletResponse.flushBuffer();
            }
        }
        return indicatorToProceed;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        // No-op in this scenario
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        //No-op in this scenario
    }

    private boolean checkURLIsAllowedForCurrentUser(String urlAction, List<String> urlList) {
        return urlList.contains(urlAction);
    }
}
