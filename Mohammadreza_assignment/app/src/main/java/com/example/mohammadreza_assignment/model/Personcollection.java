package com.example.mohammadreza_assignment.model;

import java.util.ArrayList;
import java.util.List;

public class Personcollection {
    private static List<Person> personList = new ArrayList<>();

    public static List<Person> getPersonList() {
        return personList;
    }
}
