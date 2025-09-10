package com.br.appspfx.service;

import com.br.appspfx.repository.FornecedorRepository;
import com.br.entity.Fornecedor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;

    public FornecedorService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    public List<Fornecedor> findAll(String busca) {
        return fornecedorRepository.findAll(busca);
    }
}
