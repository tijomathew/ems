package org.rms.helpers;

import org.rms.models.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by tijo on 28/12/14.
 */
public interface RequestResponseHolder {

    HttpServletRequest getCurrentRequest();

    HttpServletResponse getCurrentResponse();

    User getUser();

    void setUser(User applicationUser);

    <T> T getAttributeFromSession(String name, Class<T> typeToken);

    <T> void setAttributeToSession(String name, T object);

    void removeAttributeFromSession(String name);

    HttpSession getCurrentSession();

}
