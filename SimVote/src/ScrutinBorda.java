import java.util.HashMap;
import java.util.Vector;

/**
 * Classe permettant de simuler un scrutin Borda
 * @author Augustin Giraudier et Arthur Secher Cabot
 */
public class ScrutinBorda extends Scrutin {

	/**
	 * @param algo : algorithme de proximité à utiliser
	 * @param vecCandidats : vecteur d'acteurs contenant les candidats
	 * @param vecElecteurs : vecteur d'acteurs contenant les electeurs
	 */
	public ScrutinBorda(AlgoProximite algo, Vector<Acteur> vecCandidats, Vector<Acteur> vecElecteurs) {
		super(algo, vecCandidats, vecElecteurs);
	}

	@Override
	public HashMap<Acteur,String> simuler() {
		return new HashMap<Acteur,String>();
	}

}
