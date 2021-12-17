import java.util.HashMap;
import java.util.Vector;

/**
 * Classe permettant de simuler un scrutin majoritaire � 2 tours
 * @author Augustin Giraudier et Arthur Secher Cabot
 */
public class ScrutinMajoritaire2Tours extends Scrutin {

	/**
	 * @param algo : algorithme de proximit� � utiliser
	 * @param vecCandidats : vecteur d'acteurs contenant les candidats
	 * @param vecElecteurs : vecteur d'acteurs contenant les electeurs
	 */
	public ScrutinMajoritaire2Tours(AlgoProximite algo, Vector<Acteur> vecCandidats, Vector<Acteur> vecElecteurs) {
		super(algo, vecCandidats, vecElecteurs);
	}

	@Override
	public HashMap<Acteur,String> simuler() {
		return new HashMap<Acteur,String>();
	}

}
