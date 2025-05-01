package com.br.restcontroller;

import com.br.business.service.ContaService;
import com.br.dto.ContaByTipoDto;
import com.br.dto.ContaDto;
import com.br.entity.Conta;
import com.br.entity.TipoConta;
import com.br.filter.ContaFilter;
import com.br.mapper.ContaMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/conta")
public class ContaRestController {

    @Autowired
    private ContaService contaService;

    public static final ContaMapper contaMapper = ContaMapper.INSTANCE;

    @GetMapping()
    public ResponseEntity<List<ContaDto>> findAll(@ModelAttribute ContaFilter contaFilter, Sort sort){
        List<Conta> result = contaService.listContaSorted(contaFilter, sort);
        if(result.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(contaMapper.toDtoList(result));
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ContaDto>> findAll(@ModelAttribute ContaFilter contaFilter, Pageable page){
        Page<ContaDto> result = contaService.listContaPaged(contaFilter, page).map(contaMapper::toDto);
        if(result.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> ContaFindById(@PathVariable("id") BigInteger id){
        Optional<Conta> ContaOptional = contaService.findById(id);
        return ContaOptional.<ResponseEntity<Object>>map(value -> ResponseEntity.ok(contaMapper.toDto(value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("conta não encontrada!"));
    }

    @GetMapping("/valorTotal")
    public BigDecimal get(@ModelAttribute ContaFilter contaFilter){
        return contaService.getSumConta(contaFilter);
    }

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ContaDto> create(@RequestBody @Valid ContaDto contaDTO,
                                             UriComponentsBuilder uriBuilder){
        Conta conta = contaService.save(contaMapper.toEntity(contaDTO));
        URI uri = uriBuilder.path("/{id}").buildAndExpand(contaDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(contaMapper.toDto(conta));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> update(@RequestBody @Valid ContaDto contaDTO){
        Optional<Conta> contaOptional = contaService.findById(contaDTO.getId());
        if(contaOptional.isPresent()){
            Conta conta = contaService.save(contaMapper.toEntity(contaDTO));
            return ResponseEntity.ok(contaMapper.toDto(conta));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("nehuma conta encontrada!");
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable BigInteger id){
        Optional<Conta> conta = contaService.findById(id);
        if(conta.isPresent()){
            contaService.deleteById(conta.get().getId());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("nehuma conta encontrada!");
    }

    @GetMapping("/contaPorTipo")
    public ResponseEntity<List<ContaByTipoDto>> getContaByType(){
        List<ContaByTipoDto> lista = new ArrayList<>();

        List result = contaService.getContaByTipo();

        if(result.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        for(Object item : result){
            ContaByTipoDto contaByTipoDto = new ContaByTipoDto();
            var subitem = (Object[]) item;
            TipoConta tipoConta = new TipoConta();
            tipoConta.setId(new BigInteger(subitem[0].toString()));
            tipoConta.setNome(subitem[1].toString());
            contaByTipoDto.setTipoConta(tipoConta);
            contaByTipoDto.setValor(new BigDecimal(subitem[2].toString()));

            lista.add(contaByTipoDto);
        }

        return ResponseEntity.ok(lista);
    }
}
