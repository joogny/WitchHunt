package controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import model.partie.Action;
import model.partie.Joueur;

public class ControleurChooseBetween2Options {
	public ControleurChooseBetween2Options(Joueur j,JButton op1,JButton op2)  {
		op1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				j.setAction(Action.OPTION1);
				System.out.println("test1");
			}
		});
		op2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				j.setAction(Action.OPTION2);
				System.out.println("test2");
			}
		});
	}
}
