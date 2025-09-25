package com.br.restcontroller;

import com.br.business.service.TipoDespesaService;
import com.br.dto.TipoDespesaDTO;
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

    private TipoDespesaService tipoDespesaService;

    @Autowired
    public TipoDespesaController(TipoDespesaService tipoDespesaService) {
        this.tipoDespesaService = tipoDespesaService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<TipoDespesaDTO> getTipoDespesa() {
        return tipoDespesaService.findTipoDespesas();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TipoDespesaDTO> saveTipoDespesa(@RequestBody @Valid TipoDespesaDTO tipoDespesaDto) {
        return new ResponseEntity<>(tipoDespesaService.save(tipoDespesaDto), HttpStatus.CREATED);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> update(@RequestBody @Valid TipoDespesaDTO tipoDespesaDto){
        return new ResponseEntity<>(tipoDespesaService.save(tipoDespesaDto),  HttpStatus.OK);
     }
}
