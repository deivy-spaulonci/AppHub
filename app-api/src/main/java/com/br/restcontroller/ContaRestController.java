package com.br.restcontroller;

import com.br.business.service.ContaService;
import com.br.dto.request.ContaRequestDTO;
import com.br.dto.response.ContaByTipoResponseDTO;
import com.br.dto.response.ContaResponseDTO;
import com.br.filter.ContaFilter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/api/v1/conta")
public class
ContaRestController {

    private ContaService contaService;

    @Autowired
    public ContaRestController(ContaService contaService) {
        this.contaService = contaService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ContaResponseDTO> findAll(@ModelAttribute ContaFilter contaFilter, Sort sort){
        return contaService.listContaSorted(contaFilter, sort);
    }

    @GetMapping("/page")
    @ResponseStatus(HttpStatus.OK)
    public Page<ContaResponseDTO> findAll(@ModelAttribute ContaFilter contaFilter, Pageable page){
        return contaService.listContaPaged(contaFilter, page);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContaResponseDTO ContaFindById(@PathVariable("id") BigInteger id){
        return contaService.findById(id);
    }

    @GetMapping("/valorTotal")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal get(@ModelAttribute ContaFilter contaFilter){
        return contaService.getSumConta(contaFilter);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ContaResponseDTO> create(@RequestBody @Valid ContaRequestDTO contaRequestDTO){
        return new ResponseEntity<>(contaService.save(contaRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ContaResponseDTO> update(@RequestBody @Valid ContaRequestDTO contaRequestDTO){
        return new ResponseEntity<>(contaService.save(contaRequestDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable BigInteger id){
        contaService.deleteById(id);
    }

    @GetMapping("/contaPorTipo")
    @ResponseStatus(HttpStatus.OK)
    public List<ContaByTipoResponseDTO> getContaByType(){
        return contaService.getContaByTipo();
    }

    @GetMapping("/gastoAno/{ano}")
    @ResponseStatus(HttpStatus.OK)
    public List contaFindById(@PathVariable(name = "ano") Integer ano){
        return contaService.gastosContaAnual(ano);
    }
}
