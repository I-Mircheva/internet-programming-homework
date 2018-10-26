package org.elsys.netprog.model;

import java.util.ArrayList;

public class GameBank {

    private ArrayList<Game> allGames;

    public GameBank () {
        allGames = new ArrayList<>();
    }

    public Game getGameById(String id) {
        return allGames.stream()
                .filter(game -> id.equals(game.getId()))
                .findAny()
                .orElse(null);
    }

    public ArrayList<Game> getAllGames() {
        return allGames;
    }

    public void addGame(Game game) {
        allGames.add(game);
    }
}
