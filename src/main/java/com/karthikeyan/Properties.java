package com.karthikeyan;

import com.google.gson.Gson;
import com.karthikeyan.demopojo.Demo;
import com.karthikeyan.demopojo.Demo1;

import java.util.List;


/**
 * @author Karthikeyan on 19-06-2021
 */

public class Properties {

    public static <T, E> E transfer(T src, E des) throws IllegalAccessException {
        Loaders loaders = new Loaders();
        List<Row> srcFields = loaders.getAllFields(src);
        List<Row> desFields = loaders.getAllFields(des);
        if (loaders.typeChecker(srcFields, desFields)) {
            if (loaders.nameChecker(srcFields, desFields)) {
                loaders.setValues(srcFields, desFields, des);
            }
        }
        return des;
    }

    public static String toJson(Object o) {
        return new Gson().toJson(o);
    }

    public static void main(String[] args) throws IllegalAccessException {
        Demo demo = new Demo();
        demo.setA("A");
        demo.setB("B");
        demo.setD("D");
        demo.setC(1);
        Demo1 transfer = transfer(demo, new Demo1());
        System.out.println(toJson(transfer));
    }
}
