package com.karthikeyan.modules;


import com.karthikeyan.exception.KarClonerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.pojo.tester.api.assertion.Assertions;
import pl.pojo.tester.api.assertion.Method;
import pl.pojo.tester.internal.utils.GetterNotFoundException;
import pl.pojo.tester.internal.utils.SetterNotFoundException;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Karthikeyan on 19-06-2021
 */

public class Loaders {
    public static final Logger logger = LoggerFactory.getLogger(Loaders.class);

    <T> List<Row> getAllFields(T o) throws IllegalAccessException {
        preChecker(o);
        List<Row> map = new SortedList<>(Comparator.comparing(Row::getName));
        for (Field field : o.getClass().getDeclaredFields()) {
            Field secureAccess = grantSecureAccess(field);
            String name = secureAccess.getName().substring(field.getName().lastIndexOf(".") + 1);
            Object value = secureAccess.get(o);
            String type = secureAccess.getType().getName();
            map.add(new Row(name, value, type));
            logger.info("Field [ Name {} Value = {} Type = {} ]", secureAccess.getName(), value, type);
        }
        return map;
    }

    boolean typeChecker(List<Row> s, List<Row> d) {
        List<String> sType = s.stream().map(Row::getType).collect(Collectors.toList());
        List<String> dType = d.stream().map(Row::getType).collect(Collectors.toList());

        Map<String, Long> fs = sType.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Map<String, Long> fd = dType.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        boolean res = fs.keySet().equals(fd.keySet());
        if (!res) {
            throw new KarClonerException("Type mismatch between source and target objects");
        }
        logger.info("Type Check Passed");
        return true;
    }

    boolean nameChecker(List<Row> s, List<Row> d) {
        List<String> sName = s.stream().map(Row::getName).collect(Collectors.toList());
        List<String> dName = d.stream().map(Row::getName).collect(Collectors.toList());
        boolean res = true;
        for (int i = 0; i < sName.size(); i++) {
            if (Collections.binarySearch(sName, dName.get(i)) < 0) {
                res = false;
            }
        }
        if (!res) {
            throw new KarClonerException("Name mismatch between source and target objects");
        }
        logger.info("Name Check Passed");
        return true;
    }

    <T> void setValues(List<Row> s, T des) throws IllegalAccessException {
        Map<String, Object> m = new HashMap<>();
        s.forEach(n -> m.put(n.getName(), n.getValue()));
        for (Field field : des.getClass().getDeclaredFields()) {
            secureSet(des, m, field);
        }
    }

    private <T> void secureSet(T des, Map<String, Object> m, Field field) throws IllegalAccessException {
        String name = field.getName().substring(field.getName().lastIndexOf(".") + 1);
        Field secureAccess = grantSecureAccess(field);
        secureAccess.set(des, m.get(name));
    }

    private <T> void preChecker(T o) {
        try {
            Assertions.assertPojoMethodsFor(o.getClass()).testing(Method.GETTER, Method.SETTER).areWellImplemented();
        } catch (SetterNotFoundException | GetterNotFoundException e) {
            throw new KarClonerException(e.getLocalizedMessage());
        }
    }
    private Field grantSecureAccess(Field field) {
        field.setAccessible(true);
        return field;
    }


}
