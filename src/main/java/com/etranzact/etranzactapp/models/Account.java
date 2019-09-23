package com.etranzact.etranzactapp.models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Account extends CommonFields
{
    private Double balance = 0.0;
    
    @OneToOne
    private User user;
    
    public Account(){};
    
    public Account(User user)
    {
        this.user = user;
    }
    
    public void setBalance(Double balance)
    {
        this.balance = balance;
    }
    public Double getBalance()
    {
        return balance;
    }
    
    public void setUser(User user)
    {
        this.user = user;
    }
    
    public User getUser()
    {
        return user;
    }
}
