package me.jack.ld55.entity.tower;

import com.badlogic.gdx.graphics.Texture;
import me.jack.ld55.level.tile.Tile;

public class VineTower extends Tower{

    public VineTower(int x, int y) {
        super(x, y,TowerTypeEnum.AOE);
        range = (float) (1 * Tile.TILE_SIZE);
        fireRate = 500;
        texture = new Texture("towers/vinetower.png");
        name = "Vine Labyrinth";
    }

    @Override
    public Tower clone() {
        return new VineTower(this.getX(),this.getY());
    }
}
