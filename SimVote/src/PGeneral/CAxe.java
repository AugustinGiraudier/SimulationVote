package PGeneral;

import java.text.DecimalFormat;

import PExceptions.CAxeException;

/**
 * Classe gérant un axe d'opinion
 * @author Augustin Giraudier et Arthur Secher Cabot
 */
public class CAxe implements Comparable<CAxe> {
	
	private String nom;
	private double valeur; 
	
	/**
	 * @param _nom nom de l'axe
	 * @param _valeur valeur de l'axe
	 * @throws CAxeException si la valeur n'est pas dans [0;1]
	 */
	public CAxe(String _nom, double _valeur) throws CAxeException {
		this.nom = _nom;
		if(_valeur < 0 || _valeur > 1) {
			throw new CAxeException("An Axis has been created with a value out of [0,1]");
		}
		this.valeur = _valeur;
	};
	
	/**
	 * @return la valeur associée à l'axe
	 */
	public double getValeur() {return valeur;}

	/**
	 * Change la valeur de l'axe
	 * @param _valeur nouvelle valeur
	 * @throws CAxeException si la valeur n'est pas dans [0;1]
	 */
	public void setValeur(double _valeur) throws CAxeException {
		if(_valeur < 0 || _valeur > 1) {
			throw new CAxeException("An Axis has been created with a value out of [0,1]");
		}
		this.valeur = _valeur;
	}
	
	/**
	 * @return le nom associé à l'axe
	 */
	private String getNom() {return nom;}
	
	/**
	 * @param a second axe
	 * @return la distance entre deux axes (valeur absolue de la différence)
	 */
	public double getDistance(CAxe a) {
		return Math.abs(this.valeur - a.getValeur());
	}
	/**
	 * @param a second axe
	 * @return 'true' si les deux axes ont le même nom
	 */
	public boolean memeNom(CAxe a) {
		return nom == a.getNom();
	}
	/**
	 * @param a second axe
	 * @return moyenne des valeurs des deux axes
	 */
	public double moyenne(CAxe a) {
		return (this.getValeur() + a.getValeur()) / 2;
	}
	
	@Override
	public String toString() {
		String strBars = "";
		for(int i_bar=0; i_bar < (int)(this.valeur*10+0.5); i_bar++)
			strBars += '|';
		while(strBars.length() < 10)
			strBars += " ";
		
		DecimalFormat df = new DecimalFormat("0.00");
		return strBars + " (" + df.format(this.valeur) + ") --> " + this.nom + "\n";
	}

	@Override
	public int compareTo(CAxe o) {
		if(valeur == o.getValeur())
			return 0;
		if(valeur < o.getValeur())
			return 1;
		return -1;
	}
}
