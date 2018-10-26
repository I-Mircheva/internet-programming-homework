package org.elsys.netprog.model;

import java.util.ArrayList;

public class Game {

    private String id;
    private Integer secret;
    private Integer turnsCount = 0;
    private Boolean success = false;

    public Game() {
        id = RandomGenerator.idGen();
        secret = RandomGenerator.secretGen();
    }

    public ArrayList<Integer> checkBullsAndCows(Integer number) {
        ArrayList<Integer> bullsAndCows = new ArrayList<>();
        Integer bulls = 0;
        Integer cows = 0;

        String guess = number.toString();
        String secret = getSecret().toString();
        Integer index = 0;

        for (char single : secret.toCharArray()) {
            if (guess.charAt(index) == single) {
                bulls ++;
            }else if (guess.contains(String.valueOf(single))) {
                cows++;
            }
            index++;
        }

        if (bulls == 4) {
            setSuccess(true);
        }

        bullsAndCows.add(bulls);
        bullsAndCows.add(cows);

        return bullsAndCows;
    }

    public void setTurnsCount(Integer turnsCount) {
        this.turnsCount = turnsCount;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getId() {
        return id;
    }

    public Integer getSecret() {
        return secret;
    }

    public Integer getTurnsCount() {
        return turnsCount;
    }

    public Boolean getSuccess() {
        return success;
    }
}
