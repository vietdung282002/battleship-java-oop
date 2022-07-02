package org.fxapps.battleship.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.fxapps.battleship.app.screens.HomeScreen;


public class AppEntryPoint extends Application {

    private static final int WIDTH = 900;
    private static final int HEIGHT = 1000;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        var factory = new ScreenManagerFactory();
        var manager = factory.newScreenManager(WIDTH, HEIGHT);
        var gameRoot = manager.root(); 
        var scene = new Scene(new StackPane(gameRoot), WIDTH, HEIGHT);
        scene.getStylesheets().add("battleship-style.css");
        stage.setScene(scene);
        stage.show();
        
        Runnable resize = () -> manager.resize(scene.getWidth(), scene.getHeight());
        scene.widthProperty().addListener(l -> resize.run());
        scene.heightProperty().addListener(l -> resize.run());        
    }

}