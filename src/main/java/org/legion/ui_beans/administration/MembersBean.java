package org.legion.ui_beans.administration;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.legion.model.dao.MemberDAO;
import org.legion.model.entity.Member;
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
public class MembersBean extends ParentBean implements Serializable {
    MemberDAO memberDAO;


    List<Member> members;
    Member currentMember;

    public MembersBean() {
        try {
            memberDAO = new MemberDAO();
            members = memberDAO.getList();
        } catch (Exception e) {
            showFatalMessage(e);
        }
    }

    public void clearMember() {
        currentMember = new Member();
    }

    public void saveMember() {
        try {

            boolean isNew = currentMember.getRowId() == null;
            if (isNew) {
                currentMember.setCreatedAt(now());
                currentMember.setCreatedBy(user());
            }
            currentMember.setUpdatedAt(now());
            currentMember.setUpdateBy(user());

            memberDAO.saveRecord(currentMember);
            if (isNew) {
                members.add(currentMember);
            }
            showInfoMessage("Member saved successfully", "");
            hideLoading();
        } catch (Exception e) {
            showFatalMessage(e);
        }
    }

    public void deleteMember() {
        try {
            if (currentMember == null)
                return;

            currentMember.setDeletedAt(now());
            currentMember.setDeletedBy(user());
            memberDAO.saveRecord(currentMember);
            members.remove(currentMember);
            showInfoMessage("deleted successfully", "");
            hideLoading();
        } catch (Exception e) {
            showFatalMessage(e);
        }
    }
}
