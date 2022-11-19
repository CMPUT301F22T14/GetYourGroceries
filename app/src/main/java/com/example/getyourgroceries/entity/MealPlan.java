package com.example.getyourgroceries.entity;

public class MealPlan {
    private String id;
    private String name;
    private String next;

    public MealPlan(String name, String next,String id){
        this.name = name;
        this.next = next;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getnext() {
        return next;
    }

    public void setnext(String next) {
        this.next = next;
    }


}
