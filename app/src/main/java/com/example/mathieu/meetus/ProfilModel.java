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
    private String userId;
    private int userCount;

    private ProfilModel(){

    }



    public ProfilModel(String name, int age, String techno, String wild, String city, String userId){
        this.name = name;
        this.age = age;
        this.techno = techno;
        this.wild = wild;
        this.city = city;
        this.userId = userId;
        this.userCount = userCount;

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

    public String getUserId() {
        return userId;
    }

    public String getWild() {

        return this.wild;
    }

    public String getCity() {

        return this.city;
    }


}