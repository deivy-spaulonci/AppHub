package com.br.restcontroller;

import com.br.business.service.DespesaService;
import com.br.dto.DespesaByTipoDto;
import com.br.dto.DespesaDto;
import com.br.dto.LoteDespesaDto;
import com.br.entity.Despesa;
import com.br.entity.TipoDespesa;
import com.br.filter.DespesaFilter;
import com.br.mapper.DespesaMapper;
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

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/api/v1/despesa")
public class DespesaRestController {

    @Autowired
    private DespesaService despesaService;

    public static final DespesaMapper despesaMapper = DespesaMapper.INSTANCE;

    @GetMapping()
    public ResponseEntity<List<DespesaDto>> findAll(@ModelAttribute DespesaFilter despesaFilter, Sort sort){
        List<Despesa> result = despesaService.listDespesasSorted(despesaFilter, sort);
        if(result.isEmpty()){
            return noContent().build();
        }
        return ok(despesaMapper.toDtoList(result));
    }

    @GetMapping("/page")
    public ResponseEntity<Page<DespesaDto>> findAll(@ModelAttribute DespesaFilter despesaFilter, Pageable page){
        Page<DespesaDto> result = despesaService.listDespesasPaged(despesaFilter, page).map(despesaMapper::toDto);
        if(result.isEmpty()){
            return noContent().build();
        }
        return ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> despesaFindById(@PathVariable("id") BigInteger id){
        Optional<Despesa> despesaOptional = despesaService.findById(id);
        return despesaOptional.<ResponseEntity<Object>>map(value -> ok(despesaMapper.toDto(value)))
                .orElseGet(() -> status(HttpStatus.NOT_FOUND)
                        .body("despesa não encontrada!"));
    }

    @GetMapping("/valorTotal")
    public BigDecimal get(@ModelAttribute DespesaFilter despesaFilter){
        return despesaService.getSumDespesa(despesaFilter);
    }

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DespesaDto> create(@RequestBody @Valid DespesaDto despesaDTO,
                                             UriComponentsBuilder uriBuilder){
        Despesa despesa = despesaService.save(despesaMapper.toEntity(despesaDTO));
        URI uri = uriBuilder.path("/{id}").buildAndExpand(despesa.getId()).toUri();
        return created(uri).body(despesaMapper.toDto(despesa));
    }

//    @PostMapping("/lote")
//    @Transactional
//    @ResponseStatus(HttpStatus.CREATED)
//    public ResponseEntity<List<String>> create(@RequestBody @Valid List<LoteDespesaDto> loteDespesaDtos){
//        List<String> resultado = despesaService.saveLote(loteDespesaDtos);
//        return ResponseEntity.ok(resultado);
//    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> update(@RequestBody @Valid DespesaDto despesaDTO){
        Optional<Despesa> despesaOptional = despesaService.findById(despesaDTO.getId());
        if(despesaOptional.isPresent()){
            Despesa despesa = despesaService.save(despesaMapper.toEntity(despesaDTO));
            return ok(despesaMapper.toDto(despesa));
        }
        return status(HttpStatus.NOT_FOUND).body("nehuma despesa encontrada!");
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable BigInteger id){
        Optional<Despesa> despesa = despesaService.findById(id);
        if(despesa.isPresent()){
            despesaService.deleteById(despesa.get().getId());
            return ok().build();
        }
        return status(HttpStatus.NOT_FOUND).body("nehuma despesa encontrada!");
    }

    @GetMapping("/despesaPorTipo")
    public ResponseEntity<List<DespesaByTipoDto>> getDespesaByType(){
        List<DespesaByTipoDto> lista = new ArrayList<>();

        List result = despesaService.getDespesaByTipo();

        if(result.isEmpty())
            return notFound().build();

        for(Object item : result){
            DespesaByTipoDto despesaByTipoDto = new DespesaByTipoDto();
            var subitem = (Object[]) item;
            despesaByTipoDto.setTipoDespesa((TipoDespesa) subitem[0]);
            despesaByTipoDto.setValue(new BigDecimal(subitem[1].toString()));
            lista.add(despesaByTipoDto);
        }

        return ok(lista);
    }

//    @GetMapping("/importCSV")
//    public ResponseEntity<?> importCsv(){
//        String result = despesaService.importFromResource();
//        if(result.isEmpty()){
//            return ok("importacao relizada!");
//        };
//        return status(HttpStatus.BAD_REQUEST).body(result);
//    }

}
