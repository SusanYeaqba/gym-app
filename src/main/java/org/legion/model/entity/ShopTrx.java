package org.legion.model.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import lombok.Getter;
import lombok.Setter;
import org.legion.model.baseEntity.ShopTrxBase;
import org.legion.model.dao.ShopTrxItemDAO;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ShopTrx extends ShopTrxBase {
    List<ShopTrxItem> items;

    public void loadItems() throws Exception {
        items = new ShopTrxItemDAO().getList("trx_id = '" + super.getRowId() + "'");
    }
}
