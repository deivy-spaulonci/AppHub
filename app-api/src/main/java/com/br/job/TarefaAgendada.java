package com.br.job;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class TarefaAgendada {
    @Scheduled(fixedRate = 10000) // Executa a cada 5 segundos
    public void executarTarefa() {
        log.info("Tarefa executada em: " + System.currentTimeMillis());
        // Coloque aqui a lógica da sua tarefa
    }
}
