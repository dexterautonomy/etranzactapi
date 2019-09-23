package com.etranzact.etranzactapp.controllers;

import com.etranzact.etranzactapp.dtos.Response;
import com.etranzact.etranzactapp.services.AccountService;
import com.etranzact.etranzactapp.services.UserService;
import com.etranzact.etranzactapp.utility.GeneralUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin (origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/etranzact/")
public class HomeController
{
    @Autowired
    private GeneralUtil generalUtil;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private AccountService accountService;
    
    @GetMapping("/run_multicurrency")
    public Response updateMultiCurrency()
    {
        generalUtil.converterTest();
        
        return null;
    }
    
    
    
    @GetMapping("{initiateParam}/initiate")
    public Response initiateReg(@PathVariable String initiateParam)
    {
        return userService.initiateCreate(initiateParam);
    }
    
    @GetMapping("{registerAccount}/create_account")
    public Response createAccount(@PathVariable String registerAccount)
    {
        return userService.createUser(registerAccount);
    }
    
    @GetMapping("{depositParam}/deposit_amount")
    public Response deposit(@PathVariable String depositParam)
    {
        return accountService.deposit(depositParam);
    }
    
    @GetMapping("{withdrawParam}/withdraw_amount")
    public Response withdraw(@PathVariable String withdrawParam)
    {
        return accountService.withdraw(withdrawParam);
    }
    
    @GetMapping("{balanceParam}/balance")
    public Response balance(@PathVariable String balanceParam)
    {
        return accountService.checkBalance(balanceParam);
    }
}
