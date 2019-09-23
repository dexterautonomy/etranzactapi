package com.etranzact.etranzactapp.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.persistence.Entity;

@Entity
public class MultiCurrency extends CommonFields
{
    private String baseCurrencyName;
    private final int baseCurrencyValue = 1;
    private String extCurrencyName;
    private BigDecimal extCurrencyValue_Real;
    
    public MultiCurrency(){}
    
    public MultiCurrency(String baseCurrencyName, String extCurrencyName, BigDecimal extCurrencyValue_Real)
    {
        this.baseCurrencyName = baseCurrencyName;
        this.extCurrencyName = extCurrencyName;
        this.extCurrencyValue_Real = extCurrencyValue_Real.setScale(2, RoundingMode.CEILING);
    }
    
    public void setBaseCurrencyName(String name)
    {
        baseCurrencyName = name;
    }
    
    public String getBaseCurrencyName()
    {
        return baseCurrencyName;
    }
    
    public void setExtCurrencyName(String name)
    {
        extCurrencyName = name;
    }
    
    public String getExtCurrencyName()
    {
        return extCurrencyName;
    }
    
    public int getBaseCurrencyValue()
    {
        return baseCurrencyValue;
    }
    
    public void setExtCurrencyValue_Real(BigDecimal val)
    {
        extCurrencyValue_Real = val.setScale(2, RoundingMode.CEILING);
    }
    
    public BigDecimal getExtCurrencyValue_Real()
    {
        return extCurrencyValue_Real;
    }
}
