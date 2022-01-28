package PGeneral;

import java.text.DecimalFormat;

import PExceptions.CAxeException;

/**
 * Classe g�rant un axe d'opinion
 * @author Augustin Giraudier et Arthur Secher Cabot
 */
public class CAxe implements Comparable<CAxe> {
	
	private String nom;
	private double valeur; 
	
	/**
	 * @param _nom : nom de l'axe
	 * @param _valeur : valeur de l'axe
	 * @throws Exception
	 */
	public CAxe(String _nom, double _valeur) throws CAxeException {
		//if(Axe.SeuilProximiteOpinion == -1) {
		//	throw new Exception("Please Set The static variable Axe.SeuilProximiteOpinion before instantiate it.");
		//}
		this.nom = _nom;
		if(_valeur < 0 || _valeur > 1) {
			throw new CAxeException("An Axis has been created with a value out of [0,1]");
		}
		this.valeur = _valeur;
	};
	
	/**
	 * @return la valeur associ�e � l'axe
	 */
	public double getValeur() {return valeur;}

	public void setValeur(double value) {this.valeur = value;}
	/**
	 * @return le nom associ� � l'axe
	 */
	private String getNom() {return nom;}
	
	/**
	 * @param a : second axe
	 * @return la distance entre deux axes (valeur absolue de la diff�rence)
	 */
	public double getDistance(CAxe a) {
		return Math.abs(this.valeur - a.getValeur());
	}
	/**
	 * @param a : second axe
	 * @return 'true' si les deux axes ont le m�me nom
	 */
	public boolean memeNom(CAxe a) {
		return nom == a.getNom();
	}
	/**
	 * @param a : second axe
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
		//return "---\nAxe: '" + this.nom + "' : " + this.valeur + "\n";
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
