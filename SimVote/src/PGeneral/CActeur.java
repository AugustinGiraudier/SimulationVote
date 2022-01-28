package PGeneral;
import java.util.Vector;

import PExceptions.CAxeException;
import PExceptions.CFatalException;
import PExceptions.CUnknownParameterException;

/**
 * classe représentant un acteur (candidat ou electeur)
 * @author Augustin Giraudier et Arthur Secher Cabot
 */
public class CActeur {
	
	/**
	 * Distance en dessous de laquelle des acteurs sont considérés proches
	 */
	public static double SeuilProximiteActeurs = -1;
	/**
	 * Distance au dessus de laquelle des acteurs sont considérés éloignés
	 */
	public static double SeuilDisatnceAbstention = -1;
	/**
	 * Nombre d'axes principaux dans le cas de l'algo de proximité à axes principaux
	 */
	public static int nbrAxesPrincipaux = -1;
	/**
	 * le pas de décalage lors de l'interaction entre deux acteurs
	 */
	public static double CoefInteraction = -1;
	
	private String nom;
	protected Vector<CAxe> vecAxes;
	
	/**
	 * @return Le nom de l'acteur
	 */
	public String getNom() {return nom;}

	/**
	 * @param _nom nom de l'acteur
	 * @param _vecAxes vecteur des axes d'opinion de l'acteur
	 * @throws CFatalException si données static non initialisées
	 */
	public CActeur(String _nom, Vector<CAxe> _vecAxes) throws CFatalException {
		if(CActeur.SeuilProximiteActeurs == -1) {
			throw new CFatalException("Please Set The static variable CActeur.SeuilProximiteActeurs before instantiate it.");
		}
		else if(CActeur.nbrAxesPrincipaux == -1) {
			throw new CFatalException("Please Set The static variable CActeur.nbrAxesPrincipaux before instantiate it.");
		}
		else if(CActeur.SeuilDisatnceAbstention == -1) {
			throw new CFatalException("Please Set The static variable CActeur.SeuilDisatnceAbstention before instantiate it.");
		}
		else if(CActeur.CoefInteraction == -1) {
			throw new CFatalException("Please Set The static variable CActeur.CoefInteraction before instantiate it.");
		}
		this.nom = _nom;
		this.vecAxes = _vecAxes;
	}
	
	/**
	 * Permet de recuperer la distance d'opinion entre deux acteurs
	 * @param a second acteur
	 * @param algo algorithme de proximité d'opinion à utiliser
	 * @return une valeur représentant la distance d'opinion
	 * @throws CUnknownParameterException en cas d'algo inconnu
	 */
	public double getDistance(CActeur a, EAlgoProximite algo) throws CUnknownParameterException {
		switch(algo) {
		case DISTANCE_VECTEUR:
			return  vectorDistance(a);
		case SOMME_DIFFERENCES:
			return  moyenneDiff(this.vecAxes, a.vecAxes);
		case SOMME_DIFFERENCES_AXES_PRINCIPAUX:
			return  moyenneDiffAxesPrincipaux(a);
		default:
			throw new CUnknownParameterException("Erreur algorithme de proximité inconnu...");
		}
	}
	
	/**
	 * @return le vecteur de double contenant les valeurs de tous les axes
	 */
	private double[] getVector() {
		double[] vec = new double[this.vecAxes.size()];
		for(int i_axe = 0; i_axe < this.vecAxes.size(); i_axe++) {
			vec[i_axe] = this.vecAxes.get(i_axe).getValeur();
		}
		return vec;
	}
			
	/**
	 * @param a second acteur
	 * @return la distance entre les vecteurs d'opinion des deux acteurs
	 */
	private double vectorDistance(CActeur a) {
		double[] vec1 = this.getVector();
		double[] vec2 = a.getVector();
		double sum = 0;
		
		//TODO verifier qu'ils ont le meme nbr d'axe
		
		for(int i_axe = 0; i_axe < this.vecAxes.size(); i_axe++) {
			sum += Math.pow(vec2[i_axe] - vec1[i_axe], 2);
		}
		
		return Math.sqrt(sum);
	}
	
	/**
	 * Récupère les axes principaux d'opinion du votant et fait la myenne des différences 
	 * avec le second acteur sur ces axes.
	 * @param a second acteur
	 * @return la moyenne des différences entre les axes principaux d'opinion
	 */
	private double moyenneDiffAxesPrincipaux(CActeur a) {
		
		Vector<CAxe> vecA = new Vector<CAxe>();
		Vector<CAxe> vecB = new Vector<CAxe>();
		
		int[] positionsMax = new int[nbrAxesPrincipaux];
		
		for(int i_axe = 0; i_axe < nbrAxesPrincipaux; i_axe++)
			positionsMax[i_axe] = 0;
		
		// On recupere les indexes de n axes principaux du votant :
		for(int i_axe = 0; i_axe < this.vecAxes.size(); i_axe++) {
			for(int i_pos = 0; i_pos < nbrAxesPrincipaux; i_pos++) {
				// Si la valeur est plus grande que la plus grande stockée
				if(this.vecAxes.get(i_axe).compareTo(a.vecAxes.get(positionsMax[i_pos])) == -1) {
					for(int i_decal = nbrAxesPrincipaux-1; i_decal > i_pos; i_decal--) {
						positionsMax[i_decal] = positionsMax[i_decal-1];
					}
					positionsMax[i_pos] = i_axe;
				}
			}
		}
		// On recupere les n axes prinipaux du votant :
		for(int i_axe : positionsMax)
			vecA.add(this.vecAxes.get(i_axe));
		
		// On recupere les memes axes ches le candidat :
		for(CAxe axe : vecA) {
			for(CAxe axeB : a.vecAxes) {
				if(axe.memeNom(axeB)) {
					vecB.add(axeB);
				}
			}
		}
		
		return moyenneDiff(vecA, vecB);
	}
	
	/**
	 * @param vecA premier vecteur
	 * @param vecB second vecteur
	 * @return la moyenne des differences entre chaque terme des vecteurs
	 */
	private double moyenneDiff(Vector<CAxe> vecA, Vector<CAxe> vecB) {
		Vector<Double> vecDiff = new Vector<Double>();
		for(CAxe axe1 : vecA) {
			for(CAxe axe2 : vecB) {
				if(axe1.memeNom(axe2)) {
					vecDiff.add(axe1.getDistance(axe2));
				}
			}
		}
		double sum = 0;
		for(Double d : vecDiff) {
			sum += d;
		}
		return (sum/vecDiff.size());
	}
	
	/**
	 * Interaction avec un autre acteur (rapprochement si proches, éloignement si éloignés)
	 * @param other second acteur
	 * @param algoProximite algo de proximité à utiliser
	 * @throws CFatalException si la distance ne peut se calculer
	 */
	public void interact(CActeur other, EAlgoProximite algoProximite) throws CFatalException {
		
		double distance;
		try {
			distance = this.getDistance(other, algoProximite);
		} catch (CUnknownParameterException e) {
			throw new CFatalException(e.getMessage());
		}
		
		//opinions proches :
		if(distance < CActeur.SeuilProximiteActeurs) {
			// on rapproche les oppignons
			this.rapprocher(other);
		}
		
		// opinions lointaines :
		else if(distance > CActeur.SeuilDisatnceAbstention) {
			// on les éloigne
			this.eloigner(other);
		}
		// ni proches ni éloignés on ne fait rien...
	}
	
	/**
	 * Rapproche l'acteur d'un candidat donné
	 * @param candidat candidat de qui s'approcher
	 * @param algoProximite algo de proximité à utiliser
	 * @throws CFatalException si changement de valeur d'axe impossible
	 */
	public void rapprocherCandidat(CActeur candidat, EAlgoProximite algoProximite) throws CFatalException {
		for(int i_axe = 0; i_axe<this.vecAxes.size(); i_axe++) {
					
			// si trop proches, on break (rapprochement innutile)
			if(Math.abs(this.vecAxes.get(i_axe).getValeur() - candidat.vecAxes.get(i_axe).getValeur()) < CActeur.CoefInteraction)
				break;
			// sinon on les compare :
			int comp = this.vecAxes.get(i_axe).compareTo(candidat.vecAxes.get(i_axe));
			
			double val = this.vecAxes.get(i_axe).getValeur();
			try {
				if(comp < 0){
					this.vecAxes.get(i_axe).setValeur(val - CActeur.CoefInteraction < 0 ? 0 : val - CActeur.CoefInteraction);
				}
				else {
					this.vecAxes.get(i_axe).setValeur(val - CActeur.CoefInteraction > 1 ? 1 : val + CActeur.CoefInteraction);
				}
			}
			catch(CAxeException e) {
				throw new CFatalException(e.getMessage());
			}
			
		}
	}
	
	/**
	 * Rapproche deux acteurs
	 * @param other second acteur
	 * @throws CFatalException si changement de valeur d'axe impossible
	 */
	private void rapprocher(CActeur other) throws CFatalException {
		for(int i_axe = 0; i_axe<this.vecAxes.size(); i_axe++) {
			
			// si trop proches, on break (rapprochement innutile)
			if(Math.abs(this.vecAxes.get(i_axe).getValeur() - other.vecAxes.get(i_axe).getValeur()) < CActeur.CoefInteraction)
				break;
			
			// sinon on les compare :
			int comp = this.vecAxes.get(i_axe).compareTo(other.vecAxes.get(i_axe));
			CActeur A, B;
			
			if(comp < 0){
				A = this;
				B = other;
			}
			else {
				A = other;
				B = this;
			}
			double valA = A.vecAxes.get(i_axe).getValeur();
			double valB = B.vecAxes.get(i_axe).getValeur();
			try {
				A.vecAxes.get(i_axe).setValeur(valA - CActeur.CoefInteraction < 0 ? 0 : valA - CActeur.CoefInteraction);
				B.vecAxes.get(i_axe).setValeur(valB + CActeur.CoefInteraction > 1 ? 1 : valB + CActeur.CoefInteraction);
			}
			catch(CAxeException e) {
				throw new CFatalException(e.getMessage());
			}
		}
	}
	
	/**
	 * Eloigne deux acteurs
	 * @param other second acteur
	 * @throws CFatalException si changement de valeur d'axe impossible
	 */
	private void eloigner(CActeur other) throws CFatalException {
		for(int i_axe = 0; i_axe<this.vecAxes.size(); i_axe++) {
			
			// on les compare :
			int comp = this.vecAxes.get(i_axe).compareTo(other.vecAxes.get(i_axe));
			CActeur A, B;
			
			if(comp < 0){
				A = this;
				B = other;
			}
			else {
				A = other;
				B = this;
			}
			double valA = A.vecAxes.get(i_axe).getValeur();
			double valB = B.vecAxes.get(i_axe).getValeur();
			try {
				A.vecAxes.get(i_axe).setValeur(valA + CActeur.CoefInteraction > 1 ? 1 : valA + CActeur.CoefInteraction);
				B.vecAxes.get(i_axe).setValeur(valB - CActeur.CoefInteraction < 0 ? 0 : valB - CActeur.CoefInteraction);
			} catch(CAxeException e) {
				throw new CFatalException(e.getMessage());
			}
		}
	}
	
	@Override
	public String toString() {
		String out =  "- - - - - - - - - - - - -\nActeur: " + this.nom + "\n\n";
		for(CAxe a : this.vecAxes) {
			out += a.toString();
		}
		return out;
	}
	
	@Override
	protected CActeur clone() throws CloneNotSupportedException {
		try {
			CActeur out;
			out = new CActeur(this.nom, this.vecAxes);
			return out;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
