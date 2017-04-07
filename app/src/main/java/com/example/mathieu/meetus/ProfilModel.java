package com.example.mathieu.meetus;

/**
 * Created by mathieu on 29/03/17.
 */
public class ProfilModel {
    private String name;
    private int age;
    private String techno;
    private String wild;
    private String city;

    private ProfilModel(){

    }

    public ProfilModel(String name, int age, String techno, String wild, String city){
        this.name = name;
        this.age = age;
        this.techno = techno;
        this.wild = wild;
        this.city = city;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public String getTechno() {
        return this.techno;
    }

    public String getWild() {
        return this.wild;
    }

    public String getCity() {
        return this.city;
    }
}