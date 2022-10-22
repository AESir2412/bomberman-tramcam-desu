package uet.oop.bomberman;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.DatagramPacket;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.sound.sampled.SourceDataLine;

import javafx.print.PageOrientation;
import javafx.util.Pair;

import uet.oop.bomberman.control.Camera;
import uet.oop.bomberman.control.CollisionManager;
import uet.oop.bomberman.control.KeyCheck;
import uet.oop.bomberman.entities.BalloomEnemy;
import uet.oop.bomberman.entities.BombItem;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.DollEnemy;
import uet.oop.bomberman.entities.Enemy;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.FlameItem;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.NightmareEnemy;
import uet.oop.bomberman.entities.OnealEnemy;
import uet.oop.bomberman.entities.Portal;
import uet.oop.bomberman.entities.SpeedItem;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.entities.DuplicateEnemy;
import uet.oop.bomberman.graphics.Sprite;

import uet.oop.bomberman.entities.Entity;

import java.util.List;

public class Map {
    protected List<List<Entity>> map;
    protected List<Entity> flexibleEntities;
    protected int[][] list_item;
    protected Camera cam;
    protected List<Pair<Integer, Integer>> charCoordinate;
    protected int width;
    protected int height;
    protected int maplvl;
    protected int current_Bomber = 0;
    protected int Bomber_num = 1;
    protected int livingEnemy_num = 0;
    protected int diedBomber_num = 0;
    protected int portalPlayer_num = 0;
    protected boolean isWin;
    protected int whoWin;
    public static final int _BOMBER_MAX_NUM_ = 4;

    public Map() {}

    public Map(int lvl, KeyCheck keyCheck) {
        Constructor(lvl, keyCheck);
    }

    public void Constructor(int lvl, KeyCheck keyCheck) {
        map = new ArrayList<>();
        flexibleEntities = new ArrayList<>();
        list_item = new int[40][40];
        maplvl = lvl;
        charCoordinate = new ArrayList<>();
        livingEnemy_num = 0;
        portalPlayer_num = 0;

        Path currentDirectory = Paths.get().toAbsolutePath();
        File file = new File(currentDirectory.normalize().toString() + "/res/levels/Level" + lvl + ".txt");
        try {
            Scanner sc = new Scanner(file);
            height = sc.nextInt();
            height = sc.nextInt();
            width = sc.nextInt();
            list_item = new int[height][width];
            sc.nextLine();
            for (int i = 1; i <= _BOMBER_MAX_NUM_; i++) {
                Bomber tmp = new Bomber(1, 1, Sprite.player_right.getFxImage(), keyCheck,
                        new CollisionManager(this));
                tmp.setIndexOfFlex(i - 1);
                flexibleEntities.add(tmp);
            }
            for (int i = 0; i < height; i++) {
                String tmpStr = sc.nextLine();
                List<Entity> tmpList = new ArrayList<>();
                for (int j = 0; j < width; j++) {
                    switch (tmpStr.charAt(j)) {
                        case '*':
                            tmpList.add(new Brick(j, i, Sprite.brick.getFxImage()));
                            break;
                        case '#':
                            tmpList.add(new Wall(j, i, Sprite.wall.getFxImage()));
                            break;
                        case '1':
                            Enemy temp = new BalloomEnemy(j, i, Sprite.balloom_left1.getFxImage(),
                                    new CollisionManager(this));
                            flexibleEntities.add(temp);
                            temp.setIndexOfFlex(flexibleEntities.size() - 1);
                            tmpList.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            ++livingEnemy_num;
                            break;
                        case '2':
                            temp = new OnealEnemy(j, i, Sprite.oneal_left1.getFxImage(), new CollisionManager(this));
                            flexibleEntities.add(temp);
                            temp.setIndexOfFlex(flexibleEntities.size() - 1);
                            tmpList.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            ++livingEnemy_num;
                            break;
                        case '3':
                            temp = new DollEnemy(j, i, Sprite.doll_left1.getFxImage(), new CollisionManager(this));
                            flexibleEntities.add(temp);
                            temp.setIndexOfFlex(flexibleEntities.size() - 1);
                            tmpList.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            ++livingEnemy_num;
                            break;
                        case '4':
                            temp = new NightmareEnemy(j, i, Sprite.doll_left1.getFxImage(), new CollisionManager(this));
                            flexibleEntities.add(temp);
                            temp.setIndexOfFlex(flexibleEntities.size() - 1);
                            tmpList.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            ++livingEnemy_num;
                            break;
                        case '5':
                            temp = new DuplicateEnemy(j, i, Sprite.doll_left1.getFxImage(), new CollisionManager(this));
                            flexibleEntities.add(temp);
                            temp.setIndexOfFlex(flexibleEntities.size() - 1);
                            tmpList.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            ++livingEnemy_num;
                            break;
                        case 'p':
                            charCoordinate.add(new Pair<>(j, i));
                            tmpList.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            break;
                        case 'x':
                            tmpList.add(new Brick(j, i, Sprite.brick.getFxImage()));
                            list_item[i][j] = Portal.code;
                            break;
                        case 'b':
                            tmpList.add(new Brick(j, i, Sprite.brick.getFxImage()));
                            list_item[i][j] = BombItem.code;
                            break;
                        case 'f':
                            tmpList.add(new Brick(j, i, Sprite.brick.getFxImage()));
                            list_item[i][j] = FlameItem.code;
                            break;
                        case 's':
                            tmpList.add(new Brick(j, i, Sprite.brick.getFxImage()));
                            list_item[i][j] = SpeedItem.code;
                            break;
                        default:
                            tmpList.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            break;
                    }
                }
                map.add(tmpList);
            }
            setupBomberman(keyCheck);
            cam = new Camera(0, 0, width, height);
            sc.close();
            }
            catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void setupBomberman(KeyCheck keyCheck) {
        for (int i = 0; i < charCoordinate.size(); ++i) {
            Pair<Integer, Integer> coor = charCoordinate.get(i);
            flexibleEntities.get(i).setXUnit(coor.getKey());
            flexibleEntities.get(i).setYUnit(coor.getValue());
        }
    }

    public void update() {
        Bomber bomber = (Bomber)flexibleEntities.get(current_Bomber);
        if(bomber.getDeath() == false) bomber.update();

        for (int i = 0; i < flexibleEntities.size(); ++i) {
            if (flexibleEntities.get(i) instanceof Bomber) {
                bomber = (Bomber)flexibleEntities.get(i);
                if(bomber.getDeath() == true) {
                    continue;
                }
                bomber.getBombManager().update();
                bomber.updateItems();
                bomber.checkDeath();
            }
            if (flexibleEntities.get(i) instanceof Enemy) {
                if (current_Bomber != 0) {
                    flexibleEntities.get(i).updateCount();
                } else {
                    if (flexibleEntities.get(i) instanceof BalloomEnemy || flexibleEntities.get(i) instanceof DollEnemy)
                    {
                        flexibleEntities.get(i).update();
                    }
                    if (flexibleEntities.get(i) instanceof OnealEnemy || flexibleEntities.get(i) instanceof NightmareEnemy)
                    {
                        flexibleEntities.get(i).update(map, getBombermans());
                    }
                    if (flexibleEntities.get(i) instanceof DuplicateEnemy)
                    {
                        flexibleEntities.get(i).update(map, getBombermans());
                    }
                }
            }
        }
        cam.update(flexibleEntities.get(current_Bomber));
    }

    public void replace(int x, int y, Entity entity) {
        map.get(y).set(x, entity);
    }

    public List<List<Entity>> getMap() {
        return this.map;
    }

    public Entity getBomberman() {
        return this.flexibleEntities.get(current_Bomber);
    }

    public List<Bomber> getBombermans() {
        List<Bomber> bombermans = new ArrayList<>();
        for (int i = 0; i < Bomber_num; ++i) {
            bombermans.add((Bomber)flexibleEntities.get(i));
        }
        return bombermans;
    }

    public List<Bomber> getAllBombermans() {
        List<Bomber> bombermans = new ArrayList<>();
        for (int i = 0; i < _BOMBER_MAX_NUM_; ++i)
            bombermans.add((Bomber) flexibleEntities.get(i));
        return bombermans;
    }

    public List<Enemy> getEnemy() {
        List<Enemy> enemies = new ArrayList<>();
        for (int i = 0; i < flexibleEntities.size(); ++i) {
            if (flexibleEntities.get(i) instanceof Enemy)
            {
                enemies.add((Enemy) flexibleEntities.get(i));
            }
        }
        return enemies;
    }

    public int getLevel() {
        return this.maplvl;
    }

    public Camera getCamera() {
        return this.cam;
    }

    public List<Entity> getFlexibleEntities() {
        return this.flexibleEntities;
    }

    public Entity getCoordinate(int x, int y) {
        int modX = Math.round(x / Sprite.SCALED_SIZE);
        int modY = Math.round(y / Sprite.SCALED_SIZE);
        return map.get(modY).get(modX);
    }

    public void setCurrentBomber(int tmp) {
        this.current_Bomber = tmp;
    }

    public int getCurrentBomber() {
        return this.current_Bomber;
    }

    public void setNumberBomber(int tmp) {
        this.Bomber_num = tmp;
    }

    public int getNumberBomber() {
        return this.Bomber_num;
    }

    public int getItem(int x, int y) {
        return list_item[y][x];
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

//    public void sendItemRand() {
//        if (current_Bomber != 0) {
//            return;
//        }
//        for (int i = 0; i < height; i++) {
//            for (int j = 0; j < width; j++) {
//                if (map.get(i).get(j) instanceof Brick) {
//                    int rand = (int) (Math.random() * 8) + 1;
//                    if (rand > 3)
//                        continue;
//                    String msg = "0,Item," + rand + "," + j + "," + i;
//                    byte[] data = msg.getBytes();
//                    DatagramPacket outPacket = new DatagramPacket(data, data.length, SocketGame.address,
//                            SocketGame.PORT);
//                    try {
//                        SocketGame.socket.send(outPacket);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//    }

    public void setItem(int y, int x, int type) {
        list_item[y][x] = type;
    }
    public void setNumberEnemyLiving(int number) {
        livingEnemy_num = number;
    }

    public int getNumberEnemyLiving() {
        return livingEnemy_num;
    }

    public void setNumberPlayer
}
