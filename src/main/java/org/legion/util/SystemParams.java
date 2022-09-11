package org.legion.util;

import org.legion.model.dao.ParamDAO;
import org.legion.model.entity.Param;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SystemParams implements Serializable {
    public static Map<String, String> params = new HashMap<>();

    public synchronized static void reloadParamsMap() {
        params = new HashMap<>();
        List<Param> paramsList = new ParamDAO().getList();
        for (Param param : paramsList) {
            params.put(param.getCode(), param.getValue());
        }
    }
}
