package com.mg.game;

public class Main {

    public static void main(String[] args) {
        GameServer server = new GameServer();
        for(int i = 0; i< 25; i++)
            server.updateServer();
    }
}
