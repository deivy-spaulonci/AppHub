package com.br.restcontroller;

import com.br.business.service.FornecedorService;
import com.br.dto.FornecedorDto;
import com.br.entity.Cidade;
import com.br.entity.Estado;
import com.br.entity.Fornecedor;
import com.br.mapper.FornecedorMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigInteger;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/fornecedor")
public class FornecedorRestController {
    @Autowired
    private FornecedorService fornecedorService;
    private static final FornecedorMapper fornecedorMapper = FornecedorMapper.INSTANCE;

    @GetMapping()
    public ResponseEntity<List<FornecedorDto>> get(Sort sort) {
        List<Fornecedor> result = fornecedorService.listFornecedoresSorted("", sort);
        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ok(fornecedorMapper.toDtoList(result));
    }

    @GetMapping("/find/{busca}")
    public ResponseEntity<List<FornecedorDto>> get(@PathVariable("busca") String busca, Sort sort) {
        List<Fornecedor> result = fornecedorService.listFornecedoresSorted(busca, sort);
        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ok(fornecedorMapper.toDtoList(result));
    }

    @GetMapping("/page")
    public ResponseEntity<Page<FornecedorDto>> getPage(@RequestParam(required = false) String busca, Pageable pageable) {
        Page<FornecedorDto> result = fornecedorService.listFornecedoresPaged(busca, pageable).map(fornecedorMapper::toDto);
        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> contaFindById(@PathVariable("id") BigInteger id) {
        Optional<Fornecedor> fornecedorOptional = fornecedorService.findById(id);
        return fornecedorOptional.<ResponseEntity<Object>>map(value -> ok(fornecedorMapper.toDto(value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fornecedor não econtrado!"));
    }

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<FornecedorDto> create(@RequestBody @Valid FornecedorDto fornecedorDTO,
                                                UriComponentsBuilder uriBuilder) {
        Fornecedor fornecedor = fornecedorService.save(fornecedorMapper.toEntity(fornecedorDTO));
        URI uri = uriBuilder.path("/{id}").buildAndExpand(fornecedor.getId()).toUri();
        return ResponseEntity.created(uri).body(fornecedorMapper.toDto(fornecedor));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> update(@RequestBody @Valid FornecedorDto fornecedorDTO) {
        Optional<Fornecedor> fornecedorOptional = fornecedorService.findById(fornecedorDTO.getId());
        if (fornecedorOptional.isPresent()) {
            Fornecedor fornecedor = fornecedorService.save(fornecedorMapper.toEntity(fornecedorDTO));
            return ok(fornecedorMapper.toDto(fornecedor));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum fornecedor econtrado!");
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ok("Não ha permissao de exclusao do fornecedor!");
    }


    @GetMapping(value = "/consultaCnpj/{cnpj}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> consultaCNPJ(@PathVariable("cnpj") String cnpj) {

        List<Fornecedor> fornecedorList = fornecedorService.listFornecedoresSorted(cnpj, Sort.unsorted());
        if (!fornecedorList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CNPJ já cadastrado!");
        } else {
            return ok(fornecedorService.getFornecedorFromWeb(cnpj));
        }
        //return ResponseEntity.status(HttpStatus.CONFLICT).body("conflito na requisição da receita!");
    }
}