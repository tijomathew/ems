package org.rms.daos;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.rms.models.StudentNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by bibin on 13/10/16.
 */

@Repository
public class ChildDaoImpl implements ChildDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Long getAllRegisteredStudentsOnCategoryAndOct29Wise(String category, String date, String property, String inOutFlag) {
        Long registeredStudentCounts = 0l;
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(StudentNode.class, "studentNode").add(Restrictions.eq("studentNode.retreatSection", category));
        if (inOutFlag.equals("In")) {
            criteria.createAlias("studentNode.inOutInformerList", "inoutinfo").add(Restrictions.isNotNull("inoutinfo.inTime")).add(Restrictions.eq("inoutinfo.date", date));
        } else if (inOutFlag.equals("Out")) {
            criteria.createAlias("studentNode.inOutInformerList", "inoutinfo").add(Restrictions.isNotNull("inoutinfo.outTime")).add(Restrictions.eq("inoutinfo.date", date));
        } else if (inOutFlag.equals("All")) {
            criteria.add(Restrictions.eq("studentNode." + property, date));
        }
        registeredStudentCounts = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        if (registeredStudentCounts == null) {
            registeredStudentCounts = 0l;
        }
        return registeredStudentCounts;
    }

    @Override
    public Long getAllRegisteredStudentsOnCategoryAndNov1Wise(String inOutFlag) {
        Long registeredStudentCounts = 0l;
        Criterion retreatSelectionSenior = Restrictions.eq("studentNode.retreatSection", "Senior");
        Criterion retreatSelectionSuperSenior = Restrictions.eq("studentNode.retreatSection", "SuperSenior");

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(StudentNode.class, "studentNode").add(Restrictions.or(retreatSelectionSenior, retreatSelectionSuperSenior)).add(Restrictions.eq("studentNode.dayFour", "Nov-1"));

        if (inOutFlag.equals("In")) {
            criteria.createAlias("studentNode.inOutInformerList", "inoutinfo").add(Restrictions.isNotNull("inoutinfo.inTime")).add(Restrictions.eq("inoutinfo.date", "Nov-1"));
        } else if (inOutFlag.equals("Out")) {
            criteria.createAlias("studentNode.inOutInformerList", "inoutinfo").add(Restrictions.isNotNull("inoutinfo.outTime")).add(Restrictions.eq("inoutinfo.date", "Nov-1"));
        }

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
}
