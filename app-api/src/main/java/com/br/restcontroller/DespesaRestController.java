package com.br.restcontroller;

import com.br.business.service.DespesaService;
import com.br.dto.DespesaByTipoDTO;
import com.br.dto.DespesaDTO;
import com.br.filter.DespesaFilter;
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
import java.util.Map;

@RestController
@RequestMapping("/api/v1/despesa")
public class DespesaRestController {

    private DespesaService despesaService;

    @Autowired
    public DespesaRestController(DespesaService despesaService) {
        this.despesaService = despesaService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<DespesaDTO> findAll(@ModelAttribute DespesaFilter despesaFilter, Sort sort){
        return despesaService.listDespesasSorted(despesaFilter, sort);
    }

    @GetMapping("/page")
    @ResponseStatus(HttpStatus.OK)
    public Page<DespesaDTO> findAll(@ModelAttribute DespesaFilter despesaFilter, Pageable page){
        return despesaService.listDespesasPaged(despesaFilter, page);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DespesaDTO despesaFindById(@PathVariable("id") BigInteger id){
        return despesaService.findById(id);
    }

    @GetMapping("/valorTotal")
    public BigDecimal get(@ModelAttribute DespesaFilter despesaFilter){
        return despesaService.getSumDespesa(despesaFilter);
    }

    @GetMapping("/gastoAno/{ano}")
    @ResponseStatus(HttpStatus.OK)
    public List despesaFindById(@PathVariable(name = "ano") Integer ano){
        return despesaService.gastosDespesaAnual(ano);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DespesaDTO> create(@RequestBody @Valid DespesaDTO despesaDTO){
        return new ResponseEntity<>(despesaService.save(despesaDTO), HttpStatus.CREATED);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DespesaDTO> update(@RequestBody @Valid DespesaDTO despesaDTO){
        return new ResponseEntity<>(despesaService.save(despesaDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable BigInteger id){
        despesaService.deleteById(id);
    }

    @GetMapping("/despesaPorTipo")
    @ResponseStatus(HttpStatus.OK)
    public List<DespesaByTipoDTO> getDespesaByType(){
        return despesaService.getDespesaByTipo();
    }

//    @GetMapping("/importCSV")
//    public ResponseEntity<?> importCsv(){
//        String result = despesaService.importFromResource();
//        if(result.isEmpty()){
//            return ok("importacao relizada!");
//        };
//        return status(HttpStatus.BAD_REQUEST).body(result);
//    }

    //    @PostMapping("/lote")
//    @Transactional
//    @ResponseStatus(HttpStatus.CREATED)
//    public ResponseEntity<List<String>> create(@RequestBody @Valid List<LoteDespesaDto> loteDespesaDtos){
//        List<String> resultado = despesaService.saveLote(loteDespesaDtos);
//        return ResponseEntity.ok(resultado);
//    }

}
