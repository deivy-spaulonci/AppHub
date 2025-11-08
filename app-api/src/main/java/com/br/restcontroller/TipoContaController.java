package com.br.restcontroller;

import com.br.business.service.TipoContaService;
import com.br.dto.response.TipoContaResponseDTO;
import com.br.dto.request.TipoContaRequestDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<TipoContaResponseDTO> getTipoConta(Sort sort) {
        return tipoContaService.findTipoContas(sort);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TipoContaResponseDTO> createTipoConta(@RequestBody @Valid TipoContaRequestDTO tipoContaRequestDTO) {
        return new ResponseEntity<>(tipoContaService.save(tipoContaRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateTipoConta(@RequestBody @Valid  TipoContaRequestDTO tipoContaRequestDTO) {
        return new ResponseEntity<>(tipoContaService.save(tipoContaRequestDTO), HttpStatus.OK);
    }
}
