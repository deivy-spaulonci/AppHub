package com.br.restcontroller;

import com.br.business.service.TipoContaService;
import com.br.dto.TipoContaDto;
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

    @Autowired
    private TipoContaService tipoContaService;

    public static final TipoContaMapper tipoContaMapper = TipoContaMapper.INSTANCE;

    @GetMapping()
    public ResponseEntity<List<TipoContaDto>> getTipoConta(Sort sort) {
        List<TipoConta> result =  tipoContaService.findTipoContas(sort);
        if(result.isEmpty()){
            return noContent().build();
        }
        return ok(tipoContaMapper.toDtoList(result));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<TipoContaDto> createTipoConta(@RequestBody @Valid TipoContaDto tipoContaDto) {
        TipoConta tipoConta = tipoContaService.save(tipoContaMapper.toEntity(tipoContaDto));
        return ok(tipoContaMapper.toDto(tipoConta));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> updateTipoConta(@RequestBody TipoContaDto tipoContaDto) {
        Optional<TipoConta> tipoContaOptional = tipoContaService.findById(tipoContaDto.getId());
        if(tipoContaOptional.isPresent()){
            TipoConta tipoConta = tipoContaService.save(tipoContaMapper.toEntity(tipoContaDto));
            return ok(tipoContaMapper.toDto(tipoConta));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("nehum tipo de conta econtrado!");
    }
}
