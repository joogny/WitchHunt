package Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import bots.BotStrategy;
import bots.JoueurVirtuel;
import partie.Joueur;
import partie.Partie;
import partie.playerList;

public class ControleurAjoutJoueur {
	public ControleurAjoutJoueur(JButton btnAjoutWitch, JButton btnAjoutVillager, DefaultListModel<Joueur> model,
			playerList joueurs, JTextField playerName, JLabel errorField,JButton btnAjoutBot,JComboBox<BotStrategy> listStrats, JButton btnOK) {
		
		
		btnAjoutWitch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ajouterJoueur(model, joueurs, playerName, errorField, false, btnOK);
			}
		});
		btnAjoutVillager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ajouterJoueur(model, joueurs, playerName, errorField, true, btnOK);
			}
		});
		
		btnAjoutBot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ajouterBot(model,joueurs,listStrats,errorField, btnOK);
			}
		});
		
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Partie.getInstance().playerSetupDone();
			}
			
		});
	}


	private void ajouterBot(DefaultListModel<Joueur> model, playerList joueurs, JComboBox<BotStrategy> listStrats,
			JLabel errorField,JButton btnOK) {
		JoueurVirtuel j = new JoueurVirtuel(Partie.getInstance().randomBotName(),(BotStrategy) listStrats.getSelectedItem());
		ajouterAListe(j, joueurs, model, errorField, btnOK);
	}			
	private void ajouterJoueur(DefaultListModel<Joueur> model, playerList joueurs, JTextField playerName,
			JLabel errorField, boolean role,JButton btnOK) {
		if (!playerName.getText().equals("")) {
			Joueur j = new Joueur(playerName.getText());
			ajouterAListe(j, joueurs, model, errorField, btnOK);
			playerName.setText("");
		}
	}
	
	private void ajouterAListe(Joueur j,playerList joueurs,DefaultListModel<Joueur> model,JLabel errorField,JButton btnOK) {
		if (joueurs.getAllPlayers().size() < Partie.getMaxPlayerCount()) {
				joueurs.addPlayer(j);
				model.addElement(j);
			}
		else {
			errorField.setVisible(true);
		}
		if(joueurs.getAllPlayers().size()>=Partie.getMinPlayerCount()) {
			btnOK.setVisible(true);
		}
	}
}
