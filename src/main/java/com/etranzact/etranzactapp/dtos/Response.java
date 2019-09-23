package com.etranzact.etranzactapp.dtos;

public class Response
{
    private int status;
    private String message;
    private Object body;
    
    public Response(){};
    
    public Response(int status, String message, Object body)
    {
        this.status = status;
        this.message = message;
        this.body = body;
    }
    
    public void setStatus(int status)
    {
        this.status = status;
    }
    
    public int getStatus ()
    {
        return status;
    }
    
    public void setMessage(String msg)
    {
        this.message = msg;
    }
    
    public String getMessage ()
    {
        return message;
    }
    
    public void setBody(Object body)
    {
        this.body = body;
    }
    
    public Object getBody ()
    {
        return body;
    }
}