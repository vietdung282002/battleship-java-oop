package org.fxapps.battleship.app.screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.fxapps.battleship.app.screens.Screen;

public class LeaderboardScreen implements Screen {
    private BorderPane borderPane;
    private Label lblTop;
    private Runnable homeScreenCallback;

    public LeaderboardScreen(Runnable homeScreenCallback) {
        this.homeScreenCallback = homeScreenCallback;
        init();
    }

    public void init(){
        borderPane= new BorderPane();
        lblTop = new Label("LeaderBoard");
        lblTop.getStyleClass().add("lbl-app-title");
        lblTop.getStyleClass().add("normal-title");
        borderPane.setTop(lblTop);
        BorderPane.setMargin(lblTop, new Insets(50, 0, 100, 0));
        BorderPane.setAlignment(lblTop, Pos.BOTTOM_CENTER);



        Label first=new Label("1. ");
        Label second=new Label("2. ");
        Label third=new Label("3. ");
        Label fourth=new Label("4. ");
        Label fifth=new Label("5. ");

        first.getStyleClass().add("lbl-score");
        second.getStyleClass().add("lbl-score");
        third.getStyleClass().add("lbl-score");
        fourth.getStyleClass().add("lbl-score");
        fifth.getStyleClass().add("lbl-score");


        VBox root = new VBox(20,first,second,third,fourth,fifth);

        borderPane.setCenter(root);

        BorderPane.setAlignment(root, Pos.CENTER);

        var btnReset = new Button("Reset");
        btnReset.getStyleClass().add("btn-reset");
        borderPane.setBottom(btnReset);
        BorderPane.setMargin(btnReset, new Insets(50, 0, 100, 0));
        BorderPane.setAlignment(btnReset, Pos.BOTTOM_CENTER);


        var btnexit = new Button("Exit");
        borderPane.setCenter(btnexit);
        BorderPane.setAlignment(btnexit, Pos.BOTTOM_CENTER);
        btnexit.setOnAction(e -> homeScreenCallback.run());
    }


    @Override
    public String id() {
        return "LEADERBOARD";
    }
    @Override
    public Node content() { return borderPane; }
    @Override
    public String name() {
        return "";
    }
    @Override
    public void onShow() {



    }
}