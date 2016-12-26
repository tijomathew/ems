package org.ems.daos;

import com.sun.org.apache.xerces.internal.dom.ChildNode;
import org.ems.models.StudentNode;

import java.util.List;

/**
 * Created by bibin on 13/10/16.
 */
public interface ChildDao {

    Long getAllRegisteredPeople(String property, String inOutFlag);

    List<StudentNode> getChildDetails(Long parentId);

    List<StudentNode> getChildsByIds(List<Long> childIds);
}
