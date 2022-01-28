package PExceptions;

/**
 * Classe représentant une exception survenue à la création d'un axe
 * @author Augustin Giraudier et Arthur Secher Cabot
 * 
 */
public class CAxeException extends Exception {

	private static final long serialVersionUID = 8937200205505268851L;

	/**
	 * Crée une exception pour un axe
	 * @param msg message de l'erreur
	 */
	public CAxeException(String msg) {
		super(msg);
	}

}
