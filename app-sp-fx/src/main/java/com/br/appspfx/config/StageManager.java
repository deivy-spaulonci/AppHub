package com.br.appspfx.config;

import com.br.appspfx.event.SceneResizeEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class StageManager {

    private final Stage primaryStage;
    private final FxmlLoader fxmlLoader;
    private final ApplicationEventPublisher eventPublisher;
    private final int LARG = 1850;
    private final int HEIG = 1050;

    public StageManager(FxmlLoader fxmlLoader, Stage primaryStage, ApplicationEventPublisher eventPublisher) {
        this.primaryStage = primaryStage;
        this.fxmlLoader = fxmlLoader;
        this.eventPublisher = eventPublisher;
    }

    public void switchScene(final FxmlView view) {
        primaryStage.setMinWidth(LARG);
        primaryStage.setMinHeight(HEIG);
        primaryStage.setTitle("App Sp FX");

        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        Parent rootNode = loadRootNode(view.getFxmlPath());

        Scene scene = new Scene(rootNode);
        String stylesheet = Objects.requireNonNull(getClass().getResource("/css/main.css")).toExternalForm();
        scene.getStylesheets().add(stylesheet);
        scene.widthProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(
                    ObservableValue<? extends Number> observableValue,
                    Number oldSceneWidth,
                    Number newSceneWidth) {
                eventPublisher.publishEvent(new SceneResizeEvent(this, newSceneWidth));
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void switchToNextScene(final FxmlView view) {
        Parent rootNode = loadRootNode(view.getFxmlPath());
        primaryStage.getScene().setRoot(rootNode);
        primaryStage.show();
    }


    private Parent loadRootNode(String fxmlPath) {
        Parent rootNode;
        try {
            rootNode = fxmlLoader.load(fxmlPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rootNode;
    }

//    public void switchToFullScreenMode() {
//        primaryStage.setFullScreen(true);
//    }
//
//    public void switchToWindowedMode() {
//        primaryStage.setFullScreen(false);
//    }
//
//    public boolean isStageFullScreen() {
//        return primaryStage.isFullScreen();
//    }

    public void exit() {
        primaryStage.close();
    }

}
