package com.br.business.service;

import com.br.dto.TipoContaDTO;
import com.br.entity.TipoConta;
import com.br.mapper.TipoContaMapper;
import com.br.repository.TipoContaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
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

    private TipoContaRepository tipoContaRepository;
    public static final TipoContaMapper tipoContaMapper = TipoContaMapper.INSTANCE;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public TipoContaService(TipoContaRepository tipoContaRepository) {
        this.tipoContaRepository = tipoContaRepository;
    }

    public List<TipoContaDTO> findTipoContas(Sort sort) {
        return tipoContaMapper.toDtoList(tipoContaRepository.findAll(sort));
    }

    public List<TipoContaDTO> findTipoContas() {
        return tipoContaMapper.toDtoList(tipoContaRepository.findAll());
    }

    public TipoContaDTO findById(BigInteger id) {
        TipoConta tipoConta = tipoContaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tipo de conta não encontrado com o id: " + id));
        return tipoContaMapper.toDto(tipoConta);
    }

    @Transactional
    public TipoContaDTO save(TipoContaDTO tipoContaDTO) {
        TipoConta tipoConta = tipoContaMapper.toEntity(tipoContaDTO);
        return tipoContaMapper.toDto(tipoContaRepository.save(tipoConta));
    }
}
