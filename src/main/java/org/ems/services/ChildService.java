package org.ems.services;

import com.sun.org.apache.xerces.internal.dom.ChildNode;
import org.ems.models.ParentNode;
import org.ems.models.StudentNode;
import org.ems.visualizations.ChartResultContainer;

import java.util.List;

/**
 * Created by bibin on 13/10/16.
 */
public interface ChildService {

    Long getAllRegisteredPeople(String property, String inOutFlag);

    ChartResultContainer getChartResultContainer(String tqx, String inOutFlag);

    List<StudentNode> getChildDetails(Long parentId);

    List<StudentNode> getChildsByIds(List<Long> childIds);
}
