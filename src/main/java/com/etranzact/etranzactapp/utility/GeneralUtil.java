package com.etranzact.etranzactapp.utility;

import com.etranzact.etranzactapp.models.MultiCurrency;
import com.etranzact.etranzactapp.models.User;
import com.etranzact.etranzactapp.repository.MultiCurrencyRepo;
import com.etranzact.etranzactapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Service
public class GeneralUtil
{
    @Value("${foreign.currencies}")
    private String foreignCurrencies;
    
    @Value("${currency.converter.key}")
    private String curConKey;
    
    @Autowired
    private MultiCurrencyRepo mcRepo;
    
    @Autowired
    private UserRepository userRepo;
    
    private Double getConversion(String link, String from_to)
    {
        InputStream in;
        Double conversion = 0.0;

        try
        {
            URL url = new URL(link);
            URLConnection urlConnection = url.openConnection();
            if (!(urlConnection instanceof HttpURLConnection))
            {
                throw new IOException("Not an Http Connection");
            }
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setAllowUserInteraction(false);
            httpURLConnection.setInstanceFollowRedirects(true);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                in = httpURLConnection.getInputStream();
                Map<String, Double> result = new ObjectMapper().readValue(IOUtils.toString(in, Charsets.toCharset("UTF-8")), HashMap.class);
                conversion = result.get(from_to);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return conversion;
    }
    
    public BigDecimal getConversionFactor(String currency)
    {
        BigDecimal convFactor;
        
        if(currency == null || currency.equalsIgnoreCase("NGN") || currency.equals(""))
        {
            convFactor = BigDecimal.ONE;
        }
        else
        {
            currency = currency.toUpperCase();
            Optional<MultiCurrency> curCon = mcRepo.findByExtCurrencyName(currency);
            
            if(curCon.orElse(null) != null)
            {
                convFactor = curCon.get().getExtCurrencyValue_Real();
            }
            else
            {
                convFactor = BigDecimal.ONE;
            }
        }
        
        return convFactor;
    }
    
    //To run and update the multi currency
    public void converterTest()
    {
        String request;
        Optional<MultiCurrency> curCon;
        final String[] CURRENCY = foreignCurrencies.split(",");
        
        for(String cnv : CURRENCY)
        {
            String convertTo = cnv + "_NGN";
            curCon = mcRepo.findByExtCurrencyName(cnv);
            request = "https://free.currconv.com/api/v7/convert?q="+convertTo+"&compact=ultra&apiKey="+curConKey;
            
            if(curCon.orElse(null) != null)
            {
                BigDecimal oldVal_Real = curCon.get().getExtCurrencyValue_Real();
                BigDecimal newVal = BigDecimal.valueOf(getConversion(request, convertTo));
            
                if(newVal.compareTo(BigDecimal.ZERO) != 0)
                {
                    if(oldVal_Real.compareTo(newVal) != 0)
                    {
                        curCon.get().setExtCurrencyValue_Real(newVal);
                        mcRepo.save(curCon.get());
                    }
                }
            }
            else
            {
                BigDecimal newVal = BigDecimal.valueOf(getConversion(request, convertTo));
                MultiCurrency ccv = new MultiCurrency("NGN", cnv, newVal);
                mcRepo.save(ccv);
            }
        }
    }
    
    public BigDecimal converterToMultiCurrency(Double val, String currency)
    {
        final String BASE_CURRENCY = "NGN";
        BigDecimal otherCurrency = BigDecimal.ZERO;
        
        if(currency != null && !currency.equals(""))
        {
            if(!currency.equalsIgnoreCase(BASE_CURRENCY))
            {
                currency = currency.toUpperCase();
                Optional<MultiCurrency> curCon = mcRepo.findByExtCurrencyName(currency);

                if(curCon.orElse(null) != null)
                {
                    BigDecimal oldVal = curCon.get().getExtCurrencyValue_Real();
                    otherCurrency = BigDecimal.valueOf(val).divide(oldVal, 2, RoundingMode.HALF_UP);
                }
            }
            else
            {
                otherCurrency = BigDecimal.valueOf(val).divide(BigDecimal.ONE, 2, RoundingMode.HALF_UP);
            }
        }
        
        return otherCurrency;
    }
    
    public String generateSessionID()
    {
        Random random = new Random();
        String trackingCode = "eTZ_";
        String characters = "PQ1RSTUVWX2YZabcde3fghij4klmnA5BCDE6FG7890$_HIJK0LMNO9opqr8stuv7wxyz";
        
        while(trackingCode.length() < 7)
        {
            trackingCode += characters.charAt(random.nextInt(characters.length() - 1));
        }
        
        return trackingCode;
    }
    
    public boolean checkSessionID(String sessionID)
    {
        boolean predicate = false;
        User user = userRepo.findBySessionID(sessionID);
        
        if(user != null)
        {
            predicate = true;
        }
        
        return predicate;
    }
}
