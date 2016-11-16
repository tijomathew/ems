package org.rms.helpers;

import org.rms.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by tijo on 28/12/14.
 */
@Component
public class RMSRequestResponseHolder extends ServletRequestAttributes implements RequestResponseHolder {

    private HttpServletRequest servletRequest;

    private HttpServletResponse servletResponse;

    @Autowired
    public RMSRequestResponseHolder(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
        this.servletRequest = httpServletRequest;
    }

    @Override
    public HttpServletRequest getCurrentRequest() {
        return servletRequest;
    }

    @Override
    public HttpServletResponse getCurrentResponse() {
        return this.servletResponse;
    }

    public void setServletResponse(HttpServletResponse servletResponse) {
        this.servletResponse = servletResponse;
    }

    @Override
    public User getUser() {
        return getAttributeFromSession("user", User.class);
    }

    @Override
    public void setUser(User applicationUser) {
        this.setAttributeToSession("user", applicationUser);
    }

    @Override
    public <T> T getAttributeFromSession(String name, Class<T> typeToken) {
        return (T) this.getCurrentRequest().getSession().getAttribute(name);
    }

    @Override
    public <T> void setAttributeToSession(String name, T object) {
        this.getCurrentRequest().getSession().setAttribute(name, object);
    }

    @Override
    public void removeAttributeFromSession(String name) {
        this.getCurrentRequest().getSession().removeAttribute(name);
    }

    @Override
    public HttpSession getCurrentSession() {
        return this.getCurrentRequest().getSession(true);
    }
}
