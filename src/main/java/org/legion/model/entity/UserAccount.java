package org.legion.model.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import org.legion.model.baseEntity.UserAccountBase;
import org.legion.model.dao.MemberDAO;

import java.util.List;
import java.util.Set;

public class UserAccount extends UserAccountBase {
    Member member;

    public boolean isStaff() {
        return super.getRole().equals("Admin") || super.getRole().equals("Manager");
    }

    public boolean isAdmin() {
        return super.getRole().equals("Admin");
    }

    public boolean isManager() {
        return super.getRole().equals("Manager");
    }

    public boolean isClient() {
        return super.getRole().equals("Client");
    }

    public boolean isTrainer() {
        return super.getRole().equals("Trainer");
    }

    public Member getMember() throws Exception {
        if (member == null && isClient()) {
            List<Member> memberList = new MemberDAO().getList("user_id = '" + getRowId() + "'");
            if (memberList != null && !memberList.isEmpty()) {
                member = memberList.get(0);
            }
        }

        return member;
    }

}
