import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import partie.Carte;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CartesVue {

	private JFrame frame;
	private JComboBox cardList;
	private ArrayList<Carte> cartes;
	private JLabel imageLabel;
	/**
	 * Create the application.
	 */
	public CartesVue(ArrayList<Carte> cartes) {
		this.cartes=cartes;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 0, 600, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		String[] cardNames = new String[cartes.size()];
		Iterator<Carte> it = cartes.iterator();
		int i = 0;
		while(it.hasNext()) {
			cardNames[i]=it.next().getNomCarte();
			i++;
		}
		
		cardList = new JComboBox(cardNames);
		//mise à jour de l'image en fonction de la carte choisie
		cardList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index=cardList.getSelectedIndex();
				imageLabel.setIcon(new ImageIcon(cartes.get(index).getFilePath()));	
			}
		});
		
		frame.getContentPane().add(cardList, BorderLayout.NORTH);
		imageLabel = new JLabel(new ImageIcon(cartes.get(cardList.getSelectedIndex()).getFilePath()));	
		frame.getContentPane().add(imageLabel, BorderLayout.WEST);
	}

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		ArrayList<Carte> cartes = new ArrayList<>();
		
		cartes.add(new Carte("Angry Mob","./src/images/test.png"));
		cartes.add(new Carte("The Inquisition","./src/images/test2.png"));
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CartesVue window = new CartesVue(cartes);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
}
