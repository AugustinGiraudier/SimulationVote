package PExceptions;

/**
 * Classe repr�sentant une exception survenue � la cr�ation d'un axe
 * @author Augustin Giraudier et Arthur Secher Cabot
 * 
 */
public class CAxeException extends Exception {

	private static final long serialVersionUID = 8937200205505268851L;

	/**
	 * Cr�e une exception pour un axe
	 * @param msg message de l'erreur
	 */
	public CAxeException(String msg) {
		super(msg);
	}

}
