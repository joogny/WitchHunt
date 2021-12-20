package Vue;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import partie.Carte;
import partie.Joueur;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import Controleur.ControleurChoixCarte;

public class CartesGUI {

	private JFrame frame;
	private JComboBox<Carte> cardList;
	private ArrayList<Carte> cartes;
	private JLabel imageLabel;
	private Joueur joueur;
	private JButton btnChooseCard;

	/**
	 * Create the application.
	 * 
	 * @param joueur
	 * @param cartes
	 */
	public CartesGUI(ArrayList<Carte> cartes, Joueur joueur) {
		this.cartes = cartes;
		this.joueur = joueur;
		this.initialize();
		new ControleurChoixCarte(this.joueur, this.cardList, this.btnChooseCard);

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 0, 380, 650);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.getContentPane().setLayout(null);

		cardList = new JComboBox(this.cartes.toArray());
		cardList.setBounds(10, 11, 333, 34);
		frame.getContentPane().add(cardList);

		// mise à jour de l'image en fonction de la carte choisie
		cardList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = cardList.getSelectedIndex();
				imageLabel.setIcon(new ImageIcon(cartes.get(index).getFilePath()));
			}
		});

		imageLabel = new JLabel(new ImageIcon(((Carte) cardList.getSelectedItem()).getFilePath()));
		imageLabel.setBounds(10, 56, 333, 472);
		frame.getContentPane().add(imageLabel);

		btnChooseCard = new JButton("Choose Card");
		btnChooseCard.setBounds(10, 539, 333, 50);

		frame.getContentPane().add(btnChooseCard);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		ArrayList<Carte> cartes = new ArrayList<>();

		cartes.add(new Carte("Angry Mob", "./src/images/the_inquisition.png"));
		cartes.add(new Carte("The Inquisition", "./src/images/angry_mob.png"));

		CartesGUI window = new CartesGUI(cartes, null);
		window.frame.setVisible(true);
	}

	public void setVisible(boolean visible) {
		this.frame.setVisible(visible);

	}
}
