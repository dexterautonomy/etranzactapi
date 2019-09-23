package com.etranzact.etranzactapp.serviceImpl;

import com.etranzact.etranzactapp.dtos.Response;
import com.etranzact.etranzactapp.models.Account;
import com.etranzact.etranzactapp.models.User;
import com.etranzact.etranzactapp.repository.AccountRepository;
import com.etranzact.etranzactapp.repository.UserRepository;
import com.etranzact.etranzactapp.services.UserService;
import com.etranzact.etranzactapp.utility.GeneralUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService
{
    @Value("${initiate.code}")
    private String initiateCode;
    @Value("${initiate.message}")
    private String initiateMessage;
    @Value("${initiate.error}")
    private String errorMessage;
    @Value("${password.error}")
    private String errorPassword;
    
    @Autowired
    private GeneralUtil generalUtil;
    
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    AccountRepository accountRepository;
    
    @Override
    public Response createUser(String userParam)
    {
        Response response = null;
        String[] userStringArray = userParam.split("\\*");
        
        if(userStringArray.length == 7)
        {
            User user = new User();
        
            String password = userStringArray[5];
            String confirmPassword = userStringArray[6];
            password = password + "#";
            
            if(password.equals(confirmPassword))
            {
                String sessionID;
                user.setFirstName(userStringArray[2]);
                user.setLastName(userStringArray[3]);
                user.setPhoneNumber(userStringArray[4]);
                user.setPassword(userStringArray[5]);
                
                do
                {
                    sessionID = generalUtil.generateSessionID();
                }
                while(generalUtil.checkSessionID(sessionID));
                
                user.setSessionID(sessionID);
                user = userRepo.save(user);
                
                Account account = new Account(user);
                accountRepository.save(account);
                
                response = new Response(200, "Account created successfully", "ID: "+ sessionID);
            }
            else
            {
                response = new Response(700, "Request failure", errorPassword);
            }
        }
        else
        {
            response = new Response(700, "Request failure", errorMessage);
        }
        
        return response;
    }

    @Override
    public Response initiateCreate(String initiateParam)
    {
        Response response = null;
        
        if(initiateCode.equals(initiateParam))
        {
            response = new Response(200, "Request successful", initiateMessage);
        }
        else
        {
            response = new Response(900, "Request failure", errorMessage);
        }
        
        return response;
    }
    
}
