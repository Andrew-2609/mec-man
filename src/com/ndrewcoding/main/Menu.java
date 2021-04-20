package com.ndrewcoding.main;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import com.ndrewcoding.entities.Player;

public class Menu {

	public String[] options = { "novo_jogo", "meu_github", "sair", "continuar" };
	public String[] language = { "pt-br", "en-us", "de-de" };

	public static Idioma idioma;

	public int currentOption = 0;
	public int maxOption = options.length - 1;

	public int currentLanguage = 0;
	public int maxLanguage = language.length;

	private int frames = 0, maxFrames = 6, index = 0, maxIndex = 3;

	public boolean up, down, enter;
	public static boolean idiomaDefinido = false;

	public static boolean pause = false;

	public void tick() {
		if (idiomaDefinido == false) {
			if (up) {
				up = false;
				currentLanguage--;
				Sound.menuChange.play();
				if (currentLanguage < 0) {
					currentLanguage = (maxLanguage - 1);
				}
			} else if (down) {
				down = false;
				currentLanguage++;
				Sound.menuChange.play();
				if (currentLanguage >= maxLanguage) {
					currentLanguage = 0;
				}
			}
		} else {
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
				// Sound.changeOptionSound.play();
				if (currentOption >= maxOption) {
					currentOption = 0;
				}
			}
		}

		if (enter) {
			enter = false;
			if (idiomaDefinido == false) {
				if (language[currentLanguage].equals("pt-br")) {
					idiomaDefinido = true;
					idioma = new TextosPortugues();
				} else if (language[currentLanguage].equals("en-us")) {
					idiomaDefinido = true;
					idioma = new TextosIngles();
				} else if (language[currentLanguage].equals("de-de")) {
					idiomaDefinido = true;
					idioma = new TextosAlemao();
				}
			} else {

				if (options[currentOption].equals("novo_jogo") || options[currentOption].equals("continuar")) {
					// Sound.optionSelectedSound.play();
					Game.gameState = "NORMAL";
					pause = false;
				} else if (options[currentOption].equals("meu_github")) {
					URI uri;
					try {
						uri = new URI("https://github.com/Andrew-2609");
						openWebpage(uri);
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (options[currentOption].equals("sair")) {
					// Sound.optionSelectedSound.play();
					System.exit(0);
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

	protected static void drawStringWithBreak(Graphics g, String text, int x, int y) {
		for (String line : text.split("\n"))
			g.drawString(line, x, y += g.getFontMetrics().getHeight());
	}

	public void render(Graphics g) {
		g.setColor(new Color(0, 0, 0, 227));
		g.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);
		g.setColor(Color.blue);
		g.setFont(new Font("century gothic", Font.ITALIC, 66));
		Menu.drawCenteredString("Mec-Man", 480, 95, g);

		// Opções
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.PLAIN, 20));
		if (idiomaDefinido == false) {
			Menu.drawCenteredString("Selecione o idioma:", 480, 200, g);
			Menu.drawCenteredString("Português", 480, 250, g);
			Menu.drawCenteredString("English", 480, 300, g);
			Menu.drawCenteredString("Deutsch", 480, 350, g);

			if (language[currentLanguage].equals("pt-br") && pause == false) {
				g.setColor(Color.YELLOW);
				Menu.drawCenteredString("Português", 480, 250, g);
			} else if (language[currentLanguage].equals("en-us")) {
				g.setColor(Color.YELLOW);
				Menu.drawCenteredString("English", 480, 300, g);
			} else if (language[currentLanguage].equals("de-de")) {
				g.setColor(Color.YELLOW);
				Menu.drawCenteredString("Deutsch", 480, 350, g);
			}
		} else {
			if (pause == false) {
				Menu.drawCenteredString(idioma.novoJogo, 480, 200, g);
				g.setColor(Color.white);
				g.setFont(new Font("century gothic", Font.CENTER_BASELINE, 12));
				Menu.drawCenteredString(idioma.jogoFeitoPor, 480, 380, g);
				Menu.drawCenteredString("Andrew Monteiro", 480, 395, g);
				g.setColor(new Color(0,255,128));
				Menu.drawCenteredString(idioma.agradecimentosEspeciais, 480, 440, g);
			} else

			{
				Menu.drawCenteredString(idioma.continuar, 480, 200, g);
				g.setColor(Color.yellow);
				g.setFont(new Font("impact", Font.PLAIN, 20));
				Menu.drawCenteredString(idioma.pontuacaoAtual, 480, 375, g);
				Menu.drawCenteredString(Player.pontuacao + "", 480, 405, g);
				// drawStringWithBreak(g, "Pontuação atual:\n" + Player.pontuacao, (Game.WIDTH /
				// 2) + 56, 350);
			}

			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.PLAIN, 20));
			Menu.drawCenteredString(idioma.meuGithub, 480, 250, g);
			// g.drawString("Meu GitHub", ((Game.WIDTH * Game.SCALE) / 2) - 55, 250);
			Menu.drawCenteredString(idioma.sair, 480, 300, g);
			// g.drawString("Sair", ((Game.WIDTH * Game.SCALE) / 2) - 16, 300);

			if (options[currentOption].equals("novo_jogo") && pause == false) {
				g.setColor(Color.YELLOW);
				Menu.drawCenteredString(idioma.novoJogo, 480, 200, g);
			} else if (options[currentOption].equals("novo_jogo")) {
				g.setColor(Color.YELLOW);
				Menu.drawCenteredString(idioma.continuar, 480, 200, g);
			} else if (options[currentOption].equals("meu_github")) {
				g.setColor(Color.YELLOW);
				Menu.drawCenteredString(idioma.meuGithub, 480, 250, g);
			} else if (options[currentOption].equals("sair")) {
				g.setColor(Color.YELLOW);
				Menu.drawCenteredString(idioma.sair, 480, 300, g);
			}

			g.setColor(Color.CYAN);
			g.setFont(new Font("arial", Font.ITALIC, 15));
			Menu.drawCenteredString(idioma.controles, 480, 335, g);

			g.drawImage(Game.player.sprite_right[index], ((Game.WIDTH * Game.SCALE) / 2) - 23, 128, 48, 48, null);
		}
	}

	public static boolean openWebpage(URI uri) {
		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			try {
				desktop.browse(uri);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static boolean openWebpage(URL url) {
		try {
			return openWebpage(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void drawCenteredString(String s, int w, int y, Graphics g) {
		FontMetrics fm = g.getFontMetrics();
		int x = (w - fm.stringWidth(s)) / 2;
		g.drawString(s, x, y);
	}
}
