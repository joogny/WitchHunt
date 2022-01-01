package Vue;

import java.awt.EventQueue;

import javax.swing.JFrame;

import partie.Action;
import partie.Joueur;

import javax.swing.JButton;
import javax.swing.JLabel;

import Controleur.ControleurChooseBetween2Options;

import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

public class ChooseBetween2OptionsGUI implements Observer {

	private JFrame frame;
	private JButton btnOption1;
	private JButton btnOption2;
	private JLabel lblInstruction;
	private Joueur joueur;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChooseBetween2OptionsGUI window = new ChooseBetween2OptionsGUI("a","b",new Joueur("lol"));	
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
	public ChooseBetween2OptionsGUI(String op1, String op2,Joueur j) {
		btnOption1 = new JButton(op1);
		btnOption2 = new JButton(op2);
		lblInstruction = new JLabel(j.getNomJoueur()+", please choose one of these 2 options");
		this.joueur=j;
		j.addObserver(this);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 664, 328);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		btnOption1.setBounds(10, 68, 616, 96);
		frame.getContentPane().add(btnOption1);
		
		
		btnOption2.setBounds(10, 175, 616, 96);
		frame.getContentPane().add(btnOption2);
		
		lblInstruction.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblInstruction.setBounds(10, 11, 310, 46);
		frame.getContentPane().add(lblInstruction);
		frame.setVisible(true);
		
		new ControleurChooseBetween2Options(this.joueur, btnOption1, btnOption2);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Joueur) {
			if(arg instanceof Action) {
				this.frame.setVisible(false);
			}
		}
	}
}
