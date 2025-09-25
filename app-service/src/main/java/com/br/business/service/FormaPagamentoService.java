package com.br.business.service;

import com.br.dto.FormaPagamentoDTO;
import com.br.entity.FormaPagamento;
import com.br.entity.FormaPagamento_;
import com.br.mapper.FormaPagamentoMapper;
import com.br.repository.FormaPagamentoRepository;
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
public class FormaPagamentoService {

    private FormaPagamentoRepository formaPagamentoRepository;

    @PersistenceContext
    private EntityManager manager;
    public static final FormaPagamentoMapper formaPagamentoMapper = FormaPagamentoMapper.INSTANCE;

    @Autowired
    public FormaPagamentoService(FormaPagamentoRepository formaPagamentoRepository) {
        this.formaPagamentoRepository = formaPagamentoRepository;
    }

    public List<FormaPagamentoDTO> findFormasPagamento() {
        return formaPagamentoMapper.toDtoList(formaPagamentoRepository.findAll(Sort.by(FormaPagamento_.NOME)));
    }

    public FormaPagamentoDTO findById(BigInteger id) {
        FormaPagamento formaPagamento = formaPagamentoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Forma Pagagamento não encontrado com o ID: " + id));
        return formaPagamentoMapper.toDto(formaPagamento);
    }

    @Transactional
    public FormaPagamentoDTO save(FormaPagamentoDTO formaPagamentoDTO) {
        FormaPagamento formaPagamento = formaPagamentoMapper.toEntity(formaPagamentoDTO);
        return formaPagamentoMapper.toDto(formaPagamentoRepository.save(formaPagamento));
    }
}
