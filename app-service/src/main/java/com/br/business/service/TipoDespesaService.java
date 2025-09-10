package com.br.business.service;

import com.br.dto.TipoDespesaDTO;
import com.br.entity.TipoDespesa;
import com.br.entity.TipoDespesa_;
import com.br.mapper.TipoDespesaMapper;
import com.br.repository.TipoDespesaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
@Log4j2
public class TipoDespesaService {
    @Autowired
    TipoDespesaRepository tipoDespesaRepository;
    @PersistenceContext
    private EntityManager em;

    public static final TipoDespesaMapper tipoDespesaMapper = TipoDespesaMapper.INSTANCE;

    public List<TipoDespesaDTO> findTipoDespesas() {
        return tipoDespesaMapper.toDtoList(tipoDespesaRepository.findAll(Sort.by(TipoDespesa_.NOME)));
    }

    public TipoDespesaDTO findById(BigInteger id) {
        TipoDespesa tipoDespesa = tipoDespesaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tipo de despesa não encontrado com o id: " + id));
        return tipoDespesaMapper.toDto(tipoDespesa);
    }

    public TipoDespesaDTO save(TipoDespesaDTO tipoDespesaDTO) {
        TipoDespesa tipoDespesa = tipoDespesaMapper.toEntity(tipoDespesaDTO);
        return tipoDespesaMapper.toDto(tipoDespesaRepository.save(tipoDespesa));
    }
}
