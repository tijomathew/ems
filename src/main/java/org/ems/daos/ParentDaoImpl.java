package org.rms.daos;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.rms.models.ParentNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by cufa-03 on 19/10/16.
 */
@Repository
@Transactional
public class ParentDaoImpl implements ParentDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<ParentNode> getParentNodes(String massCentre, String date, String category) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ParentNode.class, "parentNode").
                createAlias("parentNode.studentNodeList", "studentNode", JoinType.INNER_JOIN);
        ;
        if (!massCentre.equals("All")) {
            criteria.add(Restrictions.eq("massCentreName", massCentre));
        }
        if (!date.equals("all")) {
            criteria.add(Restrictions.isNotNull("studentNode.".concat(date)));
        }
        if (!category.equals("all")) {
            criteria.add(Restrictions.eq("studentNode.retreatSection", category));
        }
        return criteria.addOrder(Order.asc("firstName")).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public ParentNode getCheckInOutParentNodeDetails(ParentNode parentNode) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ParentNode.class, "parentNode").createAlias("parentNode.studentNodeList", "studentNode");
        if (parentNode.getFirstName() != null && !parentNode.getFirstName().isEmpty()) {
            Criterion firstNameCriteria = Restrictions.eq("parentNode.firstName", parentNode.getFirstName()).ignoreCase();
            criteria.add(Restrictions.or(firstNameCriteria));
        } else if (parentNode.getLastName() != null && !parentNode.getLastName().isEmpty()) {
            Criterion lastNameCriteria = Restrictions.eq("parentNode.lastName", parentNode.getLastName()).ignoreCase();
            criteria.add(Restrictions.or(lastNameCriteria));
        } else if (parentNode.getId() != null && parentNode.getId() > 0) {
            Criterion familyIdCriteria = Restrictions.eq("parentNode.id", parentNode.getId());
            criteria.add(Restrictions.or(familyIdCriteria));
        } else if (parentNode.getChildId() != null && parentNode.getChildId() > 0) {
            Criterion childBandCodeCriteria = Restrictions.eq("studentNode.id", parentNode.getChildId());
            criteria.add(Restrictions.or(childBandCodeCriteria));
        } else if (parentNode.getChildFirstName() != null && !parentNode.getChildFirstName().isEmpty()) {
            Criterion childFirstNameCriteria = Restrictions.eq("studentNode.firstName", parentNode.getChildFirstName()).ignoreCase();
            criteria.add(Restrictions.or(childFirstNameCriteria));
        } else if (parentNode.getChildLastName() != null && !parentNode.getChildLastName().isEmpty()) {
            Criterion childLastNameCriteria = Restrictions.eq("studentNode.lastName", parentNode.getChildLastName()).ignoreCase();
            criteria.add(Restrictions.or(childLastNameCriteria));
        }
        return (ParentNode) criteria.uniqueResult();
    }

    @Override
    public ParentNode getParentNode(Long parentId) {
        return (ParentNode) sessionFactory.getCurrentSession().createCriteria(ParentNode.class).add(Restrictions.eq("id", parentId)).uniqueResult();
    }
}
