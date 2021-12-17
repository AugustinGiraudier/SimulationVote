import java.util.HashMap;
import java.util.Vector;

/**
 * Classe permettant de simuler un scrutin majoritaire à un tour
 * @author Augustin Giraudier et Arthur Secher Cabot
 */
public class ScrutinMajoritaire1Tour extends Scrutin{

	/**
	 * @param algo : algorithme de proximité à utiliser
	 * @param vecCandidats : vecteur d'acteurs contenant les candidats
	 * @param vecElecteurs : vecteur d'acteurs contenant les electeurs
	 */
	public ScrutinMajoritaire1Tour(AlgoProximite algo, Vector<Acteur> vecCandidats, Vector<Acteur> vecElecteurs) {
		super(algo, vecCandidats, vecElecteurs);
	}

	@Override
	public HashMap<Acteur,String> simuler() {
		
		Vector<Integer> vecVotes = new Vector<Integer>();
		HashMap<Acteur,String> result = new HashMap<Acteur,String>();
		
		for(Acteur electeur : this.vecElecteurs) {
			int IndexMeilleurCandidat = -1;
			double moyenneDifferencesCandidat = 500; //TODO rendre égal à une variable d'abstention
			for(Acteur candidat : this.vecCandidats) {
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
		
		for(Acteur candidat : this.vecCandidats) {
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
