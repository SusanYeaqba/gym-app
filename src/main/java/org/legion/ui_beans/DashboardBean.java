package org.legion.ui_beans;

import lombok.Getter;
import lombok.Setter;
import org.legion.model.dao.CheckInDAO;
import org.legion.model.dao.MemberDAO;
import org.legion.model.entity.CheckIn;
import org.legion.model.entity.Member;
import org.legion.util.MainDataSource;
import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Named
@ViewScoped
@Getter
@Setter
public class DashboardBean extends ParentBean implements Serializable {

    String memberSearch = "";
    Member member;
    MemberDAO memberDAO;
    CheckInDAO checkInDAO;

    BigDecimal totalMembers = BigDecimal.ZERO;
    BigDecimal activeMembers = BigDecimal.ZERO;
    BigDecimal checkedIn = BigDecimal.ZERO;
    BigDecimal maleMembers = BigDecimal.ZERO;
    BigDecimal femaleMembers = BigDecimal.ZERO;
    BigDecimal active1MonthMale = BigDecimal.ZERO;
    BigDecimal active3MonthMale = BigDecimal.ZERO;
    BigDecimal active6MonthMale = BigDecimal.ZERO;
    BigDecimal active1YearMale = BigDecimal.ZERO;
    BigDecimal active10EntryMale = BigDecimal.ZERO;
    BigDecimal active20EntryMale = BigDecimal.ZERO;
    BigDecimal active1MonthFemale = BigDecimal.ZERO;
    BigDecimal active3MonthFemale = BigDecimal.ZERO;
    BigDecimal active6MonthFemale = BigDecimal.ZERO;
    BigDecimal active1YearFemale = BigDecimal.ZERO;
    BigDecimal active10EntryFemale = BigDecimal.ZERO;
    BigDecimal active20EntryFemale = BigDecimal.ZERO;
    BigDecimal subscriptionsEndingThisWeek = BigDecimal.ZERO;

    @PostConstruct
    public void init() {
        try {
            checkInDAO = new CheckInDAO();
            memberDAO = new MemberDAO();
            MainDataSource.executeQuery("update subscription set active = 0 where getdate() > end_date ");

            reloadDashboard();
        } catch (Exception ex) {
            showFatalMessage(ex);
        }
    }

    public void reloadDashboard() {
        try {
            totalMembers = MainDataSource.executeDecimalResultQuery("select count(0) from member");
            activeMembers = MainDataSource.executeDecimalResultQuery("select count(0) from member where row_id in (select member_id from subscription where active = 1)");
            checkedIn = MainDataSource.executeDecimalResultQuery("select count(0) from check_in where expired = 0");
            maleMembers = MainDataSource.executeDecimalResultQuery("select count(0) from member where gender = 'M' and row_id in (select member_id from subscription where active = 1)");
            femaleMembers = MainDataSource.executeDecimalResultQuery("select count(0) from member where gender = 'F' and row_id in (select member_id from subscription where active = 1)");
            active1MonthMale = MainDataSource.executeDecimalResultQuery("select count(0) from member where gender = 'M' and row_id in (select member_id from subscription where active = 1 and type = '1M')");
            active3MonthMale = MainDataSource.executeDecimalResultQuery("select count(0) from member where gender = 'M' and row_id in (select member_id from subscription where active = 1 and type = '3M')");
            active6MonthMale = MainDataSource.executeDecimalResultQuery("select count(0) from member where gender = 'M' and row_id in (select member_id from subscription where active = 1 and type = '6M')");
            active1YearMale = MainDataSource.executeDecimalResultQuery("select count(0) from member where gender = 'M' and row_id in (select member_id from subscription where active = 1 and type = '1Y')");
            active10EntryMale = MainDataSource.executeDecimalResultQuery("select count(0) from member where gender = 'M' and row_id in (select member_id from subscription where active = 1 and type = '10E')");
            active20EntryMale = MainDataSource.executeDecimalResultQuery("select count(0) from member where gender = 'M' and row_id in (select member_id from subscription where active = 1 and type = '20E')");
            active1MonthFemale = MainDataSource.executeDecimalResultQuery("select count(0) from member where gender = 'F' and row_id in (select member_id from subscription where active = 1 and type = '1M')");
            active3MonthFemale = MainDataSource.executeDecimalResultQuery("select count(0) from member where gender = 'F' and row_id in (select member_id from subscription where active = 1 and type = '3M')");
            active6MonthFemale = MainDataSource.executeDecimalResultQuery("select count(0) from member where gender = 'F' and row_id in (select member_id from subscription where active = 1 and type = '6M')");
            active1YearFemale = MainDataSource.executeDecimalResultQuery("select count(0) from member where gender = 'F' and row_id in (select member_id from subscription where active = 1 and type = '1Y')");
            active10EntryFemale = MainDataSource.executeDecimalResultQuery("select count(0) from member where gender = 'F' and row_id in (select member_id from subscription where active = 1 and type = '10E')");
            active20EntryFemale = MainDataSource.executeDecimalResultQuery("select count(0) from member where gender = 'F' and row_id in (select member_id from subscription where active = 1 and type = '20E')");
            subscriptionsEndingThisWeek = BigDecimal.ZERO;
        } catch (Exception ex) {
            showFatalMessage(ex);
        }
    }

    public void searchMember() {
        try {
            if (memberSearch.isEmpty())
                return;

            List<Member> mems = memberDAO.getList("row_id = '" + memberSearch + "' or member_number = '" + memberSearch + "'");
            if (mems == null || mems.isEmpty()) {
                showWarningMessage("Member `" + memberSearch + "` Not Found");
                return;
            }
            member = mems.get(0);
            member.loadSubscriptions();

            if (member.getActiveSubscription() != null) {
                executeJS("PF('sub').show()");
                showInfoMessage("Member " + member.getFullName() + " Subscription Found");
            } else {
                showWarningMessage("Member " + member.getFullName() + " Has No Active Subscription");
            }

            memberSearch = "";
        } catch (Exception ex) {
            showFatalMessage(ex);
        }
    }

    public void checkInMember() {
        try {
            if (member != null && member.getRowId() != null && member.getActiveSubscription() != null) {
                CheckIn checkIn = new CheckIn();
                checkIn.setCreatedAt(now());
                checkIn.setCheckIn(now());
                checkIn.setCreatedBy(user());
                checkIn.setExpired(false);
                checkIn.setMemberId(member.getRowId());
                checkInDAO.saveRecord(checkIn);
                if (member.getActiveSubscription().getType().contains("E")) {
                    member.getActiveSubscription().consumeEntry();
                } else {
                    showInfoMessage("Check In Successful");
                }
            }
        } catch (Exception ex) {
            showFatalMessage(ex);
        }
    }
}
