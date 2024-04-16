package me.jack.ld55.attack;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.jack.ld55.animation.Animation;
import me.jack.ld55.entity.Entity;
import me.jack.ld55.entity.mob.Mob;
import me.jack.ld55.level.Level;

public class VineAttack extends Entity {

    private int dir;
    Animation animation = null;
    public VineAttack(int x, int y, int dir) {
        super(x, y, 64, 128);
        this.dir = dir;
        animation = new Animation("animation/vineattack");
        animation.fps = 1;
        animation.rotX = 16;
        animation.rotY = 16;
    }

    @Override
    public void drawShapes(ShapeRenderer renderer) {

    }

    @Override
    public void drawImages(SpriteBatch batch) {
        float angle = 90 * dir;
        animation.draw(batch,getX(),getY(), (int) angle);
    }
    long spawnTime = System.currentTimeMillis();
    @Override
    public void update(Level parent) {
        if(System.currentTimeMillis() - spawnTime > 250) {
            parent.removeEntity(this);
            Mob m = parent.getRandomMobInRange(getX(),getY(),128);
            if(m != null)
                m.damage(50f);
        }
    }

    @Override
    public void dispose() {

    }
}
