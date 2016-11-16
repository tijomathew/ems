package org.ems.daos;

import org.ems.models.User;

/**
 * LoginDao description
 * User: tijo
 */
public interface LoginDao {

    User getUserByUserEmail(String loginUserEmail);
}
