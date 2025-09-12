package com.br.appspfx.config;

import javafx.stage.Stage;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class ApplicationConfig {

    private final FxmlLoader fxmlLoader;
    private final ApplicationEventPublisher eventPublisher;

    public ApplicationConfig(FxmlLoader fxmlLoader, ApplicationEventPublisher eventPublisher) {
        this.fxmlLoader = fxmlLoader;
        this.eventPublisher = eventPublisher;
    }

    @Bean
    @Lazy
    public StageManager stageManager(Stage stage) {
        return new StageManager(fxmlLoader, stage, eventPublisher);
    }
}
