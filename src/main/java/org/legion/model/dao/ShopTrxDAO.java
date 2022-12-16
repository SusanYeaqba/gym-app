package org.legion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import org.legion.model.baseDAO.ShopTrxDAOBase;
import org.legion.model.entity.ShopTrx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

public class ShopTrxDAO extends ShopTrxDAOBase {
    private static final Logger logger = LoggerFactory.getLogger(ShopTrxDAOBase.class);

    @Override
    public List<ShopTrx> getList(String myQuery) throws Exception {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ShopTrx> listOfRecords = new ArrayList<>();
        try {
            con = org.legion.util.MainDataSource.getConnection();
            String query = "select top 100 from shop_trx where deleted_by is null and " + myQuery;
            logger.info(query);
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            while (rs.next()) {
                ShopTrx entity_record = new ShopTrx();
                entity_record.setRowId(rs.getString("row_id"));
                entity_record.setCreatedAt(rs.getLong("created_at"));
                entity_record.setCreatedBy(rs.getString("created_by"));
                entity_record.setUpdatedAt(rs.getLong("updated_at"));
                entity_record.setUpdateBy(rs.getString("update_by"));
                entity_record.setDeletedAt(rs.getLong("deleted_at"));
                entity_record.setDeletedBy(rs.getString("deleted_by"));
                entity_record.setBusinessDate(rs.getDate("business_date"));
                entity_record.setTotalPrice(rs.getBigDecimal("total_price"));
                entity_record.setDiscountPct(rs.getBigDecimal("discount_pct"));
                entity_record.setTotalAfterDiscount(rs.getBigDecimal("total_after_discount"));
                entity_record.setStatus(rs.getBigDecimal("status"));
                listOfRecords.add(entity_record);
            }
        } catch (Exception e) {
            logger.error("Error", e);
            throw e;
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException sqlex) {
                logger.error("SQL Error", sqlex);
                throw sqlex;
            }
        }
        return listOfRecords;
    }

}
