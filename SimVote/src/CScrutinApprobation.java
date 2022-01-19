import java.util.Vector;

/**
 * Classe permettant de simuler un scrutin par Approbation
 * @author Augustin Giraudier et Arthur Secher Cabot
 */
public class CScrutinApprobation extends CScrutin{

	/**
	 * @param algo : algorithme de proximit� � utiliser
	 * @param vecCandidats : vecteur d'acteurs contenant les candidats
	 * @param vecElecteurs : vecteur d'acteurs contenant les electeurs
	 */
	public CScrutinApprobation(EAlgoProximite algo, Vector<CActeur> vecCandidats, Vector<CActeur> vecElecteurs) {
		super(algo, vecCandidats, vecElecteurs);
	}

	@Override
	public Vector<CResultScrutin> simuler() {
		return new Vector<CResultScrutin>();
	}

}
