package PExceptions;

/**
 * Classe représentant une exception qui ne peut etre réparée
 * @author Augustin Giraudier et Arthur Secher Cabot
 *
 */
public class CFatalException extends Exception {

	private static final long serialVersionUID = -4862874053824272810L;

	/**
	 * Crée une exception fatale
	 * @param msg message d'erreur
	 */
	public CFatalException(String msg) {
		super(msg);
	}

}
