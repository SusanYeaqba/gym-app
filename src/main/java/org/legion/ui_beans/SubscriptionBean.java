package org.legion.ui_beans;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.legion.model.dao.MemberDAO;
import org.legion.model.dao.PreSignupDAO;
import org.legion.model.dao.SubscriptionDAO;
import org.legion.model.entity.Member;
import org.legion.model.entity.PreSignup;
import org.legion.model.entity.Subscription;
import org.legion.util.MainDataSource;
import org.legion.util.SystemParams;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.event.SelectEvent;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
@Getter
@Setter
@Slf4j
public class SubscriptionBean extends ParentBean implements Serializable {


    Member member;
    MemberDAO memberDAO;
    List<Member> members;
    Map<String, Member> memebrMap;

    Subscription subscription;
    SubscriptionDAO subscriptionDAO;
    List<Subscription> allSubscriptionsList;

    Date fromDate, toDate;

    @PostConstruct
    public void init() {
        try {
            clear();
            MainDataSource.executeQuery("update subscription set active = 0 where getdate() > end_date ");
            memberDAO = new MemberDAO();
            subscriptionDAO = new SubscriptionDAO();

            members = memberDAO.getList("active = 1");
            memebrMap = new HashMap<>();

            for (Member member : members) {
                memebrMap.put(member.getRowId(), member);
            }

            loadSubs();
        } catch (Exception ex) {
            showFatalMessage(ex);
        }
    }

    public void loadSubs() {
        try {
            String where = "1=1 ";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            if (fromDate != null) {
                where += " and start_date >= '" + sdf.format(fromDate) + "'";
            }
            if (toDate != null) {
                where += " and end_date >= '" + sdf.format(toDate) + "'";
            }

            allSubscriptionsList = subscriptionDAO.getList(where + " order by start_date desc");
        } catch (Exception ex) {
            showFatalMessage(ex);
        }
    }

    public void selectSubscription(SelectEvent<Subscription> selectEvent) {
        try {
            subscription = selectEvent.getObject();
            member = memebrMap.get(subscription.getMemberId());
            subscription.setGender(member.getGender());
            subscription.updateMaximumEntries();
            subscription.calculateDiscount();
        } catch (Exception ex) {
            showFatalMessage(ex);
        }
    }

    public void clear() {
        subscription = new Subscription();
        member = null;
    }

    public void selectMember(SelectEvent<Member> event) {
        member = event.getObject();
        if (subscription.getRowId() == null) {
            System.out.println("preparing sub");
            subscription.setMemberId(member.getRowId());
            subscription.setGender(member.getGender());
            subscription.updateMaximumEntries();
            subscription.calculateDiscount();
        } else {
            showWarningMessage("Cannot change member for existing subscription");
        }
    }

    public void saveSubscription() {
        try {
            if (member == null || subscription == null) {
                showErrorMessage("Something went wrong");
                return;
            }

            boolean isNew = subscription.getRowId() == null;

            if(subscription.getType() == null || subscription.getType().isEmpty()){
                showErrorMessage("Subscription Type Mandatory");
                FacesContext.getCurrentInstance().validationFailed();
                return;
            }

            if (isNew) {
                subscription.setCreatedBy(user());
                subscription.setCreatedAt(now());
                subscription.setActive(true);
                member.loadSubscriptions();
                if (member.getActiveSubscription() != null) {
                    showWarningMessage("Member already has active subscription!");
                    executeJS("PF('').hide()");
                    return;
                }
            }

            subscription.setUpdateBy(user());
            subscription.setUpdatedAt(now());

            subscriptionDAO.saveRecord(subscription);
            MainDataSource.executeQuery("update subscription set active = 0 where getdate() > end_date ");
            loadSubs();
            showInfoMessage("Subscription Saved!");
        } catch (Exception ex) {
            showFatalMessage(ex);
        }
    }
}
