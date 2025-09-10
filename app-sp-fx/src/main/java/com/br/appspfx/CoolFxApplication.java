package com.br.appspfx;

import com.br.appspfx.config.FxmlView;
import com.br.appspfx.config.StageManager;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

public class CoolFxApplication extends Application {

    private ConfigurableApplicationContext context;
    private static Stage stage;
    private StageManager stageManager;

    @Override
    public void init() {
        context = new SpringApplicationBuilder(AppSpFxApplication.class).run();
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        stageManager = context.getBean(StageManager.class, this.stage);
        stageManager.switchScene(FxmlView.HOME);
    }

    @Override
    public void stop() {
        context.close();
        stage.close();
    }

}
