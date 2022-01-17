import java.util.HashMap;
import java.util.Vector;

/**
 * Classe permettant de simuler un scrutin majoritaire à un tour
 * @author Augustin Giraudier et Arthur Secher Cabot
 */
public class CScrutinMajoritaire1Tour extends CScrutin{

	/**
	 * @param algo : algorithme de proximité à utiliser
	 * @param vecCandidats : vecteur d'acteurs contenant les candidats
	 * @param vecElecteurs : vecteur d'acteurs contenant les electeurs
	 */
	public CScrutinMajoritaire1Tour(EAlgoProximite algo, Vector<CActeur> vecCandidats, Vector<CActeur> vecElecteurs) {
		super(algo, vecCandidats, vecElecteurs);
	}

	@Override
	public HashMap<CActeur,String> simuler() {
		
		Vector<Integer> vecVotes = new Vector<Integer>();
		HashMap<CActeur,String> result = new HashMap<CActeur,String>();
		
		for(CActeur electeur : this.vecElecteurs) {
			int IndexMeilleurCandidat = -1;
			double moyenneDifferencesCandidat = 500; //TODO rendre égal à une variable d'abstention
			for(CActeur candidat : this.vecCandidats) {
				double moyenne;
				try {
					moyenne = electeur.getDistance(candidat, this.algoProximite);
					if(moyenne < moyenneDifferencesCandidat) {
						// Cas d'un candidat meilleur :
						moyenneDifferencesCandidat = moyenne;
						IndexMeilleurCandidat = this.vecCandidats.indexOf(candidat);
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(1);
				}
			}
			vecVotes.add(IndexMeilleurCandidat);
		}
		
		for(CActeur candidat : this.vecCandidats) {
			result.put(candidat, (100 * NombreIteration(vecVotes, this.vecCandidats.indexOf(candidat)) / this.vecCandidats.size() + "%"));
		}
		
		return result;
	}
	
	/**
	 * Compte le nombre d'occurence d'une valeur dans un vecteur
	 * @param vec : vecteur à observer
	 * @param intToCount : valeur dont on compte les occurences
	 * @return le nombre d'occurrence de 'intToCount' dans 'vec'
	 */
	private int NombreIteration(Vector<Integer> vec, int intToCount) {
		int count = 0;
		for(int i : vec) {
			if(i==intToCount)
				count++;
		}
		return count;
	}

}
