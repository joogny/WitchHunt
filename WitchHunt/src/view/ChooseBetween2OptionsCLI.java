package view;

import model.partie.Action;
import model.partie.Joueur;

@SuppressWarnings("deprecation")
public class ChooseBetween2OptionsCLI implements CLI {
	private Joueur j;
	private String op1;
	private String op1Full;
	private String op2;
	private String op2Full;
	
	public ChooseBetween2OptionsCLI(Joueur j, String op1, String op2, String op1Full, String op2Full) {
		this.j = j;
		this.op1 = op1;
		this.op1Full = op1Full;
		this.op2 = op2;
		this.op2Full = op2Full;
	}



	@Override
	public void action(String saisie) {
		if(saisie.equals(op1)) {
			j.setAction(Action.OPTION1);
		}
		else if (saisie.equals(op2)) {
			j.setAction(Action.OPTION2);
		}
		else {
			System.out.println("Please type either " + op1 + " or" + op2);
		}
	}

	@Override
	public void setup() {
		System.out.println("Type " + op1 + " : " + op1Full);
		System.out.println("Type " + op2 + " : " + op2Full);
		
	}

	

}
