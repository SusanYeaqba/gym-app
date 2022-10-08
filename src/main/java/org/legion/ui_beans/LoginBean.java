package org.legion.ui_beans;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.legion.model.dao.UserAccountDAO;
import org.legion.model.entity.UserAccount;
import org.legion.ui_beans.ParentBean;
import org.legion.util.MainDataSource;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;

@Named
@SessionScoped
@Getter
@Setter
@Slf4j
public class LoginBean extends ParentBean implements Serializable {
    UserAccountDAO userDAO;

    UserAccount loggedInUser;
    String username, password;


    public LoginBean() {
        try {
            username = "";
            password = "";
            userDAO = new UserAccountDAO();
        } catch (Exception ex) {
            showFatalMessage(ex);
        }
    }


    public String login() {
        try {
            loggedInUser = userDAO.login(username, password);
            if (loggedInUser != null) {
                if(!loggedInUser.getActive()){
                    showErrorMessage("User Disabled", "User account has been disabled, contact system administrator.");
                    return "";
                }

                if(loggedInUser.isClient()){
                    loggedInUser.getMember().loadSubscriptions();
                }

                putValueToSession("loggedInUser", loggedInUser);
                loggedInUser.setLastLogin(new Date());
                userDAO.saveRecord(loggedInUser);
                return "views/dashboard.xhtml?faces-redirect=true";
            } else {
                showErrorMessage("Incorrect credentials", "Email or Password is incorrect, please try again.");
                return "";
            }
        } catch (Exception e) {
            showFatalMessage(e);
            return "";
        }
    }


    public void logout() {
        try {
            loggedInUser = null;
            checkAuthentication();
        } catch (Exception e) {
            showFatalMessage(e);
        }
    }

    public boolean checkAuthentication() {
        try {
            if (loggedInUser == null) {
                FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
                executeJS("window.location.href='" + getContextPath() + "/login.xhtml'");
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            showFatalMessage(ex);
            return false;
        }
    }

}
