package org.rms.services;

import org.rms.models.ParentNode;
import org.rms.models.StudentNode;

/**
 * Created by bibin on 5/10/16.
 */
public interface RegistrationService {

    ParentNode saveRegistrationEntry(ParentNode parentNode);

    Boolean checkEmailAlreadyRegistered(String email);

    ParentNode getRegisteredEntry(String email);

    StudentNode deleteStudentNode(StudentNode studentNode);
}
