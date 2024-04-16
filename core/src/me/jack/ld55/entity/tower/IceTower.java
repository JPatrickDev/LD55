package me.jack.ld55.entity.tower;

import com.badlogic.gdx.graphics.Texture;
import me.jack.ld55.animation.Animation;
import me.jack.ld55.entity.rune.Rune;
import me.jack.ld55.level.Level;
import me.jack.ld55.level.tile.PathTile;
import me.jack.ld55.level.tile.Tile;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class IceTower extends Tower{


    public IceTower(int x, int y) {
        super(x, y,TowerTypeEnum.PASSIVE);
        range = (float) (1.5 * Tile.TILE_SIZE);
        fireRate = 0;
        texture = new Animation("towers/icepillar.png");
        name = "Ice Pillar";
        setH(texture.getHeight());
        setW(texture.getWidth());
        damage = 0;
    }

    @Override
    public Tower clone() {
        return new IceTower(getX(),getY());
    }


    @Override
    public void onSpawn(Level level) {
        List<Tile> pathsInRange = level.getTilesInRadius(getX() + 32,getY() + 32,range - 4).stream().filter(x -> x instanceof PathTile).collect(Collectors.toList());
        for(Tile t : pathsInRange){
            t.freeze = true;
        }
    }

    @Override
    public String getDescription() {
        return "An Icy Monument that freezes the path around it, slowing down enemies.";
    }

    @Override
    public HashMap<Rune, Integer> getPrice() {
        HashMap<Rune,Integer> map = new HashMap<>();
        map.put(Rune.BLUE,15);
        map.put(Rune.PURPLE,20);
        return map;
    }

    @Override
    public void dispose() {

    }
}
