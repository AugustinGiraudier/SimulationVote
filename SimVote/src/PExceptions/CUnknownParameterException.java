package PExceptions;

/**
 * Classe représentant une exception survenue lorsqu'un parametre ne correspond pas aux attentes
 * @author Augustin Giraudier et Arthur Secher Cabot
 *
 */
public class CUnknownParameterException extends Exception {

	private static final long serialVersionUID = -6309758467257462317L;

	/**
	 * Crée une erreur de parametre inconnu
	 * @param msg message d'erreur
	 */
	public CUnknownParameterException(String msg) {
		super(msg);
	}
	
}
