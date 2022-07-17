package org.fxapps.battleship.app.screens;

import java.io.File;
import java.io.IOException;
import java.lang.String;
import java.net.URISyntaxException;
import java.net.URL;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class HomeScreen implements Screen {

    private Runnable startAction;
    private Runnable startAction2;
    private BorderPane borderPane;
    private Label lblTop;

    public HomeScreen(Runnable startAction,Runnable startAction2  ) {
        super();
        this.startAction = startAction;
        this.startAction2=startAction2;
        init();
    }

    public void init() {
        String filePath="D:/dung/HUST/20212/OOP/battleship-apps/battleship-java-oop/battleship-game-master/battleship-app/src/main/resources/Sounds/background.wav";

        VBox root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(60);
        root.setPadding(new Insets(10,20, 10,10));

        var btnStart = new Button("Start");
        var btnScore = new Button("LeaderBoard");
        var btnMusic = new ToggleButton("Sound ON/OFF");


        lblTop = new Label("Battleship");
        borderPane = new BorderPane();
        lblTop.getStyleClass().add("lbl-app-title");
        lblTop.getStyleClass().add("normal-title");

        btnStart.getStyleClass().add("btn-start");
        btnScore.getStyleClass().add("btn-start");
        btnMusic.getStyleClass().add("btn-start");


        btnStart.setOnAction(e -> startAction.run());
        btnScore.setOnAction(e -> startAction2.run());

        borderPane.setTop(lblTop);
        borderPane.setCenter(root);

        root.getChildren().add(btnStart);
        root.getChildren().add(btnScore);
        root.getChildren().add(btnMusic);

        BorderPane.setMargin(lblTop, new Insets(50, 0, 100, 0));
        BorderPane.setAlignment(lblTop, Pos.BOTTOM_CENTER);
        BorderPane.setMargin(root, new Insets(25, 0, 50, 0));
        BorderPane.setAlignment(root, Pos.CENTER);

        try{
            File musicPath = new File(filePath);
            if(musicPath.exists()){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                btnMusic.setOnAction(event -> {
                    if (btnMusic.isSelected()) {
                        clip.stop();
                    }else {
                        clip.start();                    }
                });
                /*btnMusic.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        clip.stop();
                    }
                });*/
            }else {
                System.out.println("can't find file");
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public String id() {
        return "HOME";
    }

    @Override
    public Node content() {
        return borderPane;
    }

    @Override
    public String name() {
        return "Home";
    }
    @Override
    public void onShow() {
        // do nothing
    }

    @Override
    public void resize(double width, double height) {
        if (lblTop == null) return;
        if (width <= 300) {
            lblTop.getStyleClass().remove("title-small");
            lblTop.getStyleClass().remove("title-normal");
            lblTop.getStyleClass().add("title-smallest");
        }
        else if (width <= 530) {
            lblTop.getStyleClass().remove("title-smallest");
            lblTop.getStyleClass().remove("title-normal");
            lblTop.getStyleClass().add("title-small");
        }
       else {
           lblTop.getStyleClass().remove("title-smallest");
           lblTop.getStyleClass().remove("title-small");
           lblTop.getStyleClass().add("title-normal");
        }        
    }
}
