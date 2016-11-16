package org.rms.daos;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.rms.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserDao description
 * User: tijo
 */

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Boolean addUserDM(User user) {
        sessionFactory.getCurrentSession().saveOrUpdate(user);
        return true;
    }

    @Override
    public User getUserByEmail(String email) {
        return (User) sessionFactory.getCurrentSession().createCriteria(User.class).add(Restrictions.eq("email", email)).uniqueResult();
    }

    @Override
    public List<User> getAllUsers() {
        return  sessionFactory.getCurrentSession().createCriteria(User.class).list();
    }

    @Override
    public List<User> getAllUsersForParishIds(List<Long> parishIds) {
        return sessionFactory.getCurrentSession().createCriteria(User.class, "user").add(Restrictions.in("user.usersOfParish.id", parishIds)).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public List<User> getAllUsersForPrayerUnitIds(List<Long> prayerUnitIds) {
        return sessionFactory.getCurrentSession().createCriteria(User.class, "user").add(Restrictions.in("user.usersOfPrayerUnits.id", prayerUnitIds)).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public Long verifyEmailIsPresent(String mailID) {
        return (Long) sessionFactory.getCurrentSession().createCriteria(User.class, "user").setProjection(Projections.rowCount()).add(Restrictions.eq("email", mailID)).uniqueResult();
    }

}
