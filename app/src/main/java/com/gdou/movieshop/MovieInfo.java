package com.gdou.movieshop;

public class MovieInfo {
    protected String name = "小明";
    protected String surname = "西门";
    protected String email = "fever@icloud.com";
    protected static final String NAME_PREFIX = "Name_";
    protected static final String SURNAME_PREFIX = "Surname_";
    protected static final String EMAIL_PREFIX = "email_";

    public MovieInfo(String name, String surname, String email){
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static String getNamePrefix() {
        return NAME_PREFIX;
    }

    public static String getSurnamePrefix() {
        return SURNAME_PREFIX;
    }

    public static String getEmailPrefix() {
        return EMAIL_PREFIX;
    }
}