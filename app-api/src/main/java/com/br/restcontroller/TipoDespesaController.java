package com.br.restcontroller;

import com.br.business.service.TipoDespesaService;
import com.br.dto.request.TipoDespesaRequestDTO;
import com.br.dto.response.TipoDespesaResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<TipoDespesaResponseDTO> getTipoDespesa() {
        return tipoDespesaService.findTipoDespesas();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TipoDespesaResponseDTO> saveTipoDespesa(@RequestBody @Valid TipoDespesaRequestDTO tipoDespesaRequestDTO) {
        return new ResponseEntity<>(tipoDespesaService.save(tipoDespesaRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> update(@RequestBody @Valid TipoDespesaRequestDTO tipoDespesaRequestDTO){
        return new ResponseEntity<>(tipoDespesaService.save(tipoDespesaRequestDTO),  HttpStatus.OK);
     }
}
