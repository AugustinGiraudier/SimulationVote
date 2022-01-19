import java.util.Vector;

/**
 * Classe permettant de simuler un scrutin par Approbation
 * @author Augustin Giraudier et Arthur Secher Cabot
 */
public class CScrutinApprobation extends CScrutin{

	/**
	 * @param algo : algorithme de proximité à utiliser
	 * @param vecCandidats : vecteur d'acteurs contenant les candidats
	 * @param vecElecteurs : vecteur d'acteurs contenant les electeurs
	 */
	public CScrutinApprobation(EAlgoProximite algo, Vector<CActeur> vecCandidats, Vector<CActeur> vecElecteurs) {
		super(algo, vecCandidats, vecElecteurs);
	}
	
	private class CVoteAppro{
		public CActeur acteur;
		public int nbrApprobation = 0;
	}

	@Override
	public Vector<CResultScrutin> simuler() throws Exception {
		
		Vector<CVoteAppro> vecVote = generateVecAppro();
		
		// Approbations de chaque electeur :
		for(CActeur electeur : this.vecElecteurs) {
			for(CVoteAppro CA : vecVote) {
				if(electeur.getDistance(CA.acteur, algoProximite) < CActeur.SeuilProximiteActeurs)
					CA.nbrApprobation++;
			}
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

}
