package me.jack.ld55.spells;

import com.badlogic.gdx.graphics.Texture;
import me.jack.ld55.animation.Animation;
import me.jack.ld55.entity.Entity;
import me.jack.ld55.entity.mob.Mob;
import me.jack.ld55.entity.rune.Rune;
import me.jack.ld55.entity.tower.Tower;
import me.jack.ld55.entity.tower.TowerTypeEnum;
import me.jack.ld55.level.Level;

import java.util.HashMap;

public class SpikeStripSpell extends Tower {

    float health = 5;

    static HashMap<Integer,Texture> texMap = new HashMap<>();
    public SpikeStripSpell(int x, int y) {
        super(x, y, TowerTypeEnum.SPELL);
        if(texMap.isEmpty()){
            texMap.put(5,new Texture("animation/spikestrip/5.png"));
            texMap.put(4,new Texture("animation/spikestrip/4.png"));
            texMap.put(3,new Texture("animation/spikestrip/3.png"));
            texMap.put(2,new Texture("animation/spikestrip/2.png"));
            texMap.put(1,new Texture("animation/spikestrip/1.png"));

        }
        this.damage = 30;
        this.texture = new Animation(texMap.get((int)health));
        this.name = "Thorny Vines";
    }

    @Override
    public Tower clone() {
        return new SpikeStripSpell(getX(),getY());
    }

    @Override
    public String getDescription() {
        return "Summon spikes out of the ground to damage enemies that walk over them.";
    }


    long lastDamage = 0;
    @Override
    public void onCollide(Entity with, Level parent) {
        super.onCollide(with, parent);
        if(with instanceof Mob){
            ((Mob) with).damage(5f);
            if(System.currentTimeMillis() - lastDamage >= 500) {
                health-=0.5f;
                this.texture = new Animation(texMap.get((int)health));
                lastDamage = System.currentTimeMillis();
            }
        }
        if(health <= 0)
            parent.removeEntity(this);
    }

    @Override
    public void dispose() {

    }

    @Override
    public HashMap<Rune, Integer> getPrice() {
        HashMap<Rune,Integer> map = new HashMap<>();
        map.put(Rune.BLUE,10);
        map.put(Rune.GREEN,10);
        return map;
    }
}
