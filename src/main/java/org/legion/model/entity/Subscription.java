package org.legion.model.entity;

import java.math.BigDecimal;
import java.util.*;

import lombok.Getter;
import lombok.Setter;
import org.legion.model.baseEntity.SubscriptionBase;
import org.legion.model.dao.SubscriptionDAO;
import org.legion.ui_beans.ParentBean;
import org.legion.util.SystemParams;

@Getter
@Setter
public class Subscription extends SubscriptionBase {

    String gender = "M";


    @Override
    public void setStartDate(Date date) {
        if (date == null)
            date = new Date();

        super.setStartDate(date);

        if (getBonusPeriod() == null)
            setBonusPeriod(BigDecimal.ZERO);

        if (super.getType() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            switch (super.getType()) {
                case "1M": {
                    calendar.add(Calendar.MONTH, 1 + getBonusPeriod().intValue());
                    super.setEndDate(calendar.getTime());
                    break;
                }
                case "3M": {
                    calendar.add(Calendar.MONTH, 3 + getBonusPeriod().intValue());
                    super.setEndDate(calendar.getTime());
                    break;
                }
                case "6M": {
                    calendar.add(Calendar.MONTH, 6 + getBonusPeriod().intValue());
                    super.setEndDate(calendar.getTime());
                    break;
                }
                case "1Y": {
                    calendar.add(Calendar.MONTH, 12 + getBonusPeriod().intValue());
                    super.setEndDate(calendar.getTime());
                    break;
                }
                case "10E":
                case "20E": {
                    calendar.add(Calendar.MONTH, 2);
                    super.setEndDate(calendar.getTime());
                    break;
                }
            }
        }
    }

    public void updateMaximumEntries() {
        if (getType() != null && getType().contains("E")) {
            if (getBonusPeriod() == null)
                setBonusPeriod(BigDecimal.ZERO);

            switch (getType()) {
                case "10E": {
                    setMaxEntryCount(BigDecimal.valueOf(10).add(getBonusPeriod()));
                    break;
                }
                case "20E": {
                    setMaxEntryCount(BigDecimal.valueOf(20).add(getBonusPeriod()));
                    break;
                }
            }
        }

        setStartDate(getStartDate());
        System.out.println("Fetching price for " + "P" + gender + getType());
        setPrice(BigDecimal.valueOf(Long.parseLong(SystemParams.params.get("P" + gender + getType()))));
        System.out.println("Price: " + getPrice());
        calculateDiscount();
    }

    public void calculateDiscount() {
        if (getDiscount() != null && getDiscount().doubleValue() > 0) {
            setFinalPrice(getPrice().subtract(getPrice().multiply(getDiscount().divide(BigDecimal.valueOf(100)))));
        } else {
            setFinalPrice(getPrice());
        }
    }

    public void consumeEntry() {
        if(getConsumedEntryCount().compareTo(getMaxEntryCount()) < 0){
            setConsumedEntryCount(getConsumedEntryCount().add(BigDecimal.ONE));
        }

        if(getConsumedEntryCount().compareTo(getMaxEntryCount()) == 0){
            new ParentBean().showInfoMessage("No Entries Remaining!");
            setActive(false);
        }

        new SubscriptionDAO().saveRecord(this);
        new ParentBean().showInfoMessage("Entry Consumed Successfully!");
    }

}
