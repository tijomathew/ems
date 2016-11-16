package org.ems.daos;

import org.ems.models.ParentNode;
import org.ems.models.StudentNode;

/**
 * Created by bibin on 5/10/16.
 */
public interface RegistrationDao {

    ParentNode saveRegistrationEntry(ParentNode parentNode);

    Boolean alreadyRegisteredEmail(String email);

    ParentNode getRegisteredEntry(String email);

    StudentNode deleteStudentNode(StudentNode studentNode);
}
