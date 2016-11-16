package org.ems.services;

import org.ems.models.User;

/**
 * LoginService description
 * User: tijo
 */
public interface LoginService {

    User verifyLoggedInUser(String loginUserEmail, String loginUserPassword);

    User getUserByEmail(String userEmail);

}
