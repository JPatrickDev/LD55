package me.jack.ld55.entity.tower;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import me.jack.ld55.LD55Game;
import me.jack.ld55.attack.VineAttack;
import me.jack.ld55.entity.mob.Mob;
import me.jack.ld55.entity.projectile.Projectile;
import me.jack.ld55.level.Level;
import me.jack.ld55.level.tile.PathTile;
import me.jack.ld55.level.tile.Tile;

public class VineTower extends Tower{

    public VineTower(int x, int y) {
        super(x, y,TowerTypeEnum.AOE);
        range = (float) (1 * Tile.TILE_SIZE);
        fireRate = 2500;
        texture = new Texture("towers/vinetower.png");
        name = "Vine Labyrinth";
    }

    @Override
    public void drawImages(SpriteBatch batch) {
        super.drawImages(batch);
    }

    @Override
    public Tower clone() {
        return new VineTower(this.getX(),this.getY());
    }


    @Override
    public void update(Level parent) {
        super.update(parent);
        Tile up = parent.getTileAt(getTileX(),getTileY()  +1);
        Tile down = parent.getTileAt(getTileX(),getTileY() - 1);
        Tile left = parent.getTileAt(getTileX() - 1,getTileY());
        Tile right = parent.getTileAt(getTileX() + 1,getTileY());
        if(up instanceof PathTile && parent.getMobAt(up) != null){
            if(System.currentTimeMillis() - lastShot >= fireRate){
                parent.spawnEntity(new VineAttack(getX(),getY(),0));
                lastShot = System.currentTimeMillis();
            }
        } if(down instanceof PathTile && parent.getMobAt(down) != null){
            if(System.currentTimeMillis() - lastShot >= fireRate){
                parent.spawnEntity(new VineAttack(getX(),getY(),2));
                lastShot = System.currentTimeMillis();
            }
        } if(left instanceof PathTile && parent.getMobAt(left) != null){

            if(System.currentTimeMillis() - lastShot >= fireRate){
                parent.spawnEntity(new VineAttack(getX(),getY(),1));
                System.out.println("Spawning Left");
                lastShot = System.currentTimeMillis();
            }
        } if(right instanceof PathTile && parent.getMobAt(right) != null){

            if(System.currentTimeMillis() - lastShot >= fireRate){
                parent.spawnEntity(new VineAttack(getX(),getY(),3));
                System.out.println("Spawning right");
                lastShot = System.currentTimeMillis();
            }
        }
    }
}
