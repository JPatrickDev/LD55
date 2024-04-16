package me.jack.ld55.entity.tower;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import me.jack.ld55.LD55Game;
import me.jack.ld55.animation.Animation;
import me.jack.ld55.entity.rune.Rune;
import me.jack.ld55.level.Level;
import me.jack.ld55.level.tile.PathTile;
import me.jack.ld55.level.tile.Tile;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class RockTower extends Tower {


    public boolean highlight = false;
    Animation an = new Animation("animation/rockbreak");
    public boolean isBeingDestroyed = false;

    Texture highlightTex = null;
    public RockTower(int x, int y) {
        super(x, y, TowerTypeEnum.PASSIVE);
        texture = new Animation("towers/rocktower.png");
        highlightTex = new Texture("towers/highlightedrock.png");
        name = "Rock Tower";
        setH(texture.getHeight());
        setW(texture.getWidth());
        an.setLoop(false);
    }


    @Override
    public void drawImages(SpriteBatch batch) {
        if (!isBeingDestroyed) {
            if(!highlight)
              super.drawImages(batch);
            else
                batch.draw(highlightTex,getX(),getY());
        } else {
            an.draw(batch, getX(), getY(), 0);
        }
        highlight = false;
    }


    @Override
    public void update(Level parent) {
        super.update(parent);
        if (an.isDone()) {
            parent.removeEntity(this);
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public Tower clone() {
        return new RockTower(getX(), getY());
    }


    @Override
    public void onSpawn(Level level) {

    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public HashMap<Rune, Integer> getPrice() {
        HashMap<Rune, Integer> map = new HashMap<>();
        return map;
    }
}
