package me.jack.ld55.entity.rune;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.jack.ld55.entity.Entity;
import me.jack.ld55.level.Level;
import me.jack.ld55.level.tile.Tile;

import java.util.HashMap;
import java.util.Random;

public class Rune extends Entity {




    public static final HashMap<String, Texture> textureMap = new HashMap<>();
    float dX,dY;
    public Rune(int x, int y) {
        super(x, y, 8,8);
        if(!textureMap.containsKey(getTexturePath())){
            textureMap.put(getTexturePath(),new Texture(getTexturePath()));
        }
        dX = new Random().nextFloat() * 50;
        dY = new Random().nextFloat() * 50;
    }

    private String getTexturePath() {
        return "runes/redrune.png";
    }

    @Override
    public void drawShapes(ShapeRenderer renderer) {

    }

    @Override
    public void drawImages(SpriteBatch batch) {
        batch.draw(textureMap.get(getTexturePath()),getX(),getY());
    }

    @Override
    public void update(Level parent) {
        move((int) dX, (int) dY);
        dX *= 0.01F;
        dY *=0.01F;
    }
}
