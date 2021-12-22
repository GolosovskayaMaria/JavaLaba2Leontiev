package com.mg.game;

abstract class Entity {
    public static final double MAX_HEALTH = 5;
    public static final double MAX_POSITION = 10;
    private String title;
    private int xPos = 0;
    private int yPos = 0;
    int age = 0; // количество пережитых обновлений

    String get_title() {
        return title;
    };

    void set_title(String Title){
        title = Title;
    }

    int get_x(){
        return xPos;
    }

    int get_y(){
        return yPos;
    }

    void move_to(int x, int y) { // x,y задают куда двигаться
        if (xPos > x) xPos = xPos - 1;
        if (xPos < x) xPos = xPos + 1;
        if (yPos > y) yPos = yPos - 1;
        if (yPos < y) yPos = yPos + 1;
    }
    double maxHealth = MAX_HEALTH; // максимальное колво хп
    double health = MAX_HEALTH; // текущее количество хп
    Entity() {
            xPos = (int)(Math.random() * MAX_POSITION);
            yPos = (int)(Math.random() * MAX_POSITION);
    };
    void update() {
        age++;
        if ( age % 2 == 0 ) {
            //  также раз в 2 обновления если health  maxHealth && health  0, health нужно увеличить на 1
            if(((int)health & (int)maxHealth) == 0)
            {
                health++;
            }
        }
        System.out.println(getClass() + " " + title +  " age " + age + " coordinates " + xPos + "," + yPos);
    };
    public boolean attackEntityFrom(Entity entity, double damage) {
        health = health - damage;
        System.out.println(entity.title + " attacks " + title + " " + health + " remains");
        if(health<=0)
            return true; // обьект умер
        else
            return false; // обьект все еще жив
    };
}

