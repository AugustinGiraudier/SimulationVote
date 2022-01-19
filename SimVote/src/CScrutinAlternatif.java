import java.util.Vector;

/**
 * Classe permettant la simulation d'un scrutin alternatif
 * @author Augustin Giraudier et Arthur Secher Cabot
 */
public class CScrutinAlternatif extends CScrutin {

	/**
	 * @param algo : algorithme de proximit� � utiliser
	 * @param vecCandidats : vecteur d'acteurs contenant les candidats
	 * @param vecElecteurs : vecteur d'acteurs contenant les electeurs
	 */
	public CScrutinAlternatif(EAlgoProximite algo, Vector<CActeur> vecCandidats, Vector<CActeur> vecElecteurs) {
		super(algo, vecCandidats, vecElecteurs);
	}

	@Override
	public Vector<CResultScrutin> simuler() {
		return new Vector<CResultScrutin>();
	}

}
