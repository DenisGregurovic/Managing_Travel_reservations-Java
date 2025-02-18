package com.example.javafxproject.production.model;

import java.util.List;
import java.util.Set;

public class SecondGenericClassPO <T,U>{
    private List<T> list;
    private Set<U> set;
    public SecondGenericClassPO(List<T> list, Set<U> set) {
        this.list = list;
        this.set = set;
    }
    public void outputList() {
        for (T element : list) {
            System.out.println(element);
        }
    }

    public void outputSet() {
        for (U element : set) {
            System.out.println(element);
        }
    }
}
