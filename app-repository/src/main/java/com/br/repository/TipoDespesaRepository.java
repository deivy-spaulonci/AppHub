package com.br.repository;

import com.br.entity.TipoDespesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface TipoDespesaRepository extends JpaRepository<TipoDespesa, BigInteger> {
}
