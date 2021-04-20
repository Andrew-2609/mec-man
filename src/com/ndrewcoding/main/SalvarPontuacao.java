package com.ndrewcoding.main;

import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.ndrewcoding.entities.Player;

public class SalvarPontuacao extends JFrame {
	private static final long serialVersionUID = 1L;

	private JTextField filename = new JTextField(), dir = new JTextField();

	public SalvarPontuacao() {
		JFileChooser c = new JFileChooser();
		// Demonstrate "Save" dialog:
		int rVal = c.showSaveDialog(SalvarPontuacao.this);
		if (rVal == JFileChooser.APPROVE_OPTION) {
			filename.setText(c.getSelectedFile().getName());
			dir.setText(c.getCurrentDirectory().toString());
			try (FileWriter fw = new FileWriter(c.getSelectedFile() + ".txt")) {
				fw.write("Sua pontuação foi de: " + Player.pontuacao + "\nObrigado por jogar!");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if (rVal == JFileChooser.CANCEL_OPTION) {
			dispose();
		}
	}
}