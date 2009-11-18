package com.netpace.aims.controller.newmarketing;

import java.io.Serializable;

/** @author Ahson Imtiaz */
public class ContactsDetailsExt implements Serializable
{

    private Long userId;
    private String firstName;
    private String lastName;
    private String title;

    /**
     * @return Returns the firstName.
     */
    public String getFirstName()
    {
        return firstName;
    }
    /**
     * @param firstName The firstName to set.
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    /**
     * @return Returns the lastName.
     */
    public String getLastName()
    {
        return lastName;
    }
    /**
     * @param lastName The lastName to set.
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
    /**
     * @return Returns the title.
     */
    public String getTitle()
    {
        return title;
    }
    /**
     * @param title The title to set.
     */
    public void setTitle(String title)
    {
        this.title = title;
    }
    /**
     * @return Returns the userId.
     */
    public Long getUserId()
    {
        return userId;
    }
    /**
     * @param userId The userId to set.
     */
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    /**
     * @return Returns the fullName Last Name First Name.
     */
    public String getFullName()
    {
        return lastName + " " + firstName;
    }

    /**
     * @return Returns the fullName with Title - Last Name First Name (Title).
     */
    public String getFullNameTitle()
    {
        return lastName + " " + firstName + " (" + title + ")";
    }

}
