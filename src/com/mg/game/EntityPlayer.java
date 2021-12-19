package com.mg.game;

import java.util.Iterator;
import java.util.LinkedList;

public class EntityPlayer   extends Entity {
    int exp = 0;
    LinkedList<EntityGuard> guards;
    void set_guards(LinkedList<EntityGuard> newGuards) {
        guards = newGuards;
    }
    public void update()
    {
        super.update();
    };
    public boolean attackEntityFrom(Entity entity, double damage)
    {
        if(guards.size()>0) {
            Iterator<EntityGuard> iterG = guards.iterator();
            EntityGuard firstGuard = iterG.next();
            // а вдруг рядом есть гвард? атаковать его вмнесто игрока
            if (firstGuard.attackEntityFrom(entity, damage) == true)
                System.out.println("Oh " + firstGuard.title + " die");
            return false;
        }
        else {
            if(super.attackEntityFrom(entity, damage) == false)
            { // если рядом нет EntityGuard и после удара игрок выживает он вызывает attackEntityFrom для атакующей сущности
                if(entity.attackEntityFrom( this, calculateDamage())==true) {
                    // если игрок убивает сущность, то exp увеличивается на 1
                    exp++;
                }
                return false;
            }
            else
                return true;
        }
    };

    public double calculateDamage()
    {
        // метод calculateDamage расчитывает урон игрока по формуле 3 + exp  2
        return 3 + (exp * 2);
    };
}
