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

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Named
@ViewScoped
@Getter
@Setter
@Slf4j
public class PreSignupBean extends ParentBean implements Serializable {

    PreSignup record;
    PreSignupDAO preSignupDAO;
    List<PreSignup> preSignupList;


    Member member;
    MemberDAO memberDAO;
    Subscription subscription;
    SubscriptionDAO subscriptionDAO;


    @PostConstruct
    public void init() {
        try {
            preSignupDAO = new PreSignupDAO();
            memberDAO = new MemberDAO();
            subscriptionDAO = new SubscriptionDAO();
            preSignupList = preSignupDAO.getList("SUBSTRING(mobile_number, 2, 100) not in (select x.mobile_number from member x)");
        } catch (Exception ex) {
            showFatalMessage(ex);
        }
    }

    public void prepareSubscriptionConvert(PreSignup _record) {
        try {
            record = _record;
            if (record == null || record.getRowId() == null) {
                showErrorMessage("No valid pre-signup record selected");
                return;
            }

            List<Member> membersWithSamePhone = memberDAO.getList("mobile_number = '" + record.getMobileNumber() + "'" +
                    " or mobile_number = '" + record.getMobileNumber().substring(1) + "'");

            if (membersWithSamePhone != null && !membersWithSamePhone.isEmpty()) {
                showWarningMessage("Member with such mobile number already created");
                return;
            }

            member = new Member();
            subscription = new Subscription();

            member.setCreatedAt(now());
            member.setCreatedBy(user());
            member.setFullName(record.getFullName());
            member.setGender(record.getGender());
            member.setActive(true);
            member.setEmail(record.getEmail());
            member.setMobilePrefix("00970");
            member.setMobileNumber(record.getMobileNumber().substring(1));

            subscription.setCreatedAt(now());
            subscription.setCreatedBy(user());
            subscription.setGender(member.getGender());
            subscription.setType(record.getSubPeriod());
            subscription.setStartDate(new Date());
            subscription.setPrice(BigDecimal.valueOf(Long.parseLong(SystemParams.params.get("P" + member.getGender() + subscription.getType()))));
            subscription.setDiscount(BigDecimal.ZERO);
            subscription.setBonusPeriod(BigDecimal.ZERO);
            subscription.calculateDiscount();
            subscription.setFullPaid(false);
            subscription.setConsumedEntryCount(BigDecimal.ZERO);
            subscription.setMaxEntryCount(BigDecimal.ZERO);
            subscription.setFrozen(false);
            subscription.setSuspended(false);
            subscription.setActive(true);
            subscription.setPaidAmount(BigDecimal.ZERO);
            subscription.setRemarks("from pre-signup with ID [" + record.getRowId() + "]");

            executeJS("PF('subform').show()");
        } catch (Exception ex) {
            showFatalMessage(ex);
        }
    }

    public void confirmSubscription() {
        try {
            if (member == null || subscription == null) {
                showErrorMessage("Something went wrong");
                return;
            }

            member.setMemberNumber(MainDataSource.executeDecimalResultQuery("select max(member_number) from member").intValue() + 1);
            memberDAO.saveRecord(member);

            if (member.getRowId() != null) {
                subscription.setMemberId(member.getRowId());
                subscriptionDAO.saveRecord(subscription);
                MainDataSource.executeQuery("update subscription set active = 0 where getdate() > end_date ");

                member = null;
                subscription = null;
                showInfoMessage("Member and Subscription Created!");
            } else {
                showErrorMessage("Could not create member record");
            }

        } catch (Exception ex) {
            showFatalMessage(ex);
        }
    }

    public void cancel(){
        record = null;
        member = null;
        subscription = null;
    }

}
