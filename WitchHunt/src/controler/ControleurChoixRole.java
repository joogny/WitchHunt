package controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import model.partie.Joueur;

public class ControleurChoixRole {
	public ControleurChoixRole(Joueur j,JButton btnVillager, JButton btnWitch) {
		btnVillager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				j.setEstSorciere(false);
			}
		});
		btnWitch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				j.setEstSorciere(true);
			}
		});
	}
}
