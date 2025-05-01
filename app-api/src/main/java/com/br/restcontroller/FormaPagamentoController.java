package com.br.restcontroller;

import com.br.business.service.FormaPagamentoService;
import com.br.dto.FormaPagamentoDto;
import com.br.dto.TipoDespesaDto;
import com.br.entity.FormaPagamento;
import com.br.entity.TipoDespesa;
import com.br.mapper.FormaPagamentoMapper;
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
@RequestMapping("/api/v1/forma-pagamento")
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    public static final FormaPagamentoMapper formaPagamentoMapper = FormaPagamentoMapper.INSTANCE;

    @GetMapping()
    public ResponseEntity<List<FormaPagamentoDto>> getFormaPagamento() {
        List<FormaPagamento> result =  formaPagamentoService.findFormasPagamento();
        if(result.isEmpty()){
            return noContent().build();
        }
        return ok(formaPagamentoMapper.toDtoList(result));
    }
}
