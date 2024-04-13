package me.jack.ld55.level.tile;

public class GrassWithFlowers extends Tile{
    public GrassWithFlowers(int x, int y) {
        super(x, y);
        this.setWalkable(false);
    }

    @Override
    public String getTexturePath() {
        return "tiles/grasswithflowers.png";
    }
}
