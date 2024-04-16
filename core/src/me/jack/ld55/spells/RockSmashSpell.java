package me.jack.ld55.spells;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import me.jack.ld55.animation.Animation;
import me.jack.ld55.entity.Entity;
import me.jack.ld55.entity.mob.Mob;
import me.jack.ld55.entity.rune.Rune;
import me.jack.ld55.entity.tower.RockTower;
import me.jack.ld55.entity.tower.Tower;
import me.jack.ld55.entity.tower.TowerTypeEnum;
import me.jack.ld55.level.Level;
import me.jack.ld55.level.tile.PathTile;
import me.jack.ld55.level.tile.Tile;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class RockSmashSpell extends Tower {

    float health = 5;

    static HashMap<Integer, Texture> texMap = new HashMap<>();

    public RockSmashSpell(int x, int y) {
        super(x, y, TowerTypeEnum.SPELL);
        this.damage = 30;
        this.range = 0;
        this.fireRate = 0;
        this.name = "Rock Smash";
        this.texture = new Animation("towers/rocksmashspell.png");
    }

    @Override
    public Tower clone() {
        return new RockSmashSpell(getX(), getY());
    }

    @Override
    public String getDescription() {
        return "Summon a blast of power to remove rocks. ";
    }


    long lastDamage = 0;

    @Override
    public void onCollide(Entity with, Level parent) {
        super.onCollide(with, parent);
    }

    @Override
    public void dispose() {

    }

    @Override
    public HashMap<Rune, Integer> getPrice() {
        HashMap<Rune, Integer> map = new HashMap<>();
        map.put(Rune.RED, 10);
        map.put(Rune.PURPLE,5);
        map.put(Rune.GREEN,5);
        return map;
    }

    public void drawAsPlacing(ShapeRenderer renderer, SpriteBatch batch, boolean canPlace, Level parent) {
        if (parent.getTowerAt(getTileX(), getTileY()) instanceof RockTower) {
            drawShapes(renderer);
            drawImages(batch);
            ((RockTower) parent.getTowerAt(getTileX(), getTileY())).highlight = true;
        } else {

        }
    }
}
