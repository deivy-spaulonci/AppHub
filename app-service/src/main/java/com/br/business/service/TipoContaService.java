package com.br.business.service;

import com.br.entity.TipoConta;
import com.br.repository.TipoContaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class TipoContaService {

    @Autowired
    private TipoContaRepository tipoContaRepository;
    @PersistenceContext
    private EntityManager em;

    public List<TipoConta> findTipoContas(Sort sort) {
        return tipoContaRepository.findAll(sort);
    }

    public Optional<TipoConta> findById(BigInteger id) {
        return tipoContaRepository.findById(id);
    }

    public TipoConta save(TipoConta tipoConta) {
        return Optional.ofNullable(tipoContaRepository.save(tipoConta))
                .orElseThrow(()-> new RuntimeException("Erro ao salvar o tipo de conta"));
    }
}
