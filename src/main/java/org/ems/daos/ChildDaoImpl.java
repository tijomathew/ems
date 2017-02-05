package org.ems.daos;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.ems.models.StudentNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bibin on 13/10/16.
 */

@Repository
public class ChildDaoImpl implements ChildDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Long getAllRegisteredPeople(String registeredDate, String inOutFlag, String ageRange) {
        Long registeredStudentCounts = 0l;
        Map<String, String> dateMap = new HashMap<>();
        dateMap.put("27-12-2016", "Dec-27");
        dateMap.put("28-12-2016", "Dec-28");
        dateMap.put("29-12-2016", "Dec-29");
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(StudentNode.class, "studentNode").createAlias("studentNode.parentNode", "parentNode");
        if (inOutFlag.equals("In")) {
            criteria.createAlias("studentNode.inOutInformerList", "inoutinfo").add(Restrictions.isNotNull("inoutinfo.inTime")).add(Restrictions.eq("inoutinfo.date", dateMap.get(registeredDate)));
        } else if (inOutFlag.equals("Out")) {
            criteria.createAlias("studentNode.inOutInformerList", "inoutinfo").add(Restrictions.isNotNull("inoutinfo.outTime")).add(Restrictions.eq("inoutinfo.date", dateMap.get(registeredDate)));
        } else if (inOutFlag.equals("All")) {
            criteria.add(Restrictions.eq("parentNode.day", registeredDate));
        }
        criteria.add(Restrictions.eq("studentNode.ageRange", ageRange));
        registeredStudentCounts = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        if (registeredStudentCounts == null) {
            registeredStudentCounts = 0l;
        }
        return registeredStudentCounts;
    }

    @Override
    public List<StudentNode> getChildDetails(Long parentId) {
        return sessionFactory.getCurrentSession().createCriteria(StudentNode.class, "studentNode").add(Restrictions.eq("parentNode.id", parentId)).list();
    }

    @Override
    public List<StudentNode> getChildsByIds(List<Long> childIds) {
        return sessionFactory.getCurrentSession().createCriteria(StudentNode.class, "studentNode").add(Restrictions.in("studentNode.id", childIds)).list();
    }

    @Override
    public Long getAllRegisteredFamilyCount(String registeredDate, String inOutFlag, String ageRange) {
        Long registeredStudentCounts = 0l;
        Map<String, String> dateMap = new HashMap<>();
        dateMap.put("27-12-2016", "Dec-27");
        dateMap.put("28-12-2016", "Dec-28");
        dateMap.put("29-12-2016", "Dec-29");
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(StudentNode.class, "studentNode").createAlias("studentNode.parentNode", "parentNode");
        if (inOutFlag.equals("In")) {
            criteria.createAlias("studentNode.inOutInformerList", "inoutinfo").add(Restrictions.isNotNull("inoutinfo.inTime")).add(Restrictions.eq("inoutinfo.date", dateMap.get(registeredDate)));
        } else if (inOutFlag.equals("Out")) {
            criteria.createAlias("studentNode.inOutInformerList", "inoutinfo").add(Restrictions.isNotNull("inoutinfo.outTime")).add(Restrictions.eq("inoutinfo.date", dateMap.get(registeredDate)));
        } else if (inOutFlag.equals("All")) {
            criteria.add(Restrictions.eq("parentNode.day", registeredDate));
        }

        registeredStudentCounts = (Long) criteria.setProjection(Projections.countDistinct("parentNode.id")).uniqueResult();
        if (registeredStudentCounts == null) {
            registeredStudentCounts = 0l;
        }
        return registeredStudentCounts;
    }
}
