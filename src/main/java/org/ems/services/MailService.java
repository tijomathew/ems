
package org.rms.services;


import org.rms.models.ParentNode;
import org.rms.models.User;

/**
 * Created by tijo on 8/10/16.
 */
public interface MailService {

    Boolean sendRegistrationDetailsWithConsentForm(ParentNode registeredUser);

    Boolean sendNewUserPassword(User user);
}
