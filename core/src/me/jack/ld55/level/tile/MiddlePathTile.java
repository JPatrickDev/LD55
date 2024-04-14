package me.jack.ld55.level.tile;

public class MiddlePathTile extends PathTile{
    public MiddlePathTile(int x, int y) {
        super(x, y);
        this.setWalkable(true);
    }

    @Override
    public String getTexturePath() {
        return "tiles/pathmiddle.png";
    }



    public String getFrozenTexture(){
        return "tiles/frozenpathmiddle.png";
    }
}
