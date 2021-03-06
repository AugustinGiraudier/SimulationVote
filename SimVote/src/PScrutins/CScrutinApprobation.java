package PScrutins;
import java.util.Vector;

import PExceptions.CFatalException;
import PExceptions.CUnknownParameterException;
import PGeneral.CActeur;
import PGeneral.CResultScrutin;
import PGeneral.EAlgoProximite;

/**
 * Classe permettant de simuler un scrutin par Approbation
 * @author Augustin Giraudier et Arthur Secher Cabot
 */
public class CScrutinApprobation extends CScrutin{

	private int nbAbstention = 0;
	
	/**
	 * Classe repr?sentant un vote dans un scrutin par approbation
	 */
	private class CVoteAppro{
		public CActeur acteur;
		public int nbrApprobation = 0;
	}
	
	/**
	 * @param vecCandidats vecteur d'acteurs contenant les candidats
	 * @param vecAll vecteur d'acteurs contenant les electeurs
	 */
	public CScrutinApprobation(Vector<CActeur> vecCandidats, Vector<CActeur> vecAll) {
		super(vecCandidats, vecAll);
	}
	
	@Override
	public Vector<CResultScrutin> simuler(EAlgoProximite algoProximite)throws CFatalException{
		
		this.nbAbstention = 0;
		
		Vector<CVoteAppro> vecVote = generateVecAppro();
		
		// Approbations de chaque electeur :
		for(CActeur electeur : this.vecAll) {
			int nbAppro = 0;
			for(CVoteAppro CA : vecVote) {
				try {
					if(electeur.getDistance(CA.acteur, algoProximite) < CActeur.SeuilProximiteActeurs) {
						CA.nbrApprobation++;
						nbAppro++;
					}
				}
				catch(CUnknownParameterException e) {
					throw new CFatalException(e.getMessage());
				}
			}
			if(nbAppro == 0)
				this.nbAbstention++;
		}
		
		// D?pouillage : 
		Vector<CResultScrutin> vecResults = new Vector<CResultScrutin>();
		for(CVoteAppro CA : vecVote)
			vecResults.add(new CResultScrutin(CA.acteur, CA.nbrApprobation, Integer.toString(CA.nbrApprobation) + " Approbation(s)"));
		
		return vecResults;
	}
	
	/**
	 * G?n?re un vecteur pouvant comptabiliser les votes par approbation pour chaque candidat
	 * @return le vecteur g?n?r?
	 */
	private Vector<CVoteAppro> generateVecAppro(){
		Vector<CVoteAppro> vec = new Vector<CVoteAppro>();
		for(CActeur act : this.vecCandidats) {
			CVoteAppro CA = new CVoteAppro();
			CA.acteur = act;
			vec.add(CA);
		}
		return vec;
	}
	
	@Override
	public double ComuteAbstention(Vector<CResultScrutin> res) {
		return nbAbstention / (float)this.vecAll.size() * 100;
	}

}
