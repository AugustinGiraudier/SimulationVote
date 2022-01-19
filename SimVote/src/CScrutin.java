import java.util.Vector;

/**
 * classe abstraite servant de base aux diff�rents scrutins
 * @author Augustin Giraudier et Arthur Secher Cabot
 */
public abstract class CScrutin {
	
	protected Vector<CActeur> vecCandidats;
	protected Vector<CActeur> vecElecteurs;
	protected EAlgoProximite algoProximite;
	
	/**
	 * @param algo : algorithme de proximit� � utiliser
	 * @param vecCandidats : vecteur d'acteurs contenant les candidats
	 * @param vecElecteurs : vecteur d'acteurs contenant les electeurs
	 */
	public CScrutin(EAlgoProximite algo, Vector<CActeur> vecCandidats, Vector<CActeur> vecElecteurs) {
		this.vecCandidats = vecCandidats;
		this.vecElecteurs = vecElecteurs;
		this.algoProximite = algo;
	}

	/**
	 * @return le r�sultat du Scrutin
	 * @throws Exception 
	 */
	public abstract Vector<CResultScrutin> simuler() throws Exception;
}
