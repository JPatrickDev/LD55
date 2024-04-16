package me.jack.ld55.level.tile;

import com.badlogic.gdx.graphics.Texture;

public class DirtTile extends Tile{
    public DirtTile(int x, int y) {
        super(x, y);
        if(!textureMap.containsKey(getFrozenTexture())){
            textureMap.put(getFrozenTexture(),new Texture(getFrozenTexture()));
            textureMap.put(getHighlightTexture(),new Texture(getHighlightTexture()));
        }
        this.setWalkable(true);
    }

    @Override
    public String getTexturePath() {
        return "tiles/dirt.png";
    }

    public String getFrozenTexture(){
        return "tiles/dirt.png";
    }

    public String getHighlightTexture() {
        return "tiles/dirt.png";
    }
}
