package com.br.business.service;

import com.br.entity.FormaPagamento;
import com.br.entity.FormaPagamento_;
import com.br.repository.FormaPagamentoRepository;
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
public class FormaPagamentoService {
    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;
    @PersistenceContext
    private EntityManager manager;

    public List<FormaPagamento> findFormasPagamento() {
        return formaPagamentoRepository.findAll(Sort.by(FormaPagamento_.NOME));
    }

    public Optional<FormaPagamento> findById(BigInteger id) {
        return formaPagamentoRepository.findById(id);
    }

    public FormaPagamento save(FormaPagamento formaPagamento) {
        return Optional.ofNullable(formaPagamentoRepository.save(formaPagamento))
                .orElseThrow(()-> new RuntimeException("Erro ao salvar a forma de pagamento"));
    }
}
