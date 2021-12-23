package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;

import VueTest.CartesGUI;
import partie.Carte;
import partie.Joueur;


public class ControleurChoixCarte implements ActionListener {
	private CartesGUI cartesGUI;

	
	public ControleurChoixCarte(CartesGUI cartesGUI) {
		this.cartesGUI = cartesGUI;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Joueur joueur = cartesGUI.getJoueur();
		JComboBox comboBox = cartesGUI.getCardList();
		joueur.setChosenCard((Carte) comboBox.getSelectedItem());
		cartesGUI.getFrame().setVisible(false);
		comboBox.removeAllItems();
		
	}
}
