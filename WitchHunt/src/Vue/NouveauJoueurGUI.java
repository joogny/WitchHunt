package Vue;

import java.awt.EventQueue;


import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JTextField;

import partie.Joueur;
import partie.playerList;

import javax.swing.JList;
import javax.swing.ListSelectionModel;

import Controleur.ControleurAjoutJoueur;
import bots.AccuserStrategy;
import bots.BotStrategy;
import bots.CartesStrategy;
import bots.RandomStrategy;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JComboBox;
public class NouveauJoueurGUI {

	private JFrame frame;
	private JTextField playerName;
	private playerList joueurs;
	private JComboBox comboBox;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		playerList p = new playerList();
		ArrayList<BotStrategy> strats = new ArrayList<>();
		strats.add(new AccuserStrategy());
		strats.add(new CartesStrategy());
		strats.add(new RandomStrategy());

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NouveauJoueurGUI window = new NouveauJoueurGUI(p,strats);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(p.getAllPlayers().toString());
	}

	/**
	 * Create the application.
	 */
	public NouveauJoueurGUI(playerList listeJoueur,ArrayList<BotStrategy> strats) {
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
		
		
		DefaultListModel<Joueur> model = new DefaultListModel<Joueur>();
		JList<Joueur> list = new JList<Joueur>(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBounds(10, 11, 120, 309);
		frame.getContentPane().add(list);
		
		playerName = new JTextField();
		playerName.setBounds(174, 63, 113, 42);
		frame.getContentPane().add(playerName);
		playerName.setColumns(10);
		
		JButton addWitch = new JButton("Add a Witch");
		addWitch.setBounds(174, 108, 113, 42);
		frame.getContentPane().add(addWitch);
		
		JButton addVillager = new JButton("Add a Villager");

		addVillager.setBounds(174, 153, 113, 42);
		frame.getContentPane().add(addVillager);
		
		JLabel errorField = new JLabel("Le nombre de joueurs maximum est de 6!");
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
		
		JButton btnOK = new JButton("OK");
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
		btnOK.setBounds(632, 225, 80, 62);
		btnOK.setVisible(false);
		frame.getContentPane().add(btnOK);
		
		
		new ControleurAjoutJoueur(addWitch, addVillager, model, joueurs, playerName,errorField, btnAddABot, comboBox,btnOK);
		
	}
}
