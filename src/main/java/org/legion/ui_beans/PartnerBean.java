package org.legion.ui_beans;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.legion.model.dao.MemberDAO;
import org.legion.model.dao.SubscriptionDAO;
import org.legion.model.entity.Member;
import org.legion.model.entity.Subscription;
import org.legion.util.MainDataSource;
import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.io.Serializable;
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
public class PartnerBean extends ParentBean implements Serializable {

    MemberDAO memberDAO;
    List<Member> members;

    @PostConstruct
    public void init() {
        try {
            memberDAO = new MemberDAO();
            MainDataSource.executeQuery("update subscription set active = 0 where getdate() > end_date ");
            members = memberDAO.getList("row_id in (select member_id from subscription where active = 1) order by created_at");
        } catch (Exception ex) {
            showFatalMessage(ex);
        }
    }

}
