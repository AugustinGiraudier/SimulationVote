package PGeneral;

/**
 * Classe représentant le résultat d'un sondage ou scrutin
 * @author Augustin Giraudier et Arthur Secher Cabot
 *
 */
public class CResultScrutin {

	private CActeur acteur;
	private double dScore;
	private String strScore;
	
	/**
	 * Crée un résultat avec un acteur et son score
	 * @param _acteur acteur
	 * @param _dScore score
	 */
	public CResultScrutin(CActeur _acteur, double _dScore) {
		this(_acteur, _dScore, null);
	}
	
	/**
	 * Crée un résultat avec un acteur, son score et une string à afficher
	 * @param _acteur acteur
	 * @param _dScore score
	 * @param _strScore message à afficher à la place du score
	 */
	public CResultScrutin(CActeur _acteur, double _dScore, String _strScore) {
		this.acteur = _acteur;
		this.dScore = _dScore;
		this.strScore = _strScore;
	}
	
	/**
	 * Récupérer l'acteur
	 * @return l'acteur
	 */
	public CActeur getActeur() {
		return acteur;
	}
	/**
	 * Récupérer son score numérique
	 * @return le score numérique
	 */
	public double getIscore() {
		return dScore;
	}
	/**
	 * Récupérer son score en caractères
	 * @return le score en caractères
	 */
	public String getStrScore() {
		return strScore;
	}
	
	@Override
	public String toString() {
		String out = this.acteur.getNom() + " : ";
		
		if(this.strScore == null) 
			out += Double.toString(this.dScore);
		else
			out += this.strScore;
		
		return out;
	}
	
}
