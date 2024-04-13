package me.jack.ld55.level.tile;

public class VerticlePathTile extends Tile{
    public VerticlePathTile(int x, int y) {
        super(x, y);
        this.setWalkable(true);
    }

    @Override
    public String getTexturePath() {
        return "tiles/pathup.png";
    }
}
