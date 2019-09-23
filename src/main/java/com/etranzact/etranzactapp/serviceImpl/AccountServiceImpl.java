package com.etranzact.etranzactapp.serviceImpl;

import com.etranzact.etranzactapp.dtos.Response;
import com.etranzact.etranzactapp.models.Account;
import com.etranzact.etranzactapp.models.User;
import com.etranzact.etranzactapp.repository.AccountRepository;
import com.etranzact.etranzactapp.repository.UserRepository;
import com.etranzact.etranzactapp.services.AccountService;
import com.etranzact.etranzactapp.utility.GeneralUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService
{
    @Value("${initiate.message}")
    private String initiateMessage;
    @Value("${password.error}")
    private String errorPassword;
    
    @Autowired
    private GeneralUtil generalUtil;
    
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    AccountRepository accountRepository;
    
    @Override
    public Response deposit(String depositString)
    {
        Response response = null;
        String[] depositArray = depositString.split("\\*");
        
        if(depositArray.length == 5)
        {
            if(depositArray[2].equals("2"))
            {
                User user = userRepo.findBySessionID(depositArray[3]);
                if(user != null)
                {
                    Account account = user.getAccount();
                    
                    Double depositAmount = Double.parseDouble(depositArray[4].replace("#", ""));
                    if(depositAmount != null)
                    {
                        account.setBalance(account.getBalance() + depositAmount);
                        account = accountRepository.save(account);
                        
                        response = new Response(200, "Request successful", "New balance is: "+ account.getBalance());
                    }
                    else
                    {
                        response = new Response(700, "Request failure", "Invalid deposit");
                    }
                }
                else
                {
                    response = new Response(700, "Request failure", "User does not exists");
                }
            }
            else
            {
                response = new Response(700, "Request failure", initiateMessage);
            }
        }
        else
        {
            response = new Response(700, "Request failure", initiateMessage);
        }
        
        return response;
    }

    @Override
    public Response withdraw(String withdrawString)
    {
        Response response = null;
        String[] withdrawArray = withdrawString.split("\\*");
        
        if(withdrawArray.length == 6)
        {
            if(withdrawArray[2].equals("3"))
            {
                User user = userRepo.findBySessionID(withdrawArray[3]);
                if(user != null)
                {
                    if(withdrawArray[4].equals(user.getPassword()))
                    {
                        Account account = user.getAccount();

                        Double withdrawalAmount = Double.parseDouble(withdrawArray[5].replace("#", ""));
                        if(withdrawalAmount != null)
                        {
                            if(withdrawalAmount == 0.0 || withdrawalAmount < 0)
                            {
                                response = new Response(200, "Request failure", "Invalid amount");
                            }
                            if(account.getBalance() < withdrawalAmount)
                            {
                                response = new Response(200, "Request failure", "Insufficient balance");
                            }
                            else
                            {
                                account.setBalance(account.getBalance() - withdrawalAmount);
                                account = accountRepository.save(account);

                                response = new Response(200, "Request successful", "New balance is: "+ account.getBalance());
                            }
                        }
                        else
                        {
                            response = new Response(700, "Request failure", "Invalid withdrawal");
                        }
                    }
                    else
                    {
                        response = new Response(700, "Request failure", "Password error");
                    }
                }
                else
                {
                    response = new Response(700, "Request failure", "User does not exists");
                }
            }
            else
            {
                response = new Response(700, "Request failure", initiateMessage);
            }
        }
        else
        {
            response = new Response(700, "Request failure", initiateMessage);
        }
        
        return response;
    }

    @Override
    public Response checkBalance(String balanceString)
    {
        Response response = null;
        String[] balanceArray = balanceString.split("\\*");
        
        if(balanceArray.length == 6)
        {
            if(balanceArray[2].equals("4"))
            {
                User user = userRepo.findBySessionID(balanceArray[3]);
                if(user != null)
                {
                    if(balanceArray[4].equals(user.getPassword()))
                    {
                        Account account = user.getAccount();
                        String currency = balanceArray[5].replace("#", "");
                        Double balance = account.getBalance();
                        
                        response = new Response(200, "Request successful", "New balance is: "+ generalUtil.converterToMultiCurrency(balance, currency));
                    }
                    else
                    {
                        response = new Response(700, "Request failure", "Password error");
                    }
                }
                else
                {
                    response = new Response(700, "Request failure", "User does not exists");
                }
            }
            else
            {
                response = new Response(700, "Request failure", initiateMessage);
            }
        }
        else
        {
            response = new Response(700, "Request failure", initiateMessage);
        }
        
        return response;
    }
    
}
