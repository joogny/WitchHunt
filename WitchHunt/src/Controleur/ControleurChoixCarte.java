package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;

import partie.Carte;
import partie.Joueur;


public class ControleurChoixCarte {
	public ControleurChoixCarte(Joueur j,JComboBox comboBox, JButton btn) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				j.setChosenCard((Carte) comboBox.getSelectedItem());
			}
		});
	}
}
