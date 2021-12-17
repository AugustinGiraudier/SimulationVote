/**
 * Classe g�rant un axe d'opinion
 * @author Augustin Giraudier et Arthur Secher Cabot
 */
public class Axe implements Comparable<Axe> {
	
	private String nom;
	private double valeur; 
	
	/**
	 * @param _nom : nom de l'axe
	 * @param _valeur : valeur de l'axe
	 * @throws Exception
	 */
	public Axe(String _nom, double _valeur) throws Exception {
		//if(Axe.SeuilProximiteOpinion == -1) {
		//	throw new Exception("Please Set The static variable Axe.SeuilProximiteOpinion before instantiate it.");
		//}
		this.nom = _nom;
		if(_valeur < 0 || _valeur > 1) {
			throw new Exception("An Axis has been created with a value out of [0,1]");
		}
		this.valeur = _valeur;
	};
	
	/**
	 * @return la valeur associ�e � l'axe
	 */
	public double getValeur() {return valeur;}
	/**
	 * @return le nom associ� � l'axe
	 */
	private String getNom() {return nom;}
	
	/**
	 * @param a : second axe
	 * @return la distance entre deux axes (valeur absolue de la diff�rence)
	 */
	public double getDistance(Axe a) {
		return Math.abs(this.valeur - a.getValeur());
	}
	/**
	 * @param a : second axe
	 * @return 'true' si les deux axes ont le m�me nom
	 */
	public boolean memeNom(Axe a) {
		return nom == a.getNom();
	}
	/**
	 * @param a : second axe
	 * @return moyenne des valeurs des deux axes
	 */
	public double moyenne(Axe a) {
		return (this.getValeur() + a.getValeur()) / 2;
	}
	
	@Override
	public String toString() {
		return "---\nAxe: '" + this.nom + "' " + this.valeur + "\n---\n";
	}

	@Override
	public int compareTo(Axe o) {
		if(valeur == o.getValeur())
			return 0;
		if(valeur < o.getValeur())
			return 1;
		return -1;
	}
}
