package com.br.restcontroller;

import com.br.business.service.FormaPagamentoService;
import com.br.dto.FormaPagamentoDTO;
import com.br.entity.FormaPagamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/forma-pagamento")
public class FormaPagamentoController {

    private FormaPagamentoService formaPagamentoService;

    @Autowired
    public FormaPagamentoController(FormaPagamentoService formaPagamentoService) {
        this.formaPagamentoService = formaPagamentoService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<FormaPagamentoDTO> getFormaPagamento() {
        return formaPagamentoService.findFormasPagamento();
    }
}
