package view;

import java.awt.EventQueue;


import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import controler.ControleurAjoutJoueur;
import model.bots.AccuserStrategy;
import model.bots.BotStrategy;
import model.bots.CartesStrategy;
import model.bots.RandomStrategy;
import model.partie.Joueur;
import model.partie.Partie;
import model.partie.playerList;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JComboBox;
public class NouveauJoueurGUI implements Observer{

	private JFrame frame;
	private JTextField playerName;
	private playerList joueurs;
	private JComboBox comboBox;
	private JList<Joueur> list;
	private DefaultListModel<Joueur> model;
	private JButton btnOK;

	/**
	 * Create the application.
	 */
	public NouveauJoueurGUI(playerList listeJoueur,ArrayList<BotStrategy> strats, Partie partie) {
		partie.addObserver(this);
		listeJoueur.addObserver(this);
		this.joueurs=listeJoueur;
		comboBox = new JComboBox<BotStrategy>();
		Iterator<BotStrategy> it = strats.iterator();
		while(it.hasNext()) {
			comboBox.addItem(it.next());
		}
		initialize();
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 744, 381);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		model = new DefaultListModel<Joueur>();
		list = new JList<Joueur>(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBounds(10, 11, 120, 309);
		frame.getContentPane().add(list);
		
		playerName = new JTextField();
		playerName.setBounds(174, 63, 113, 42);
		frame.getContentPane().add(playerName);
		playerName.setColumns(10);
		
		JButton addPlayer = new JButton("Add player");
		addPlayer.setBounds(174, 108, 113, 42);
		frame.getContentPane().add(addPlayer);
		
		JLabel errorField = new JLabel("There can only be up to 6 players!");
		errorField.setFont(new Font("Tahoma", Font.BOLD, 15));
		errorField.setForeground(Color.RED);
		errorField.setBounds(158, 285, 322, 35);
		errorField.setVisible(false);
		frame.getContentPane().add(errorField);
		

		comboBox.setBounds(345, 63, 373, 35);
		frame.getContentPane().add(comboBox);
		
		JButton btnAddABot = new JButton("Add a Bot");
		btnAddABot.setBounds(480, 108, 113, 42);
		frame.getContentPane().add(btnAddABot);
		
		btnOK = new JButton("OK");
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
		btnOK.setBounds(632, 225, 80, 62);
		btnOK.setVisible(false);
		frame.getContentPane().add(btnOK);
		
		new ControleurAjoutJoueur(addPlayer, joueurs, playerName, errorField, btnAddABot, comboBox, btnOK);
		
	}

	@Override
	public void update(Observable o, Object arg) {;
		if(o instanceof playerList) {
			if(arg instanceof Joueur) {
				model.addElement((Joueur) arg);
			}
			playerList list = (playerList) o;
			if(list.getAllPlayers().size()>=Partie.getMinPlayerCount()) {
				btnOK.setVisible(true);
			}
		}
		if(o instanceof Partie) {
			Partie p = (Partie) o;
			if(p.getPlayerSetupDone()) {
				this.frame.setVisible(false);
			}
		}
	}
}
