package org.rms.daos;

import org.rms.models.User;

/**
 * LoginDao description
 * User: tijo
 */
public interface LoginDao {

    User getUserByUserEmail(String loginUserEmail);
}
