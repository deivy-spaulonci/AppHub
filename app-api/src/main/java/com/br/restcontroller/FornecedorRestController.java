package com.br.restcontroller;

import com.br.business.service.FornecedorService;
import com.br.dto.FornecedorDTO;
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
import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/fornecedor")
public class FornecedorRestController {
    private FornecedorService fornecedorService;

    @Autowired
    public FornecedorRestController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<FornecedorDTO> get(Sort sort) {
        return fornecedorService.listFornecedoresSorted("", sort);
    }

    @GetMapping("/find/{busca}")
    @ResponseStatus(HttpStatus.OK)
    public List<FornecedorDTO> get(@PathVariable("busca") String busca, Sort sort) {
        return fornecedorService.listFornecedoresSorted(busca, sort);
    }

    @GetMapping("/page")
    @ResponseStatus(HttpStatus.OK)
    public Page<FornecedorDTO> getPage(@RequestParam(required = false) String busca, Pageable pageable) {
        return fornecedorService.listFornecedoresPaged(busca, pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FornecedorDTO contaFindById(@PathVariable("id") BigInteger id) {
        return fornecedorService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<FornecedorDTO> create(@RequestBody @Valid FornecedorDTO fornecedorDTO) {
        return new ResponseEntity<>(fornecedorService.save(fornecedorDTO), HttpStatus.CREATED);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<FornecedorDTO> update(@RequestBody @Valid FornecedorDTO fornecedorDTO) {
        return new ResponseEntity<>(fornecedorService.save(fornecedorDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ok("Não ha permissão de exclusão do fornecedor até o momentod!");
    }

    @GetMapping(value = "/consultaCnpj/{cnpj}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> consultaCNPJ(@PathVariable("cnpj") String cnpj) {
        List<FornecedorDTO> fornecedorList = fornecedorService.listFornecedoresSorted(cnpj, Sort.unsorted());
        if (!fornecedorList.isEmpty())
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CNPJ já cadastrado!");

        return ok(fornecedorService.getFornecedorFromWeb(cnpj));
    }
}