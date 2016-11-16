package org.rms.services;

import org.rms.daos.LoginDao;
import org.rms.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * LoginServiceImpl description
 * User: tijo
 */
@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginDao loginDao;

    @Override
    public User verifyLoggedInUser(String loginUserEmail, String loginUserPassword) {
        User loggedInUser = loginDao.getUserByUserEmail(loginUserEmail);
        if (loggedInUser != null) {
            if (!loginUserPassword.equalsIgnoreCase(loggedInUser.getPassword()) && (loginUserEmail.equalsIgnoreCase(loggedInUser.getEmail()))) {
                loggedInUser = null;
            }
        }
        return loggedInUser;
    }

    @Override
    public User getUserByEmail(String userEmail) {
        return loginDao.getUserByUserEmail(userEmail);
    }

}
