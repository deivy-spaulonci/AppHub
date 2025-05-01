package com.br.repository;

import com.br.entity.Fatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface FaturaRepository extends JpaRepository<Fatura, BigInteger> {
}