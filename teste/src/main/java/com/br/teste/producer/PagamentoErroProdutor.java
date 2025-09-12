package com.br.teste.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagamentoErroProdutor {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void gerarResposta(String mensagem){
        System.out.println("PASSANDO PELO PAGAMENTO ERROR PRODUTOR");
        amqpTemplate.convertAndSend(
                "pagamento-response-erro-exchange",
                "pagamento-response-erro-rout-key",
                mensagem);
    }
}

