package org.fxapps.battleship.app.screens;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

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
