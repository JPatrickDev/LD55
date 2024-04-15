package me.jack.ld55.entity.mob;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.jack.ld55.animation.Animation;
import me.jack.ld55.level.Level;
import me.jack.ld55.level.tile.Tile;
import org.xguzm.pathfinding.grid.GridCell;

import java.util.ArrayList;
import java.util.List;

public class SpiderEnemy extends BaseEnemy {

    private Animation animation;
    public SpiderEnemy(int x, int y, int targetX, int targetY, Level parent) {
        super(x, y, targetX, targetY, parent);
        animation = new Animation("animation/spiderwalk");
    }

    @Override
    public void drawImages(SpriteBatch batch) {
        animation.draw(batch,getX() + 16,getY() + 16,this.rotation);
    }

    @Override
    public float getMaxHealth() {
        return 5f;
    }
}
