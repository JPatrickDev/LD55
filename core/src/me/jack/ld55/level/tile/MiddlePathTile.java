package me.jack.ld55.level.tile;

public class MiddlePathTile extends Tile{
    public MiddlePathTile(int x, int y) {
        super(x, y);
        this.setWalkable(true);
    }

    @Override
    public String getTexturePath() {
        return "tiles/pathmiddle.png";
    }
}
