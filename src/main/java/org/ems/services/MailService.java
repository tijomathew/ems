
package org.ems.services;


import org.ems.models.ParentNode;
import org.ems.models.User;

/**
 * Created by tijo on 8/10/16.
 */
public interface MailService {

    Boolean sendRegistrationDetailsWithConsentForm(ParentNode registeredUser);

    Boolean sendNewUserPassword(User user);
}
