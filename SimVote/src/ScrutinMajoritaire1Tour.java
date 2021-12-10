import java.util.HashMap;
import java.util.Vector;

public class ScrutinMajoritaire1Tour extends Scrutin{

	public ScrutinMajoritaire1Tour(Vector<Acteur> vecCandidats, Vector<Acteur> vecElecteurs) {
		super(vecCandidats, vecElecteurs);
	}

	@Override
	public HashMap<Acteur,String> simuler() {
		
		Vector<Integer> vecVotes = new Vector<Integer>();
		HashMap<Acteur,String> result = new HashMap<Acteur,String>();
		
		for(Acteur electeur : this.vecElecteurs) {
			int IndexMeilleurCandidat = -1;
			double moyenneDifferencesCandidat = 1;
			for(Acteur candidat : this.vecCandidats) {
				double moyenne = electeur.OpinionProche(candidat);
				if(moyenne < moyenneDifferencesCandidat) {
					// Cas d'un candidat meilleur :
					moyenneDifferencesCandidat = moyenne;
					IndexMeilleurCandidat = this.vecCandidats.indexOf(candidat);
				}
			}
			vecVotes.add(IndexMeilleurCandidat);
		}
		
		for(Acteur candidat : this.vecCandidats) {
			result.put(candidat, (100 * NombreIteration(vecVotes, this.vecCandidats.indexOf(candidat)) / this.vecCandidats.size() + "%"));
		}
		
		return result;
	}
	
	private int NombreIteration(Vector<Integer> vec, int intToCount) {
		int count = 0;
		for(int i : vec) {
			if(i==intToCount)
				count++;
		}
		return count;
	}

}
