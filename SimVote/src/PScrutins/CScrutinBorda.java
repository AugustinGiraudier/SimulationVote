package PScrutins;
import java.util.Vector;

import PGeneral.CActeur;
import PGeneral.CResultScrutin;
import PGeneral.EAlgoProximite;

/**
 * Classe permettant de simuler un scrutin Borda
 * @author Augustin Giraudier et Arthur Secher Cabot
 */
public class CScrutinBorda extends CScrutin {

	int IcoefBorda; // Nombre de personne dans la liste de chaque electeur
	
	
	/**
	 * @param algo : algorithme de proximité à utiliser
	 * @param vecCandidats : vecteur d'acteurs contenant les candidats
	 * @param vecElecteurs : vecteur d'acteurs contenant les electeurs
	 * @throws Exception 
	 */
	public CScrutinBorda(EAlgoProximite algo, Vector<CActeur> vecCandidats, Vector<CActeur> vecElecteurs, int coefBorda) throws Exception {
		super(algo, vecCandidats, vecElecteurs);
		
		if(coefBorda > vecCandidats.size())
			throw new Exception("Cannot instantiate Borda Ballot with coefBorda > nb electeors");
		
		this.IcoefBorda = coefBorda;
	}
	
	
	private class CVoteBorda{
		public CActeur candidat;
		public double score;
		
		public String toString() {
			return candidat.getNom() + " : " + Double.toString(score);
		}
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public Vector<CResultScrutin> simuler() {
		
		Vector<Vector<CVoteBorda>> urne = new Vector<Vector<CVoteBorda>>();
		
		for(CActeur electeur : this.vecElecteurs) {
			
			// Création du vecteur des votes pour chaque candidat :
			Vector<CVoteBorda> vecVote = this.GetVoteBordaVector();
			
			// Ajout des Scores :
			for(CVoteBorda VB : vecVote) {
				try {
					VB.score = electeur.getDistance(VB.candidat, this.algoProximite);
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(1);
				}
			}
			// Tris des votes :
			for(int i=0 ; i < vecVote.size();i++) {
				for(int j = 0; j< vecVote.size()-1;j++) {
					if(vecVote.get(j).score > vecVote.get(j+1).score) {
						CVoteBorda temp = vecVote.get(j+1);
						vecVote.set(j+1, vecVote.get(j));
						vecVote.set(j, temp);
					}
				}
			}
			
			// Récupération du vote final :
			Vector<CVoteBorda> finalVote = new Vector<CVoteBorda>();
			for(int iCoefBorda = 0; iCoefBorda < this.IcoefBorda; iCoefBorda++) {
				finalVote.add(vecVote.get(iCoefBorda));
			}
			
			// Ajout à l'urne :
			urne.add(finalVote);
			
		}
		
		// Traitement de tous les votes :
		Vector<CVoteBorda> votesFinaux = GetVoteBordaVector();
		
		for(Vector<CVoteBorda> vote : urne) {
			for(int iVote = 0; iVote < this.IcoefBorda; iVote++) {
				votesFinaux.get(indexOfActorinVec(votesFinaux,vote.get(iVote).candidat)).score += this.IcoefBorda - iVote;
			}
		}
		
		Vector<CResultScrutin> result = new Vector<CResultScrutin>();
		int TotalPoints = SommeEntiers(this.IcoefBorda) * this.vecElecteurs.size();
		for(CVoteBorda candidatVote : votesFinaux) {
			double percentage = (candidatVote.score *100.0f / (double)TotalPoints);
			result.add(new CResultScrutin(candidatVote.candidat, percentage, Double.toString(percentage) + "%"));
		}
		
		return result;
	}
	
	private int indexOfActorinVec(Vector<CVoteBorda> vec, CActeur act) {
		for(int i = 0; i < vec.size(); i++) {
			if(vec.get(i).candidat == act)
				return i;
		}
		return -1;
	}
	
	private Vector<CVoteBorda> GetVoteBordaVector(){
		
		// Création du vecteur des votes pour chaque candidat :
		Vector<CVoteBorda> vecVote = new Vector<CVoteBorda>();
		
		// Récupération des candidats :
		for(CActeur candidat : this.vecCandidats) {
			CVoteBorda vb = new CVoteBorda();
			vb.candidat = candidat;
			vb.score = 0;
			vecVote.add(vb);
		}
		return vecVote;
	}
	
	private int SommeEntiers(int index) {
		int result = 0;
		for(int i_summ = 1; i_summ <= index; i_summ++) {
			result += i_summ;
		}
		return result;
	}

}
