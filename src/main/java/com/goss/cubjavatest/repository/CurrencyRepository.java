package com.goss.cubjavatest.repository;

import com.goss.cubjavatest.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
}
