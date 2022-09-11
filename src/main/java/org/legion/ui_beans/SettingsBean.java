package org.legion.ui_beans;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.legion.model.dao.ParamDAO;
import org.legion.model.entity.Param;
import org.legion.util.SystemParams;
import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
@Getter
@Setter
@Slf4j
public class SettingsBean extends ParentBean implements Serializable {

    Param param;
    ParamDAO paramDAO;
    List<Param> paramList;

    @PostConstruct
    public void init() {
        param = new Param();
        paramDAO = new ParamDAO();
        paramList = paramDAO.getList();
    }

    public void clearParam() {
        param = new Param();
    }

    public void saveParam() {
        try {
            boolean isNew = param.getRowId() == null;
            if (isNew) {
                param.setCreatedAt(now());
                param.setCreatedBy(user());
            }
            param.setUpdatedAt(now());
            param.setUpdateBy(user());

            paramDAO.saveRecord(param);
            if (isNew) {
                paramList.add(param);
            }
            SystemParams.reloadParamsMap();
            showInfoMessage("Param saved successfully", "");
            hideLoading();
        } catch (Exception e) {
            showFatalMessage(e);
        }
    }
}
