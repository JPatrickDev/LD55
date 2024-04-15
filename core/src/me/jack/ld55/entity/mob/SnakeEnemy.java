package me.jack.ld55.entity.mob;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import me.jack.ld55.animation.Animation;
import me.jack.ld55.level.Level;

public class SnakeEnemy extends BaseEnemy {

    private Animation animation;
    public SnakeEnemy(int x, int y, int targetX, int targetY, Level parent) {
        super(x, y, targetX, targetY, parent);
        animation = new Animation("animation/snakewalk");
        this.moveSpeed = 2;
        this.baseMoveSpeed = 2;
    }

    @Override
    public void drawImages(SpriteBatch batch) {
        animation.draw(batch,getX() + 16,getY() + 16,this.rotation);
    }

    @Override
    public float getMaxHealth() {
        return 10f;
    }
}
