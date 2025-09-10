package com.br.appspfx.service;

import com.br.appspfx.repository.TipoDespesaRepository;
import com.br.entity.TipoDespesa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoDespesaService {

    private final TipoDespesaRepository tipoDespesaRepository;

    @Autowired
    public TipoDespesaService(TipoDespesaRepository tipoDespesaRepository) {
        this.tipoDespesaRepository = tipoDespesaRepository;
    }

    public List<TipoDespesa> listarTipoDespesas(){
        return  tipoDespesaRepository.listarTipoDespesa();
    }

}
