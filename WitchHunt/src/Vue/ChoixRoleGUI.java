package Vue;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Controleur.ControleurChangementCarte;
import Controleur.ControleurChoixCarte;
import Controleur.ControleurChoixRole;
import partie.Carte;
import partie.Joueur;
import java.awt.Font;

@SuppressWarnings("deprecation")
public class ChoixRoleGUI implements Observer {

	private JFrame frame;
	private Joueur joueur;
	private JComboBox<Carte> cardList;
	private	JLabel instructionLabel;
	private JButton btnPlayAsAVillager;
	private JButton btnPlayAsWitch;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChoixRoleGUI window = new ChoixRoleGUI(null);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChoixRoleGUI(ArrayList<Joueur> j) {
		Iterator<Joueur> it = j.iterator();
		while(it.hasNext()) {
			it.next().addObserver(this);
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 389, 666);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		cardList = new JComboBox<Carte>();
		cardList.setBounds(10, 44, 333, 34);
		frame.getContentPane().add(cardList);

		JLabel imageLabel;

		imageLabel = new JLabel(new ImageIcon());
		imageLabel.setBounds(10, 89, 333, 472);
		frame.getContentPane().add(imageLabel);

		btnPlayAsWitch = new JButton("play as a Witch");
		btnPlayAsWitch.setBounds(20, 572, 143, 50);

		frame.getContentPane().add(btnPlayAsWitch);
		
		new ControleurChangementCarte(cardList, imageLabel);
		
		btnPlayAsAVillager = new JButton("play as a Villager");
		btnPlayAsAVillager.setBounds(179, 572, 143, 50);
		frame.getContentPane().add(btnPlayAsAVillager);
		
		instructionLabel = new JLabel("Please choose a role!");
		instructionLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		instructionLabel.setBounds(10, 11, 292, 19);
		frame.getContentPane().add(instructionLabel);
		
		this.frame.setVisible(true);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Joueur) {
			this.joueur=(Joueur) o;
			this.instructionLabel.setText(this.joueur.getNomJoueur()+", please choose a role!");
			this.cardList.removeAllItems();
			Iterator<Carte> it = joueur.getCards().iterator();
			while(it.hasNext()) {
				this.cardList.addItem(it.next());
			}
			new ControleurChoixRole(joueur, btnPlayAsAVillager, btnPlayAsWitch);
			
		}
		
	}

	public void hide() {
		this.frame.setVisible(false);
	}
}
