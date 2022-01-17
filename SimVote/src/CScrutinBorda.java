import java.util.HashMap;
import java.util.Vector;

/**
 * Classe permettant de simuler un scrutin Borda
 * @author Augustin Giraudier et Arthur Secher Cabot
 */
public class CScrutinBorda extends CScrutin {

	/**
	 * @param algo : algorithme de proximité à utiliser
	 * @param vecCandidats : vecteur d'acteurs contenant les candidats
	 * @param vecElecteurs : vecteur d'acteurs contenant les electeurs
	 */
	public CScrutinBorda(EAlgoProximite algo, Vector<CActeur> vecCandidats, Vector<CActeur> vecElecteurs) {
		super(algo, vecCandidats, vecElecteurs);
	}

	@Override
	public HashMap<CActeur,String> simuler() {
		return new HashMap<CActeur,String>();
	}

}
