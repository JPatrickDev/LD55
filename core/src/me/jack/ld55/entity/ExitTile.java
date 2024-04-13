package me.jack.ld55.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.jack.ld55.level.Level;
import me.jack.ld55.level.tile.Tile;

public class ExitTile extends Entity{
    public ExitTile(int x, int y) {
        super(x, y, Tile.TILE_SIZE,Tile.TILE_SIZE);
    }

    @Override
    public void drawShapes(ShapeRenderer renderer) {
        renderer.setColor(Color.RED);
        renderer.rect(getX(),getY(),getW(),getH());
    }

    @Override
    public void drawImages(SpriteBatch batch) {

    }

    @Override
    public void update(Level parent) {

    }


    @Override
    public void onCollide(Entity with,Level parent) {
        super.onCollide(with,parent);
        parent.removeEntity(with);
    }
}
