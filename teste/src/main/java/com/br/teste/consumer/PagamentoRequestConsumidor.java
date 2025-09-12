package com.br.teste.consumer;

import com.br.teste.producer.PagamentoErroProdutor;
import com.br.teste.producer.PagamentoSucessoProdutor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component
public class PagamentoRequestConsumidor {
    @Autowired
    private PagamentoErroProdutor erroProdutor;
    @Autowired private PagamentoSucessoProdutor sucessoProdutor;

    @RabbitListener(queues = {"pagamento-request-queue"})
    public void receive(@Payload Message message) throws InterruptedException {
        System.out.println("Message " + message + "  " + LocalDateTime.now());
        String payload = String.valueOf(message.getPayload());
        System.out.println("OUVIU E PASSANDO PELO PAGAMENTO REQUEST CONSUMIDDOR");
        Thread.sleep(3000);
        if (new Random().nextBoolean()) {
            sucessoProdutor.gerarResposta("Recebido com SUCESSO: "+ payload);
        } else {
            erroProdutor.gerarResposta("Recebido com ERRO: "+ payload);
        }
    }
}
