package org.fxapps.battleship;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

import org.fxapps.battleship.model.BoardGame;
import org.fxapps.battleship.model.GameManager;
import org.fxapps.battleship.model.GameStats;
import org.fxapps.battleship.model.Player;
import org.fxapps.battleship.model.Ship;
import org.fxapps.battleship.model.ShipPosition;

public interface BattleshipGame {


    String getId();


    void addVerticalShip(Player player, Ship ship, int x, int y);


    void addHorizontalShip(Player player, Ship ship, int x, int y);


    void addShips(Player player, List<ShipPosition> shipsPositions);


    void removeShip(Player player, Ship ship);


    void ready(Player player);


    void unready(Player player);


    GameStats stats();

    boolean isReady(Player player);


    void guess(Player player, int x, int y, BiConsumer<Boolean, Boolean> result);


    void guess(Player player, int x, int y);

    Optional<Player> winningPlayer();


    void start();


    void abort();


    Optional<Player> playerTurn();

    Optional<Player> waitingPlayer();

    public static BattleshipGame create(Player player1, Player player2) {
        BoardGame boardGame = BoardGame.create(player1, player2);
        return GameManager.create(boardGame);
    }

}
