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
public class ItemsBean extends ParentBean implements Serializable {
    ItemDAO itemDAO;
    CategoryDAO categoryDAO;


    List<Item> items;
    List<Category> categories;
    Item currentItem;

    public ItemsBean() {
        try {
            itemDAO = new ItemDAO();
            categoryDAO = new CategoryDAO();

            categories = categoryDAO.getList("1=1 order by created_at");
            items = itemDAO.getList();
        } catch (Exception e) {
            showFatalMessage(e);
        }
    }

    public void clearItem() {
        currentItem = new Item();
    }

    public void saveItem() {
        try {

            boolean isNew = currentItem.getRowId() == null;
            if (isNew) {
                currentItem.setCreatedAt(now());
                currentItem.setCreatedBy(user());
            }
            currentItem.setUpdatedAt(now());
            currentItem.setUpdateBy(user());

            itemDAO.saveRecord(currentItem);
            if (isNew) {
                items.add(currentItem);
            }
            showInfoMessage("Item saved successfully", "");
            hideLoading();
        } catch (Exception e) {
            showFatalMessage(e);
        }
    }

    public void deleteItem() {
        try {
            if (currentItem == null)
                return;

            currentItem.setDeletedAt(now());
            currentItem.setDeletedBy(user());
            itemDAO.saveRecord(currentItem);
            items.remove(currentItem);
            showInfoMessage("deleted successfully", "");
            hideLoading();
        } catch (Exception e) {
            showFatalMessage(e);
        }
    }
}
