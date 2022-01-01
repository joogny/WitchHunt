package Vue;

import java.awt.EventQueue;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;

import partie.Carte;
import partie.Joueur;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import Controleur.ControleurChangementCarte;
import Controleur.ControleurChoixCarte;

public class CartesGUI{

	private JFrame frame;
	private JComboBox<Carte> cardList;
	private JLabel imageLabel;
	private Joueur joueur;
	private JButton btnChooseCard;
	
	/**
	 * Create the application.
	 * 
	 * @param cartes
	 */
	public CartesGUI(ArrayList<Carte> cartes, Joueur j) {
		this.joueur=j;
		cardList = new JComboBox<Carte>();
		Iterator<Carte> it = cartes.iterator();
		while(it.hasNext()) {
			cardList.addItem(it.next());
		}
		this.initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 0, 380, 650);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.getContentPane().setLayout(null);

		cardList.setBounds(10, 11, 333, 34);
		frame.getContentPane().add(cardList);



		imageLabel = new JLabel(new ImageIcon());
		imageLabel.setBounds(10, 56, 333, 472);
		frame.getContentPane().add(imageLabel);

		btnChooseCard = new JButton("Choose Card");
		btnChooseCard.setBounds(10, 539, 333, 50);

		frame.getContentPane().add(btnChooseCard);
		
		
		// mise à jour de l'image en fonction de la carte choisie
		new ControleurChangementCarte(cardList,imageLabel);
		imageLabel.setIcon(new ImageIcon(((Carte) cardList.getSelectedItem()).getFilePath()));		
		btnChooseCard.addActionListener(new ControleurChoixCarte(joueur, cardList, btnChooseCard));
		this.frame.setVisible(true);
	}
	

	public void hide() {
		this.frame.setVisible(false);
	}


}
