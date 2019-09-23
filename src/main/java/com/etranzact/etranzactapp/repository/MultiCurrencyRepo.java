package com.etranzact.etranzactapp.repository;

import com.etranzact.etranzactapp.models.MultiCurrency;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MultiCurrencyRepo extends JpaRepository<MultiCurrency, Long>
{
    Optional<MultiCurrency> findByExtCurrencyName(String extCurrencyName);
}
