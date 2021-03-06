package PScrutins;
import java.util.Vector;

import PExceptions.CFatalException;
import PExceptions.CUnknownParameterException;
import PGeneral.CActeur;
import PGeneral.CResultScrutin;
import PGeneral.EAlgoProximite;

/**
 * Classe permettant la simulation d'un scrutin alternatif
 * @author Augustin Giraudier et Arthur Secher Cabot
 */
public class CScrutinAlternatif extends CScrutin {

	private int nbAbstention = 0;
	
	/**
	 * Classe repr?sentant un vote dans un scrutin alternatif
	 */
	private class CVoteAlter{
		public CActeur acteur;
		public double score;
	}
	
	/**
	 * @param vecCandidats vecteur d'acteurs contenant les candidats
	 * @param vecAll vecteur d'acteurs contenant les electeurs
	 */
	public CScrutinAlternatif(Vector<CActeur> vecCandidats, Vector<CActeur> vecAll) {
		super(vecCandidats, vecAll);
	}
	
	
	@Override
	public Vector<CResultScrutin> simuler(EAlgoProximite algoProximite) throws CFatalException {
		
		this.nbAbstention = 0;
		
		Vector<Vector<CVoteAlter>> urne = new Vector<Vector<CVoteAlter>>();
		
		// Cr?ation de la liste d'intentions de vote pour chaque ?l?cteur :
		for(CActeur electeur : this.vecAll) {
			
			Vector<CVoteAlter> vecVote = new Vector<CVoteAlter>();
			
			// on r?cup?re le score pour le candidat
			for(CActeur candidat : this.vecCandidats) {
				CVoteAlter vote = new CVoteAlter();
				vote.acteur = candidat;
				try {
					vote.score = electeur.getDistance(candidat, algoProximite);
				}
				catch(CUnknownParameterException e) {
					throw new CFatalException(e.getMessage());
				}
				vecVote.add(vote);
			}
			
			this.sortByScore(vecVote);
			
			// Si le candidat le plus proche est quand meme tres ?loign?, on break (abstention)
			if(vecVote.get(0).score > CActeur.SeuilDisatnceAbstention) {
				this.nbAbstention++;
				break;
			}
			
			// on l'ajoute ? l'urne
			urne.add(vecVote);
		}
		
		
		// r?cup?ration d'un vecteur de candidats
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
	
	/**
	 * G?n?re un vecteur pouvant comptabiliser les votes alternatifs pour chaque candidat
	 * @return le vecteur g?n?r?
	 */
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
	
	/**
	 * Cherche l'acteur dans la liste et incr?mente son score
	 * @param vec vecteur de votes
	 * @param actor acteur ? incr?menter
	 */
	private void IncrementWhereActor(Vector<CVoteAlter> vec, CActeur actor) {
		for(CVoteAlter VA : vec) {
			if(VA.acteur == actor) {
				VA.score++;
				return;
			}
		}
	}
	
	/**
	 * Trie le vecteur de votes par score
	 * @param vec vecteur de votes
	 */
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
	
	/**
	 * Enl?ve le candidat dernier ? ce tour de l'urne globale et remet les scores ? 0
	 * @param vec r?sultat du tour
	 * @param urne tous les r?sultats
	 * @return l'acteur supprim?
	 */
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
	
	@Override
	public double ComuteAbstention(Vector<CResultScrutin> res) {
		return nbAbstention / (float)this.vecAll.size() * 100;
	}

}
