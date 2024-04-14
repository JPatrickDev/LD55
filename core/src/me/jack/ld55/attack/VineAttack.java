package me.jack.ld55.attack;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.jack.ld55.entity.Entity;
import me.jack.ld55.entity.mob.Mob;
import me.jack.ld55.level.Level;

public class VineAttack extends Entity {

    private int dir;

    public VineAttack(int x, int y, int dir) {
        super(x, y, 64, 128);
        this.dir = dir;
    }

    @Override
    public void drawShapes(ShapeRenderer renderer) {

    }

    @Override
    public void drawImages(SpriteBatch batch) {
        float angle = 90 * dir;
        batch.draw(new Texture("animation/vineattack/1.png"),getX(),getY(),32,28,64,128,1,1,angle,0,0,64,128,false,false);
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
}
