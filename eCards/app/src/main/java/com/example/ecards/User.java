package com.example.ecards;

/**
 * Created by Cathy on 15-02-21.
 */
public class User {

    //Identification information
    private String first_name, last_name, mid_name;

    //Contact information
    private String main_phone, alt_phone;
    private String email, alt_email;

    //Links
    private String linkedin, website, other, github;


    /**
     * Constructor
     */
    public User() {
        //create empty strings
        first_name="";
        last_name="";
        mid_name="";
        email="";
        alt_email="";
        linkedin="";
        github = "";
        website= "";
        other = "";
    }

    public User(String first, String last, String phone) {

        first_name=first;
        last_name=last;
        main_phone= phone;
        mid_name="";
        email="";
        alt_email="";
        linkedin="";
        github = "";
        website= "";
        other = "";
    }

    /** Methods - Getters */
    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public String getMidName() {
        return mid_name;
    }

    public String getMainPhone() {
        return main_phone;
    }

    public String getAltPhone() {
        return alt_phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAltEmail() {
        return alt_email;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public String getGithub() {
        return github;
    }

    public String getWebsite() {
        return website;
    }

    public String getOther() {
        return other;
    }

    /** Methods - Setters*/
    public void setFirstName(String n) {
        first_name=n;
    }

    public void setLastName(String l) {
        last_name=l;
    }

    public void setMidName(String m) {
        mid_name=m;
    }

    public void setMainPhone(String mp) {
        main_phone=mp;
    }

    public void setAltPhone(String ap) {
        alt_phone = ap;
    }

    public void setEmail(String em) {
        email=em;
    }

    public void setAltEmail(String aem) {
        alt_email=aem;
    }

    public void setLinkedin(String link) {
        linkedin=link;
    }

    public void setWebsite(String web) {
        website=web;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public void setOther(String o) {
        other=o;
    }

}
