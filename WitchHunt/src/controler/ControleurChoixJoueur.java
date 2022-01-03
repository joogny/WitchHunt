package controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;

import model.partie.Joueur;
import view.JoueursGUI;

public class ControleurChoixJoueur{
	public ControleurChoixJoueur(Joueur j, JComboBox playerList, JButton btnchooseplayer) {
		btnchooseplayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				j.setChosenPlayer((Joueur)playerList.getSelectedItem());
				playerList.removeAllItems();
			}
		});
	}

}
