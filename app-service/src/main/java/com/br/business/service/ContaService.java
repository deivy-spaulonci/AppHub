package com.br.business.service;

import com.br.dto.request.ContaRequestDTO;
import com.br.dto.response.ContaByTipoResponseDTO;
import com.br.dto.response.ContaResponseDTO;
import com.br.entity.Conta;
import com.br.filter.ContaFilter;
import com.br.mapper.ContaMapper;
import com.br.repository.ContaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
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

    private ContaRepository contaRepository;
    @PersistenceContext
    private EntityManager em;
    public static final ContaMapper contaMapper = ContaMapper.INSTANCE;

    @Autowired
    public ContaService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    public List<ContaResponseDTO> listContas() {
        return contaMapper.toDtoList(contaRepository.findAll());
    }
    public ContaResponseDTO findById(BigInteger id) {
        Conta conta = contaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com o ID: " + id));
        return contaMapper.toDto(conta);
    }
    public List<ContaResponseDTO> listContaSorted(ContaFilter contaFilter, Sort sort) {
        return contaMapper.toDtoList(contaRepository.listContaSorted(sort, contaFilter));
    }
    public Page<ContaResponseDTO> listContaPaged(ContaFilter contaFilter, Pageable pageable) {
        Page<Conta> contaPage = contaRepository.listContaPaged(pageable, contaFilter);
        return contaPage.map(contaMapper::toDto);
    }
    @Transactional
    public ContaResponseDTO save(ContaRequestDTO contaRequestDTO) {
        Conta conta = contaMapper.toEntity(contaRequestDTO);
        conta.setLancamento(LocalDateTime.now());
        Conta newConta =  contaRepository.save(conta);
        return contaMapper.toDto(newConta);
    }
    @Transactional
    public void deleteById(BigInteger idConta) {
        if(Objects.nonNull(findById(idConta)))
            contaRepository.deleteById(idConta);
    }
    public List<ContaByTipoResponseDTO> getContaByTipo(){
        List<ContaByTipoResponseDTO> lista = new ArrayList<>();

        for(Object item : contaRepository.getContaByTipo(em)){
            ContaByTipoResponseDTO contaByTipoResponseDto = new ContaByTipoResponseDTO();
            var subitem = (Object[]) item;
            contaByTipoResponseDto.setNomeTipoConta(subitem[0].toString());
            contaByTipoResponseDto.setValor(new BigDecimal(subitem[1].toString()));
            lista.add(contaByTipoResponseDto);
        }

        return lista;
    }

    public List gastosContaAnual(int ano){
        return contaRepository.findGastoPorAno(ano);
    }

    public BigDecimal getSumConta(ContaFilter contaFilter){
        return contaRepository.getSumConta(contaFilter, em);
    }

    public BigInteger getCountConta() {
        return BigInteger.valueOf(contaRepository.count());
    }
}
