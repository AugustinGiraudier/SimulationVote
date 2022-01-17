import java.util.HashMap;
import java.util.Vector;

/**
 * Classe permettant de simuler un scrutin majoritaire à 2 tours
 * @author Augustin Giraudier et Arthur Secher Cabot
 */
public class CScrutinMajoritaire2Tours extends CScrutin {

	/**
	 * @param algo : algorithme de proximité à utiliser
	 * @param vecCandidats : vecteur d'acteurs contenant les candidats
	 * @param vecElecteurs : vecteur d'acteurs contenant les electeurs
	 */
	public CScrutinMajoritaire2Tours(EAlgoProximite algo, Vector<CActeur> vecCandidats, Vector<CActeur> vecElecteurs) {
		super(algo, vecCandidats, vecElecteurs);
	}

	@Override
	public HashMap<CActeur,String> simuler() {
		return new HashMap<CActeur,String>();
	}

}
