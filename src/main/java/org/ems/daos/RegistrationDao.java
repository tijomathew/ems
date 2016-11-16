package org.rms.daos;

import org.rms.models.ParentNode;
import org.rms.models.StudentNode;

/**
 * Created by bibin on 5/10/16.
 */
public interface RegistrationDao {

    ParentNode saveRegistrationEntry(ParentNode parentNode);

    Boolean alreadyRegisteredEmail(String email);

    ParentNode getRegisteredEntry(String email);

    StudentNode deleteStudentNode(StudentNode studentNode);
}
