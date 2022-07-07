package org.fxapps.battleship.app.screens;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.fxapps.battleship.app.model.GamePreparationData;
import org.fxapps.battleship.app.utils.BattleshipPainter;
import org.fxapps.battleship.bot.impl.BattleshipRandomBot;
import org.fxapps.battleship.bot.impl.CheaterBattleshipBot;
import org.fxapps.battleship.model.Board;
import org.fxapps.battleship.model.BoardGame;
import org.fxapps.battleship.model.GameManager;
import org.fxapps.battleship.model.GameState;
import org.fxapps.battleship.model.Location;
import org.fxapps.battleship.model.Player;
import org.fxapps.battleship.model.ShipPosition;

public class GameScreen implements Screen {

    private static final Image IMG_TARGET = new Image("/images/target.png");

    GamePreparationData gamePreparationData;

    Canvas player2Canvas;
    Canvas playerCanvas;
    SimpleBooleanProperty isPlayerTurnProperty = new SimpleBooleanProperty(false);
    SimpleObjectProperty<Location> targetLocationProperty = new SimpleObjectProperty<>();
    SimpleObjectProperty<GameState> gameStateProperty = new SimpleObjectProperty<>();

    private GameManager manager;

    private BattleshipRandomBot player2;

    private Player player;

    private Label lblPlayerGuessResult;

    private Timeline playerHitAnimation;

    private Timeline botHitAnimation;

    private List<ShipPosition> botShips;

    private StackPane root;

    private Runnable homeScreenCallback;

    private Label lblBotGuessResult;

    private Labeled lblEndTitle;

    private Labeled lblTime;

    private Labeled lblHits;

    private Labeled lblMisses;

    private Labeled lblScore;
    private Text PlayerName;

    private Text Player2Name;

    private Button btnFire;

    public GameScreen(Runnable homeScreenCallback) {
        this.homeScreenCallback = homeScreenCallback;
        buildUI();
    }

    @Override
    public String id() {
        return "game";
    }

    @Override
    public Node content() {
        return root;
    }

    @Override
    public String name() {
        return "";
    }

    @Override
    public void onShow() {
        cleanUp();
        prepareBattle();
        paintBoards();
    }

    @Override
    public void resize(double width, double height) {
        playerCanvas.setWidth(width/2-40);
        playerCanvas.setHeight(height - (height / 4) - 30);
        player2Canvas.setWidth(width/2-40);
        player2Canvas.setHeight(height - (height / 4) - 30);
        btnFire.setMinSize(width/20, height / 20);


        if (this.manager != null) {
            paintBoards();
        }
    }

    public void setGamePreparationData(GamePreparationData gamePreparationData) {
        this.gamePreparationData = gamePreparationData;
    }

    private void buildUI() {

        var vbox = new VBox();
        vbox.setSpacing(30);
        vbox.setAlignment(Pos.CENTER);
        var vbGameOverOverlay = new VBox(40);
        var btnNewGame = new Button("New Game");

        btnFire = new Button("Fire");
        lblEndTitle = new Label("You Lose!");
        lblHits = new Label("Total Hits: 20");
        lblMisses = new Label("Total Miss: 30");
        lblTime = new Label("Time: 30 minutes");
        lblScore = new Label("Score");
        player2Canvas = new Canvas(800, 800);
        playerCanvas = new Canvas(800, 800);
        lblPlayerGuessResult = new Label();
        lblBotGuessResult = new Label();

        playerCanvas.getStyleClass().add("game-canvas");
        player2Canvas.getStyleClass().add("game-canvas");
        player2Canvas.setOnMouseClicked(this::updateTarget);
        player2Canvas.setOnTouchReleased(e -> this.updateTarget(null));

        playerHitAnimation = buildTransitions(lblPlayerGuessResult, () -> {
            targetLocationProperty.set(null);
            botGuess();
        });
        botHitAnimation = buildTransitions(lblBotGuessResult, this::paintBoards);
        lblBotGuessResult.getStyleClass().add("lbl-botguessInfo");

        lblPlayerGuessResult.getStyleClass().add("lbl-playerguessInfo");
        lblPlayerGuessResult.setOpacity(0.0);
        lblBotGuessResult.setOpacity(0.0);

        btnFire.disableProperty().bind(targetLocationProperty.isNull().or(isPlayerTurnProperty.not()));

        btnFire.setMinSize(player2Canvas.getWidth()/2, 70);

        btnFire.setContentDisplay(ContentDisplay.RIGHT);
        btnFire.getStyleClass().add("btn-fire");
        btnFire.setOnMouseClicked(e -> playerGuess());

        lblEndTitle.getStyleClass().add("lbl-end-winner");
        vbGameOverOverlay.getStyleClass().add("vb-end");
        btnNewGame.setOnAction(e -> homeScreenCallback.run());
        vbGameOverOverlay.getChildren().addAll(lblEndTitle, lblTime, lblHits, lblMisses,lblScore, btnNewGame);
        vbGameOverOverlay.setAlignment(Pos.CENTER);
        vbGameOverOverlay.setMaxSize(500, 500);


        var playerBoardContainer = new StackPane(player2Canvas, lblPlayerGuessResult);

        VBox.setMargin(lblEndTitle, new Insets(5, 0, 10, 0));
        VBox.setMargin(playerBoardContainer, new Insets(5, 0, 0, 0));

        var Rec1 = new Rectangle(player2Canvas.getWidth()-5,50);
        var Rec2 = new Rectangle(player2Canvas.getWidth()-5,50);


        PlayerName =new Text("PlayerName");
        Player2Name = new Text("Computer");



        var hbName = new HBox(50,
                new StackPane(Rec1, PlayerName),
                new StackPane(Rec2, Player2Name));

        hbName.setAlignment(Pos.CENTER);

        Rec1.setFill(Color.GREENYELLOW);
        Rec2.setFill(Color.GREENYELLOW);
        Rec1.setArcHeight(105);
        Rec1.setArcWidth(50);
        Rec2.setArcHeight(105);
        Rec2.setArcWidth(50);

        PlayerName.getStyleClass().add("text-name");
        Player2Name.getStyleClass().add("text-name");

        var hbGame = new HBox(30,
                new StackPane(playerCanvas, lblBotGuessResult),
                playerBoardContainer
        );
        hbGame.setAlignment(Pos.CENTER);

        vbox.getChildren().add(hbName);
        vbox.getChildren().add(hbGame);
        vbox.getChildren().add(btnFire);


        StackPane.setAlignment(vbGameOverOverlay, Pos.TOP_CENTER);

        root = new StackPane(vbox, vbGameOverOverlay);

        hbGame.disableProperty().bind(playerHitAnimation.statusProperty()
                .isEqualTo(Status.RUNNING)
                .or(botHitAnimation.statusProperty().isEqualTo(Status.RUNNING))
                .or(gameStateProperty.isNotEqualTo(GameState.STARTED)));
        playerCanvas.disableProperty().bind(hbGame.disableProperty());
        vbGameOverOverlay.visibleProperty().bind(gameStateProperty.isEqualTo(GameState.FINISHED));

        gameStateProperty.addListener(l -> {
            if (gameStateProperty.get() == GameState.FINISHED) {
                updateLabels();
            }
            paintBoards();
        });

    }

    private void updateLabels() {
        var stats = manager.stats();
        var totalSeconds = stats.getStarted().until(LocalDateTime.now(), ChronoUnit.SECONDS);
        var totalMinutes = stats.getStarted().until(LocalDateTime.now(), ChronoUnit.MINUTES);
        var guesses = stats.getGuesses().get(player);
        var isWin = stats.getWinner().get() == player;
        var wrongguess = guesses.stream().filter(g -> !g.isHit()).count();
        lblEndTitle.setText(isWin ? "You Win!" : "You lose!");
        lblEndTitle.getStyleClass().clear();
        lblEndTitle.getStyleClass().add(isWin ? "lbl-end-winner" : "lbl-end-loser");
        lblTime.setText("Time: " + totalMinutes + "m " + totalSeconds + "s");
        lblHits.setText("Total Guesses: " + guesses.size());
        lblMisses.setText("Wrong Guesses: " + wrongguess);
        var scr = (guesses.size()-wrongguess)*100-wrongguess*20;
        if (scr<0)scr=0;
        lblScore.setText("YOUR SCORE: "+ scr);

    }

    private Timeline buildTransitions(Node target, Runnable onFinished) {
        Timeline animation = new Timeline(new KeyFrame(Duration.millis(100),
                new KeyValue(target.opacityProperty(), 1.0),
                new KeyValue(target.scaleXProperty(), 5),
                new KeyValue(target.scaleYProperty(), 5)),
                new KeyFrame(Duration.millis(500)),
                new KeyFrame(Duration.millis(600),
                        new KeyValue(target.opacityProperty(), 0)));
        animation.setOnFinished(evt -> {
            target.setScaleX(1);
            target.setScaleY(1);
            paintBoards();
            onFinished.run();
        });
        return animation;
    }

    private void cleanUp() {
        targetLocationProperty.set(null);
    }

    private void prepareBattle() {
        botShips = Board.randomShips().getShipsPositions();
        player = Player.create("user");
        player2 = new CheaterBattleshipBot(gamePreparationData.getDifficult().getHitProbability(),
                gamePreparationData.getShipsPositions());
        manager = GameManager.create(BoardGame.create(player, player2));
        manager.addShips(player2, botShips);
        manager.addShips(player, gamePreparationData.getShipsPositions());
        manager.ready(player);
        manager.ready(player2);
        manager.start();
        manager.playerTurn().ifPresent(p -> isPlayerTurnProperty.set(p == player));
        if (!isPlayerTurnProperty.get()) {
            botGuess();
        }
        gameStateProperty.set(manager.state());
    }

    private void paintBoards() {
        paintPlayerBoard();
        paintPlayer2Board();
    }

    private void updateTarget(MouseEvent e) {
        var playerGuesses = manager.stats().getGuesses().get(player);
        var tileWidth = player2Canvas.getWidth() / Board.DEFAULT_COLS;
        var tileHeight = player2Canvas.getHeight() / Board.DEFAULT_ROWS;
        var location = BattleshipPainter.getLocationOnBoard(e);
        var ctx = player2Canvas.getGraphicsContext2D();

        boolean guessAlreadyMade = playerGuesses.stream().anyMatch(guess -> guess.getLocation().equals(location));
        if (!guessAlreadyMade) {
            paintPlayer2Board();
            ctx.drawImage(IMG_TARGET,
                    location.x() * tileWidth,
                    location.y() * tileHeight,
                    tileWidth,
                    tileHeight);
            targetLocationProperty.set(location);
            if (e.getClickCount() == 2) {
                playerGuess();
            }
        }

    }

    private void paintPlayerBoard() {
        var player2Guesses = manager.stats().getGuesses().get(player2);
        BattleshipPainter.paintBoard(playerCanvas, gamePreparationData.getShipsPositions(), player2Guesses,Color.STEELBLUE);

    }

    private void paintPlayer2Board() {
        List<ShipPosition> possibleBotShips = manager.state() == GameState.FINISHED ? botShips : Collections.emptyList();
        var playerGuesses = manager.stats().getGuesses().get(player);
        BattleshipPainter.paintBoard(player2Canvas, possibleBotShips, playerGuesses,Color.STEELBLUE);

    }

    private void playerGuess() {
        var location = targetLocationProperty.get();
        manager.guess(player, location.x(), location.y(), (hit, sink) -> {
            updateLabelText(lblPlayerGuessResult, hit, sink);
            playerHitAnimation.stop();
            playerHitAnimation.play();
            gameStateProperty.set(manager.state());
        });
    }

    private void botGuess() {
        if (manager.state() != GameState.FINISHED) {
            Location location = player2.newLocation();
            manager.guess(player2, location.x(), location.y(), (hit, sink) -> updateLabelText(lblBotGuessResult, hit, sink));
            isPlayerTurnProperty.set(true);
            botHitAnimation.stop();
            botHitAnimation.play();
            gameStateProperty.set(manager.state());
        }
        paintBoards();
    }

    private void updateLabelText(Label target, Boolean hit, Boolean sink) {
        if (Boolean.TRUE.equals(hit)) {
            target.setText("Hit" + (Boolean.TRUE.equals(sink) ? "\nand Sunk" : ""));
        } else {
            target.setText("Miss");
        }
    }

}
