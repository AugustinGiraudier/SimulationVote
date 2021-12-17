import java.util.HashMap;
import java.util.Vector;

/**
 * classe abstraite servant de base aux diff�rents scrutins
 * @author Augustin Giraudier et Arthur Secher Cabot
 */
public abstract class Scrutin {
	
	protected Vector<Acteur> vecCandidats;
	protected Vector<Acteur> vecElecteurs;
	protected AlgoProximite algoProximite;
	
	/**
	 * @param algo : algorithme de proximit� � utiliser
	 * @param vecCandidats : vecteur d'acteurs contenant les candidats
	 * @param vecElecteurs : vecteur d'acteurs contenant les electeurs
	 */
	public Scrutin(AlgoProximite algo, Vector<Acteur> vecCandidats, Vector<Acteur> vecElecteurs) {
		this.vecCandidats = vecCandidats;
		this.vecElecteurs = vecElecteurs;
		this.algoProximite = algo;
	}

	/**
	 * @return le map 'Acteur' => 'pourcentage' de r�sultat du Scrutin
	 */
	public abstract HashMap<Acteur,String> simuler();
}
