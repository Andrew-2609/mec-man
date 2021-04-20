package com.ndrewcoding.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.ndrewcoding.entities.Enemy;
import com.ndrewcoding.entities.Entity;
import com.ndrewcoding.entities.Infection;
import com.ndrewcoding.entities.MecSlime;
import com.ndrewcoding.entities.Player;
import com.ndrewcoding.graficos.Spritesheet;
import com.ndrewcoding.graficos.UI;
import com.ndrewcoding.world.World;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 240;
	public static final int HEIGHT = 240;
	public static final int SCALE = 2;

	private BufferedImage image;

	public static List<Entity> entities;
	public static List<Infection> infection;
	public static List<MecSlime> mecSlime;
	public static Spritesheet spritesheet;
	public static World world;
	public static Player player;

	public UI ui;
	public Menu menu;
	public GameOverScreen gameOverScreen;
	public GameWonScreen gameWonScreen;

	public static int trabalhosAtual = 0;
	public static int trabalhosContagem = 0;

	public static String level = "/level2.png";
	public static int CUR_LEVEL = 2, MAX_LEVEL = 10;
	public static boolean restartGame = false;

	public static String gameState = "MENU";

	public Game() {
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		initFrame();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

		// Inicializando objetos.
		spritesheet = new Spritesheet("/spritesheet.png");
		player = new Player(0, 0, 16, 16, 1, spritesheet.getSprite(32, 0, 16, 16));
		entities = new ArrayList<Entity>();
		infection = new ArrayList<Infection>();
		mecSlime = new ArrayList<MecSlime>();
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
			myImg = ImageIO.read(imgStream);
			frame.setIconImage(myImg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
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

	public static void main(String args[]) {
		Game game = new Game();
		game.start();
		checarLevelSong();
	}

	public void tick() {
		if (Game.gameState.equals("NORMAL")) {
			for (int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.tick();
			}

			for (int i = 0; i < infection.size(); i++) {
				Infection in = infection.get(i);
				in.tick();
			}

			for (int i = 0; i < mecSlime.size(); i++) {
				MecSlime ms = mecSlime.get(i);
				ms.tick();
			}

			if (trabalhosAtual == trabalhosContagem) {
				// Avançar para o próximo level
				CUR_LEVEL++;
				Player.pontuacao = Player.pontuacao + 200;
				Enemy.rangeInimigo += 40;
				if (CUR_LEVEL > MAX_LEVEL) {
					gameState = "GAME_WON";
					CUR_LEVEL = 1;
				}
				String newWorld = "level" + CUR_LEVEL + ".png";
				Sound.levelUp.play();
				World.restartGame(newWorld);
				Enemy.aumentarVelocidadeInimigo(1);
			}
		} else if (gameState.equals("MENU")) {
			// Menu
			player.updateCamera();
			menu.tick();
		} else if (gameState.equals("GAME_OVER")) {
			// Enemy.velocidadeInimigo = 65;
			gameOverScreen.tick();
			if (restartGame) {
				String newWorld = "level" + CUR_LEVEL + ".png";
				World.restartGame(newWorld);
				gameState = "NORMAL";
				restartGame = false;
			}
		} else if (gameState.equals("GAME_WON")) {
			gameWonScreen.tick();
			if (restartGame) {
				CUR_LEVEL = 1;
				String newWorld = "level" + CUR_LEVEL + ".png";
				World.restartGame(newWorld);
				gameState = "NORMAL";
				restartGame = false;
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

		/* Renderização do jogo */
		// Graphics2D g2 = (Graphics2D) g;
		world.render(g);
		Collections.sort(entities, Entity.nodeSorter);
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}

		for (int i = 0; i < infection.size(); i++) {
			Infection in = infection.get(i);
			in.render(g);
		}

		for (int i = 0; i < mecSlime.size(); i++) {
			MecSlime ms = mecSlime.get(i);
			ms.render(g);
		}
		/***/
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		ui.render(g);

		if (Game.gameState.equals("MENU")) {
			menu.render(g);
		} else if (Game.gameState.equals("GAME_OVER")) {
			gameOverScreen.render(g);
		} else if (Game.gameState.equals("GAME_WON")) {
			gameWonScreen.render(g);
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

	public static void checarLevelSong() {
		Sound.gameWonMusic.stop();
		Sound.gameOverMusic.stop();
		if (CUR_LEVEL <= 3) {
			Sound.bglvl1.loop();
		} else if (CUR_LEVEL == 4) {
			Sound.bglvl1.stop();
			Sound.bglvl4.loop();
		} else if (CUR_LEVEL == 5) {
			Sound.bglvl4.stop();
			Sound.bglvl5.loop();
		} else if (CUR_LEVEL == 6) {
			Sound.bglvl5.stop();
			Sound.bglvl6.loop();
		} else if (CUR_LEVEL == 7) {
			Sound.bglvl6.stop();
			Sound.bglvl7.loop();
		} else if (CUR_LEVEL == 8) {
			Sound.bglvl7.stop();
			Sound.bglvl8.loop();
		} else if (CUR_LEVEL == 9) {
			Sound.bglvl8.stop();
			Sound.bglvl9.loop();
		} else if (CUR_LEVEL == 10) {
			Sound.bglvl9.stop();
			Sound.bglvl10.loop();
		} else if (gameState == "GAME_WON") {
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

			if (Game.gameState.equals("MENU")) {
				menu.up = true;
			} else if (Game.gameState.equals("GAME_OVER")) {
				gameOverScreen.up = true;
			} else if (Game.gameState.equals("GAME_WON")) {
				gameWonScreen.up = true;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.down = false;

			if (Game.gameState.equals("MENU")) {
				menu.down = true;
			} else if (Game.gameState.equals("GAME_OVER")) {
				gameOverScreen.down = true;
			} else if (Game.gameState.equals("GAME_WON")) {
				gameWonScreen.down = true;
			}
		}

		/*
		 * if (e.getKeyCode() == KeyEvent.VK_T) { Game.player.teleporte(); }
		 */

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (Game.gameState.equals("MENU")) {
				menu.enter = true;
			} else if (Game.gameState.equals("NORMAL")) {
				gameState = "MENU";
				Menu.pause = true;
			} else if (Game.gameState.equals("GAME_OVER")) {
				gameOverScreen.enter = true;
			} else if (Game.gameState.equals("GAME_WON")) {
				gameWonScreen.enter = true;
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if (gameState.equals("NORMAL")) {
				gameState = "MENU";
				Menu.pause = true;
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

}
