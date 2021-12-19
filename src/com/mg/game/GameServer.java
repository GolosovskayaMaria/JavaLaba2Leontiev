package com.mg.game;

public class GameServer {
    World world = new World(); // мир c сущностями
    int updateCounter = 0; // колчество обновлений сервера
    public void updateServer()
    {
        System.out.println("\n--- Day " + updateCounter + " is coming ---");
        world.updateWorld();
        updateCounter++;
    };
}

