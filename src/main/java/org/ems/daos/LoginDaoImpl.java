package org.ems.daos;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.ems.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * LoginDaoImpl description
 * User: tijo
 */
@Repository
public class LoginDaoImpl implements LoginDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User getUserByUserEmail(String loginUserEmail) {
        return (User) sessionFactory.getCurrentSession().createCriteria(User.class, "user").add(Restrictions.eq("user.email", loginUserEmail)).uniqueResult();
    }

}
