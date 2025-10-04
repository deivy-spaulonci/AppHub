package com.br.restcontroller;

import com.br.business.service.ContaService;
import com.br.dto.ContaByTipoDTO;
import com.br.dto.ContaDTO;
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
public class ContaRestController {

    private ContaService contaService;

    @Autowired
    public ContaRestController(ContaService contaService) {
        this.contaService = contaService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ContaDTO> findAll(@ModelAttribute ContaFilter contaFilter, Sort sort){
        return contaService.listContaSorted(contaFilter, sort);
    }

    @GetMapping("/page")
    @ResponseStatus(HttpStatus.OK)
    public Page<ContaDTO> findAll(@ModelAttribute ContaFilter contaFilter, Pageable page){
        return contaService.listContaPaged(contaFilter, page);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContaDTO ContaFindById(@PathVariable("id") BigInteger id){
        return contaService.findById(id);
    }

    @GetMapping("/valorTotal")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal get(@ModelAttribute ContaFilter contaFilter){
        return contaService.getSumConta(contaFilter);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ContaDTO> create(@RequestBody @Valid ContaDTO contaDTO){
        return new ResponseEntity<>(contaService.save(contaDTO), HttpStatus.CREATED);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ContaDTO> update(@RequestBody @Valid ContaDTO contaDTO){
        return new ResponseEntity<>(contaService.save(contaDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable BigInteger id){
        contaService.deleteById(id);
    }

    @GetMapping("/contaPorTipo")
    @ResponseStatus(HttpStatus.OK)
    public List<ContaByTipoDTO> getContaByType(){
        return contaService.getContaByTipo();
    }

    @GetMapping("/gastoAno/{ano}")
    @ResponseStatus(HttpStatus.OK)
    public List contaFindById(@PathVariable(name = "ano") Integer ano){
        return contaService.gastosContaAnual(ano);
    }
}
