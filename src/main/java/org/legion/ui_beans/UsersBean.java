package org.legion.ui_beans;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.legion.model.dao.UserAccountDAO;
import org.legion.model.entity.UserAccount;
import org.legion.ui_beans.ParentBean;
import org.omnifaces.cdi.ViewScoped;

import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
@Slf4j
@Getter
@Setter
public class UsersBean extends ParentBean implements Serializable {
    UserAccountDAO userDAO;


    List<UserAccount> users;
    UserAccount currentUser;

    public UsersBean() {
        try {
            userDAO = new UserAccountDAO();
            users = userDAO.getList();
        } catch (Exception e) {
            showFatalMessage(e);
        }
    }

    public void clearUser() {
        currentUser = new UserAccount();
    }

    public void saveUser() {
        try {

            boolean isNew = currentUser.getRowId() == null;
            if (isNew) {
                currentUser.setCreatedAt(now());
                currentUser.setCreatedBy(user());
            }
            currentUser.setUpdatedAt(now());
            currentUser.setUpdateBy(user());

            userDAO.saveRecord(currentUser);
            if (isNew) {
                users.add(currentUser);
            }
            showInfoMessage("User saved successfully", "");
            hideLoading();
        } catch (Exception e) {
            showFatalMessage(e);
        }
    }

    public void deleteUser() {
        try {
            if (currentUser == null)
                return;

            currentUser.setDeletedAt(now());
            currentUser.setDeletedBy(user());
            userDAO.saveRecord(currentUser);
            users.remove(currentUser);
            showInfoMessage("deleted successfully", "");
            hideLoading();
        } catch (Exception e) {
            showFatalMessage(e);
        }
    }
}
