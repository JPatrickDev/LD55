package me.jack.ld55.level.tile;

public class PathTile extends Tile{
    public PathTile(int x, int y) {
        super(x, y);
        this.setWalkable(true);
    }

    @Override
    public String getTexturePath() {
        return "tiles/path.png";
    }
}
