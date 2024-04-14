package me.jack.ld55.entity.mob;

import me.jack.ld55.entity.Entity;

public abstract class Mob extends Entity {

    private float health;

    int moveSpeed = 4;
    public Mob(int x, int y, int w, int h) {
        super(x, y, w, h);
        health = getMaxHealth();
    }

    public abstract void onSpawn();
    public abstract void onDeath();
    public abstract float getMaxHealth();

    public void damage(float amount){
        health -= amount;
    }

    public float getHealth(){
        return health;
    }
}
