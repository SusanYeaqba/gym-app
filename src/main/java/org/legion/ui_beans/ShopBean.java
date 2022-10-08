package org.legion.ui_beans;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.legion.model.dao.CategoryDAO;
import org.legion.model.dao.ItemDAO;
import org.legion.model.dao.ShopTrxDAO;
import org.legion.model.dao.ShopTrxItemDAO;
import org.legion.model.entity.Category;
import org.legion.model.entity.Item;
import org.legion.model.entity.ShopTrx;
import org.legion.model.entity.ShopTrxItem;
import org.omnifaces.cdi.ViewScoped;

import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named
@ViewScoped
@Slf4j
@Getter
@Setter
public class ShopBean extends ParentBean implements Serializable {
    ItemDAO itemDAO;
    CategoryDAO categoryDAO;
    ShopTrxDAO shopTrxDAO;
    ShopTrxItemDAO shopTrxItemDAO;

    List<Item> items;
    List<Category> categories;

    Item currentItem;
    Category currentCategory = new Category();

    String itemSearch = "";

    ShopTrx trx = new ShopTrx();
    List<ShopTrxItem> trxItems = new ArrayList<>();

    List<ShopTrx> transactionHistoryList;

    public ShopBean() {
        try {
            itemDAO = new ItemDAO();
            categoryDAO = new CategoryDAO();
            shopTrxDAO = new ShopTrxDAO();
            shopTrxItemDAO = new ShopTrxItemDAO();


            categories = categoryDAO.getList("1=1 order by created_at");
            items = itemDAO.getList("1=1 order by last_sold desc");
        } catch (Exception e) {
            showFatalMessage(e);
        }
    }

    public void selectCategory(Category category) {
        try {
            if (category.getRowId().equals(currentCategory.getRowId())) {
                clearSelection();
                return;
            }
            currentCategory = category;
            items = itemDAO.getList("category_id = '" + category.getRowId() + "' order by last_sold desc");
        } catch (Exception e) {
            showFatalMessage(e);
        }
    }

    public void clearSelection() {
        try {
            currentCategory = new Category();
            items = itemDAO.getList("1=1 order by last_sold desc");
        } catch (Exception e) {
            showFatalMessage(e);
        }
    }

    public void lookupItem() {
        try {
            String where = "1=1 ";
            if (currentCategory.getRowId() != null)
                where += " and category_id = '" + currentCategory.getRowId() + "'";

            if (itemSearch.length() >= 3) {
                where += " and LOWER(description) like '%" + itemSearch.toLowerCase() + "%' or barcode = '" + itemSearch + "' ";
            }

            where += " order by last_sold desc";
            items = itemDAO.getList(where);

        } catch (Exception e) {
            showFatalMessage(e);
        }
    }

    public void cancelTrx() {
        try {
            trx = new ShopTrx();
            trxItems = new ArrayList<>();
        } catch (Exception e) {
            showFatalMessage(e);
        }
    }

    public void addItem(Item item) {
        try {
            if (item.getOnHand().doubleValue() <= 0) {
                showWarningMessage("Item not available in stock");
                return;
            }
            boolean exists = false;
            for (ShopTrxItem tI : trxItems) {
                if (tI.getItemId().equals(item.getRowId())) {
                    exists = true;
                    tI.setQty(tI.getQty().add(BigDecimal.ONE));
                    break;
                }
            }

            if (!exists) {
                ShopTrxItem trxItem = new ShopTrxItem();
                trxItem.setItemId(item.getRowId());
                trxItem.setItemDescription(item.getDescription());
                trxItem.setCreatedAt(now());
                trxItem.setCreatedBy(user());
                trxItem.setQty(BigDecimal.ONE);
                trxItem.setUnitPrice(item.getSellPrice());
                trxItem.setLineDiscountPct(BigDecimal.ZERO);
                trxItem.setFinalPrice(trxItem.getUnitPrice());
                trxItems.add(trxItem);
            }

            showInfoMessage(item.getDescription() + " added to cart");
            calculateTrxPrice();

        } catch (Exception e) {
            showFatalMessage(e);
        }
    }

    public void removeItem(ShopTrxItem trxItem) {
        try {
            if (trxItem.getQty().doubleValue() > 1) {
                trxItem.setQty(trxItem.getQty().subtract(BigDecimal.ONE));
            } else {
                trxItems.remove(trxItem);
            }

            calculateTrxPrice();

        } catch (Exception e) {
            showFatalMessage(e);
        }
    }

    public void calculateTrxPrice() {
        try {
            double currentPrice = 0.0;

            for (ShopTrxItem tI : trxItems) {
                tI.setFinalPrice(tI.getUnitPrice().multiply(tI.getQty()));
                tI.setFinalPrice(tI.getFinalPrice().subtract(tI.getFinalPrice().multiply(tI.getLineDiscountPct())));
                currentPrice += tI.getFinalPrice().doubleValue();
            }
            trx.setTotalPrice(BigDecimal.valueOf(currentPrice));
            trx.setTotalAfterDiscount(trx.getTotalPrice().subtract(trx.getTotalPrice().multiply(trx.getDiscountPct())));

        } catch (Exception e) {
            showFatalMessage(e);
        }
    }

    public void closeTrx() {
        try {
            calculateTrxPrice();
            trx.setCreatedAt(now());
            trx.setCreatedBy(user());
            trx.setBusinessDate(new Date());
            shopTrxDAO.saveRecord(trx);
            for (ShopTrxItem tI : trxItems) {
                tI.setTrxId(trx.getRowId());
                shopTrxItemDAO.saveRecord(tI);

                Item item = itemDAO.getRecord(tI.getItemId());
                item.setOnHand(item.getOnHand().subtract(tI.getQty()));
                itemDAO.saveRecord(item);
            }
            lookupItem();
            showInfoMessage("Transaction complete!");
            cancelTrx();
        } catch (Exception e) {
            showFatalMessage(e);
        }
    }

    public void loadHistory() {
        try {
            transactionHistoryList = shopTrxDAO.getList("1=1 order by created_at desc");
            System.out.println(transactionHistoryList.size());
        } catch (Exception e) {
            showFatalMessage(e);
        }
    }

}
