package com.br.business.service;

import com.br.entity.TipoConta;
import com.br.entity.TipoDespesa;
import com.br.entity.TipoDespesa_;
import com.br.repository.TipoDespesaRepository;
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
public class TipoDespesaService {
    @Autowired
    TipoDespesaRepository tipoDespesaRepository;
    @PersistenceContext
    private EntityManager em;

    public List<TipoDespesa> findTipoDespesas() {
        return tipoDespesaRepository.findAll(Sort.by(TipoDespesa_.NOME));
    }

    public Optional<TipoDespesa> findById(BigInteger id) {
        return tipoDespesaRepository.findById(id);
    }

    public TipoDespesa save(TipoDespesa tipoDespesa) {
        return Optional.ofNullable(tipoDespesaRepository.save(tipoDespesa))
                .orElseThrow(()-> new RuntimeException("Erro ao salvar o tipo de despesa"));
    }
}
