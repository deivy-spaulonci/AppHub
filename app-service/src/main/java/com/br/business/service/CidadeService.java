package com.br.business.service;

import com.br.dto.CidadeDTO;
import com.br.entity.Cidade;
import com.br.entity.Conta;
import com.br.mapper.CidadeMapper;
import com.br.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeService {
    @Autowired
    private CidadeRepository cidadeRepository;

    private static final CidadeMapper cidadeMapper = CidadeMapper.INSTANCE;

    public List<Cidade> listCidades() {
        return cidadeRepository.findAll();
    }
    public List<CidadeDTO> listCidadeByUf(String uf) {
        return cidadeMapper.toDtoList(cidadeRepository.findCidadeByUf(uf));
    }
    public List<CidadeDTO> listCidadesByUfContainingNome(String uf, String nome) {
        return cidadeMapper.toDtoList(cidadeRepository.findCidadeByUfAndAndNomeContainingIgnoreCaseOrderByNome(uf, nome));
    }

}
