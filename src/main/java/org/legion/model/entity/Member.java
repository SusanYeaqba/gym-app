package org.legion.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.legion.model.baseEntity.MemberBase;
import org.legion.model.dao.SubscriptionDAO;
import org.legion.model.dao.UserAccountDAO;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;

@Getter
@Setter
public class Member extends MemberBase {

    UserAccount userAccount;

    List<Subscription> subscriptionList;
    Subscription activeSubscription;

    public UserAccount getUserAccount() throws Exception {
        if (userAccount == null && super.getUserId() != null) {
            userAccount = new UserAccountDAO().getRecord(super.getUserId());
        }
        return userAccount;
    }

    public void loadSubscriptions() throws Exception{
        subscriptionList = new SubscriptionDAO().getList("member_id = '"+getRowId()+"' order by end_date desc");
        List<Subscription> subscriptionListActive = new SubscriptionDAO().getList("member_id = '"+getRowId()+"' and active = 1");
        if(subscriptionListActive != null && !subscriptionListActive.isEmpty()){
            activeSubscription = subscriptionListActive.get(0);
        }
    }

    public void loadActiveSubscription() throws Exception{
        List<Subscription> subscriptionListActive = new SubscriptionDAO().getList("member_id = '"+getRowId()+"' and active = 1");
        if(subscriptionListActive != null && !subscriptionListActive.isEmpty()){
            activeSubscription = subscriptionListActive.get(0);
        }
    }

}
