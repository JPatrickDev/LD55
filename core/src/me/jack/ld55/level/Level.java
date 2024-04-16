package me.jack.ld55.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import me.jack.ld55.LD55Game;
import me.jack.ld55.entity.Entity;
import me.jack.ld55.entity.ExitTile;
import me.jack.ld55.entity.MobSpawner;
import me.jack.ld55.entity.mob.Mob;
import me.jack.ld55.entity.mob.SnakeEnemy;
import me.jack.ld55.entity.mob.SpiderEnemy;
import me.jack.ld55.entity.mob.StoneGolemEnemy;
import me.jack.ld55.entity.rune.Rune;
import me.jack.ld55.entity.rune.RuneShard;
import me.jack.ld55.entity.tower.RockTower;
import me.jack.ld55.entity.tower.Tower;
import me.jack.ld55.entity.tower.TowerTypeEnum;
import me.jack.ld55.level.tile.*;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.jack.ld55.spells.RockSmashSpell;
import me.jack.ld55.state.InGameState;
import me.jack.ld55.ui.RuneCollectionElement;
import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.NavigationGrid;
import org.xguzm.pathfinding.grid.finders.AStarGridFinder;

public class Level {

    public Tile[][] tiles;

    private Tower[][] towers;
    private int w, h;

    private List<MobSpawner> spawners;
    private List<ExitTile> exits;

    private List<Entity> entities = new ArrayList<>();

    public NavigationGrid<GridCell> pathfindingGrid;
    public AStarGridFinder<GridCell> finder = new AStarGridFinder(GridCell.class);

    public int roundNum = 0;


    public Level(){
        this.w = 12;
        this.h = 12;
    }

    public Level(int w, int h) {
        tiles = new Tile[w][h];
        towers = new Tower[w][h];
        this.w = w;
        this.h = h;
        spawners = new ArrayList<>();
        exits = new ArrayList<>();
        shapeRenderer.setAutoShapeType(true);
        pathfindingGrid = new NavigationGrid<GridCell>(tiles);

    }


    public static Level loadLevel(String name) {
        Level level = new Level(14,14);
        Pixmap pixmap = new Pixmap(Gdx.files.internal("levels/" + name + ".png"));
        for(int x= 0;x!= 14;x++){
            for(int y = 0; y!= 14;y++){
                level.tiles[x][y] = new VoidTile(x,y);
            }
        }
        for (int x = 0; x != 14; x++) {
            for (int y = 0; y != 14; y++) {
                int val = pixmap.getPixel(x, y);
                // System.out.println(val);
                if (x >= 1 && x <= 10 && y >= 1 && y <= 10) {
                    if (val == 2134049023) {
                        level.tiles[x][y] = new GrassTile(x, y);
                        if(LD55Game.rand(3) == 0){
                            level.placeTower(new RockTower(x * Tile.TILE_SIZE,y * Tile.TILE_SIZE));
                        }
                    } else if (val == 16720383) {
                        level.tiles[x][y] = new PathTile(x, y);
                    } else if (val == -1224802049) {
                        level.tiles[x][y] = new VerticlePathTile(x, y);
                    } else if (val == 16748799) {
                        level.tiles[x][y] = new MiddlePathTile(x, y);
                    } else if (val == -16748801) {
                        level.tiles[x][y] = new GrassWithStones(x, y);
                    } else if (val == -2621185) {
                        level.tiles[x][y] = new GrassWithFlowers(x, y);
                    }
                } else {
                    if (val == 1208025087) {
                        MobSpawner e = new MobSpawner((x) * Tile.TILE_SIZE, (y) * Tile.TILE_SIZE);
                        level.spawners.add(e);
                        level.entities.add(e);
                        System.out.println("Spawner at " + x + "," + y);
                        level.tiles[x][y] = new DirtTile(x, y);
                    } else if (val == -16776961) {
                        ExitTile e = new ExitTile((x) * Tile.TILE_SIZE, (y) * Tile.TILE_SIZE);
                        level.exits.add(e);
                        level.entities.add(e);
                        System.out.println("Exit at " + x + "," + y);
                        level.tiles[x][y] = new DirtTile(x, y);
                    } else {
                        if (x < 12 && y < 12) {
                            //  System.out.println(val);
                            level.tiles[x][y] = new VoidTile(x, y);
                        }
                    }

                }
            }
        }
        pixmap.dispose();

        return level;
    }


    public static Texture loadLevelAsIcon(String name) {
        Pixmap pixmap = new Pixmap(Gdx.files.internal("levels/" + name + ".png"));
        Pixmap result = new Pixmap(pixmap.getWidth(),pixmap.getHeight(), Pixmap.Format.RGBA8888);
        for (int x = 0; x != 12 + 2; x++) {
            for (int y = 0; y != 12 + 2; y++) {
                int val = pixmap.getPixel(x, y);
                // System.out.println(val);
                if (x >= 1 && x <= 10 && y >= 1 && y <= 10) {
                    if (val == 2134049023) {
                        result.drawPixel(x,pixmap.getHeight() - y, Color.rgba8888(Color.GREEN));
                    } else if (val == 16720383) {
                        result.drawPixel(x,pixmap.getHeight() - y, Color.rgba8888(Color.BROWN));
                    } else if (val == -1224802049) {
                        result.drawPixel(x,pixmap.getHeight() - y, Color.rgba8888(Color.BROWN));
                    } else if (val == 16748799) {
                        result.drawPixel(x,pixmap.getHeight() - y, Color.rgba8888(Color.BROWN));
                    } else if (val == -16748801) {
                        result.drawPixel(x,pixmap.getHeight() - y, Color.rgba8888(Color.GREEN));
                    } else if (val == -2621185) {
                        result.drawPixel(x,pixmap.getHeight() - y, Color.rgba8888(Color.GREEN));
                    }
                } else {
                    if (val == 1208025087) {
                        pixmap.drawPixel(x,pixmap.getHeight() - y, Color.rgba8888(Color.BLUE));
                    } else if (val == -16776961) {
                        pixmap.drawPixel(x,pixmap.getHeight() - y, Color.rgba8888(Color.GREEN));
                    } else {
                        pixmap.drawPixel(x,pixmap.getHeight() - y, Color.rgba8888(Color.BLACK));
                    }

                }
            }
        }
        Texture t = new Texture(result);
        pixmap.dispose();
        result.dispose();
        return t;
    }


    public boolean placeTower(Tower t) {
        if(t instanceof RockSmashSpell){
            if(towers[t.getTileX()][t.getTileY()] instanceof RockTower){
                ((RockTower) towers[t.getTileX()][t.getTileY()]).isBeingDestroyed = true;
                return true;
            }else{
                return false;
            }
        }
        try {
            if (towers[t.getX() / 64][t.getY() / 64] == null && ((tiles[t.getX() / 64][t.getY() / 64] instanceof GrassTile && t.type != TowerTypeEnum.SPELL)
                    || (tiles[t.getX() / 64][t.getY() / 64] instanceof PathTile && t.type == TowerTypeEnum.SPELL)
            )) {
                towers[t.getX() / 64][t.getY() / 64] = t;
                entities.add(t);
                t.onSpawn(this);
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
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
        List<Entity> entitiesSort = new ArrayList<>(entities);
        entitiesSort.sort((o1, o2) -> Integer.compare(o2.getTileY(), o1.getTileY()));
        for (Entity e : entitiesSort) {
            e.drawImages(batchRenderer);
        }
        batchRenderer.end();
        shapeRenderer.begin();
        for (Entity e : entitiesSort) {
            e.drawShapes(shapeRenderer);
        }
        shapeRenderer.end();
    }

    int lastRoundNotified = -1;

    public void update(InGameState parent) {


        if (!mobsRemaining() && remainingToSpawn == 0) {

            if (lastRoundNotified != roundNum) {
                parent.notifyRoundEnd();
                lastRoundNotified = roundNum;
            }
        }
        for (Entity s : entities) {
            s.update(this);
        }

        for (Entity e : entities) {
            Rectangle r = new Rectangle(e.getX(), e.getY(), e.getW(), e.getH());
            for (Entity en : entities) {
                if (en == e)
                    continue;
                Rectangle r2 = new Rectangle(en.getX(), en.getY(), en.getW(), en.getH());
                if (r2.intersects(r))
                    e.onCollide(en, this);
            }
            if (e instanceof Mob) {

                if (((Mob) e).getHealth() <= 0) {
                    removeEntity(e);
                    RuneShard ru = new RuneShard(e.getX(), e.getY());
                    if (roundNum < 3) {
                        ru.runeType = Rune.RED;
                        if(LD55Game.rand(3) == 0)
                            ru.runeType = Rune.BLUE;
                    }
                    this.spawnEntity(ru);
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
        //  System.out.println("Spawning Mob at " +x + "," + y);
        if (remainingToSpawn != 0) {
            ExitTile exit = exits.get(new Random().nextInt(exits.size()));
            int i = LD55Game.rand(5);
            if ( i <= 1 || roundNum <= 3) {
                spawnEntity(new SnakeEnemy(x, y, (exit.getX() / Tile.TILE_SIZE), exit.getY() / Tile.TILE_SIZE, this));
            } else if(i == 3 && roundNum > 5){
                spawnEntity(new SpiderEnemy(x, y, (exit.getX() / Tile.TILE_SIZE), exit.getY() / Tile.TILE_SIZE, this));
            }else{
                spawnEntity(new StoneGolemEnemy(x, y, (exit.getX() / Tile.TILE_SIZE), exit.getY() / Tile.TILE_SIZE, this));
            }
            remainingToSpawn--;
        }
    }

    public void spawnEntity(Entity e) {
        e.spawnTime = System.currentTimeMillis();
        toSpawn.add(e);
    }

    public int totalKills = 0;
    List<Entity> toRemove = new ArrayList<>();

    public void removeEntity(Entity e) {
        toRemove.add(e);
        if (e instanceof RuneShard) {
            RuneCollectionElement.instance.addRune(((RuneShard) e).getRuneType(), 4);
        }
        if (e instanceof Tower) {
            towers[e.getTileX()][e.getTileY()] = null;
        }
        if(e instanceof  Mob){
            totalKills++;
        }
    }


    public int remainingToSpawn = 0;
    public int mobsInRound = 0;

    public int livesRemaining = 10;

    public void startRound() {
        if (remainingToSpawn > 0) {
            return;
        }
        roundNum++;
        remainingToSpawn = getAmountToSpawn();
        mobsInRound = remainingToSpawn;
        System.out.println("Round: " + roundNum + ", Spawning " + remainingToSpawn + " Started");
    }

    public Mob getRandomMobInRange(Entity tower, float range) {
        List<Mob> choices = new ArrayList<>();
        for (Entity e : entities) {
            if ((e instanceof Mob)) {
                if (dist(e, tower) <= range) {
                    choices.add((Mob) e);
                }
            }
        }
        if (choices.isEmpty())
            return null;
        return choices.get(new Random().nextInt(choices.size()));
    }

    public Mob getRandomMobInRange(int x, int y, float range) {
        List<Mob> choices = new ArrayList<>();
        for (Entity e : entities) {
            if ((e instanceof Mob)) {
                if (dist(e, x, y) <= range) {
                    choices.add((Mob) e);
                }
            }
        }
        if (choices.isEmpty())
            return null;
        return choices.get(new Random().nextInt(choices.size()));
    }

    public boolean mobsRemaining() {
        return entities.stream().anyMatch(x -> x instanceof Mob);
    }

    public int mobCountRemaining() {
        return (int) entities.stream().filter(x -> x instanceof Mob).count();
    }

    public static int dist(Entity o, Entity t) {
        return (int) Point2D.distance(o.getX(), o.getY(), t.getX(), t.getY());
    }

    public static int dist(Entity e, Tile t) {
        return (int) Point2D.distance(e.getX(), e.getY(), t.getX() * Tile.TILE_SIZE, t.getY() * Tile.TILE_SIZE);
    }

    public static int dist(Entity e, int x, int y) {
        return (int) Point2D.distance(e.getX(), e.getY(), x, y);
    }

    public Tile getTileAt(int x, int y) {
        try {
            return tiles[x][y];
        } catch (Exception e) {
            return null;
        }
    }

    public int getAmountToSpawn() {
        return (int) (5 * Math.pow(5, ((roundNum / 5.0))));
    }

    public List<Tile> getTilesInRadius(int x, int y, float range) {
        ArrayList<Tile> result = new ArrayList<>();

        for (Tile[] t : tiles) {
            for (Tile tile : t) {
                Rectangle r = new Rectangle(tile.getX() * Tile.TILE_SIZE, tile.getY() * Tile.TILE_SIZE, Tile.TILE_SIZE, Tile.TILE_SIZE);
                if (circleIntersectsRectangle(x, y, (int) range, r)) {
                    System.out.println("Found " + tile);
                    result.add(tile);
                }
            }
        }

        return result;
    }


    public static boolean circleIntersectsRectangle(int cx, int cy, int r, Rectangle rect) {
        // Finding the closest point on the rectangle to the circle's center
        int closestX = clamp(cx, rect.x, rect.x + rect.width);
        int closestY = clamp(cy, rect.y, rect.y + rect.height);

        // Calculating the distance from the closest point to the circle's center
        int distanceX = cx - closestX;
        int distanceY = cy - closestY;
        int distanceSquared = distanceX * distanceX + distanceY * distanceY;

        // The circle intersects if the squared distance is less than the squared radius
        return distanceSquared <= r * r;
    }

    private static int clamp(int value, int min, int max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }


    public Mob getMobAt(Tile t) {
        for (Entity e : this.entities) {
            if (!(e instanceof Mob)) {
                continue;
            }
            if (e.getTileX() == t.getX() && e.getTileY() == t.getY()) {
                return (Mob) e;
            }
        }
        return null;
    }

    public long getCurrentSpawnRate() {
        if (roundNum > 5 && roundNum <10) {
            return 400;
        } else if(roundNum >= 10){
            return 250;
        }else {
            return 500;
        }
    }

    public Tower getTowerAt(int tileX, int tileY) {
        return towers[tileX][tileY];
    }
}
