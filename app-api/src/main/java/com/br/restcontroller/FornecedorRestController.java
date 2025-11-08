package com.br.restcontroller;

import com.br.business.service.FornecedorService;
import com.br.dto.request.FornecedorRequestDTO;
import com.br.dto.response.FornecedorResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

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
    public List<FornecedorResponseDTO> get(Sort sort) {
        return fornecedorService.listFornecedoresSorted("", sort);
    }

    @GetMapping("/find/{busca}")
    @ResponseStatus(HttpStatus.OK)
    public List<FornecedorResponseDTO> get(@PathVariable("busca") String busca, Sort sort) {
        return fornecedorService.listFornecedoresSorted(busca, sort);
    }

    @GetMapping("/page")
    @ResponseStatus(HttpStatus.OK)
    public Page<FornecedorResponseDTO> getPage(@RequestParam(required = false) String busca, Pageable pageable) {
        return fornecedorService.listFornecedoresPaged(busca, pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FornecedorResponseDTO contaFindById(@PathVariable("id") BigInteger id) {
        return fornecedorService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<FornecedorResponseDTO> create(@RequestBody @Valid FornecedorRequestDTO fornecedorRequestDTO) {
        return new ResponseEntity<>(fornecedorService.save(fornecedorRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<FornecedorResponseDTO> update(@RequestBody @Valid FornecedorRequestDTO fornecedorRequestDTO) {
        return new ResponseEntity<>(fornecedorService.save(fornecedorRequestDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ok("Não ha permissão de exclusão do fornecedor até o momentod!");
    }

    @GetMapping(value = "/consultaCnpj/{cnpj}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> consultaCNPJ(@PathVariable("cnpj") String cnpj) {
        List<FornecedorResponseDTO> fornecedorList = fornecedorService.listFornecedoresSorted(cnpj, Sort.unsorted());
        if (!fornecedorList.isEmpty())
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CNPJ já cadastrado!");

        return ok(fornecedorService.getFornecedorFromWeb(cnpj));
    }
}