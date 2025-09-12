package com.br.teste.api;

import com.br.teste.dto.PagamentoDTO;
import com.br.teste.service.PagamentoFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoAPI {

    @Autowired
    private PagamentoFacade pagamentoFacade;

    @PostMapping
    public String processar (@RequestBody PagamentoDTO request) {
        System.out.println("PASSANDO PELA API...");
        return pagamentoFacade.solicitarPagamento(request);
    }
}
