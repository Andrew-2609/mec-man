package com.ndrewcoding.main;

import com.ndrewcoding.entities.Player;
import com.ndrewcoding.main.languages.English;
import com.ndrewcoding.main.languages.German;
import com.ndrewcoding.main.languages.Language;
import com.ndrewcoding.main.languages.Portuguese;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;

public class Menu {

    private final String[] options = {"novo_jogo", "meu_github", "sair", "continuar"};
    private final String[] languages = {"pt-br", "en-us", "de-de"};

    public static Language language;

    private int currentOption = 0;
    private final int maxOption = options.length - 1;

    private int currentLanguage = 0;
    private final int maxLanguage = languages.length;

    private int frames = 0;
    private int index = 0;

    public boolean up, down, enter;
    private static boolean languageIsSet = false;

    public static boolean pause = false;

    public void tick() {
        moveThroughDisplayedMenu();

        if (enter) {
            enter = false;
            if (!languageIsSet) {
                switch (languages[currentLanguage]) {
                    case "pt-br":
                        languageIsSet = true;
                        language = new Portuguese();
                        break;
                    case "en-us":
                        languageIsSet = true;
                        language = new English();
                        break;
                    case "de-de":
                        languageIsSet = true;
                        language = new German();
                        break;
                }
            } else {
                switch (options[currentOption]) {
                    case "novo_jogo":
                    case "continuar":
                        Game.gameState = GameState.NORMAL;
                        pause = false;
                        break;
                    case "meu_github":
                        URI uri;
                        try {
                            uri = new URI("https://github.com/Andrew-2609");
                            openWebpage(uri);
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "sair":
                        System.exit(0);
                }
            }
        }

        frames++;
        int maxFrames = 6;
        if (frames == maxFrames) {
            frames = 0;
            index++;
            int maxIndex = 3;
            if (index > maxIndex) {
                index = 0;
            }
        }

    }

    private void moveThroughDisplayedMenu() {
        if (up) {
            up = false;
            if (!languageIsSet) {
                currentLanguage--;
                if (currentLanguage < 0) {
                    currentLanguage = (maxLanguage - 1);
                }
            } else {
                currentOption--;
                if (currentOption < 0) {
                    currentOption = (maxOption - 1);
                }
            }
            Sound.menuChange.play();
        } else if (down) {
            down = false;
            if (!languageIsSet) {
                currentLanguage++;
                if (currentLanguage >= maxLanguage) {
                    currentLanguage = 0;
                }
            } else {
                currentOption++;
                if (currentOption >= maxOption) {
                    currentOption = 0;
                }
            }
            Sound.menuChange.play();
        }
    }

    public void render(Graphics g) {
        g.setColor(new Color(0, 0, 0, 227));
        g.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);
        g.setColor(Color.blue);
        g.setFont(new Font("century gothic", Font.ITALIC, 66));
        Menu.drawCenteredString("Mec-Man", 480, 95, g);

        g.setColor(Color.white);
        g.setFont(new Font("arial", Font.PLAIN, 20));

        if (!languageIsSet) {
            displayLanguageMenu(g);
        } else {
            if (!pause) {
                displayInitialMenu(g);
            } else {
                displayPauseMenu(g);
            }

            g.setColor(Color.white);
            g.setFont(new Font("arial", Font.PLAIN, 20));
            Menu.drawCenteredString(language.myGithub, 480, 250, g);
            Menu.drawCenteredString(language.exit, 480, 300, g);

            highlightSelectedOption(g);

            g.setColor(Color.CYAN);
            g.setFont(new Font("arial", Font.ITALIC, 15));
            Menu.drawCenteredString(language.controls, 480, 335, g);

            g.drawImage(Game.player.spriteRight[index], ((Game.WIDTH * Game.SCALE) / 2) - 23, 128, 48, 48, null);
        }
    }

    private void displayLanguageMenu(Graphics g) {
        Menu.drawCenteredString("Selecione o idioma:", 480, 200, g);
        Menu.drawCenteredString("Português", 480, 250, g);
        Menu.drawCenteredString("English", 480, 300, g);
        Menu.drawCenteredString("Deutsch", 480, 350, g);

        if (languages[currentLanguage].equals("pt-br") && !pause) {
            g.setColor(Color.YELLOW);
            Menu.drawCenteredString("Português", 480, 250, g);
        } else if (languages[currentLanguage].equals("en-us")) {
            g.setColor(Color.YELLOW);
            Menu.drawCenteredString("English", 480, 300, g);
        } else if (languages[currentLanguage].equals("de-de")) {
            g.setColor(Color.YELLOW);
            Menu.drawCenteredString("Deutsch", 480, 350, g);
        }
    }

    private void displayInitialMenu(Graphics g) {
        Menu.drawCenteredString(language.newGame, 480, 200, g);
        g.setColor(Color.white);
        g.setFont(new Font("century gothic", Font.BOLD, 12));
        Menu.drawCenteredString(language.gameMadeBy, 480, 380, g);
        Menu.drawCenteredString("Andrew Monteiro", 480, 395, g);
        g.setColor(new Color(0, 255, 128));
        Menu.drawCenteredString(language.specialThanks, 480, 440, g);
    }

    private void displayPauseMenu(Graphics g) {
        Menu.drawCenteredString(language.continueGame, 480, 200, g);
        g.setColor(Color.yellow);
        g.setFont(new Font("impact", Font.PLAIN, 20));
        Menu.drawCenteredString(language.currentScore, 480, 375, g);
        Menu.drawCenteredString(Player.getScore() + "", 480, 405, g);
    }

    private void highlightSelectedOption(Graphics g) {
        if (options[currentOption].equals("novo_jogo") && !pause) {
            g.setColor(Color.YELLOW);
            Menu.drawCenteredString(language.newGame, 480, 200, g);
        } else if (options[currentOption].equals("novo_jogo")) {
            g.setColor(Color.YELLOW);
            Menu.drawCenteredString(language.continueGame, 480, 200, g);
        } else if (options[currentOption].equals("meu_github")) {
            g.setColor(Color.YELLOW);
            Menu.drawCenteredString(language.myGithub, 480, 250, g);
        } else if (options[currentOption].equals("sair")) {
            g.setColor(Color.YELLOW);
            Menu.drawCenteredString(language.exit, 480, 300, g);
        }
    }

    public static void openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void drawCenteredString(String s, int w, int y, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int x = (w - fm.stringWidth(s)) / 2;
        g.drawString(s, x, y);
    }
}
