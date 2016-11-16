package org.rms.models;

import org.rms.enums.SystemRole;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by bibin on 13/10/16.
 */

@Entity
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 4953982568814652531L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "system_role")
    @Enumerated(EnumType.STRING)
    private SystemRole systemRole;

    @Column(name = "already_loggedIn")
    private Boolean alreadyLoggedIn = Boolean.FALSE;

    @Column(name = "sent_mail")
    private Boolean sendMailFlag = Boolean.TRUE;

    @Transient
    private String newPassword;

    @Transient
    private String confirmPassword;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SystemRole getSystemRole() {
        return systemRole;
    }

    public void setSystemRole(SystemRole systemRole) {
        this.systemRole = systemRole;
    }

    public Boolean getAlreadyLoggedIn() {
        return alreadyLoggedIn;
    }

    public void setAlreadyLoggedIn(Boolean alreadyLoggedIn) {
        this.alreadyLoggedIn = alreadyLoggedIn;
    }

    public Boolean getSendMailFlag() {
        return sendMailFlag;
    }

    public void setSendMailFlag(Boolean sendMailFlag) {
        this.sendMailFlag = sendMailFlag;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
