package me.jack.ld55.level.tile;

public class VerticlePathTile extends PathTile{
    public VerticlePathTile(int x, int y) {
        super(x, y);
    }

    @Override
    public String getTexturePath() {
        return "tiles/pathup.png";
    }

    public String getFrozenTexture(){
        return "tiles/frozenpathup.png";
    }

    public String getHighlightTexture() {
        return "tiles/highlightpathup.png";
    }
}
