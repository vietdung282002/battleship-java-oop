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
        BorderPane.setMargin(lblTop, new Insets(50, 0, 150, 0));
        BorderPane.setAlignment(lblTop, Pos.BOTTOM_CENTER);

        VBox root = new VBox();
        borderPane.setCenter(root);

        var btnReset = new Button("Reset");
        borderPane.setBottom(btnReset);
        BorderPane.setMargin(btnReset, new Insets(50, 0, 150, 0));
        BorderPane.setAlignment(btnReset, Pos.BOTTOM_CENTER);
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
