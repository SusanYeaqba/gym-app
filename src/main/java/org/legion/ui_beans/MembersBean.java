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
import org.legion.util.Utilities;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.event.SelectEvent;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
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
public class MembersBean extends ParentBean implements Serializable {

    // temporary only for pre-signup period
    PreSignupDAO preSignupDAO;
    PreSignup selectedPreSignup;
    List<PreSignup> preSignupList;
    List<PreSignup> preSignupListFiltered;

    MemberDAO memberDAO;
    Member currentMember = new Member();
    List<Member> memberList;

    String memberSearchText = "";

    SubscriptionDAO subscriptionDAO;
    List<Subscription> subscriptionList;

    UserAccountDAO userAccountDAO;

    @PostConstruct
    public void init() {
        try {
            preSignupDAO = new PreSignupDAO();
            memberDAO = new MemberDAO();
            subscriptionDAO = new SubscriptionDAO();
            userAccountDAO = new UserAccountDAO();

            loadMembers();

            preSignupList = preSignupDAO.getList();
            preSignupListFiltered = new ArrayList<>();
            preSignupListFiltered.addAll(preSignupList);
        } catch (Exception ex) {
            showFatalMessage(ex);
        }
    }

    public void loadMembers() {
        try {
            String where = "1=1";
            if (memberSearchText != null && !memberSearchText.isEmpty() && memberSearchText.length() >= 3) {
                where += " and (LOWER(full_name) like '%" + memberSearchText.toLowerCase() + "%' " +
                        " or " +
                        " LOWER(email) like '%" + memberSearchText + "%'" +
                        " or mobile_number like '%" + memberSearchText + "%')";
            }
            memberList = memberDAO.getList(where);
            for(Member member : memberList){
                if(member.getActive())
                    member.loadActiveSubscription();
            }
            hideLoading();
        } catch (Exception ex) {
            showFatalMessage(ex);
        }
    }

    public void clearMember() {
        currentMember = new Member();
        subscriptionList = null;
    }

    public void selectMember(SelectEvent<Member> selectEvent) {
        try {
            currentMember = selectEvent.getObject();
//            subscriptionList = subscriptionDAO.getList("member_id = '" + currentMember.getRowId() + "' order by end_date desc");
            hideLoading();
        } catch (Exception ex) {
            showFatalMessage(ex);
        }
    }

    public void saveMember() {
        try {
            boolean isNew = currentMember.getRowId() == null;

            currentMember.setUpdateBy(user());
            currentMember.setUpdatedAt(now());

            if (isNew) {
                currentMember.setCreatedBy(user());
                currentMember.setCreatedAt(now());
            }


            List<Member> sameNumber = memberDAO.getList("mobile_number = '"+currentMember.getMobileNumber()+"'" +
                    " and row_id <> '"+currentMember.getRowId()+"'");
            if(sameNumber != null && !sameNumber.isEmpty()){
                showErrorMessage("Mobile number already registered");
                FacesContext.getCurrentInstance().validationFailed();
                return;
            }
            memberDAO.saveRecord(currentMember);
            if(isNew){
                memberList.add(0, currentMember);
            }

            showInfoMessage("Member profile saved - " + currentMember.getFullName());

            hideLoading();
        } catch (Exception ex) {
            showFatalMessage(ex);
        }
    }

    public void disableMember() {
        try {
            if (currentMember == null || currentMember.getRowId() == null) {
                showErrorMessage("No member selected");
                return;
            }
            currentMember.setActive(false);
            memberDAO.saveRecord(currentMember);
            showInfoMessage("Member profile deactivated for " + currentMember.getFullName());
            hideLoading();
        } catch (Exception ex) {
            showFatalMessage(ex);
        }
    }

    public void makeMemberUserAccount(){
        try {
            if (currentMember == null || currentMember.getRowId() == null) {
                showErrorMessage("No member selected");
                return;
            }

            if(currentMember.getUserAccount() == null){
                UserAccount account = new UserAccount();
                account.setActive(true);
                account.setRole(currentMember.getType());
                account.setCreatedAt(now());
                account.setCreatedBy(user());
                account.setUsername(currentMember.getFullName().toLowerCase().replaceAll(" ", "."));

                List<UserAccount> userAccounts = userAccountDAO.getList("username = '"+account.getUsername()+"'");
                if(userAccounts != null & !userAccounts.isEmpty()){
                    account.setUsername(account.getUsername() + userAccounts.size());
                }

                account.setPassword(Utilities.getAlphaNumericString(7));
                userAccountDAO.saveRecord(account);
                if(account.getRowId() != null){
                    currentMember.setUserId(account.getRowId());
                    memberDAO.saveRecord(currentMember);
                }
                showInfoMessage("User account created for member", "Username: " + account.getUsername() + " | Password: " + account.getPassword());
            }

            hideLoading();
        } catch (Exception ex) {
            showFatalMessage(ex);
        }
    }

}
