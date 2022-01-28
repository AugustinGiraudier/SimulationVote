package PGeneral;

/**
 * Classe repr�sentant le r�sultat d'un sondage ou scrutin
 * @author Augustin Giraudier et Arthur Secher Cabot
 *
 */
public class CResultScrutin {

	private CActeur acteur;
	private double dScore;
	private String strScore;
	
	/**
	 * Cr�e un r�sultat avec un acteur et son score
	 * @param _acteur acteur
	 * @param _dScore score
	 */
	public CResultScrutin(CActeur _acteur, double _dScore) {
		this(_acteur, _dScore, null);
	}
	
	/**
	 * Cr�e un r�sultat avec un acteur, son score et une string � afficher
	 * @param _acteur acteur
	 * @param _dScore score
	 * @param _strScore message � afficher � la place du score
	 */
	public CResultScrutin(CActeur _acteur, double _dScore, String _strScore) {
		this.acteur = _acteur;
		this.dScore = _dScore;
		this.strScore = _strScore;
	}
	
	/**
	 * R�cup�rer l'acteur
	 * @return l'acteur
	 */
	public CActeur getActeur() {
		return acteur;
	}
	/**
	 * R�cup�rer son score num�rique
	 * @return le score num�rique
	 */
	public double getIscore() {
		return dScore;
	}
	/**
	 * R�cup�rer son score en caract�res
	 * @return le score en caract�res
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
