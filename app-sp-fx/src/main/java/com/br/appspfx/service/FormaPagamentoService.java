package com.br.appspfx.service;

import com.br.appspfx.repository.FormaPagamentoRepository;
import com.br.entity.FormaPagamento;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormaPagamentoService {

    private final FormaPagamentoRepository formaPagamentoRepository;


    public FormaPagamentoService(FormaPagamentoRepository formaPagamentoRepository) {
        this.formaPagamentoRepository = formaPagamentoRepository;
    }

    public List<FormaPagamento> listarFormaPagamento() {
        return formaPagamentoRepository.listarFormaPagamento();
    }
}
