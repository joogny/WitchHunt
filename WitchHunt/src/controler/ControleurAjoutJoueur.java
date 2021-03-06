package controler;


import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import model.bots.BotStrategy;
import model.bots.JoueurVirtuel;
import model.partie.Joueur;
import model.partie.Partie;
import model.partie.playerList;

public class ControleurAjoutJoueur {
	public ControleurAjoutJoueur(JButton btnAjoutJoueur,
			playerList joueurs, JTextField playerName, JLabel errorField,JButton btnAjoutBot,JComboBox<BotStrategy> listStrats, JButton btnOK) {
		
		
		btnAjoutJoueur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ajouterJoueur(joueurs, playerName, errorField, btnOK);
			}
		});
		
		btnAjoutBot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ajouterBot(joueurs,listStrats,errorField, btnOK);
			}
		});
		
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Partie.getInstance().playerSetupDone();
			}
			
		});
	}


	private void ajouterBot(playerList joueurs, JComboBox<BotStrategy> listStrats,
			JLabel errorField,JButton btnOK) {
		JoueurVirtuel j = new JoueurVirtuel(Partie.getInstance().randomBotName(),(BotStrategy) listStrats.getSelectedItem());
		ajouterAListe(j, joueurs, errorField, btnOK);
	}			
	private void ajouterJoueur(playerList joueurs, JTextField playerName,
			JLabel errorField,JButton btnOK) {
		if (!playerName.getText().equals("")) {
			Joueur j = new Joueur(playerName.getText());
			ajouterAListe(j, joueurs, errorField, btnOK);
			playerName.setText("");
		}
	}
	
	private void ajouterAListe(Joueur j,playerList joueurs,JLabel errorField,JButton btnOK) {
		if (joueurs.getAllPlayers().size() < Partie.getMaxPlayerCount()) {
				joueurs.addPlayer(j);
			}
		else {
			errorField.setVisible(true);
		}
	}
}
