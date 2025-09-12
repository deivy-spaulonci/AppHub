package com.br.teste.producer;

import com.br.teste.dto.PagamentoDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagamentoRequestProdutor {
    @Autowired
    private AmqpTemplate amqpTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void integrarPagamento(PagamentoDTO pagamento) throws JsonProcessingException {
        System.out.println("PASSANDO PELO PAGAMENTO REQUEST PRODUTOR " + pagamento);
        amqpTemplate.convertAndSend(
                "pagamento-request-exchange",
                "pagamento-request-rout-key",
                objectMapper.writeValueAsString(pagamento));
    }
}
