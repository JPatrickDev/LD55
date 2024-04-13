package me.jack.ld55.level.tile;

public class GrassWithStones extends Tile{
    public GrassWithStones(int x, int y) {
        super(x, y);
        this.setWalkable(false);
    }

    @Override
    public String getTexturePath() {
        return "tiles/grasswithstones.png";
    }
}
