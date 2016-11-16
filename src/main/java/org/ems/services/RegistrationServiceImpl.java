package org.rms.services;

import org.rms.daos.RegistrationDao;
import org.rms.models.ParentNode;
import org.rms.models.StudentNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by bibin on 5/10/16.
 */

@Service
@Transactional
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private RegistrationDao registrationDao;

    @Override
    public ParentNode saveRegistrationEntry(ParentNode parentNode) {
        if (parentNode == null) {
            throw new IllegalArgumentException("Parent Node cannot be null!!..");
        }
        return registrationDao.saveRegistrationEntry(parentNode);
    }

    @Override
    public Boolean checkEmailAlreadyRegistered(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null!!..");
        }
        return registrationDao.alreadyRegisteredEmail(email);
    }

    @Override
    public ParentNode getRegisteredEntry(String email) {
        return registrationDao.getRegisteredEntry(email);
    }

    @Override
    public StudentNode deleteStudentNode(StudentNode studentNode) {
        if (studentNode == null) {
            throw new IllegalArgumentException("Student Node cannot be null!!..");
        }
        return registrationDao.deleteStudentNode(studentNode);
    }
}
