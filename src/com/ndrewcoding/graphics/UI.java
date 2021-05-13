package com.ndrewcoding.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.ndrewcoding.entities.Player;
import com.ndrewcoding.main.Game;
import com.ndrewcoding.main.languages.Language;
import com.ndrewcoding.main.Menu;
import com.ndrewcoding.main.languages.Portuguese;

public class UI {

	public Language language;

	public Language definirTextosUI() {
		if (Menu.language == null) {
			language = new Portuguese();
		} else {
			language = Menu.language;
		}

		return language;
	}

	public void render(Graphics g) {
		g.setColor(new Color(0, 0, 0, 187));
		g.fillRect(0, 0, Game.WIDTH * 2, 32);

		g.setColor(Color.WHITE);
		g.setFont(new Font("century gothic", Font.BOLD, 15));
		g.drawString(definirTextosUI().homeworks + Game.trabalhosAtual + "|" + Game.trabalhosContagem, 32, 20);

		if (Game.CUR_LEVEL < 9) {
			g.setColor(Color.yellow);
			g.drawString(definirTextosUI().level + Game.CUR_LEVEL, 400, 20);
		} else if (Game.CUR_LEVEL > 9) {
			g.setColor(Color.green);
			g.drawString(definirTextosUI().finalTest, 384, 20);
		} else {
			g.setColor(Color.red);
			g.drawString(definirTextosUI().pandemic, 384, 20);
		}

		g.setColor(Color.white);
		g.drawString(definirTextosUI().score + Player.score, 240, 20);

		g.setColor(new Color(0, 0, 0, 187));
		g.fillRect(0, Game.HEIGHT * 2 - 32, Game.WIDTH * 2, 32);

		g.setColor(Color.red);
		g.fillRect(32, 455, Game.player.maxLife / 2, 16);
		g.setColor(Color.green);
		g.fillRect(32, 455, Game.player.life / 2, 16);
		if (Game.player.life == Game.player.maxLife) {
			g.setColor(Color.yellow);
			g.fillRect(32, 455, Game.player.life / 2, 16);
		}

		g.setColor(new Color(255, 255, 255, 127));
		g.fillRect(Game.WIDTH * Game.SCALE - 64, 453, 56, 20);
		if (Game.player.tries == 2) {
			g.drawImage(Game.player.spriteRight[0], Game.WIDTH * Game.SCALE - 56, 455, 16, 16, null);
			g.drawImage(Game.player.spriteRight[0], Game.WIDTH * Game.SCALE - 32, 455, 16, 16, null);
		} else if (Game.player.tries == 1) {
			g.drawImage(Game.player.spriteRight[0], Game.WIDTH * Game.SCALE - 44, 455, 16, 16, null);
		}
	}

}
