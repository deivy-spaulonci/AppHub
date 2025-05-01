package com.br.repository;

import com.br.entity.TipoConta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface TipoContaRepository extends JpaRepository<TipoConta, BigInteger> {
}