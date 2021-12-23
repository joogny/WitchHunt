package Vue;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;

import partie.Carte;
import partie.Joueur;
import partie.PlayerAction;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import Controleur.ControleurChoixCarte;

@SuppressWarnings("deprecation")
public class CartesGUI implements Observer{

	private JFrame frame;
	private JComboBox<Carte> cardList;
	private JLabel imageLabel;
	private Joueur joueur;
	private JButton btnChooseCard;
	private final PlayerAction playerAction = PlayerAction.CHOOSECARD;
	
	/**
	 * Create the application.
	 * 
	 * @param cartes
	 */
	public CartesGUI(ArrayList<Carte> cartes, ArrayList<Joueur> joueurs) {
		
		Iterator<Joueur> it = joueurs.iterator();
		while(it.hasNext()) {
			Joueur j = it.next();

			j.addObserver(this);
		}
		
		Iterator<Carte> it2 = cartes.iterator();
		while(it2.hasNext()) {
			it2.next().addObserver(this);
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

		cardList = new JComboBox<Carte>();
		cardList.setBounds(10, 11, 333, 34);
		frame.getContentPane().add(cardList);

		// mise à jour de l'image en fonction de la carte choisie
		cardList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Carte c = (Carte) cardList.getSelectedItem();
				if(c!=null) {
					imageLabel.setIcon(new ImageIcon(((Carte) cardList.getSelectedItem()).getFilePath()));				}
			}
		});

		imageLabel = new JLabel(new ImageIcon());
		imageLabel.setBounds(10, 56, 333, 472);
		frame.getContentPane().add(imageLabel);

		btnChooseCard = new JButton("Choose Card");
		btnChooseCard.setBounds(10, 539, 333, 50);

		frame.getContentPane().add(btnChooseCard);
		
		btnChooseCard.addActionListener(new ControleurChoixCarte(this));
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(Joueur.getPlayerAction()==this.playerAction) {
			this.frame.setVisible(true);
			if(o instanceof Joueur) {
				Joueur j = (Joueur) o;
					this.joueur=j;
			}
			if(o instanceof Carte) {
				Carte c = (Carte) o;
				cardList.addItem(c);
			}
		}
		else {
			this.frame.setVisible(false);
		}
		
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

	public Joueur getJoueur() {
		return this.joueur;
	}

	public JComboBox<Carte> getCardList() {
		return this.cardList;
	}

	public JFrame getFrame() {
		return frame;
	}


}
