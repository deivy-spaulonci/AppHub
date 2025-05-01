package com.br.repository;

import com.br.entity.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, BigInteger> {
}