package com.br.business.service;

import com.br.entity.Cidade;
import com.br.entity.Conta;
import com.br.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeService {
    @Autowired
    private CidadeRepository cidadeRepository;

    public List<Cidade> listCidades() {
        return cidadeRepository.findAll();
    }
    public List<Cidade> listCidadeByUf(String uf) {
        return cidadeRepository.findCidadeByUf(uf);
    }
    public List<Cidade> listCidadesByUfContainingNome(String uf, String nome) {
        return cidadeRepository.findCidadeByUfAndAndNomeContainingIgnoreCaseOrderByNome(uf, nome);
    }

}
