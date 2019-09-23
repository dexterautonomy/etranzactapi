package com.etranzact.etranzactapp.services;

import com.etranzact.etranzactapp.dtos.Response;
import com.etranzact.etranzactapp.models.User;

public interface UserService
{
    Response initiateCreate(String initiateParam);
    Response createUser(String userParam);
}
