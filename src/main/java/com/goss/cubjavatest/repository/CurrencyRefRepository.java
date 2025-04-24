package com.goss.cubjavatest.repository;

import com.goss.cubjavatest.entity.CurrencyRef;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRefRepository extends JpaRepository<CurrencyRef, String> {
}
