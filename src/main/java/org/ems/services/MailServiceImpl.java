package org.rms.services;

import org.apache.velocity.app.VelocityEngine;
import org.rms.models.ParentNode;
import org.rms.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tijo on 8/10/16.
 */

@Component
public class MailServiceImpl implements MailService {

    private static final String EMAIL_REGISTRATION_TEMPLATE = "registration_email_template.vm";

    @Autowired(required = true)
    private JavaMailSender mailSender;

    @Autowired(required = true)
    private VelocityEngine velocityEngine;

    @Override
    public Boolean sendRegistrationDetailsWithConsentForm(ParentNode registeredParentNode) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                String mergedContent = null;

                message.addTo(registeredParentNode.getEmail());

                Map<String, ParentNode> model = new HashMap<>();
                model.put("registeredParentNode", registeredParentNode);

                message.setSubject("Syro Malabar Church Ireland Retreat Registration");
                mergedContent = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, EMAIL_REGISTRATION_TEMPLATE, model);
                message.setText(mergedContent, true);


            }
        };
        try {
            this.mailSender.send(preparator);
        } catch (MailException e) {
            System.out.println("Error is happened from email sending!!..");
            return false;
        }
        return true;

    }

    @Override
    public Boolean sendNewUserPassword(User user) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mailMsg = new MimeMessageHelper(mimeMessage);
        try {
            mailMsg.setTo(user.getEmail());
            mailMsg.setSubject("Welcome to Retreat");
            mailMsg.setText("Password: " + user.getPassword());
            mailSender.send(mimeMessage);
            return true;
        } catch (Exception e) {
            System.out.println("---Errorrr from Mail Sending---");
            return false;
        }
    }
}
