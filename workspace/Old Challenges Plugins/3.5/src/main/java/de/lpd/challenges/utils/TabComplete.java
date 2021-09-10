package de.lpd.challenges.utils;

import de.lpd.challenges.main.ChallengesMainClass;

import java.util.ArrayList;

public class TabComplete {

    private String[] beforeArgs;
    private ArrayList<String> pos;

    public TabComplete(ChallengesMainClass plugin, String[] beforeArgs, ArrayList<String> possebilitis) {
        this.beforeArgs = beforeArgs;
        this.pos = possebilitis;
    }

    public ArrayList<String> getPos() {
        return pos;
    }

    public String[] getBeforeArgs() {
        return beforeArgs;
    }

}