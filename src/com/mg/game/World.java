package com.mg.game;

import java.util.Iterator;
import java.util.LinkedList;

public class World {
    LinkedList<Entity> entities;

    World() {
        entities = new LinkedList<Entity>();
        EntityMonster amoeba = new EntityMonster();
        amoeba.title = "Amoeba";
        entities.add(amoeba);
        EntityMonster telepuzik = new EntityMonster();
        telepuzik.title = "Telepuzik";
        entities.add(telepuzik);
        EntityGuard marly = new EntityGuard();
        marly.title = "Marly";
        entities.add(marly);
        EntityPlayer maria = new EntityPlayer();
        maria.title = "Maria";
        entities.add(maria);
        EntityPlayer ksenia = new EntityPlayer();
        ksenia.title = "Ksenia";
        entities.add(ksenia);
    }

    public void updateWorld() {
        Iterator<Entity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            Entity object = iterator.next();
            System.out.println("--- update object " + object.title);

//      Передвинуть обьект ближе к ближайшему EntityPlayer
            if (!(object instanceof EntityPlayer)) {
                // если это не игрок то найдем ближайшего игрока и переместимся к нему на 1
                LinkedList<EntityPlayer> players = getPlayersNearEntity(object, 10);
                if(players.size()>0) {
                    EntityPlayer nearestPlayer = getNearestPlayer(object, players);
                    System.out.println("NearestPlayer for " + object.title + " is " + nearestPlayer.title);
                    object.move_to(nearestPlayer.xPos, nearestPlayer.yPos);
                } else
                {
                    System.out.println(object.title + " has no players in range 10");
                }

            }

            // обновить сущность мира
            object.update();

            // monster атакует игрока в радисе 2ух клеток
            if (object instanceof EntityMonster) { // атаковать могут только монтсры
                // атаковать игрока
                LinkedList<EntityPlayer> players = getPlayersNearEntity(object, 2);
                if(players.size()==0) {
                    System.out.println("Monster has no players for attack ");
                }
                else {
                        EntityPlayer nearestPlayer = getNearestPlayer(object, players);
                        System.out.println(object.title + " can attack " + nearestPlayer.title);

                        LinkedList<EntityGuard> guards = getGuardiansInRegion(object.xPos, object.yPos, 2);
                        nearestPlayer.set_guards(guards);

                        if (nearestPlayer.attackEntityFrom(object, 1) == true)
                            System.out.println("Oh " + nearestPlayer.title + " die");
                }
            }
        }

        // удалить из мира если кончилось здоровье
        Iterator<Entity> iterator1 = entities.iterator();
        while (iterator1.hasNext()) {
            Entity object = iterator1.next();
            if (object.health <= 0) {
                iterator1.remove();
            }
        }
    }

    public LinkedList<Entity> getEntitiesNearEntity(Entity entity, double range) {
        return getEntitiesInRegion(entity.xPos, entity.yPos, range);
    }

    public LinkedList<EntityPlayer> getPlayersNearEntity(Entity entity, double range) {
        // get all
        LinkedList<Entity> allObjects = getEntitiesNearEntity(entity, range);
        LinkedList<EntityPlayer> allPalyers = new LinkedList<EntityPlayer>();
        // remove not players
        Iterator<Entity> iterator = allObjects.iterator();
        while (iterator.hasNext()) {
            Entity object = iterator.next();
            if (object instanceof EntityPlayer) {
                allPalyers.add((EntityPlayer) object);
            }
        }
        return allPalyers;
    }

    public LinkedList<EntityGuard> getGuardiansInRegion(int x, int y, double range)
    {
        // get all
        LinkedList<Entity> allObjects = getEntitiesInRegion(x, y, range);
        LinkedList<EntityGuard> allGuards = new LinkedList<EntityGuard>();
        // remove not players
        Iterator<Entity> iterator = allObjects.iterator();
        while (iterator.hasNext()) {
            Entity object = iterator.next();
            if (object instanceof EntityGuard) {
                allGuards.add((EntityGuard) object);
            }
        }
        return allGuards;
    }

    public LinkedList<Entity> getEntitiesInRegion(int x, int y, double range) {
        LinkedList<Entity> in_range = new LinkedList<Entity>();
        Iterator<Entity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            Entity next = iterator.next();
            double distance = calculateDistanceBetweenPoints(x, y, next.xPos, next.yPos);
            if (distance < range)
                in_range.add(next);
        }
        return in_range;
    }

    public EntityPlayer getNearestPlayer(Entity entity, LinkedList<EntityPlayer> players) {
        if (players.size() == 1)
            return players.iterator().next();
        Iterator<EntityPlayer> iterator = players.iterator();
        EntityPlayer nearest = iterator.next(); // fisrt object
        while (iterator.hasNext()) {
            EntityPlayer next = iterator.next();
            double distance1 = calculateDistanceBetweenPoints(entity.xPos, entity.yPos, next.xPos, next.yPos);
            double distance2 = calculateDistanceBetweenPoints(entity.xPos, entity.yPos, nearest.xPos, nearest.yPos);
            if (distance1 < distance2)
                nearest = next;
        }
        return nearest;
    }

    public double calculateDistanceBetweenPoints(
            double x1,
            double y1,
            double x2,
            double y2) {
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }
}
