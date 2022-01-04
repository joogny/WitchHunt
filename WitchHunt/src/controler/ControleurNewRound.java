package controler;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import model.partie.Partie;
public class ControleurNewRound {
	public ControleurNewRound(JButton btnnewround) {
		btnnewround.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Partie.getInstance().setNewRound("Y");
			}
		});
	}
	

}
