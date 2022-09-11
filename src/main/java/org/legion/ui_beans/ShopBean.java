package org.legion.ui_beans;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.legion.model.dao.CategoryDAO;
import org.legion.model.dao.ItemDAO;
import org.legion.model.entity.Category;
import org.legion.model.entity.Item;
import org.omnifaces.cdi.ViewScoped;

import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
@Slf4j
@Getter
@Setter
public class ShopBean extends ParentBean implements Serializable {
    ItemDAO itemDAO;
    CategoryDAO categoryDAO;


    List<Item> items;
    List<Category> categories;

    Item currentItem;
    Category currentCategory = new Category();

    String itemSearch = "";


    public ShopBean() {
        try {
            itemDAO = new ItemDAO();
            categoryDAO = new CategoryDAO();

            categories = categoryDAO.getList("1=1 order by created_at");
            items = itemDAO.getList("1=1 order by last_sold desc");
        } catch (Exception e) {
            showFatalMessage(e);
        }
    }

    public void selectCategory(Category category) {
        try {
            if(category.getRowId().equals(currentCategory.getRowId())){
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
            items = itemDAO.getList("1=1 order by last_sold desc");
        } catch (Exception e) {
            showFatalMessage(e);
        }
    }

    public void lookupItem() {
        try {
            items = itemDAO.getList("LOWER(description) like '%" + itemSearch.toLowerCase() + "%' or barcode = '" + itemSearch + "' order by last_sold desc");
            if (items == null || items.isEmpty())
                showWarningMessage("No items found");
            hideLoading();
        } catch (Exception e) {
            showFatalMessage(e);
        }
    }

}
