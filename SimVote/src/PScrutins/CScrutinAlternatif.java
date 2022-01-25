package PScrutins;
import java.util.Vector;

import PGeneral.CActeur;
import PGeneral.CResultScrutin;
import PGeneral.EAlgoProximite;

/**
 * Classe permettant la simulation d'un scrutin alternatif
 * @author Augustin Giraudier et Arthur Secher Cabot
 */
public class CScrutinAlternatif extends CScrutin {

	/**
	 * @param algo : algorithme de proximité à utiliser
	 * @param vecCandidats : vecteur d'acteurs contenant les candidats
	 * @param vecElecteurs : vecteur d'acteurs contenant les electeurs
	 */
	public CScrutinAlternatif(EAlgoProximite algo, Vector<CActeur> vecCandidats, Vector<CActeur> vecElecteurs) {
		super(algo, vecCandidats, vecElecteurs);
	}

	
	private class CVoteAlter{
		public CActeur acteur;
		public double score;
	}
	
	@Override
	public Vector<CResultScrutin> simuler() throws Exception {
		
		Vector<Vector<CVoteAlter>> urne = new Vector<Vector<CVoteAlter>>();
		
		// Création de la liste d'intentions de vote pour chaque élécteur :
		for(CActeur electeur : this.vecElecteurs) {
			
			Vector<CVoteAlter> vecVote = new Vector<CVoteAlter>();
			
			// on récupère le score pour le candidat
			for(CActeur candidat : this.vecCandidats) {
				CVoteAlter vote = new CVoteAlter();
				vote.acteur = candidat;
				vote.score = electeur.getDistance(candidat, this.algoProximite);
				vecVote.add(vote);
			}
			
			this.sortByScore(vecVote);
			
			// on l'ajoute à l'urne
			urne.add(vecVote);
		}
		
		
		// récupération d'un vecteur de candidats
		Vector<CVoteAlter> candidatsCount = this.getVecCandidats();
		
		Vector<CResultScrutin> result = new Vector<CResultScrutin>();
		
		// simulation des tours jusqu'au dernier candidat :
		for(int i_tour = 0; i_tour < this.vecCandidats.size() -1; i_tour ++) {
			
			for(Vector<CVoteAlter> list : urne) {
				IncrementWhereActor(candidatsCount, list.get(0).acteur);
			}
			
			CActeur looser = RemoveLastCandidatAndReset(candidatsCount, urne);
			result.add(new CResultScrutin(looser, 100, "a perdu au tour : " + Integer.toString(i_tour+1)));
			
		}
		
		result.add(new CResultScrutin(candidatsCount.get(0).acteur, 100, "Gagnant"));
		
		
		return result ;
	}
	
	private Vector<CVoteAlter> getVecCandidats(){
		Vector<CVoteAlter> vec = new Vector<CVoteAlter>();
		
		for(CActeur act : this.vecCandidats) {
			CVoteAlter VA = new CVoteAlter();
			VA.acteur = act;
			VA.score = 0;
			vec.add(VA);
		}
		return vec;
	}
	
	private void IncrementWhereActor(Vector<CVoteAlter> vec, CActeur actor) {
		for(CVoteAlter VA : vec) {
			if(VA.acteur == actor) {
				VA.score++;
				return;
			}
		}
	}
	
	private void sortByScore(Vector<CVoteAlter> vec) {
		for(int i=0 ; i < vec.size();i++) {
			for(int j = 0; j< vec.size()-1;j++) {
				if(vec.get(j).score > vec.get(j+1).score) {
					CVoteAlter temp = vec.get(j+1);
					vec.set(j+1, vec.get(j));
					vec.set(j, temp);
				}
			}
		}
	}
	
	private CActeur RemoveLastCandidatAndReset(Vector<CVoteAlter> vec, Vector<Vector<CVoteAlter>> urne) {
		int min = (int)vec.get(0).score;
		int minIndex = 0;
		
		// recherche du minimum :
		for(int i_candidat = 0; i_candidat < vec.size(); i_candidat++) {
			CVoteAlter VA = vec.get(i_candidat); 
			if(VA.score < min) {
				min = (int)VA.score;
				minIndex = i_candidat;
			}
			// reset :
			VA.score = 0;
		}
		
		CActeur actLoose = vec.get(minIndex).acteur;
		// supression du dernier :
		vec.remove(minIndex);
		
		// supression du dernier dans chaque liste :
		for(Vector<CVoteAlter> vecVote : urne) {
			for(CVoteAlter VA : vecVote) {
				if(VA.acteur == actLoose) {
					vecVote.remove(VA);
					break;
				}
			}
		}
		
		return actLoose;
		
	}

}
