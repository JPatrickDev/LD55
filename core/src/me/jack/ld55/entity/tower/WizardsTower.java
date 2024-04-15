package me.jack.ld55.entity.tower;

import com.badlogic.gdx.graphics.Texture;
import me.jack.ld55.entity.mob.Mob;
import me.jack.ld55.entity.projectile.Projectile;
import me.jack.ld55.entity.rune.Rune;
import me.jack.ld55.level.Level;
import me.jack.ld55.level.tile.Tile;

import java.util.HashMap;

public class WizardsTower extends Tower{
    public WizardsTower(int x, int y) {
        super(x, y,TowerTypeEnum.RANGED);
        range = (float) (1.5 * Tile.TILE_SIZE);
        fireRate = 750;
        texture = new Texture("towers/wizardstower.png");
        name = "Wizards Tower";
        setH(texture.getHeight());
        setW(texture.getWidth());
        this.damage = 10;
    }

    @Override
    public Tower clone() {
        return new WizardsTower(getX(),getY());
    }

    @Override
    public String getDescription() {
        return "An ancient tower that shoots mystical orbs at targets";
    }

    @Override
    public void update(Level parent) {
        super.update(parent);
        Mob target = parent.getRandomMobInRange(this, range);
        if (target != null && System.currentTimeMillis() - lastShot >= fireRate) {
            parent.spawnEntity(new Projectile(getX(), getY(), 16, 16, target));
            lastShot = System.currentTimeMillis();
        }
    }

    @Override
    public HashMap<Rune, Integer> getPrice() {
        HashMap<Rune,Integer> map = new HashMap<>();
        map.put(Rune.RED,5);
        return map;
    }
}
