package com.br.restcontroller;

import com.br.business.service.TipoDespesaService;
import com.br.dto.TipoDespesaDto;
import com.br.entity.TipoDespesa;
import com.br.mapper.TipoDespesaMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/tipo-despesa")
public class TipoDespesaController {

    @Autowired
    private TipoDespesaService tipoDespesaService;

    public static final TipoDespesaMapper tipoDespesaMapper = TipoDespesaMapper.INSTANCE;

    @GetMapping()
    public ResponseEntity<List<TipoDespesaDto>> getTipoDespesa() {
        List<TipoDespesa> result =  tipoDespesaService.findTipoDespesas();
        if(result.isEmpty()){
            return noContent().build();
        }
        return ok(tipoDespesaMapper.toDtoList(result));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<TipoDespesaDto> saveTipoDespesa(@RequestBody @Valid TipoDespesaDto tipoDespesaDto) {
        TipoDespesa tipoDespesa = tipoDespesaService.save(tipoDespesaMapper.toEntity(tipoDespesaDto));
        return ok(tipoDespesaMapper.toDto(tipoDespesa));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> update(@RequestBody @Valid TipoDespesaDto tipoDespesaDto){
        Optional<TipoDespesa> tipoDespesaOptional = tipoDespesaService.findById(tipoDespesaDto.getId());
        if(tipoDespesaOptional.isPresent()){
            TipoDespesa tipoDespesa = tipoDespesaService.save(tipoDespesaMapper.toEntity(tipoDespesaDto));
            return ok(tipoDespesaMapper.toDto(tipoDespesa));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("nenhum tipo de despesa econtrado!");
     }
}
