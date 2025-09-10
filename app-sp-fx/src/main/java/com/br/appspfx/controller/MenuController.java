package com.br.appspfx.controller;

import com.br.appspfx.config.FxmlView;
import com.br.appspfx.config.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MenuController {

    private final StageManager stageManager;
    private final ApplicationEventPublisher eventPublisher;

    @Lazy
    public MenuController(StageManager stageManager, ApplicationEventPublisher eventPublisher) {
        this.stageManager = stageManager;
        this.eventPublisher = eventPublisher;
    }

    @FXML
    public void initialize() {}

    @FXML
    private void loadHomePanel(ActionEvent event) throws IOException {
        stageManager.switchToNextScene(FxmlView.HOME);
    }

    @FXML
    private void loadListDespesa(ActionEvent event) throws IOException {
        stageManager.switchToNextScene(FxmlView.DESPESAS);
    }

    @FXML
    private void exit(ActionEvent event) throws IOException {
        stageManager.exit();
    }
}

