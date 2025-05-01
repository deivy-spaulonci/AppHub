package com.br.restcontroller;

import com.br.business.service.DespesaService;
import com.br.business.service.TipoContaService;
import com.br.dto.TipoContaDto;
import com.br.entity.*;
import com.br.mapper.DespesaMapper;
import com.br.mapper.TipoContaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

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

}
