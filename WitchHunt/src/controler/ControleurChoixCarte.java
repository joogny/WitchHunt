package controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;

import model.partie.Carte;
import model.partie.Joueur;
import view.CartesGUI;


public class ControleurChoixCarte implements ActionListener {
	private CartesGUI cartesGUI;

	
	public ControleurChoixCarte(Joueur j, JComboBox comboBox,JButton btnChooseCard) {
		btnChooseCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				j.setChosenCard((Carte) comboBox.getSelectedItem());
				comboBox.removeAllItems();
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		
	}
}
