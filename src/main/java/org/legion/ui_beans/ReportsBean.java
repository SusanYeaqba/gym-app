package org.legion.ui_beans;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.legion.model.dao.MemberDAO;
import org.legion.model.dao.PreSignupDAO;
import org.legion.model.dao.SubscriptionDAO;
import org.legion.model.dao.UserAccountDAO;
import org.legion.model.entity.Member;
import org.legion.model.entity.PreSignup;
import org.legion.model.entity.Subscription;
import org.legion.model.entity.UserAccount;
import org.legion.util.MainDataSource;
import org.legion.util.Utilities;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.event.SelectEvent;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

@Named
@ViewScoped
@Getter
@Setter
@Slf4j
public class ReportsBean extends ParentBean implements Serializable {

    MemberDAO memberDAO;

    SubscriptionDAO subscriptionDAO;
    List<Subscription> subscriptionList;

    Date fromDate = new Date();
    Date toDate = new Date();
    boolean activeOnly = false;
    String subType = "ALL";

    @PostConstruct
    public void init() {
        try {
            memberDAO = new MemberDAO();
            subscriptionDAO = new SubscriptionDAO();
        } catch (Exception ex) {
            showFatalMessage(ex);
        }
    }

    public void searchSubs() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String query = " 1=1 ";
            if (fromDate != null && toDate != null) {
                query += " and start_date between '" + sdf.format(fromDate) + "' and '" + sdf.format(toDate) + "' ";
            }
            if (activeOnly) {
                query += " and active = 1 ";
            }
            if (!subType.equals("ALL")) {
                query += " and type = '" + subType + "' ";
            }
            subscriptionList = subscriptionDAO.getList(query);
            for (Subscription sub : subscriptionList) {
                sub.setMember(memberDAO.getRecord(sub.getMemberId()));
            }
            hideLoading();
        } catch (Exception ex) {
            showFatalMessage(ex);
        }
    }

}
