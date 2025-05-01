package com.br.business.service;

import com.br.entity.Conta;
import com.br.filter.ContaFilter;
import com.br.repository.ContaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;
    @PersistenceContext
    private EntityManager em;

    public List<Conta> listContas() {
        return contaRepository.findAll();
    }
    public Optional<Conta> findById(BigInteger id) {
        return contaRepository.findById(id);
    }
    public List<Conta> listContaSorted(ContaFilter contaFilter, Sort sort) {
        return contaRepository.listContaSorted(sort, contaFilter, em);
    }
    public Page<Conta> listContaPaged(ContaFilter contaFilter, Pageable pageable) {
        return contaRepository.listContaPaged(pageable, contaFilter, em);
    }
    public Conta save(Conta conta) {
        conta.setLancamento(LocalDateTime.now());
        return Optional.ofNullable(contaRepository.save(conta))
                .orElseThrow(()-> new RuntimeException("Erro ao salvar a conta!"));
    }
    public boolean deleteById(BigInteger idConta) {
        Optional<Conta> contaOptional = findById(idConta);
        contaOptional.ifPresent(conta -> contaRepository.deleteById(idConta));
        return contaRepository.findById(idConta).isEmpty();
    }
    public List getContaByTipo(){
        return contaRepository.getContaByTipo(em);
    }
    public BigDecimal getSumConta(ContaFilter contaFilter){
        return contaRepository.getSumConta(contaFilter, em);
    }

    public BigInteger getCountConta() {
        return BigInteger.valueOf(contaRepository.count());
    }
}
