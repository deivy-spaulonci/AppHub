package com.br.repository;

import com.br.entity.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, BigInteger>{
    List<Cidade> findCidadeByUf(String uf);
    List<Cidade> findCidadeByUfAndAndNomeContainingIgnoreCaseOrderByNome(String uf, String nome);
    Cidade findCidadeByIbgeCod(String ibge);

}


