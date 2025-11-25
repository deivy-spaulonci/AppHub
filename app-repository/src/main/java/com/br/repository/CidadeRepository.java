package com.br.repository;

import com.br.entity.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, BigInteger> {
    List<Cidade> findByUf(String uf);

    List<Cidade> findByUfAndNomeContainingIgnoreCaseOrderByNome(String uf, String nome);

    Optional<Cidade> findByIbgeCod(String ibge);
}