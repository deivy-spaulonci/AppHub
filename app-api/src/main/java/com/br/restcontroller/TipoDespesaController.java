package com.br.restcontroller;

import com.br.business.service.TipoDespesaService;
import com.br.dto.TipoContaDto;
import com.br.dto.TipoDespesaDto;
import com.br.entity.TipoConta;
import com.br.entity.TipoDespesa;
import com.br.mapper.TipoContaMapper;
import com.br.mapper.TipoDespesaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
