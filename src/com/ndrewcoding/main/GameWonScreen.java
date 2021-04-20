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
	public String[] options = { "jogar_novamente", "meu_github", "sair" };

	public int currentOption = 0;
	public int maxOption = options.length;
	private int[] randX = new int[9];
	private int[] randY = new int[9];

	public boolean up, down, enter;
	private boolean gameWonMusicOn = true;

	private BufferedImage[] starSprite;
	private int frames = 0, maxFrames = 24, index = 0, maxIndex = 5;

	GameWonScreen() {
		starSprite = new BufferedImage[6];
		for (int i = 0; i < 6; i++) {
			starSprite[i] = Game.spritesheet.getSprite(0 + (i * 16), 96, 16, 16);
		}
		for (int i = 0; i < 9; i++) {
			randX[i] = Entity.rand.nextInt(480);
			randY[i] = Entity.rand.nextInt(480);
		}
	}
	
	public void tick() {
		Sound.pararTodosOsSons();
		
		if(gameWonMusicOn) {
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
			Game.checarLevelSong();
			enter = false;
			if (options[currentOption].equals("jogar_novamente")) {
				// SalvarPontuacao sp = new SalvarPontuacao();
				Game.restartGame = true;
				Enemy.velocidadeEstatica = 65;
				if (Game.restartGame) {
					Player.pontuacao = 0;
				}
			} else if (options[currentOption].equals("sair")) {
				// Sound.optionSelectedSound.play();
				System.exit(0);
			} else if (options[currentOption].equals("meu_github")) {
				URI uri;
				try {
					uri = new URI("https://github.com/Andrew-2609");
					Menu.openWebpage(uri);
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		frames++;
		if (frames == maxFrames) {
			frames = 0;
			index++;
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
		Menu.drawCenteredString(Menu.idioma.parabens, 480, 100, g);
		Menu.drawCenteredString(Menu.idioma.voceGanhou, 480, 135, g);

		g.setColor(Color.cyan);
		g.setFont(new Font("impact", Font.PLAIN, 15));
		Menu.drawCenteredString(Menu.idioma.pontuacaoFinal, 480, 360, g);
		Menu.drawCenteredString(Player.pontuacao + "", 480, 380, g);

		// Opções
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.PLAIN, 20));
		Menu.drawCenteredString(Menu.idioma.jogarNovamente, 480, 220, g);
		Menu.drawCenteredString(Menu.idioma.meuGithub, 480, 270, g);
		Menu.drawCenteredString(Menu.idioma.sair, 480, 320, g);

		if (options[currentOption].equals("jogar_novamente")) {
			g.setColor(Color.YELLOW);
			Menu.drawCenteredString(Menu.idioma.jogarNovamente, 480, 220, g);
		} else if (options[currentOption].equals("meu_github")) {
			g.setColor(Color.YELLOW);
			Menu.drawCenteredString(Menu.idioma.meuGithub, 480, 270, g);
		} else if (options[currentOption].equals("sair")) {
			g.setColor(Color.YELLOW);
			Menu.drawCenteredString(Menu.idioma.sair, 480, 320, g);
		}

		g.setColor(Color.white);
		g.setFont(new Font("century gothic", Font.CENTER_BASELINE, 12));
		Menu.drawCenteredString(Menu.idioma.obrigadoPorJogar, 480, 415, g);
		Menu.drawCenteredString(Menu.idioma.jogoFeitoPor, 480, 435, g);
		Menu.drawCenteredString("Andrew Monteiro", 480, 450, g);
	}
}
