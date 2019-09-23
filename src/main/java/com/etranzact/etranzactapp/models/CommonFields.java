package com.etranzact.etranzactapp.models;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public class CommonFields implements Serializable
{
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    
    @Temporal(TemporalType.TIMESTAMP)
    public Date createdOn = new Date();

    public Date getCreatedOn()
    {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn)
    {
        this.createdOn = createdOn;
    }

    public Long getId()
    {
        return id;
    }
}
