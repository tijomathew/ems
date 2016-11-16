package org.rms.services;

import org.rms.daos.ParentDao;
import org.rms.models.ParentNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by cufa-03 on 19/10/16.
 */
@Service
@Transactional
public class ParentServiceImpl implements ParentService {

    @Autowired
    private ParentDao dao;

    @Override
    public List<ParentNode> getParentNodes(String massCentre, String date, String category) {
        return dao.getParentNodes(massCentre, date, category);
    }

    @Override
    public ParentNode getCheckInOutParentNodeDetails(ParentNode parentNode) {
        ParentNode retrievedParentNode = null;
        try {
            retrievedParentNode = dao.getCheckInOutParentNodeDetails(parentNode);
        } catch (Exception e) {

        }
        return retrievedParentNode;
    }

    @Override
    public ParentNode getParentNode(Long parentId) {
        return dao.getParentNode(parentId);
    }
}
