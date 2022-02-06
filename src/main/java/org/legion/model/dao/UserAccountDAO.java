package org.legion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import lombok.extern.slf4j.Slf4j;
import org.legion.model.baseDAO.UserAccountDAOBase;
import org.legion.model.entity.UserAccount;
import org.legion.util.MainDataSource;

import java.util.List;
import java.util.Set;

@Slf4j
public class UserAccountDAO extends UserAccountDAOBase {

    public UserAccount login(String username, String password) throws Exception {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        UserAccount user = null;
        try {
            cn = MainDataSource.getConnection();
            st = cn.prepareStatement("select row_id from user_account where upper(username) = ? and upper(password) = ? ");
            st.setString(1, username.toUpperCase());
            st.setString(2, password.toUpperCase());
            rs = st.executeQuery();
            while (rs.next()) {
                String userId = rs.getString(1);
                user = getRecord(userId);
                log.info(userId);
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (rs != null)
                rs.close();
            if (st != null)
                st.close();
            if (cn != null)
                cn.close();
            return user;
        }
    }

}
