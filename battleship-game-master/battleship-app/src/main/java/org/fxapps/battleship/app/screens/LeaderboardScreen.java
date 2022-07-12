package org.fxapps.battleship.app.screens;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.fxapps.battleship.app.screens.Screen;
import org.fxapps.battleship.app.score.leaderboard;

public class LeaderboardScreen implements Screen {
    private BorderPane borderPane;
    private Label lblTop;
    private Runnable homeScreenCallback;
    private leaderboard LDB;
    private Label first;
    private Label second;
    private Label third;
    private Label fourth;
    private Label fifth;

    public LeaderboardScreen(Runnable homeScreenCallback) {
        this.homeScreenCallback = homeScreenCallback;
        init();
    }

    public void init(){
        LDB=leaderboard.getInstance();
        LDB.loadScore();
        borderPane= new BorderPane();
        lblTop = new Label("LeaderBoard");
        lblTop.getStyleClass().add("lbl-app-title");
        lblTop.getStyleClass().add("normal-title");
        borderPane.setTop(lblTop);
        BorderPane.setMargin(lblTop, new Insets(50, 0, 100, 0));
        BorderPane.setAlignment(lblTop, Pos.BOTTOM_CENTER);

        first=new Label("1. "+LDB.getScore(0) );
        second=new Label("2. "+LDB.getScore(1));
        third=new Label("3. "+LDB.getScore(2));
        fourth=new Label("4. "+LDB.getScore(3) );
        fifth=new Label("5. "+LDB.getScore(4));

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

        btnReset.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                reset();
                LDB.createSaveData();
            }
        });
        updatelabel();
    }

    public void reset(){
        first.setText("1. 0");
        second.setText("2. 0");
        third.setText("3. 0");
        fourth.setText("4. 0");
        fifth.setText("5. 0");
    }

    public void updatelabel(){
        LDB.loadScore();
        first.setText("1. "+LDB.getScore(0));
        second.setText("2. "+LDB.getScore(1));
        third.setText("3. "+LDB.getScore(2));
        fourth.setText("4. "+LDB.getScore(3));
        fifth.setText("5. "+LDB.getScore(4));
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
