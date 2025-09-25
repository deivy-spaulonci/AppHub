package com.br.restcontroller;

import com.br.business.service.TipoContaService;
import com.br.dto.TipoContaDTO;
import com.br.entity.TipoConta;
import com.br.mapper.TipoContaMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/tipo-conta")
public class TipoContaController {

    private TipoContaService tipoContaService;

    @Autowired
    public TipoContaController(TipoContaService tipoContaService) {
        this.tipoContaService = tipoContaService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<TipoContaDTO> getTipoConta(Sort sort) {
        return tipoContaService.findTipoContas(sort);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TipoContaDTO> createTipoConta(@RequestBody @Valid TipoContaDTO tipoContaDto) {
        return new ResponseEntity<>(tipoContaService.save(tipoContaDto), HttpStatus.CREATED);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateTipoConta(@RequestBody TipoContaDTO tipoContaDto) {
        return new ResponseEntity<>(tipoContaService.save(tipoContaDto), HttpStatus.OK);
    }
}
