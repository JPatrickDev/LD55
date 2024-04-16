package me.jack.ld55.entity.tower;

import com.badlogic.gdx.graphics.Texture;
import me.jack.ld55.animation.Animation;
import me.jack.ld55.entity.mob.Mob;
import me.jack.ld55.entity.projectile.Projectile;
import me.jack.ld55.entity.rune.Rune;
import me.jack.ld55.level.Level;
import me.jack.ld55.level.tile.Tile;

import java.util.HashMap;

import static jdk.javadoc.internal.doclets.toolkit.util.DocPath.parent;

public class WizardsTower extends Tower {

    private Projectile currentShot;

    public WizardsTower(int x, int y) {
        super(x, y, TowerTypeEnum.RANGED);
        range = (float) (1.5 * Tile.TILE_SIZE);
        fireRate = 750;
        texture = new Animation("animation/wizardtower");
        name = "Wizards Tower";
        setH(texture.getHeight());
        setW(texture.getWidth());
        this.damage = 10;
    }

    @Override
    public Tower clone() {
        return new WizardsTower(getX(), getY());
    }

    @Override
    public String getDescription() {
        return "An ancient tower that shoots mystical orbs at targets";
    }

    @Override
    public void update(Level parent) {
        super.update(parent);
        if (currentShot == null) {
            currentShot = new Projectile(getX() + getW() / 2 - 8, getY() + getH() - 10, 16, 16);
            parent.spawnEntity(currentShot);
            currentShot.spawnTime = -1;
        }
        Mob target = parent.getRandomMobInRange(this, range);
        if (target != null && currentShot.animation.isDone() && System.currentTimeMillis() - lastShot >= fireRate) {
         //   System.out.println("Random mob in range " + target);
            lastShot = System.currentTimeMillis();
            currentShot.fire(target);
            currentShot = null;
        }
    }

    @Override
    public void dispose() {
        currentShot.dispose();
    }

    @Override
    public HashMap<Rune, Integer> getPrice() {
        HashMap<Rune, Integer> map = new HashMap<>();
        map.put(Rune.RED, 10);
        return map;
    }
}
