package me.jack.ld55.level.tile;

public class VoidTile extends Tile{
    public VoidTile(int x, int y) {
        super(x, y);
        this.setWalkable(false);
    }

    @Override
    public String getTexturePath() {
        return "tiles/void.png";
    }
}
