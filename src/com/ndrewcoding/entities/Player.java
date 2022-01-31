package com.ndrewcoding.entities;

import com.ndrewcoding.main.Game;
import com.ndrewcoding.main.GameState;
import com.ndrewcoding.main.Sound;
import com.ndrewcoding.world.Camera;
import com.ndrewcoding.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Player extends Entity {

    public boolean right, up, left, down;
    public int lastDirection = 1;

    public static int xInitial = 0, yInitial = 0;

    public boolean isDamaged = false;
    public boolean isHealing = false;
    public boolean isRising = false;

    private int damageFrames = 0;
    private int healingFrames = 0;
    private int risingFrames = 0;

    public BufferedImage[] spriteRight;
    public BufferedImage[] spriteLeft;

    private int frames = 0;
    private int index = 0;

    private int previousSpeed;
    private int framesIniciaisCafe = 0;
    private boolean moved = false;

    public int life = 200, maxLife = 200;

    public int tries = 2;

    private static int score = 0;

    public Player(int x, int y, int width, int height, double speed, BufferedImage sprite, String playerSkin) {
        super(x, y, width, height, speed, sprite);
        Map<String, int[]> playerSkins = populatePlayerSkins();

        if (playerSkins.get(playerSkin) == null) {
            playerSkin = "normal";
        }

        int yPlayerMovingRight = playerSkins.get(playerSkin)[0];
        int yPlayerMovingLeft = playerSkins.get(playerSkin)[1];

        this.spriteRight = new BufferedImage[4];
        this.spriteLeft = new BufferedImage[4];

        populatePlayerSprites(yPlayerMovingRight, yPlayerMovingLeft);
    }

    private Map<String, int[]> populatePlayerSkins() {
        Map<String, int[]> playerSkins = new HashMap<>();
        playerSkins.put("normal", new int[]{0, 16});
        playerSkins.put("suit", new int[]{32, 48});
        return playerSkins;
    }

    private void populatePlayerSprites(int yPlayerMovingRight, int yPlayerMovingLeft) {
        for (int i = 0; i < 4; i++) {
            this.spriteRight[i] = Game.playerSpritesheet.getSprite(i * 16, yPlayerMovingRight, 16, 16);
            this.spriteLeft[i] = Game.playerSpritesheet.getSprite(i * 16, yPlayerMovingLeft, 16, 16);
        }
    }

    public void tick() {
        this.depth = 2;

        if (score <= 0) {
            score = 0;
        }

        this.moved = false;

        checkPlayerMovement();
        updatePlayerSpritesAndSoundsIfMoved();
        checkIfCoffeeEffectHasBeenTriggered();

        checkCollisionWithEnemy();
        checkCollisionWithInfection();

        checkIfPlayerIsGettingDamage();
        checkIfPlayerIsHealing();
        checkIfPlayerIsRising();

        catchHoliday();
        catchCoffee();
        catchGraduationHat();
        verifyIfPlayerIsOnSnow();

        updateCamera();
    }

    private void checkCollisionWithEnemy() {
        for (Enemy enemy : Game.enemies) {
            if (Entity.isColliding(this, enemy) && !Enemy.ghostMode) {
                takeDamage();
            } else if (Entity.isColliding(this, enemy) && Enemy.ghostMode) {
                this.life++;
                this.isHealing = true;
                if (this.life >= this.maxLife) {
                    this.life = this.maxLife;
                }
            }
        }
    }

    private void checkCollisionWithInfection() {
        for (Infection infection : Game.infections) {
            if (Entity.isColliding(this, infection)) {
                takeDamage();
            }
        }
    }

    private void checkPlayerMovement() {
        if (this.right && World.isFree((int) (this.x + this.speed), this.getY())) {
            this.moved = true;
            this.x += this.speed;
            this.lastDirection = 1;
        } else if (this.left && World.isFree((int) (this.x - this.speed), this.getY())) {
            this.moved = true;
            this.x -= this.speed;
            this.lastDirection = -1;
        }

        if (this.up && World.isFree(this.getX(), (int) (this.y - this.speed))) {
            this.moved = true;
            this.y -= this.speed;
        } else if (this.down && World.isFree(this.getX(), (int) (this.y + this.speed))) {
            this.moved = true;
            this.y += this.speed;
        }
    }

    private void updatePlayerSpritesAndSoundsIfMoved() {
        if (this.moved) {
            this.frames++;
            int maxFrames = 6;
            if (this.frames == maxFrames) {
                this.frames = 0;
                this.index++;

                int maxIndex = 3;
                if (this.index > maxIndex) {
                    if (Game.CUR_LEVEL != 5) {
                        Sound.playerMovingSound.play();
                    } else {
                        Sound.snowFootstep.play();
                    }
                    this.index = 0;
                }
            }
        } else {
            Sound.snowFootstep.stop();
        }
    }

    private void checkIfPlayerIsGettingDamage() {
        if (this.isDamaged) {
            this.damageFrames++;
            if (this.damageFrames == 8) {
                Sound.playerHurtEffect.play();
                this.damageFrames = 0;
                this.isDamaged = false;
                score -= 2;
            }
        }
    }

    private void checkIfPlayerIsHealing() {
        if (this.isHealing) {
            this.healingFrames++;
            if (this.healingFrames == 8) {
                Sound.healingSound.play();
                this.healingFrames = 0;
                this.isHealing = false;
                score += 2;
            }
        }
    }

    private void checkIfPlayerIsRising() {
        if (this.isRising) {
            resetPlayerPosition();
            blockPlayerMovement();

            World.generateParticles(1, this.getX() + 8, this.getY() + 8, Color.red);
            this.risingFrames++;
            Enemy.ghostMode = true;

            if (this.risingFrames == 150) {
                this.risingFrames = 0;
                this.isRising = false;
                Enemy.ghostMode = false;
            }
        }
    }

    private void resetPlayerPosition() {
        Game.player.setX(xInitial);
        Game.player.setY(yInitial);
    }

    private void blockPlayerMovement() {
        this.moved = false;
        this.left = false;
        this.right = false;
        this.up = false;
        this.down = false;
    }

    private void takeDamage() {
        life--;
        isDamaged = true;
        if (life <= 0) {
            if (tries > 0) {
                tries--;
                life = 200;
                Player.score -= 600;
                isRising = true;
                World.generateParticles(500, this.getX(), this.getY(), Color.ORANGE);
                if (tries == 0) {
                    isRising = false;
                    Game.gameState = GameState.GAME_OVER;
                    Sound.gameOverMusic.loop();
                }
            }
        }
    }

    public static int getScore() {
        return score;
    }

    public static void addScore(int score) {
        Player.score += score;
    }

    public static void resetScore() {
        Player.score = 0;
    }

    private void catchHoliday() {
        for (int i = 0; i < Game.entities.size(); i++) {
            Entity current = Game.entities.get(i);
            if (current instanceof Holiday) {
                if (Entity.isColliding(this, current)) {
                    Sound.calendarCaught.play();
                    Game.entities.remove(i);
                    Enemy.ghostMode = true;
                    for (int i1 = 0; i1 < Game.enemies.size(); i1++) {
                        Enemy e = Game.enemies.get(i1);
                        e.ghostFrames = 0;
                    }
                    score += 10;
                    return;
                }
            }
        }
    }

    private void checkIfCoffeeEffectHasBeenTriggered() {
        if (this.framesIniciaisCafe != 0) {
            this.framesIniciaisCafe++;

            int coffeeEffectDurationTime = 240;
            if (this.framesIniciaisCafe == coffeeEffectDurationTime) {
                this.framesIniciaisCafe = 0;
                for (Enemy enemy : Game.enemies) {
                    enemy.enemyCurrentSpeed = previousSpeed;
                }
            }
        }
    }

    private void catchCoffee() {
        for (int i = 0; i < Game.entities.size(); i++) {
            Entity current = Game.entities.get(i);
            if (current instanceof Coffee) {
                if (Entity.isColliding(this, current)) {
                    Game.entities.remove(i);
                    framesIniciaisCafe++;
                    World.generateParticles(15, getX() + 8, getY() + 8, Color.orange);
                    Sound.slowMotionSound.play();
                    for (int i1 = 0; i1 < Game.enemies.size(); i1++) {
                        Enemy e = Game.enemies.get(i1);
                        if (e.enemyCurrentSpeed == 25) {
                            previousSpeed = 65;
                        } else {
                            previousSpeed = e.enemyCurrentSpeed;
                        }
                        e.enemyCurrentSpeed = 25;
                    }
                    life -= 10;
                    isDamaged = true;
                    score -= 10;
                    return;
                }
            }
        }
    }

    private void verifyIfPlayerIsOnSnow() {
        for (int i = 0; i < Game.entities.size(); i++) {
            Entity current = Game.entities.get(i);
            if (current instanceof SnowFloor) {
                if (Entity.isColliding(this, current)) {
                    World.generateParticles(10, this.getX() + 8, this.getY() + 8, new Color(127, 255, 255));
                    return;
                }
            }
        }
    }

    private void catchGraduationHat() {
        for (int i = 0; i < Game.entities.size(); i++) {
            Entity current = Game.entities.get(i);
            if (current instanceof GraduationHat) {
                if (Entity.isColliding(this, current)) {
                    Sound.graduationCaught.play();
                    Game.entities.remove(i);
                    score += 600;
                    Enemy.increaseEnemySpeed(1);
                    return;
                }
            }
        }
    }

    public void render(Graphics g) {
        Game.player.speed = 1;
        if (isHealing) {
            World.generateParticles(2, this.getX() + 8, this.getY() + 8, Color.green);
        } else if (isDamaged) {
            World.generateParticles(1, this.getX() + 8, this.getY() + 8, Color.red);

            if (Game.CUR_LEVEL == 9) {
                Game.player.speed = 0.5;
            }
        }

        if (moved) {
            if (lastDirection == 1) {
                g.drawImage(spriteRight[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
            } else if (lastDirection == -1) {
                g.drawImage(spriteLeft[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
            }
        } else {
            if (lastDirection == 1) {
                g.drawImage(spriteRight[0], this.getX() - Camera.x, this.getY() - Camera.y, null);
            } else if (lastDirection == -1) {
                g.drawImage(spriteLeft[0], this.getX() - Camera.x, this.getY() - Camera.y, null);
            }
        }
    }

}
