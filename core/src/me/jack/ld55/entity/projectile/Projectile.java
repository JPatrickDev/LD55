package me.jack.ld55.entity.projectile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.jack.ld55.LD55Game;
import me.jack.ld55.animation.Animation;
import me.jack.ld55.entity.Entity;
import me.jack.ld55.entity.mob.Mob;
import me.jack.ld55.entity.tower.Tower;
import me.jack.ld55.level.Level;

import java.util.Random;

public class Projectile extends Entity {
    long lifeSpan = 1000;//milliseconds

    private Mob target;

    public Animation animation;



    float dX,dY;
    public Projectile(int x, int y, int w, int h) {
        super(x, y, w, h);
        animation = new Animation("animation/wizardprojectile");
        animation.setLoop(false);
    }


    public void fire(Mob target){
        this.target = target;
        float xSpeed = (fuzz(target.getX()) - getX());
        float ySpeed = (fuzz(target.getY()) - getY());

        float factor = (float) (15 / Math.sqrt(xSpeed * xSpeed + ySpeed * ySpeed));
        xSpeed *= factor;
        ySpeed *= factor;
        dX = xSpeed;
        dY = ySpeed;
        this.spawnTime = System.currentTimeMillis();
    }

    public int fuzz(int input) {
        int i = LD55Game.rand(5);
        if (LD55Game.randBool())
            i *= -1;
        return input + i;
    }
    @Override
    public void drawShapes(ShapeRenderer renderer) {
    }

    @Override
    public void drawImages(SpriteBatch batch) {
        animation.draw(batch,getX(),getY(),0);
    }

    Random r = new Random();

    @Override
    public void update(Level parent) {

        move((int) dX, (int) dY);
        if (spawnTime != -1 && System.currentTimeMillis() - spawnTime > lifeSpan)
            parent.removeEntity(this);
    }

    @Override
    public void onCollide(Entity with, Level parent) {
        super.onCollide(with, parent);
        if(spawnTime == -1)
            return;
        if (with instanceof Mob) {
            ((Mob) with).damage(10);
        }
        if (!(with instanceof Projectile || with instanceof Tower)) // Maybe stop collisions with Towers, but allow the tower that created the projectile
            parent.removeEntity(this);
    }

    @Override
    public void dispose() {

    }
}
