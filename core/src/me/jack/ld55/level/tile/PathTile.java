package me.jack.ld55.level.tile;

import com.badlogic.gdx.graphics.Texture;

public class PathTile extends Tile{
    public PathTile(int x, int y) {
        super(x, y);
        if(!textureMap.containsKey(getFrozenTexture())){
            textureMap.put(getFrozenTexture(),new Texture(getFrozenTexture()));
        }
        this.setWalkable(true);
    }

    @Override
    public String getTexturePath() {
        return "tiles/path.png";
    }

    public String getFrozenTexture(){
        return "tiles/frozenpath.png";
    }
}
