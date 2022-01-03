package view;


import java.util.*;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.BorderLayout;

import javax.swing.JLabel;

import controler.ControleurChoixJoueur;
import model.partie.*;

public class JoueursGUI {

	private JFrame frame;
	private JComboBox<Joueur> playerList;
	private ArrayList<Joueur> Joueurs;
	private JButton btnchooseplayer;
	private JLabel lblNewLabel;
	private JLabel imageLabel;
	private Joueur j;

	/**
	 * Create the application.
	 */
	public JoueursGUI(ArrayList<Joueur> joueurs, Joueur joueur) {
		Iterator<Joueur> it = joueurs.iterator();
		playerList = new JComboBox<Joueur>();
		while(it.hasNext()) {
			playerList.addItem(it.next());
		}
		this.Joueurs=joueurs;
		this.j=joueur;
		this.initialize();
	}

	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		playerList.setBounds(25, 200, 400, 50);
		frame.getContentPane().add(playerList);
		
		btnchooseplayer = new JButton("Choose Player");
		btnchooseplayer.setBounds(25,150,200,50);
		frame.getContentPane().add(btnchooseplayer);
		new ControleurChoixJoueur(j,playerList,btnchooseplayer);
		

		
		lblNewLabel = new JLabel("Choose a player!");
		lblNewLabel.setBounds(100,10,10,50);
		frame.getContentPane().add(lblNewLabel, BorderLayout.NORTH);
		
		imageLabel = new JLabel(new ImageIcon());
		frame.getContentPane().add(imageLabel, BorderLayout.CENTER);
		
		//imageLabel.setIcon(new ImageIcon(((Joueur) playerList.getSelectedItem()).getFilePath()));
		this.frame.setVisible(true);
	}
	
	public void hide() {
		this.frame.setVisible(false);
	}

}
