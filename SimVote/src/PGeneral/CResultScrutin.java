package PGeneral;

/**
 * Classe repr�sentant le r�sultat d'un sondage ou scrutin
 * @author Augustin Giraudier et Arthur Secher Cabot
 *
 */
public class CResultScrutin {

	private CActeur acteur;
	private double Fscore;
	private String strScore;
	
	/**
	 * Cr�e un r�sultat avec un acteur et son score
	 * @param _acteur acteur
	 * @param _Fscore score
	 */
	public CResultScrutin(CActeur _acteur, double _Fscore) {
		this(_acteur, _Fscore, null);
	}
	
	/**
	 * Cr�e un r�sultat avec un acteur, son score et une string � afficher
	 * @param _acteur acteur
	 * @param _Fscore score
	 * @param _strScore message � afficher � la place du score
	 */
	public CResultScrutin(CActeur _acteur, double _Fscore, String _strScore) {
		this.acteur = _acteur;
		this.Fscore = _Fscore;
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
		return Fscore;
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
			out += Double.toString(this.Fscore);
		else
			out += this.strScore;
		
		return out;
	}
	
}
