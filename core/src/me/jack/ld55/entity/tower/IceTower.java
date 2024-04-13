package me.jack.ld55.entity.tower;

import com.badlogic.gdx.graphics.Texture;
import me.jack.ld55.level.tile.Tile;

public class IceTower extends Tower{
    public IceTower(int x, int y) {
        super(x, y,TowerTypeEnum.PASSIVE);
        range = (float) (1.5 * Tile.TILE_SIZE);
        fireRate = 500;
        texture = new Texture("towers/icepillar.png");
        name = "Ice Pillar";
    }

    @Override
    public Tower clone() {
        return new IceTower(getX(),getY());
    }
}
