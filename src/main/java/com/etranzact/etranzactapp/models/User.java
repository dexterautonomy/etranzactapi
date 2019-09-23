package com.etranzact.etranzactapp.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
public class User extends CommonFields
{
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
    private String sessionID;
    
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Account account;
    
    public User(){};
    
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    public String getFirstName()
    {
        return firstName;
    }
    
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
    public String getLastName()
    {
        return lastName;
    }
    
    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }
    public String getPhoneNumber()
    {
        return phoneNumber;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    public String getPassword()
    {
        return password;
    }
    
    public void setSessionID(String sessionID)
    {
        this.sessionID = sessionID;
    }
    public String getSessionID()
    {
        return sessionID;
    }
    
    public void setAccount(Account account)
    {
        this.account = account;
    }
    
    public Account getAccount()
    {
        return account;
    }
}