package com.br.business.service;

import com.br.dto.ContaByTipoDTO;
import com.br.dto.ContaDTO;
import com.br.entity.Conta;
import com.br.entity.TipoConta;
import com.br.filter.ContaFilter;
import com.br.mapper.ContaMapper;
import com.br.repository.ContaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;
    @PersistenceContext
    private EntityManager em;
    public static final ContaMapper contaMapper = ContaMapper.INSTANCE;

    public List<ContaDTO> listContas() {
        return contaMapper.toDtoList(contaRepository.findAll());
    }
    public ContaDTO findById(BigInteger id) {
        Conta conta = contaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com o ID: " + id));
        return contaMapper.toDto(conta);
    }
    public List<ContaDTO> listContaSorted(ContaFilter contaFilter, Sort sort) {
        return contaMapper.toDtoList(contaRepository.listContaSorted(sort, contaFilter, em));
    }
    public Page<ContaDTO> listContaPaged(ContaFilter contaFilter, Pageable pageable) {
        Page<Conta> contaPage = contaRepository.listContaPaged(pageable, contaFilter, em);
        return contaPage.map(contaMapper::toDto);
    }
    public ContaDTO save(ContaDTO contaDTO) {
        Conta conta = contaMapper.toEntity(contaDTO);
        conta.setLancamento(LocalDateTime.now());
        Conta newConta =  contaRepository.save(conta);
        return contaMapper.toDto(newConta);
    }
    public void deleteById(BigInteger idConta) {
        if(Objects.nonNull(findById(idConta)))
            contaRepository.deleteById(idConta);
    }
    public List<ContaByTipoDTO> getContaByTipo(){
        List<ContaByTipoDTO> lista = new ArrayList<>();

        for(Object item : contaRepository.getContaByTipo(em)){
            ContaByTipoDTO contaByTipoDto = new ContaByTipoDTO();
            var subitem = (Object[]) item;
            contaByTipoDto.setTipoConta((TipoConta) subitem[0]);
            contaByTipoDto.setValor(new BigDecimal(subitem[1].toString()));
            lista.add(contaByTipoDto);
        }

        return lista;
    }
    public BigDecimal getSumConta(ContaFilter contaFilter){
        return contaRepository.getSumConta(contaFilter, em);
    }

    public BigInteger getCountConta() {
        return BigInteger.valueOf(contaRepository.count());
    }
}
