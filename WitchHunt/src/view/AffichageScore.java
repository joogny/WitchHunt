package view;

import java.awt.EventQueue;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JTable;

import controler.ControleurNewRound;

import java.awt.BorderLayout;
import model.partie.Joueur;
import javax.swing.JLabel;
import javax.swing.JButton;

public class AffichageScore {

	private JFrame frame;
	private JTable table;
	private JButton btnNewRound;

	public AffichageScore(ArrayList<Joueur> joueurs) {
		Object[][] donnees=new Object[5][5];
		String[]entete= {"Rank","Player Name","Score"};
		int i=0;
		Iterator it=joueurs.iterator();
		while(it.hasNext()) {
			Joueur joueur =(Joueur)it.next();
			donnees[i][0]=i+1;
			donnees[i][1]=joueur.getNomJoueur();
			donnees[i][2]=joueur.getScore();
			i++;
		}
		table = new JTable(donnees,entete);
		this.initialize();
	}

	
	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.getContentPane().add(table.getTableHeader(), BorderLayout.NORTH);
		frame.getContentPane().add(table, BorderLayout.CENTER);
		
		btnNewRound = new JButton("Next Step");
		frame.getContentPane().add(btnNewRound, BorderLayout.SOUTH);
		new ControleurNewRound(btnNewRound);
		
		this.frame.setVisible(true);
	}
	
	public void hide() {
		this.frame.setVisible(false);
	}

}
