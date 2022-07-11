package org.fxapps.battleship.app.screens;

import java.lang.String;
import java.net.URL;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


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
        VBox root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(60);
        root.setPadding(new Insets(15,20, 10,10));

        var btnStart = new Button("Start");
        var btnScore = new Button("LeaderBoard");
        var btnMusic = new Button("Music");


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

        BorderPane.setMargin(lblTop, new Insets(50, 0, 150, 0));
        BorderPane.setAlignment(lblTop, Pos.BOTTOM_CENTER);
        BorderPane.setMargin(root, new Insets(25, 0, 50, 0));
        BorderPane.setAlignment(root, Pos.CENTER);

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
