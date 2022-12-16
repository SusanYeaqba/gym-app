package org.legion.model.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import org.legion.model.baseEntity.ItemBase;
import org.legion.storage.StorageService;
import org.legion.ui_beans.ParentBean;
import org.primefaces.event.FileUploadEvent;

import java.util.List;
import java.util.Set;

public class Item extends ItemBase {

    public void uploadImage(FileUploadEvent event) {
        try {
            StorageService storageService = new StorageService();
            String filePath = storageService.saveObject("products", event.getFile().getFileName(), event.getFile().getContent());
            setImage(filePath);
        } catch (Exception ex) {
            new ParentBean().showErrorMessage("Upload failed", ex.getMessage());
        }
    }

}
