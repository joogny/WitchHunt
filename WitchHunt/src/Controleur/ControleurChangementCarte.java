package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import partie.Carte;

public class ControleurChangementCarte {
	public ControleurChangementCarte(JComboBox cardList,JLabel imageLabel) {
		cardList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Carte c = (Carte) cardList.getSelectedItem();
				if(c!=null) {
					imageLabel.setIcon(new ImageIcon(((Carte) cardList.getSelectedItem()).getFilePath()));				}
			}
		});
	}
}
