package com.karthikeyan;


import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Karthikeyan on 19-06-2021
 */

public class Loaders {

    <T> List<Row> getAllFields(T o) throws IllegalAccessException {
        List<Row> map = new SortedList<>(Comparator.comparing(Row::getName));
        for (Field field : o.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String name = field.getName().substring(field.getName().lastIndexOf(".") + 1);
            Object value = field.get(o);
            String type = field.getType().getName();
            map.add(new Row(name, value, type));
            System.out.println("Field [ Name " + field.getName() + " Value = " + value + " Type = " + type + " ]");
        }
        return map;
    }

    boolean typeChecker(List<Row> s, List<Row> d) {
        List<String> sType = s.stream().map(Row::getType).collect(Collectors.toList());
        List<String> dType = d.stream().map(Row::getType).collect(Collectors.toList());

        Map<String, Long> fs = sType.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Map<String, Long> fd = dType.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        boolean equals = fs.keySet().equals(fd.keySet());
        System.out.println("Type Passed = " + equals);
        return equals;
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
        System.out.println("Name Passed = " + res);
        return res;
    }

    <T> void setValues(List<Row> s, List<Row> d, T des) throws IllegalAccessException {
        Map<String, Object> m = new HashMap<>();
        s.forEach(n -> m.put(n.getName(), n.getValue()));
        for (Field field : des.getClass().getDeclaredFields()) {
            String name = field.getName().substring(field.getName().lastIndexOf(".") + 1);
            field.setAccessible(true);
            field.set(des, m.get(name));
        }
    }


}
