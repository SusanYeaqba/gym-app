package org.legion.model.dao;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

import org.legion.model.baseDAO.MemberDAOBase;
import org.legion.model.entity.Member;

public class MemberDAO extends MemberDAOBase {

    @Override
    public void saveRecord(Member record) {
        if (record.getBirthDate() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(record.getBirthDate());
            LocalDate dob = LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            LocalDate curDate = LocalDate.now();
            Period period = Period.between(dob, curDate);
            record.setAge(period.getYears());
        }

        super.saveRecord(record);
    }
}
