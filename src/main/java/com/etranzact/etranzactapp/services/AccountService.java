package com.etranzact.etranzactapp.services;

import com.etranzact.etranzactapp.dtos.Response;
import com.etranzact.etranzactapp.models.User;

public interface AccountService
{
    Response deposit(String depositString);
    Response withdraw(String withdrawString);
    Response checkBalance(String balanceString);
}
