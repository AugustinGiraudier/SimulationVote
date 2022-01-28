package PScrutins;
import java.util.Vector;

import PExceptions.CUnknownParameterException;
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
	 * Classe représentant un vote dans un scrutin Borda
	 */
	private class CVoteBorda{
		public CActeur candidat;
		public double score;
		
		public String toString() {
			return candidat.getNom() + " : " + Double.toString(score);
		}
	}
	
	/**
	 * @param vecCandidats vecteur d'acteurs contenant les candidats
	 * @param vecAll vecteur d'acteurs contenant les electeurs
	 * @param coefBorda nombre de vote par candidat
	 * @throws CUnknownParameterException /
	 */
	public CScrutinBorda(Vector<CActeur> vecCandidats, Vector<CActeur> vecAll, int coefBorda) throws CUnknownParameterException {
		super(vecCandidats, vecAll);
		
		if(coefBorda > vecCandidats.size())
			throw new CUnknownParameterException("Cannot instantiate Borda Ballot with coefBorda > nb electeors");
		
		this.IcoefBorda = coefBorda;
	}
	
	@Override
	public Vector<CResultScrutin> simuler(EAlgoProximite algoProximite){
		
		Vector<Vector<CVoteBorda>> urne = new Vector<Vector<CVoteBorda>>();
		
		for(CActeur electeur : this.vecAll) {
			
			// Création du vecteur des votes pour chaque candidat :
			Vector<CVoteBorda> vecVote = this.GetVoteBordaVector();
			
			// Ajout des Scores :
			for(CVoteBorda VB : vecVote) {
				try {
					VB.score = electeur.getDistance(VB.candidat, algoProximite);
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
			
			
			// Si le candidat le plus proche est quand meme tres éloigné, on break (abstention)
			if(vecVote.get(0).score > CActeur.SeuilDisatnceAbstention)
				break;
			
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
		int TotalPoints = SommeEntiers(this.IcoefBorda) * this.vecAll.size();
		for(CVoteBorda candidatVote : votesFinaux) {
			double percentage = (candidatVote.score *100.0f / (double)TotalPoints);
			result.add(new CResultScrutin(candidatVote.candidat, percentage, Double.toString(percentage) + "%"));
		}

		return result;
	}
	
	/**
	 * Donne l'index du candidat dans la liste
	 * @param vec liste des votes de type borda
	 * @param act acteur à cibler
	 * @return l'index de l'acteur ou -1 si introuvable
	 */
	private int indexOfActorinVec(Vector<CVoteBorda> vec, CActeur act) {
		for(int i = 0; i < vec.size(); i++) {
			if(vec.get(i).candidat == act)
				return i;
		}
		return -1;
	}
	
	/**
	 * Génère un vecteur pouvant comptabiliser les votes Borda pour chaque candidat
	 * @return le vecteur généré
	 */
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
	
	/**
	 * Réalise une somme des entiers bornée
	 * @param index borne suppérieure
	 * @return la somme des entiers de 0 à la borne
	 */
	private int SommeEntiers(int index) {
		int result = 0;
		for(int i_summ = 1; i_summ <= index; i_summ++) {
			result += i_summ;
		}
		return result;
	}

}
