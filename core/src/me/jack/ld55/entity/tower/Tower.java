package me.jack.ld55.entity.tower;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.jack.ld55.entity.Entity;
import me.jack.ld55.entity.mob.Mob;
import me.jack.ld55.entity.projectile.Projectile;
import me.jack.ld55.level.Level;
import me.jack.ld55.level.tile.Tile;

public class Tower extends Entity {

    private float range;
    private long fireRate;



    public Tower(int x, int y) {
        super(x, y, Tile.TILE_SIZE, Tile.TILE_SIZE);
        fireRate = 500;
    }

    @Override
    public void drawShapes(ShapeRenderer renderer) {
        renderer.setColor(Color.BLACK);
        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.circle(getX() + 32, getY() + 32, 16);
        //  renderer.rect(getX(),getY(),getW(),getH());
        renderer.set(ShapeRenderer.ShapeType.Line);
    }

    @Override
    public void drawImages(SpriteBatch batch) {

    }

    long lastShot = 0;
    @Override
    public void update(Level parent) {
        Mob target = parent.getRandomMobInRange(this, Tile.TILE_SIZE * 1.5f);
        if (target != null && System.currentTimeMillis() - lastShot >= fireRate) {
            parent.spawnEntity(new Projectile(getX(), getY(), 16, 16, target));
            lastShot = System.currentTimeMillis();
        }
    }
}
