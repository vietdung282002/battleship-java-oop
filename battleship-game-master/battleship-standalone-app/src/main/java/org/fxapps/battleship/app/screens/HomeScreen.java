package org.fxapps.battleship.app.screens;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.*;
import java.util.Optional;


public class HomeScreen implements Screen {


    private Runnable startAction;
    private BorderPane borderPane;
    private Label lblTop;

    public HomeScreen(Runnable startAction) {
        super();
        this.startAction = startAction;
        init();
    }

    public void ShowInput(){
        TextInputDialog inputname = new TextInputDialog();
        inputname.setHeaderText("Enter your name:");
        inputname.setContentText("Name:");
        Optional<String> result = inputname.showAndWait();
        result.ifPresent(name->{
            File file = new File("C:/Users/huyho/battleship-java-oop/name.txt");

            file.getParentFile().mkdirs();
            try {
                PrintWriter writer = new PrintWriter(file);
                writer.print("");
                writer.close();
                FileWriter fileWriter = new FileWriter(file,true);
                fileWriter.write(name);
                startAction.run();
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }

    public void init() {
        VBox root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(60);
        root.setPadding(new Insets(15,20, 10,10));

        var btnStart = new Button("Start");

        lblTop = new Label("Battleship");
        borderPane = new BorderPane();
        lblTop.getStyleClass().add("lbl-app-title");
        lblTop.getStyleClass().add("normal-title");
        btnStart.getStyleClass().add("btn-start");


        //btnStart.setOnAction(e -> startAction.run());
        borderPane.setTop(lblTop);
        borderPane.setCenter(root);
        root.getChildren().add(btnStart);

        BorderPane.setMargin(lblTop, new Insets(50, 0, 150, 0));
        BorderPane.setAlignment(lblTop, Pos.BOTTOM_CENTER);
        BorderPane.setMargin(root, new Insets(25, 0, 50, 0));
        BorderPane.setAlignment(root, Pos.CENTER);
        btnStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ShowInput();
            }
        });
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
