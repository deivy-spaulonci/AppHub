package com.br.teste.service;

import com.br.teste.dto.PagamentoDTO;
import com.br.teste.producer.PagamentoRequestProdutor;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagamentoFacade {
    @Autowired
    private PagamentoRequestProdutor produtor;

    public String solicitarPagamento (PagamentoDTO pagamento) {
        try {
            produtor.integrarPagamento(pagamento);
            System.out.println("PASSANDO PELO PAGAMENTO FACADE!!");
        } catch (JsonProcessingException e) {
            return "Erro ao processar pagamento";
        }
        return "Seu pagamento está sendo processado...";
    }

    public void sucessoPagamento(String payload) {
        System.out.println("O pagamento foi confirmado: " + payload);
    }

    public void erroPagamento(String payload) {
        System.err.println("Erro ao processar pagamento " + payload);
    }
}
