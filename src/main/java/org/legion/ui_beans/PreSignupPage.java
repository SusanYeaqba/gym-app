package org.legion.ui_beans;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.legion.model.dao.PreSignupDAO;
import org.legion.model.entity.PreSignup;
import org.legion.model.pojo.SubModel;
import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
@Getter
@Setter
@Slf4j
public class PreSignupPage extends ParentBean implements Serializable {

    PreSignup currentObject;
    PreSignupDAO preSignupDAO;

    List<SelectItem> subModelList;

    @PostConstruct
    public void init() {
        currentObject = new PreSignup();
        preSignupDAO = new PreSignupDAO();
        subModelList = new ArrayList<>();

        subModelList.add(new SelectItem("1M", "1 Month",
                null, false, false));
        subModelList.add(new SelectItem("3M", "3 Months",
                null, false, false));
        subModelList.add(new SelectItem("6M", "6 Months",
                null, false, false));
        subModelList.add(new SelectItem("1Y", "1 Year",
                null, false, false));
    }

    public void saveSignup() {
        try {
            if (currentObject.getFullName() == null || currentObject.getFullName().isEmpty()) {
                showErrorMessage("Please specify your full name");
                return;
            }
            if (currentObject.getMobileNumber() == null || currentObject.getMobileNumber().isEmpty()) {
                showErrorMessage("Please specify your mobile number");
                return;
            }
//            if (currentObject.getEmail() == null || currentObject.getEmail().isEmpty()) {
//                showErrorMessage("Please specify your email");
//                return;
//            }

            if (!currentObject.getMobileNumber().startsWith("059") && !currentObject.getMobileNumber().startsWith("056")
                    && currentObject.getMobileNumber().length() != 10) {
                showErrorMessage("Mobile number incorrect, should be like: 0597267xxx");
                return;
            }

            if (currentObject.getEmail() != null && (!currentObject.getEmail().contains("@") || !currentObject.getEmail().contains("."))) {
                showErrorMessage("Email seems to be incorrect");
                return;
            }

            currentObject.setCreatedBy("PRESIGNUP");
            currentObject.setCreatedAt(System.currentTimeMillis());

            preSignupDAO.saveRecord(currentObject);
            currentObject = new PreSignup();

            showInfoMessage("Thank you for signing up", "We will contact you soon!");

        } catch (Exception ex) {
            showFatalMessage(new Exception("Something went wrong! We apologize for the inconvenience, please try again later"));
            ex.printStackTrace();
        }
    }
}
