package me.jack.ld55.entity.projectile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.jack.ld55.LD55Game;
import me.jack.ld55.entity.Entity;
import me.jack.ld55.entity.mob.Mob;
import me.jack.ld55.entity.tower.Tower;
import me.jack.ld55.level.Level;

import java.util.Random;

public class Projectile extends Entity {
    long lifeSpan = 1000;//milliseconds

    private Mob target;

    float dX,dY;
    public Projectile(int x, int y, int w, int h, Mob target) { //Mob given as target, some projectiles should fire where they are currently, others may track
        super(x, y, w, h);
        this.target = target;
        float xSpeed = (fuzz(target.getX()) - x);
        float ySpeed = (fuzz(target.getY()) - y);

        float factor = (float) (15 / Math.sqrt(xSpeed * xSpeed + ySpeed * ySpeed));
        xSpeed *= factor;
        ySpeed *= factor;
        dX = xSpeed;
        dY = ySpeed;
    }

    public int fuzz(int input) {
        int i = LD55Game.rand(15);
        if (LD55Game.randBool())
            i *= -1;
        return input + i;
    }
    @Override
    public void drawShapes(ShapeRenderer renderer) {
        renderer.setColor(Color.YELLOW);
        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.circle(getX() + getW() / 2, getY() + getH() / 2, getH() / 2);
        renderer.set(ShapeRenderer.ShapeType.Line);
    }

    @Override
    public void drawImages(SpriteBatch batch) {

    }

    Random r = new Random();

    @Override
    public void update(Level parent) {

        move((int) dX, (int) dY);
        if (System.currentTimeMillis() - spawnTime > lifeSpan)
            parent.removeEntity(this);
    }

    @Override
    public void onCollide(Entity with, Level parent) {
        super.onCollide(with, parent);
        if (with instanceof Mob) {
            ((Mob) with).damage(10);
        }
        if (!(with instanceof Projectile || with instanceof Tower)) // Maybe stop collisions with Towers, but allow the tower that created the projectile
            parent.removeEntity(this);
    }
}
