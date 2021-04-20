package com.ndrewcoding.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.ndrewcoding.entities.Player;

public class GameOverScreen {

	public String[] options = { "tentar_novamente", "sair" };

	public int currentOption = 0;
	public int maxOption = options.length;

	public boolean up, down, enter;

	public void tick() {
		Sound.pararTodosOsSons();

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
			if (options[currentOption].equals("tentar_novamente")) {
				Sound.gameOverMusic.stop();
				Game.restartGame = true;
			} else if (options[currentOption].equals("sair")) {
				// Sound.optionSelectedSound.play();
				System.exit(0);
			}
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);
		g.setColor(Color.RED);
		g.setFont(new Font("century gothic", Font.ITALIC, 60));
		Menu.drawCenteredString(Menu.idioma.fimDeJogo, 480, 150, g);

		// Opções
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.PLAIN, 20));
		Menu.drawCenteredString(Menu.idioma.tentarNovamente, 480, 220, g);
		Menu.drawCenteredString(Menu.idioma.sair, 480, 270, g);

		Menu.drawCenteredString(Menu.idioma.pontuacaoFinal, 480, 350, g);
		if (Player.pontuacao > 0) {
			Menu.drawCenteredString(Player.pontuacao + "", 480, 380, g);
		} else if (Player.pontuacao < 0) {
			Menu.drawCenteredString("0", 480, 380, g);
		}

		if (options[currentOption].equals("tentar_novamente")) {
			g.setColor(Color.YELLOW);
			Menu.drawCenteredString(Menu.idioma.tentarNovamente, 480, 220, g);
		} else if (options[currentOption].equals("sair")) {
			g.setColor(Color.YELLOW);
			Menu.drawCenteredString(Menu.idioma.sair, 480, 270, g);
		}

		g.drawImage(Game.spritesheet.getSprite(0, 80, 16, 16), ((Game.WIDTH * Game.SCALE) / 2 - 32), 16, 64, 64, null);
	}
}
