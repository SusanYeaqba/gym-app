package org.legion.ui_beans.operations;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.legion.model.dao.MemberDAO;
import org.legion.model.dao.UserAccountDAO;
import org.legion.model.entity.Member;
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
public class HomepageBean extends ParentBean implements Serializable {
    MemberDAO memberDAO;
    Member member;

    String numberSearch;

    public HomepageBean() {
        try {
            memberDAO = new MemberDAO();
        } catch (Exception e) {
            showFatalMessage(e);
        }
    }

    public void search() {
        try {
            List<Member> members = memberDAO.getList("member_number = '" + numberSearch + "'");
            if (members != null && !members.isEmpty()) {
                member = members.get(0);
                showInfoMessage("Found " + member.getFullName());
            } else {
                showErrorMessage("Member #" + numberSearch + " Not Found");
                numberSearch = "";
            }
        } catch (Exception e) {
            showFatalMessage(e);
        }
    }

}
