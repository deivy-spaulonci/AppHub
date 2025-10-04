package com.br.repository;

import com.br.entity.Parametro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface ParametroRepository extends JpaRepository<Parametro, BigInteger> {
}