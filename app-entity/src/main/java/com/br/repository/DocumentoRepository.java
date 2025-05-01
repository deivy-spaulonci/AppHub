package com.br.repository;

import com.br.entity.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, BigInteger> {
}