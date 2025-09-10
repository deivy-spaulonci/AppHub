package com.br.appspfx.config;

import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.io.IOException;

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
    public StageManager stageManager(Stage stage) throws IOException {
        return new StageManager(fxmlLoader, stage, eventPublisher);
    }
}
