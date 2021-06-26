package com.karthikeyan.modules;

import com.google.gson.Gson;

import java.util.List;


/**
 * @author Karthikeyan on 19-06-2021
 */

public class Properties {

    public static <T, E> E cloner(T src, E des) throws IllegalAccessException {
        Loaders loaders = new Loaders();
        List<Row> srcFields = loaders.getAllFields(src);
        List<Row> desFields = loaders.getAllFields(des);
        if (loaders.typeChecker(srcFields, desFields) && loaders.nameChecker(srcFields, desFields)) {
            loaders.setValues(srcFields, des);
        }
        return des;
    }

    public static String toJson(Object o) {
        return new Gson().toJson(o);
    }

    private Properties() {
    }
}
