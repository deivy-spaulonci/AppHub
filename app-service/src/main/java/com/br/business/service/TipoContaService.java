package com.br.business.service;

import com.br.dto.request.create.TipoContaCreateRequestDTO;
import com.br.dto.request.update.TipoContaUpdateRequestDTO;
import com.br.dto.response.TipoContaResponseDTO;
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

    public List<TipoContaResponseDTO> findTipoContas(Sort sort) {
        return tipoContaMapper.toDtoList(tipoContaRepository.findAll(sort));
    }

    public List<TipoContaResponseDTO> findTipoContas() {
        return tipoContaMapper.toDtoList(tipoContaRepository.findAll());
    }

    public TipoContaResponseDTO findById(BigInteger id) {
        TipoConta tipoConta = tipoContaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tipo de conta não encontrado com o id: " + id));
        return tipoContaMapper.toDto(tipoConta);
    }

    @Transactional
    public TipoContaResponseDTO update(TipoContaUpdateRequestDTO tipoContaUpdateRequestDTO) {
        TipoConta tipoConta = tipoContaMapper.toEntity(tipoContaUpdateRequestDTO);
        return tipoContaMapper.toDto(tipoContaRepository.save(tipoConta));
    }

    @Transactional
    public TipoContaResponseDTO save(TipoContaCreateRequestDTO tipoContaCreateRequestDTO) {
        TipoConta tipoConta = tipoContaMapper.toEntity(tipoContaCreateRequestDTO);
        return tipoContaMapper.toDto(tipoContaRepository.save(tipoConta));
    }
}
