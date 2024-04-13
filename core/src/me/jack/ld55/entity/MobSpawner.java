package me.jack.ld55.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.jack.ld55.level.Level;
import me.jack.ld55.level.tile.Tile;

import java.util.Random;

public class MobSpawner extends Entity{
    public MobSpawner(int x, int y) {
        super(x, y, Tile.TILE_SIZE,Tile.TILE_SIZE);
    }

    @Override
    public void drawShapes(ShapeRenderer renderer) {
        renderer.setColor(Color.GREEN);
        renderer.rect(getX(),getY(),getW(),getH());
    }

    @Override
    public void drawImages(SpriteBatch batch) {

    }


    long lastSpawn = 0;
    @Override
    public void update(Level parent) {
        if(System.currentTimeMillis() - lastSpawn > 1000){
            parent.spawnMobAt(getX(),getY());
            lastSpawn = System.currentTimeMillis();
        }
    }

}
