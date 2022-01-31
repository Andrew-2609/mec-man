package com.ndrewcoding.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.net.URISyntaxException;

import com.ndrewcoding.entities.Enemy;
import com.ndrewcoding.entities.Entity;
import com.ndrewcoding.entities.Player;

public class GameWonScreen {
    public String[] options = {"jogar_novamente", "meu_github", "sair"};

    public int currentOption = 0;
    public int maxOption = options.length;
    private final int[] randX = new int[9];
    private final int[] randY = new int[9];

    public boolean up, down, enter;
    private boolean gameWonMusicOn = true;

    private final BufferedImage[] starSprite;
    private int frames = 0;
    private int index = 0;

    GameWonScreen() {
        starSprite = new BufferedImage[6];
        for (int i = 0; i < 6; i++) {
            starSprite[i] = Game.spritesheet.getSprite((i * 16), 96, 16, 16);
        }
        for (int i = 0; i < 9; i++) {
            randX[i] = Entity.rand.nextInt(480);
            randY[i] = Entity.rand.nextInt(480);
        }
    }

    public void tick() {
        Sound.stopAllBackgroundMusic();

        if (gameWonMusicOn) {
            Sound.gameWonMusic.loop();
            gameWonMusicOn = false;
        }

        if (up) {
            up = false;
            currentOption--;
            Sound.menuChange.play();
            if (currentOption < 0) {
                currentOption = (maxOption - 1);
            }
        } else if (down) {
            down = false;
            currentOption++;
            Sound.menuChange.play();
            if (currentOption >= maxOption) {
                currentOption = 0;
            }
        }

        if (enter) {
            Game.checkCurrentLevelToSetRespectiveMusic();
            enter = false;
            switch (options[currentOption]) {
                case "jogar_novamente":
                    Game.restartGame = true;
                    Enemy.enemyStaticSpeed = 65;
                    Player.resetScore();
                    break;
                case "sair":
                    System.exit(0);
                case "meu_github":
                    URI uri;
                    try {
                        uri = new URI("https://github.com/Andrew-2609");
                        Menu.openWebpage(uri);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

        frames++;
        int maxFrames = 24;
        if (frames == maxFrames) {
            frames = 0;
            index++;
            int maxIndex = 5;
            if (index > maxIndex) {
                index = 0;
            }
        }
    }

    public void render(Graphics g) {
        g.drawImage(Game.spritesheet.getSprite(0, 112, 480, 480), 0, 0, null);
        g.setColor(new Color(0, 0, 0, 127));
        g.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);

        for (int i = 0; i < 9; i++) {
            g.drawImage(starSprite[index], randX[i], randY[i], 32, 32, null);
        }

        g.setColor(Color.YELLOW);
        g.setFont(new Font("century gothic", Font.ITALIC, 30));
        Menu.drawCenteredString(Menu.language.congratulations, 480, 100, g);
        Menu.drawCenteredString(Menu.language.youWon, 480, 135, g);

        g.setColor(Color.cyan);
        g.setFont(new Font("impact", Font.PLAIN, 15));
        Menu.drawCenteredString(Menu.language.finalScore, 480, 360, g);
        Menu.drawCenteredString(Player.getScore() + "", 480, 380, g);

        g.setColor(Color.white);
        g.setFont(new Font("arial", Font.PLAIN, 20));
        Menu.drawCenteredString(Menu.language.playAgain, 480, 220, g);
        Menu.drawCenteredString(Menu.language.myGithub, 480, 270, g);
        Menu.drawCenteredString(Menu.language.exit, 480, 320, g);

        switch (options[currentOption]) {
            case "jogar_novamente":
                g.setColor(Color.YELLOW);
                Menu.drawCenteredString(Menu.language.playAgain, 480, 220, g);
                break;
            case "meu_github":
                g.setColor(Color.YELLOW);
                Menu.drawCenteredString(Menu.language.myGithub, 480, 270, g);
                break;
            case "sair":
                g.setColor(Color.YELLOW);
                Menu.drawCenteredString(Menu.language.exit, 480, 320, g);
                break;
        }

        g.setColor(Color.white);
        g.setFont(new Font("century gothic", Font.BOLD, 12));
        Menu.drawCenteredString(Menu.language.thanksForPlaying, 480, 415, g);
        Menu.drawCenteredString(Menu.language.gameMadeBy, 480, 435, g);
        Menu.drawCenteredString("Andrew Monteiro", 480, 450, g);
    }
}
