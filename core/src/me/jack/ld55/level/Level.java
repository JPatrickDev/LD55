package me.jack.ld55.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;

import me.jack.ld55.entity.Entity;
import me.jack.ld55.entity.ExitTile;
import me.jack.ld55.entity.MobSpawner;
import me.jack.ld55.entity.mob.BaseEnemy;
import me.jack.ld55.entity.mob.Mob;
import me.jack.ld55.entity.tower.Tower;
import me.jack.ld55.level.tile.GrassTile;
import me.jack.ld55.level.tile.PathTile;
import me.jack.ld55.level.tile.Tile;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.jack.ld55.level.tile.VoidTile;
import org.w3c.dom.css.Rect;
import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.NavigationGrid;
import org.xguzm.pathfinding.grid.finders.AStarGridFinder;
public class Level {

    private Tile[][] tiles;

    private Tower[][] towers;
    private int w, h;

    private List<MobSpawner> spawners;
    private List<ExitTile> exits;

    private List<Entity> entities = new ArrayList<>();

    public NavigationGrid<GridCell> pathfindingGrid;
    public AStarGridFinder<GridCell> finder = new AStarGridFinder(GridCell.class);
    public Level(int w, int h) {
        tiles = new Tile[w][h];
        towers = new Tower[w][h];
        this.w = w;
        this.h = h;
        spawners = new ArrayList<>();
        exits = new ArrayList<>();
        loadLevel("level1");
        shapeRenderer.setAutoShapeType(true);
        pathfindingGrid = new NavigationGrid<GridCell>(tiles);
    }


    //TODO Load levels from PNG files
    public void loadLevel(String name) {
        Pixmap pixmap = new Pixmap(Gdx.files.internal("levels/" + name + ".png"));

        for (int x = 0; x != w + 2; x++) {
            for (int y = 0; y != h + 2; y++) {
                int val = pixmap.getPixel(x, y);
                // System.out.println(val);
                if (x >= 1 && x <= 10 && y >= 1 && y <= 10) {
                    if (val == 2134049023) {
                        tiles[x][y] = new GrassTile(x,y);
                    } else if (val == 16720383) {
                        tiles[x][y] = new PathTile(x,y);
                    }
                } else {
                    if (val ==1208025087) {
                        MobSpawner e  =  new MobSpawner((x) * Tile.TILE_SIZE,(y) * Tile.TILE_SIZE);
                        spawners.add(e);
                        entities.add(e);
                        System.out.println("Spawner at " + x + "," + y);
                        tiles[x][y] = new PathTile(x,y);
                    } else if (val == -16776961){
                        ExitTile e = new ExitTile((x) * Tile.TILE_SIZE,(y) * Tile.TILE_SIZE);
                        exits.add(e);
                        entities.add(e);
                        System.out.println("Exit at " + x + "," + y);
                        tiles[x][y] = new PathTile(x,y);
                    }else{
                        if(x < 12 && y <12)
                        tiles[x][y] = new VoidTile(x,y);
                    }

                }
            }
        }
        pixmap.dispose();
    }



    public void placeTower(Tower t){
        if(towers[t.getX()/64][t.getY()/64] == null && tiles[t.getX()/64][t.getY()/64] instanceof GrassTile){
            towers[t.getX()/64][t.getY()/64] = t;
            entities.add(t);
        }
    }

    ShapeRenderer shapeRenderer = new ShapeRenderer();
    SpriteBatch batchRenderer = new SpriteBatch();

    public void render() {

        batchRenderer.begin();
        for (int x = 0; x != w; x++) {
            for (int y = 0; y != h; y++) {
                if (tiles[x][y] == null)
                    continue;
                tiles[x][y].renderImages(batchRenderer);
            }
        }
        batchRenderer.end();
        shapeRenderer.begin();
        for (int x = 0; x != w; x++) {
            for (int y = 0; y != h; y++) {
                if (tiles[x][y] == null)
                    continue;
                tiles[x][y].renderShapes(shapeRenderer);
            }
        }
        shapeRenderer.end();


        batchRenderer.begin();
        for(Entity e : entities){
            e.drawImages(batchRenderer);
        }
        batchRenderer.end();
        shapeRenderer.begin();
        for(Entity e : entities){
            e.drawShapes(shapeRenderer);
        }
        shapeRenderer.end();
    }

    public void update(){
        for(Entity s : entities){
            s.update(this);
        }

        for(Entity e : entities){
            Rectangle r = new Rectangle(e.getX(),e.getY(),e.getW(),e.getH());
            for(Entity en : entities){
                if(en == e)
                    continue;
                Rectangle r2 = new Rectangle(en.getX(),en.getY(),en.getW(),en.getH());
                if(r2.intersects(r))
                    e.onCollide(en,this);
            }
            if(e instanceof Mob){
                if(((Mob) e).getHealth() <= 0){
                    removeEntity(e);
                }
            }
        }

        entities.addAll(toSpawn);

        entities.removeAll(toRemove);
        toSpawn.clear();
        toRemove.clear();

    }
    List<Entity> toSpawn = new ArrayList<>();
    public void spawnMobAt(int x, int y) {
        System.out.println("Spawning Mob at " +x + "," + y);
        ExitTile exit = exits.get(new Random().nextInt(exits.size()));
        spawnEntity(new BaseEnemy(x,y,(exit.getX() /Tile.TILE_SIZE),exit.getY() / Tile.TILE_SIZE ,this));
    }

    public void spawnEntity(Entity e){
        e.spawnTime = System.currentTimeMillis();
        toSpawn.add(e);
    }

    List<Entity> toRemove = new ArrayList<>();
    public void removeEntity(Entity e){
        toRemove.add(e);
    }

    public Mob getRandomMobInRange(Entity tower, float range) {
        List<Mob> choices = new ArrayList<>();
        for(Entity e : entities){
            if((e instanceof Mob)){
                if(dist(e,tower) <= range){
                    choices.add((Mob) e);
                }
            }
        }
        if(choices.isEmpty())
            return null;
        return choices.get(new Random().nextInt(choices.size()));
    }


    public static int dist(Entity o, Entity t) {
        return (int) Point2D.distance(o.getX(), o.getY(), t.getX(), t.getY());
    }

    public static int dist(Entity e, Tile t) {
        return (int) Point2D.distance(e.getX(), e.getY(), t.getX() * Tile.TILE_SIZE, t.getY() * Tile.TILE_SIZE);
    }
}
