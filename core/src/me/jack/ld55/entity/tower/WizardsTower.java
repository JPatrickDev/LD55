package me.jack.ld55.entity.tower;

import com.badlogic.gdx.graphics.Texture;
import me.jack.ld55.level.tile.Tile;

public class WizardsTower extends Tower{
    public WizardsTower(int x, int y) {
        super(x, y,TowerTypeEnum.RANGED);
        range = (float) (1.5 * Tile.TILE_SIZE);
        fireRate = 500;
        texture = new Texture("towers/wizardstower.png");
        name = "Wizards Tower";
    }

    @Override
    public Tower clone() {
        return new WizardsTower(getX(),getY());
    }
}
