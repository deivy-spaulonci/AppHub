package com.br.restcontroller;

import com.br.business.service.TipoContaService;
import com.br.dto.request.create.TipoContaCreateRequestDTO;
import com.br.dto.request.update.TipoContaUpdateRequestDTO;
import com.br.dto.response.TipoContaResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<TipoContaResponseDTO> createTipoConta(@RequestBody @Valid TipoContaCreateRequestDTO tipoContaCreateRequestDTO) {
        return new ResponseEntity<>(tipoContaService.save(tipoContaCreateRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> update(@RequestBody @Valid TipoContaUpdateRequestDTO tipoContaUpdateRequestDTO) {
        return new ResponseEntity<>(tipoContaService.update(tipoContaUpdateRequestDTO), HttpStatus.OK);
    }
}
