package me.jack.ld55.level.tile;

public class GrassTile extends Tile{
    public GrassTile(int x, int y) {
        super(x, y);
        this.setWalkable(false);
    }

    @Override
    public String getTexturePath() {
        return "tiles/grass.png";
    }
}
