package com.br.teste.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagamentoSucessoProdutor {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void gerarResposta(String mensagem){
        System.out.println("PASSANDO PELO PAGAMENTO SUCESSO PRODUTOR");
        amqpTemplate.convertAndSend(
                "pagamento-response-sucesso-exchange",
                "pagamento-response-sucesso-rout-key",
                mensagem);
    }
}
