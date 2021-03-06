package com.ndrewcoding.main;

import com.ndrewcoding.entities.*;
import com.ndrewcoding.graphics.Spritesheet;
import com.ndrewcoding.graphics.UI;
import com.ndrewcoding.world.World;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Game extends Canvas implements Runnable, KeyListener {

    public static JFrame frame;
    private Thread thread;
    private boolean isRunning = true;
    public static final int WIDTH = 240;
    public static final int HEIGHT = 240;
    public static final int SCALE = 2;

    private final BufferedImage image;

    public static List<Entity> entities;
    public static List<Enemy> enemies;
    public static List<Infection> infections;

    public static Spritesheet spritesheet;
    public static Spritesheet playerSpritesheet;

    public static World world;
    public static Player player;

    public UI ui;
    public Menu menu;
    public GameOverScreen gameOverScreen;
    public GameWonScreen gameWonScreen;

    public static int currentHomeworks = 0;
    public static int homeworksCount = 0;

    public static String level = "/level1.png";
    public static int CUR_LEVEL = 1, MAX_LEVEL = 10;
    public static boolean restartGame = false;

    public static GameState gameState = GameState.MENU;

    public Game() {
        addKeyListener(this);
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        initFrame();
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

        spritesheet = new Spritesheet("/spritesheet.png");
        playerSpritesheet = new Spritesheet("/player_spritesheet.png");

        player = new Player(0, 0, 16, 16, 1, playerSpritesheet.getSprite(0, 0, 16, 16), "normal");
        entities = new ArrayList<>();
        enemies = new ArrayList<>();
        infections = new ArrayList<>();
        world = new World(level);
        ui = new UI();

        entities.add(player);

        menu = new Menu();
        gameOverScreen = new GameOverScreen();
        gameWonScreen = new GameWonScreen();
    }

    public void initFrame() {
        frame = new JFrame("Mec-Man");
        frame.add(this);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        String imagePath = "/gameIcon.png";
        InputStream imgStream = Game.class.getResourceAsStream(imagePath);
        BufferedImage myImg;
        try {
            myImg = ImageIO.read(Objects.requireNonNull(imgStream));
            frame.setIconImage(myImg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void start() {
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    public synchronized void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
        checkCurrentLevelToSetRespectiveMusic();
    }

    public void tick() {
        switch (Game.gameState) {
            case NORMAL -> {
                for (int i = 0; i < entities.size(); i++) {
                    entities.get(i).tick();
                }
                for (int i = 0; i < enemies.size(); i++) {
                    enemies.get(i).tick();
                }
                for (int i = 0; i < infections.size(); i++) {
                    infections.get(i).tick();
                }

                if (currentHomeworks == homeworksCount) {
                    CUR_LEVEL++;
                    Player.addScore(200);
                    Enemy.enemyRange += 40;
                    if (CUR_LEVEL > MAX_LEVEL) {
                        gameState = GameState.GAME_WON;
                        CUR_LEVEL = 1;
                    }
                    String newWorld = "level" + CUR_LEVEL + ".png";
                    Sound.levelUp.play();
                    World.restartGame(newWorld);
                    Enemy.increaseEnemySpeed(1);
                }
            }
            case MENU -> {
                player.updateCamera();
                menu.tick();
            }
            case GAME_OVER -> {
                gameOverScreen.tick();
                if (restartGame) {
                    String newWorld = "level" + CUR_LEVEL + ".png";
                    World.restartGame(newWorld);
                    gameState = GameState.NORMAL;
                    restartGame = false;
                }
            }
            case GAME_WON -> {
                gameWonScreen.tick();
                if (restartGame) {
                    CUR_LEVEL = 1;
                    String newWorld = "level" + CUR_LEVEL + ".png";
                    World.restartGame(newWorld);
                    gameState = GameState.NORMAL;
                    restartGame = false;
                }
            }
        }
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = image.getGraphics();
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        world.render(g);
        entities.sort(Entity.nodeSorter);
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).render(g);
        }

        enemies.sort(Entity.nodeSorter);
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).render(g);
        }

        infections.sort(Entity.nodeSorter);
        for (int i = 0; i < infections.size(); i++) {
            infections.get(i).render(g);
        }
        g.dispose();
        g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        ui.render(g);

        switch (Game.gameState) {
            case MENU -> menu.render(g);
            case GAME_OVER -> gameOverScreen.render(g);
            case GAME_WON -> gameWonScreen.render(g);
        }

        bs.show();
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int frames = 0;
        double timer = System.currentTimeMillis();
        requestFocus();
        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                tick();
                render();
                frames++;
                delta--;
            }

            if (System.currentTimeMillis() - timer >= 1000) {
                System.out.println("FPS: " + frames);
                frames = 0;
                timer += 1000;
            }

        }

        stop();
    }

    public static void checkCurrentLevelToSetRespectiveMusic() {
        Sound.gameWonMusic.stop();
        Sound.gameOverMusic.stop();
        if (CUR_LEVEL <= 3) {
            Sound.bgLevelOneToThree.loop();
        } else if (CUR_LEVEL == 4) {
            Sound.bgLevelOneToThree.stop();
            Sound.bgLevelFour.loop();
        } else if (CUR_LEVEL == 5) {
            Sound.bgLevelFour.stop();
            Sound.bgLevelFive.loop();
        } else if (CUR_LEVEL == 6) {
            Sound.bgLevelFive.stop();
            Sound.bgLevelSix.loop();
        } else if (CUR_LEVEL == 7) {
            Sound.bgLevelSix.stop();
            Sound.bgLevelSeven.loop();
        } else if (CUR_LEVEL == 8) {
            Sound.bgLevelSeven.stop();
            Sound.bgLevelEight.loop();
        } else if (CUR_LEVEL == 9) {
            Sound.bgLevelEight.stop();
            Sound.bgLevelNine.loop();
        } else if (CUR_LEVEL == 10) {
            Sound.bgLevelNine.stop();
            Sound.bgLevelTen.loop();
        } else if (gameState.equals(GameState.GAME_WON)) {
            Sound.gameWonMusic.loop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            player.right = true;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            player.left = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
            player.up = true;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
            player.down = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            player.right = false;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            player.left = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
            player.up = false;

            switch (Game.gameState) {
                case MENU -> menu.up = true;
                case GAME_OVER -> gameOverScreen.up = true;
                case GAME_WON -> gameWonScreen.up = true;
            }

        } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
            player.down = false;

            switch (Game.gameState) {
                case MENU -> menu.down = true;
                case GAME_OVER -> gameOverScreen.down = true;
                case GAME_WON -> gameWonScreen.down = true;
            }

        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            switch (Game.gameState) {
                case MENU -> menu.enter = true;
                case NORMAL -> {
                    gameState = GameState.MENU;
                    Menu.pause = true;
                }
                case GAME_OVER -> gameOverScreen.enter = true;
                case GAME_WON -> gameWonScreen.enter = true;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (gameState.equals(GameState.NORMAL)) {
                gameState = GameState.MENU;
                Menu.pause = true;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
