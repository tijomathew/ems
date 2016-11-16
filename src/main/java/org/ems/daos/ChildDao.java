package org.rms.daos;

import com.sun.org.apache.xerces.internal.dom.ChildNode;
import org.rms.models.StudentNode;

import java.util.List;

/**
 * Created by bibin on 13/10/16.
 */
public interface ChildDao {

    Long getAllRegisteredStudentsOnCategoryAndOct29Wise(String category, String date, String property, String inOutFlag);

    Long getAllRegisteredStudentsOnCategoryAndNov1Wise(String inOutFlag);

    List<StudentNode> getChildDetails(Long parentId);

    List<StudentNode> getChildsByIds(List<Long> childIds);
}
