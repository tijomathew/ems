package org.ems.daos;

import org.ems.models.ParentNode;

import java.util.List;

/**
 * Created by cufa-03 on 19/10/16.
 */
public interface ParentDao {

    List<ParentNode> getParentNodes(String massCentre, String date);

    ParentNode getCheckInOutParentNodeDetails(ParentNode parentNode);

    ParentNode getParentNode(Long parentId);
}
