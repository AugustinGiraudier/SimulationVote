package PScrutins;
import java.util.Vector;

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
	 * @param algo : algorithme de proximité à utiliser
	 * @param vecCandidats : vecteur d'acteurs contenant les candidats
	 * @param vecAll : vecteur d'acteurs contenant les electeurs
	 */
	public CScrutinApprobation(EAlgoProximite algo, Vector<CActeur> vecCandidats, Vector<CActeur> vecAll) {
		super(algo, vecCandidats, vecAll);
	}
	
	private class CVoteAppro{
		public CActeur acteur;
		public int nbrApprobation = 0;
	}

	@Override
	public Vector<CResultScrutin> simuler() throws Exception {
		
		this.nbAbstention = 0;
		
		Vector<CVoteAppro> vecVote = generateVecAppro();
		
		// Approbations de chaque electeur :
		for(CActeur electeur : this.vecAll) {
			int nbAppro = 0;
			for(CVoteAppro CA : vecVote) {
				if(electeur.getDistance(CA.acteur, algoProximite) < CActeur.SeuilProximiteActeurs) {
					CA.nbrApprobation++;
					nbAppro++;
				}
			}
			if(nbAppro == 0)
				this.nbAbstention++;
		}
		
		// Dépouillage : 
		Vector<CResultScrutin> vecResults = new Vector<CResultScrutin>();
		for(CVoteAppro CA : vecVote)
			vecResults.add(new CResultScrutin(CA.acteur, CA.nbrApprobation, Integer.toString(CA.nbrApprobation) + " Approbation(s)"));
		
		return vecResults;
	}
	
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
