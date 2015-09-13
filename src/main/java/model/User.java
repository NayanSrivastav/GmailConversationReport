/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Locale;

/**
 *
 * @author nayan
 */
public class User
{

    private final String id;
    private final String email;
    private final boolean verified_email;
    private final String name;
    private final String given_ame;
    private final String family_name;
    private final String link;
    private final String picture;
    private final String gender;
    private final Locale locale;

    public User(String id, String email, boolean verified_email, String name, String given_ame, String family_name, String link, String picture, String gender, Locale locale)
    {
        this.id = id;
        this.email = email;
        this.verified_email = verified_email;
        this.name = name;
        this.given_ame = given_ame;
        this.family_name = family_name;
        this.link = link;
        this.picture = picture;
        this.gender = gender;
        this.locale = locale;
    }

    public String getId()
    {
        return id;
    }

    public String getEmail()
    {
        return email;
    }

    public boolean isVerified_email()
    {
        return verified_email;
    }

    public String getName()
    {
        return name;
    }

    public String getGiven_ame()
    {
        return given_ame;
    }

    public String getFamily_name()
    {
        return family_name;
    }

    public String getLink()
    {
        return link;
    }

    public String getPicture()
    {
        return picture;
    }

    public String getGender()
    {
        return gender;
    }

    public Locale getLocale()
    {
        return locale;
    }
}
