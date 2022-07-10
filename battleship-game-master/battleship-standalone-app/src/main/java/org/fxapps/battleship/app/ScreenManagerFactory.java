package org.fxapps.battleship.app;

import java.util.function.Consumer;

import org.fxapps.battleship.app.model.GamePreparationData;
import org.fxapps.battleship.app.screens.*;

public class ScreenManagerFactory {

    private ScreenManager screenManager;
    private Screen preparationScreen;
    private Screen LeaderboardScreen;
    private Screen homeScreen;
    private GameScreen gameScreen;

    public ScreenManager newScreenManager(double width, double height) {
        Runnable goToPreparation = () -> screenManager.goTo(preparationScreen.id());
        Consumer<GamePreparationData> preparationDataConsumer = gamePreparationData -> {
            gameScreen.setGamePreparationData(gamePreparationData);
            screenManager.goTo(gameScreen.id());
        };
        Runnable gotoLeaderboard = () ->screenManager.goTo(LeaderboardScreen.id());
        Runnable gotoHomeScreen = () ->screenManager.goTo(homeScreen.id());

        gameScreen = new GameScreen(goToPreparation);
        preparationScreen = new PreparationScreen(preparationDataConsumer);
        homeScreen = new HomeScreen(goToPreparation,gotoLeaderboard);
        LeaderboardScreen = new LeaderboardScreen(gotoHomeScreen);
        screenManager = new ScreenManager(width, height, homeScreen,LeaderboardScreen, preparationScreen, gameScreen);
        screenManager.home();

        return screenManager;
    }

}