package com.br.appspfx.service;

import com.br.appspfx.repository.DespesaRepository;
import com.br.entity.Despesa;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DespesaService {

    private final DespesaRepository despesaRepository;

    public DespesaService(DespesaRepository despesaRepository) {
        this.despesaRepository = despesaRepository;
    }

    public List<Despesa> listarDespesa() {
        return despesaRepository.listarDespesa();
    }

}
