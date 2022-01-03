package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TextInterface implements Runnable {
	private CLI cli;
	private boolean stop;

	public TextInterface() {
		this.stop = false;
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		String saisie = null;
		while (!stop) {
			saisie = this.lireChaine();
			if (cli != null) {
				cli.action(saisie);
			}
		}
	}

	public void setCLI(CLI cli) {
		this.cli = cli;
		if(cli!=null) {
			cli.setup();
		}
	}

	public void stop() {
		this.stop=true;
	}

	private String lireChaine() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String resultat = null;
		try {
			resultat = br.readLine();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return resultat;
	}
}
