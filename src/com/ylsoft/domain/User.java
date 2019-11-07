package com.ylsoft.domain;

public class User {
    private int id;
    private String name;
    private String gendet;
    private int age;
    private String address;
    private int qq;
    private String email;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGendet() {
        return gendet;
    }

    public void setGendet(String gendet) {
        this.gendet = gendet;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getQq() {
        return qq;
    }

    public void setQq(int qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(int id, String name, String gendet, int age, String address, int qq, String email) {

        this.id = id;
        this.name = name;
        this.gendet = gendet;
        this.age = age;
        this.address = address;
        this.qq = qq;
        this.email = email;
    }
}
