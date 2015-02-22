package com.example.ecards;

/**
 * Created by Cathy on 15-02-21.
 */
public class eCard {

    //Identification information
    private String first_name, last_name, mid_name;

    //Contact information
    private int main_phone, alt_phone;
    private String email, alt_email;

    //Links
    private String linkedin, website, other;


    /**
     * Constructor
     */
    public eCard() {
        //create empty strings
        first_name="";
        last_name="";
        mid_name="";
        email="";
        alt_email="";
        linkedin="";
        website= "";
        other = "";
    }

    public eCard(String first, String last, int phone, String address) {
        first_name=first;
        last_name=last;
        main_phone= phone;
        email=address;
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

    public int getMainPhone() {
        return main_phone;
    }

    public int getAltPhone() {
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

    public void setMainPhone(int mp) {
        main_phone=mp;
    }

    public void setAltPhone(int ap) {
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

    public void setOther(String o) {
        other=o;
    }

}
